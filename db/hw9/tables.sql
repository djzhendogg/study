create table Flights(
	    FlightId int not null PRIMARY KEY,
        FlightTime timestamp not null,
        PlaneId int not null,
        SellAllowed bool not null DEFAULT true, 
        ReservationAllowed bool not null DEFAULT true
);

create table Users(
	UserId int not null primary key,
    pswhash varchar(100) not null
);

create table Seats(
    PlaneId int not null,
    SeatNo varchar(4) not null,
    CONSTRAINT seats_pk PRIMARY KEY (PlaneId, SeatNo)
);

create table BookingDetails(
	
);
 --------------------------------------------------------
CREATE EXTENSION pgcrypto;

--
create or replace function RegisterUser(InUserId int, InPass text)
RETURNS BOOLEAN 
AS $$
declare
    sucssesful BOOLEAN := false;
begin
	if exists (Select 1 from Users where UserId = InUserId) then
		RETURN FALSE;
	else
		begin
			insert into Users(userid, pswhash) 
			values (InUserId, crypt(InPass, gen_salt('md5')));
			sucssesful := TRUE;
		end;
		return true;
	end if;
	return sucssesful;
end;
$$ language plpgsql SECURITY definer;

-- Проверка пароля
create or replace function CorrectPasword(InUserId int, InPass text)
RETURNS BOOLEAN AS $$
declare
    sucssesful BOOLEAN := false;
    RealPass text;
begin
	if not exists (Select 1 from Users where UserId = InUserId) then
		return false;
	else
		begin
			select pswhash into RealPass
			from Users 
			where UserId = InUserId;
			sucssesful := RealPass = crypt(InPass, RealPass);
		end;
	end if;
	return sucssesful;
end;
$$ language plpgsql SECURITY definer;

create or replace procedure ManageFlight(InUserId int, InPass text, InFlightId int, InSellAllowed bool, InReservationAllowed bool)
AS $$
begin
	if not exists (select 1 from Flights where FlightId = InFlightId) then
		RAISE SQLSTATE '22016';
	end if;
	if CorrectPasword(InUserId, InPass) then
		begin
			update Flights 
            set SellAllowed = InSellAllowed 
            where FlightId = InFlightId;

			update Flights 
            set ReservationAllowed = InReservationAllowed 
            where FlightId = InFlightId;
		end;
	else
		RAISE SQLSTATE '22012';
	end if;
end;
$$ language plpgsql SECURITY definer;
----------------------------------------------------
-- автоматическое отключение бронирования за трое суток до начала рейса
create or replace function DisableReservation()
returns trigger as $$
begin 
	if new.FlightTime - interval '3 days' <= NOW() then
		new.ReservationAllowed := false;
	end if;
	return new;
end;
$$ language plpgsql SECURITY definer;

-- тригер отключения бронирования
create or replace trigger DisableReservationTrigger
before update on Flights
for each row 
when (
    old.SellAllowed != new.SellAllowed 
    or old.ReservationAllowed != new.ReservationAllowed 
    or old.FlightTime != new.FlightTime
)
execute procedure DisableReservation();

-- автоматическое отключение возможности покупки
create or replace function DisableSell()
returns trigger as $$
begin 
	if new.FlightTime - interval '3 hours' <= NOW() then
		new.SellAllowed := false;
	end if;
	return new;
end;
$$ language plpgsql SECURITY definer;

-- тригер отключения покупки
create or replace trigger DisableSellTrigger
before update on Flights
for each row 
when (
    old.SellAllowed != new.SellAllowed
    or old.FlightTime != new.FlightTime
)
execute procedure DisableSell();
----------------------------------------------------------
create or replace function ReservationIsAllowed(InFlightId int)
returns BOOLEAN as $$
declare 
 allowes BOOLEAN;
begin 
	Select ReservationAllowed into allowes
	from Flights where FlightId = InFlightId;
	if found then
		return allowes;
	else
		return false;
	end if;
end;
$$ language plpgsql;

-- возвращает свободные места на данном рейсе
create or replace function FreeSeats(InFlightId int)
returns setof varchar(4) as $$
begin
	delete from bookingdetails 
    where Sold = false 
    and Exparation < Now();
	return query
		select SeatNo 
        from Seats 
        natural join Flights 
        where FlightId = InFlightId
		except
		select SeatNo 
        from BookingDetails 
        natural join Flights 
        where FlightId = InFlightId;
end;
$$ language plpgsql;


create or replace function Reserve(
    InUserId int, 
    InPass text, 
    InFlightId int, 
    InSeatNo varchar(4)
)
returns bool as $$
declare
	sucssesful BOOLEAN := false;
	InPlaneId int;
