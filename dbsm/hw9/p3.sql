create or replace function ExtendReserve(
	UserId integer,
	Pass text,
	ReserveFlightId integer,
	ReserveSeatNo varchar(4)
) returns boolean
as $proc$
declare
  CurrentSeatUser integer;
begin
  if not CheckPassword(UserId, Pass) then
    raise notice 'Incorrect password or user';
    return false;
  elsif IsFlightInPast(ReserveFlightId) then
    raise notice 'Flight was in the past';
    return false;
  end if;
  CurrentSeatUser := SeatUser(ReserveFlightId, ReserveSeatNo);
  if (CurrentSeatUser is null or UserId != CurrentSeatUser) then
    raise notice 'Seat is not booked by current user';
    return false;
  end if;
  update SeatsStatus
  set BookedTime = BookedTime + make_interval(days => 3)
  where FlightId = ReserveFlightId
  and SeatNo = ReserveSeatNo;
  return true;
end;
$proc$
language plpgsql;