select StudentId, StudentName, GroupName
from Students S, Groups G
where S.GroupId = G.GroupId
and S.StudentId not in (
    select StudentId
	from Marks
	where CourseId = :CourseId
);