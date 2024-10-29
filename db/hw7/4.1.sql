update Students
set Marks = (
	select count(CourseId)
	from Marks
	where StudentId = :StudentId
)
where StudentId = :StudentId;