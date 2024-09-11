select TeamId, count(Letter) as Solved
from (
	select distinct TeamId, Letter
	from Runs
	natural join Sessions
	where Accepted = 1
) as AcceptedRuns 
group by TeamId;