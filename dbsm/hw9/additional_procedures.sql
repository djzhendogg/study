create or replace function HashPassword(Pass text)
returns text
as $proc$
begin
  return crypt(Pass, gen_salt('md5'));
end;
$proc$
language plpgsql;

create or replace function CheckPassword(PassedUserId integer, Pass text)
returns boolean
as $proc$
declare
HashedPassedPassword text;
UserHashedPassword text;
begin
  select HashedPassword into UserHashedPassword
  from Users
  where UserId = PassedUserId;
  return UserHashedPassword = crypt(Pass, UserHashedPassword);
end;
$proc$
language plpgsql;

create or replace function IsFlightInPast(TargetFlightId integer)
returns boolean
as $proc$
begin
  return CURRENT_TIMESTAMP > (select FlightTime from Flights where FlightId = TargetFlightId);
end;
$proc$
language plpgsql;

create or replace function SeatUser(TargetFlightId integer, TargetSeatNo varchar(4))
returns integer
as $proc$
declare
  SeatUserId integer;
begin
  select UserId into SeatUserId
  from SeatsStatus
  where FlightId = TargetFlightId
  and SeatNo = TargetSeatNo;
  
  return SeatUserId;
end;
$proc$
language plpgsql;