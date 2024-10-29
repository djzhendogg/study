create view Debts(StudentId, Debts) as
select 
	Students.StudentId as StudentId,
	count(distinct Plan.CourseId) as Debts
from Students
natural join Plan
left join Marks
on Students.StudentId = Marks.StudentId 
and Plan.GroupId = Students.GroupId 
and Plan.CourseId = Marks.CourseId
where Mark is null
group by Students.StudentId;