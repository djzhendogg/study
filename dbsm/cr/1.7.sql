select SessionId
from Sessions
where SessionId not in (
	select SessionId from (
		select SessionId, Letter
		from Sessions
		natural join Problems
		except
		select SessionId, Letter
		from Runs
	) as Unsolved
);