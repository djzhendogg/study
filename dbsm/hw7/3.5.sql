update Students
set Debts = (
	select count(distinct CourseId)
    from Students S
    natural join Plan P
    natural left join Marks M
    where Mark is NULL
	and StudentId = :StudentId
)
where StudentId = :StudentId;