select ContestId, Letter
from Sessions
natural join Problems
except
select ContestId, Letter
from Runs
natural join Sessions
natural join Problems
where Accepted = 1