begin
	if not CorrectPasword(InUserId, InPass) then
		return false;
	end if;
	if not (
            select ReservationAllowed 
            from flights 
            where flightid = InFlightId
        ) then
		return fasle;
	end if;
	if not exists (
            select 1 
            from FreeSeats(InFlightId) 
            where freeseats = InSeatNo
        ) then
		return false;
	end if;
	select PlaneId into InPlaneId 
    from Flights 
    where FlightId = InFlightId; 

	insert into bookingdetails(UserId, FlightId, PlaneId, SeatNo, Exparation, Sold) 
    values(InUserId, InFlightId, InPlaneId, InSeatNo, NOW() + interval '1 day', false);
	sucssesful := true;
	return sucssesful;
end;
$$ language plpgsql;

create or replace function ExtendReservation(
    InUserId int, 
    InPass text, 
    InFlightId int, 
    InSeatNo varchar(4)
)
returns bool as $$
declare
	InPlaneId int;
begin
	select PlaneId 
    into InPlaneId 
    from Flights 
    where FlightId = InFlightId;

	if not CorrectPasword(InUserId, InPass) then
		return false;
	end if;
    if not (
        select ReservationAllowed 
        from flights 
        where flightid = InFlightId
    ) then
		return fasle;
	end if;
	if not exists (
        select 1 from bookingdetails 
		where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false
    ) then
		return false;
	end if;
	if (
        select Exparation 
        from bookingdetails 
		where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false
    ) < NOW() then
		delete from bookingdetails 
        where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false;
		return false;
	end if;
	update bookingdetails 
    set Exparation = NOW() + interval '1 day' 
    where UserId = InUserId 
    and FlightId = InFlightId 
	and PlaneId = InPlaneId 
    and SeatNo = InSeatNo 
    and Sold = false;
	return true;
end;
$$ language plpgsql;

create or replace function BuyFree(InFlightId int, InSeatNo varchar(4))
returns bool as $$
declare
	InPlaneId int;
begin
	select PlaneId into InPlaneId 
    from Flights 
    where FlightId = InFlightId;
	if not (
        select SellAllowed 
        from flights 
        where flightid = InFlightId
    ) then
		return fasle;
	end if;
	if not exists (
        select 1 from FreeSeats(InFlightId) 
        where freeseats = InSeatNo
    ) then
		return false;
	end if;
	insert into bookingdetails(UserId, FlightId, PlaneId, SeatNo, Exparation, Sold)
	values(null, InFlightId, InPlaneId, InSeatNo, NOW(), true);
	return true;
end;
$$ language plpgsql;

create or replace function BuyReserved(
    InUserId int, 
    InPass text, 
    InFlightId int, 
    InSeatNo varchar(4)
)
returns bool as $$
declare
	InPlaneId int;
begin
	if not CorrectPasword(InUserId, InPass) then
		return false;
	end if;
    if not (
        select SellAllowed 
        from flights 
        where flightid = InFlightId
    ) then
		return fasle;
	end if;

	select PlaneId into InPlaneId
    from Flights 
    where FlightId = InFlightId;

	if not exists (
        select 1 from bookingdetails 
		where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false
    ) then
		return false;
	end if;
	if (
        select Exparation 
        from bookingdetails 
		where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false
    ) < NOW() then
		delete from bookingdetails 
        where UserId = InUserId 
        and FlightId = InFlightId 
		and PlaneId = InPlaneId 
        and SeatNo = InSeatNo 
        and Sold = false;
		return false;
	end if;
	update bookingdetails 
    set Sold = true 
    where UserId = InUserId 
    and FlightId = InFlightId 
	and PlaneId = InPlaneId 
    and SeatNo = InSeatNo 
    and Sold = false;
	return true;
end;
$$ language plpgsql;
----------------------------------------------
create or replace function FlightStat(InUserId int, InPass text, InFlightId int)
returns table(
	FlightIndex int,
	ReservationIsAllowed bool,
	SellIsAllowed bool,
	FreeSeatsNumber int,
	BookedSeatsNumber int,
	SoldSeatsNumber int
) as $$
declare
	InPlaneId int;
	ReservationAllowedRes bool := false;
	SellAllowedRes bool := false;
	FreeSeatsNumberRes int;
	BookedSeatsNumberRes int;
	SoldSeatsNumberRes int;
begin
delete from bookingdetails 
where Sold = false 
and Exparation < Now();

if not CorrectPasword(InUserId, InPass) then
	RAISE SQLSTATE '22012';
end if;
select PlaneId into InPlaneId 
from Flights 
where FlightId = InFlightId;

select count(*) into FreeSeatsNumberRes 
from FreeSeats(InFlightId);

select count(*) into BookedSeatsNumberRes 
from bookingdetails 
where FlightId = InFlightId 
and sold = false;

select count(*) into SoldSeatsNumberRes 
from bookingdetails 
where FlightId = InFlightId 
and sold = true;

if (
    select ReservationAllowed 
    from flights 
    where flightid = InFlightId
) then
	if FreeSeatsNumberRes > 0 then
		ReservationAllowedRes := true;
	end if;
