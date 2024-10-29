update Students
set Debts = (
	select count(distinct Plan.CourseId)
    from Students S
    natural join Plan
    left join Marks
    on S.StudentId = Marks.StudentId 
    and Plan.GroupId = S.GroupId 
    and Plan.CourseId = Marks.CourseId
    where Mark is null
	and Students.StudentId = S.StudentId
),
Marks = (
	select count(CourseId)
	from Marks
	where Students.StudentId = Marks.StudentId
);