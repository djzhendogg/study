update Students
set Marks = (
	select count(distinct CourseId)
	from Marks M
	where Students.StudentId = M.StudentId
)
where 1=1;