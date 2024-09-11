create or replace function BuyReserved(
	UserId integer,
	Pass text,
	TargetFlightId integer,
	TargetSeatNo varchar(4)
) returns boolean
as $proc$
declare
  CurrentSeatUser integer;
  CurrentSeatStatus seatStatus;
begin
  if not CheckPassword(UserId, Pass) then
    raise notice 'Incorrect password or user';
    return false;
  elsif IsFlightInPast(TargetFlightId) then
    raise notice 'Flight was in the past';
    return false;
  end if;
  CurrentSeatUser := SeatUser(TargetFlightId, TargetSeatNo);
  if (CurrentSeatUser is null or UserId != CurrentSeatUser) then
    raise notice 'Seat is not booked by current user';
    return false;
  end if;
  select SeatStatus into CurrentSeatStatus
  from SeatsStatus
  where FlightId = TargetFlightId
  and SeatNo = TargetSeatNo;
  if CurrentSeatStatus = 'sold' then
    raise notice 'Seat already sold to that user';
    return false;
  end if;
  update SeatsStatus
  set SeatStatus = 'sold'
  where FlightId = TargetFlightId
  and SeatNo = TargetSeatNo;
  return true;
end;
$proc$
language plpgsql;