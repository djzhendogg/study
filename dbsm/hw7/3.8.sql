update Students
set Debts = (
	select count(distinct CourseId)
    from Students S
    natural join Plan P
    natural left join Marks M
    where Mark is NULL
	and Students.StudentId = S.StudentId
),
Marks = (
	select count(CourseId)
	from Marks M
	where Students.StudentId = M.StudentId
)
where 1=1;