end if;

if (
    select SellAllowed 
    from flights 
    where flightid = InFlightId
) then
	if FreeSeatsNumberRes > 0 then
		ReservationAllowedRes := true;
	end if;
end if;

return query 
	select InFlightId as FlightIndex, 
	ReservationAllowedRes as ReservationIsAllowed, 
	SellAllowedRes as SellIsAllowed, 
	FreeSeatsNumberRes as FreeSeatsNumber, 
	BookedSeatsNumberRes as BookedSeatsNumber, 
	SoldSeatsNumberRes as SoldSeatsNumber;
end;
$$ language plpgsql;


create or replace function FlightsStatistics(InUserId int, InPass text) 
returns table(
	FlightIndex int,
	ReservationIsAllowed bool,
	SellIsAllowed bool,
	FreeSeatsNumber int,
	BookedSeatsNumber int,
	SoldSeatsNumber int
)
as $$
declare
	CurrentFlight integer;
	FlightCursor cursor for
    	select FlightId from Flights;
begin
    delete from bookingdetails 
    where Sold = false 
    and Exparation < Now();

	if not CorrectPasword(InUserId, InPass) then
		RAISE SQLSTATE '22012';
	end if;
	create temp table Res(
		FlightIndex int,
		ReservationIsAllowed bool,
		SellIsAllowed bool,
		FreeSeatsNumber int,
		BookedSeatsNumber int,
		SoldSeatsNumber int
	) on commit drop;

	open FlightCursor;
	loop
		fetch FlightCursor into CurrentFlight;
		exit when not found;
		insert into Res (FlightIndex, ReservationIsAllowed, SellIsAllowed, FreeSeatsNumber, BookedSeatsNumber, SoldSeatsNumber)
		select St.FlightIndex, St.ReservationIsAllowed, St.SellIsAllowed, St.FreeSeatsNumber, St.BookedSeatsNumber, St.SoldSeatsNumber
		from FlightStat(InUserId, InPass, CurrentFlight) as St;
	end loop;
	return query
    	select * from Res;
end;
$$
language plpgsql;
----------------------------------------------
create or replace procedure CompressSeats(InUserId int, InPass text, InFlightId int) 
as $$
declare
	cp record;
	InPlaneId int;
    SeatsCursor refcursor;
	NewSeat varchar(4);
	BookedCursor cursor for
    	select * from bookingdetails 
        where FlightId = InFlightId 
        and sold = false;
	SoldCursor cursor for
    	select * from bookingdetails 
        where FlightId = InFlightId 
        and sold = true;
begin
	if not CorrectPasword(InUserId, InPass) then
		RAISE SQLSTATE '22012';
	end if;
	delete from bookingdetails 
    where Sold = false 
    and Exparation < Now();

	create temp table Res(
		UserId int,
		FlightId int,
		PlaneId int,
		SeatNo varchar(4),
		Exparation timestamp,
		Sold bool
	);

	select PlaneId into InPlaneId 
    from Flights 
    where FlightId = InFlightId;
	open SeatsCursor for
    	select SeatNo 
        from Seats 
        where PlaneId = InPlaneId 
        order by SeatNo desc;

	open SoldCursor;
	loop
		fetch SoldCursor into cp;
		exit when not found;
		fetch SeatsCursor into NewSeat;
		insert into Res(UserId, FlightId, PlaneId, SeatNo, Exparation, Sold)
			values (cp.UserId, cp.FlightId, cp.PlaneId, NewSeat, cp.Exparation, cp.Sold);

		delete from bookingdetails 
        where FlightId = cp.FlightId 
        and PlaneId = cp.PlaneId 
        and SeatNo = cp.SeatNo;
	end loop;

	open BookedCursor;
	loop
		fetch BookedCursor into cp;
		exit when not found;
		fetch SeatsCursor into NewSeat;
		insert into Res(UserId, FlightId, PlaneId, SeatNo, Exparation, Sold)
			values (cp.UserId, cp.FlightId, cp.PlaneId, NewSeat, cp.Exparation, cp.Sold);
		delete from bookingdetails 
        where FlightId = cp.FlightId 
        and PlaneId = cp.PlaneId 
        and SeatNo = cp.SeatNo;
	end loop;
	
	insert into bookingdetails(UserId, FlightId, PlaneId, SeatNo, Exparation, Sold) 
        select * from Res;
	drop table if exists Res;
end;
$$
language plpgsql;
-----------------------------------------------------------------------------
insert into bookingdetails values(1, 2, 2, '123B', NOW(), false);
insert into seats values(2, '123B'), (2, '13B');
insert into flights values(2, '2024-01-08 04:05:06.000', 2);
select BuyReserved(1, 'pampam1', 2, '13B');
select CorrectPasword(1, 'pampam1');
insert into bookingdetails values(1, 2, 2, '123A', NOW() - interval '1 day', false);