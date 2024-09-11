update Students
set Marks = (
	select count(CourseId)
	from Marks M
	where Students.StudentId = M.StudentId
)
where 1=1;