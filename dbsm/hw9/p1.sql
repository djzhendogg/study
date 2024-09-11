create or replace function FreeSeats(SearchFlightId integer)
returns table(SeatNo varchar(4))
as $proc$
declare 
SearchPlaneId integer;
CurrendDate timestamp := CURRENT_TIMESTAMP;
begin
  select f.PlaneId into SearchPlaneId
  from Flights as f
  where f.FlightId = SearchFlightId
  and f.FlightTime > CurrendDate;
  if SearchPlaneId is null then
    return;
  else
    return query
      select s.SeatNo from Seats 
	  where s.PlaneId = SearchPlaneId
	  and s.SeatNo not in (
		select ss.SeatNo from SeatsStatus as ss
		where ss.FlightId = SearchFlightId
	  );
  end if;
end;
$proc$
language plpgsql;