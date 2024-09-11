select TeamName
from Teams
where TeamId in (
	select distinct TeamId
	from Sessions
	natural join Runs
	where Accepted = 1
	and Letter = :Letter
	and ContestId = :ContestId
);