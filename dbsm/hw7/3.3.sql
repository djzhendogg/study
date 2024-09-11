update Students
set Marks = Marks + (
	select count(CourseId)
	from NewMarks NM
	where Students.StudentId = NM.StudentId
)
where 1=1;