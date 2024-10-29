update Marks
set Mark = (
	select Mark
    from NewMarks
    where Marks.StudentId = NewMarks.StudentId
    and Marks.CourseId = NewMarks.CourseId
)
where exists (
	select StudentId, CourseId
	from NewMarks
	where NewMarks.StudentId = Marks.StudentId
	and NewMarks.CourseId = Marks.CourseId
);