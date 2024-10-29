delete from Students
where StudentId not in (
    select Students.StudentId 
    from Students
    natural join Plan
    left join Marks
	on Students.GroupId = Plan.GroupId
	and Plan.CourseId = Marks.CourseId
	and Students.StudentId = Marks.StudentId
    where Mark is NULL
	group by Students.StudentId
	having count(distinct Plan.CourseId) > 3
);