update Marks
set Mark = (
    select Mark
    from NewMarks NM
    where Marks.StudentId = NM.StudentId
    and Marks.CourseId = NM.CourseId
)
where exists (
	select StudentId, CourseId
	from NewMarks NM
	where NM.StudentId = Marks.StudentId
	and NM.CourseId = Marks.CourseId
);