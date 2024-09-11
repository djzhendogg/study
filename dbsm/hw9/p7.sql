create or replace function FlightStat(
	TargetUserId integer,
	Pass text,
	TargetFlightId integer
) returns table(
	IsBookPossible boolean,
	IsBuyPossible boolean,
	FreeSeatsAmount integer,
	BookedSeatsAmount integer,
	BoughtSeatsAmount integer
)
as $proc$
declare
  IsCurrentFlightInPast boolean := IsFlightInPast(TargetFlightId);
  TargetPlaneId integer;
  AllSeatsAmount integer;
  HasSeatsBookedByUser boolean;
  IsBookPossible boolean;
  IsBuyPossible boolean;
  FreeSeatsAmount integer;
  BookedSeatsAmount integer;
  BoughtSeatsAmount integer;
begin
  if not CheckPassword(TargetUserId, Pass) then
    raise notice 'Incorrect password or user';
    return;
  end if;
  
  select PlaneId into TargetPlaneId
  from Flights
  where FlightId = TargetFlightId;
  
  select count(*) into AllSeatsAmount
  from Seats
  where PlaneId = TargetPlaneId;
  
  select count(*) into BookedSeatsAmount
  from SeatsStatus
  where FlightId = TargetFlightId
  and SeatStatus = 'booked'
  and BookedTime > CURRENT_TIMESTAMP;
  
  select count(*) into BoughtSeatsAmount
  from SeatsStatus
  where FlightId = TargetFlightId
  and SeatStatus = 'sold';
  
  FreeSeatsAmount := AllSeatsAmount - BoughtSeatsAmount - BookedSeatsAmount;
  HasSeatsBookedByUser := exists (
    select * from SeatsStatus
    where FlightId = TargetFlightId
    and SeatStatus = 'booked'
	and UserId = TargetUserId
  );
  
  IsBookPossible := not IsCurrentFlightInPast and FreeSeatsAmount > 0;
  IsBuyPossible := not IsCurrentFlightInPast and (FreeSeatsAmount > 0 or HasSeatsBookedByUser);
  
  return query (
	select *
    from (values (IsBookPossible, IsBuyPossible, FreeSeatsAmount, BookedSeatsAmount,BoughtSeatsAmount))
	as t(IsBookPossible, IsBuyPossible, FreeSeatsAmount, BookedSeatsAmount, BoughtSeatsAmount)
  );
end;
$proc$
language plpgsql;