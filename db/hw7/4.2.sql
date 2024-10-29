update Students
set Marks = (
	select count(CourseId)
	from Marks
	where Students.StudentId = Marks.StudentId
);