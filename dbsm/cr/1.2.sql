select RunId, SessionId, Letter, SubmitTime, Accepted 
from Sessions
natural join Runs
where ContestId = :ContestId
and TeamId = :TeamId;