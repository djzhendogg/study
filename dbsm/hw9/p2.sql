create or replace function Reserve(
	UserId integer,
	Pass text,
	ReserveFlightId integer,
	ReserveSeatNo varchar(4)
) returns boolean
as $proc$
begin
  if not CheckPassword(UserId, Pass) then
    raise notice 'Incorrect password or user';
    return false;
  elsif IsFlightInPast(ReserveFlightId) then
    raise notice 'Flight was in the past';
    return false;
  elsif exists (select * from SeatsStatus where SeatNo = ReserveSeatNo and FlightId = ReserveFlightId) then 
    raise notice 'Selected seat is booked or sold';
    return false;
  end if;
  
  insert into SeatsStatus (UserId, FlightId, SeatNo, SeatStatus, BookedTime) values
    (UserId, ReserveFlightId, ReserveSeatNo, 'booked', CURRENT_TIMESTAMP + make_interval(days => 3));
  return true;
end;
$proc$
language plpgsql;