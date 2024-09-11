create or replace function FlightsStatistics(
	TargetUserId integer,
	Pass text
) returns table(
	FlightId integer,
	IsBookPossible boolean,
	IsBuyPossible boolean,
	FreeSeatsAmount integer,
	BookedSeatsAmount integer,
	BoughtSeatsAmount integer
)
as $proc$
declare
  declare FId integer;
  FCursor cursor for
    select f.FlightId from Flights as f;
begin
  create temp table ResultTable(
	FlightId integer,
	IsBookPossible boolean,
	IsBuyPossible boolean,
	FreeSeatsAmount integer,
	BookedSeatsAmount integer,
	BoughtSeatsAmount integer
  ) on commit drop;
  open FCursor;
  loop
	fetch FCursor into FId;
	exit when not found;
	insert into ResultTable (FlightId, IsBookPossible, IsBuyPossible, FreeSeatsAmount, BookedSeatsAmount, BoughtSeatsAmount)
	select FId, stat.IsBookPossible, stat.IsBuyPossible, stat.FreeSeatsAmount, stat.BookedSeatsAmount, stat.BoughtSeatsAmount
	from FlightStat(TargetUserId, Pass, FId) as stat;
  end loop;
  return query
    select * from ResultTable;
end;
$proc$
language plpgsql;