update Students
set Marks = Marks + (
	select count(CourseId)
	from NewMarks
	where Students.StudentId = NewMarks.StudentId
);