delete from Students
where StudentId in (
    select StudentId
    from Students S
    natural join Plan P
    natural left join Marks M
    where Mark is NULL
	group by StudentId
	having count(distinct CourseId) > 1
);