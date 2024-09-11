create view AllMarks(StudentId, Marks) as
select S.StudentId,
(
	select count(CourseId)
	from Marks M
	where S.StudentId = M.StudentId
) + (
	select count(CourseId)
	from NewMarks NM
	where S.StudentId = NM.StudentId
)
from Students S;