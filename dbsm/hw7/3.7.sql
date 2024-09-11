update Students
set Debts = (
	select count(distinct CourseId)
    from Students S
    natural join Plan P
    natural left join Marks M
    where Mark is NULL
	and Students.StudentId = S.StudentId
)
where GroupId in (
	select GroupId
	from Groups
	where GroupName = :GroupName
);