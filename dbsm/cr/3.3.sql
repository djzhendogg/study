insert into Sessions (ContestId, TeamId, Start)
select ContestId, TeamId, CURRENT_TIMESTAMP as Start
from Teams
natural join Sessions
where ContestId not in (
	select ContestId
	from Sessions
	where ContestId = :ContestId;
);