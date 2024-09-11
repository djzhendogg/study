create or replace function BuyFree(
	TargetFlightId integer,
	TargetSeatNo varchar(4)
) returns boolean
as $proc$
declare
  CurrentSeatUser integer;
begin
  if IsFlightInPast(TargetFlightId) then
    raise notice 'Flight was in the past';
    return false;
  elsif exists (select * from SeatsStatus where FlightId = TargetFlightId and SeatNo = TargetSeatNo) then
    raise notice 'Seat is already booked or sold';
	return false;
  end if;
  insert into SeatsStatus (UserId, FlightId, SeatNo, SeatStatus, BookedTime) values
    (null, TargetFlightId, TargetSeatNo, 'sold', null);
  return true;
end;
$proc$
language plpgsql;