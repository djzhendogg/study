create view StudentMarks(StudentId, Marks) as
select S.StudentId,
(
	select count(CourseId)
	from Marks M
	where S.StudentId = M.StudentId
)
from Students S;