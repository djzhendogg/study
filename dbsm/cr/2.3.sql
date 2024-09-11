select distinct TeamId
from Sessions
natural join Runs
where Accepted = 1
and ContestId = :ContestId;