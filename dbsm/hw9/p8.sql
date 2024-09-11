-- Храним номера в виде 001A
-- Поэтому отсортировав номера как строки мы отсортируем их сначала по ряду, а потом по номеру в ряду
create or replace procedure CompressSeats(TargetFlightId integer)
as $proc$
declare
  TargetPlaneId integer;
  CurrentSeatNo varchar(4);
  MigrateSeatNo varchar(4);
  FullSeatsCursor refcursor;
  BookedSeatsCursor cursor for
    select * from SeatsStatus
	where FlightId = TargetFlightId
	and SeatStatus = 'booked'
	and BookedTime > CURRENT_TIMESTAMP;
  BoughtSeatsCursor cursor for
    select * from SeatsStatus
	where FlightId = TargetFlightId
	and SeatStatus = 'sold';
  CurrentRecord record;
begin
  select PlaneId into TargetPlaneId
  from Flights
  where FlightId = TargetFlightId;
  
  open FullSeatsCursor for
    select SeatNo
	from Seats
	where PlaneId = TargetPlaneId
	order by SeatNo;
  
  create temp table DuplicateSeatsStatus as (
    select * from SeatsStatus
  ) with no data;
  
  open BoughtSeatsCursor;
  loop
    fetch BoughtSeatsCursor into CurrentRecord;
	exit when not found;
	fetch FullSeatsCursor into MigrateSeatNo;
	
	insert into DuplicateSeatsStatus (UserId, FlightId, SeatNo, SeatStatus, BookedTime) values
	(CurrentRecord.UserId, CurrentRecord.FlightId, MigrateSeatNo, CurrentRecord.SeatStatus, CurrentRecord.BookedTime);
	
	delete from SeatsStatus
	where FlightId = TargetFlightId
	and SeatNo = CurrentRecord.SeatNo;
  end loop;
  
  open BookedSeatsCursor;
  loop
    fetch BookedSeatsCursor into CurrentRecord;
	exit when not found;
	fetch FullSeatsCursor into MigrateSeatNo;
	
	insert into DuplicateSeatsStatus (UserId, FlightId, SeatNo, SeatStatus, BookedTime) values
	(CurrentRecord.UserId, CurrentRecord.FlightId, MigrateSeatNo, CurrentRecord.SeatStatus, CurrentRecord.BookedTime);
	
	delete from SeatsStatus
	where FlightId = TargetFlightId
	and SeatNo = CurrentRecord.SeatNo;
  end loop;
  
  insert into SeatsStatus
  select * from DuplicateSeatsStatus;
  
  drop table if exists DuplicateSeatsStatus;
end;
$proc$
language plpgsql;