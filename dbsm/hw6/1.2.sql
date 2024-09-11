select StudentId, StudentName, GroupId
from Students
where GroupId in (
    select GroupId
	from Groups
	where GroupName = :GroupName
);