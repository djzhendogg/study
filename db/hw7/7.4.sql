create view StudentDebts(StudentId, Debts) as
select 
	Students.StudentId as StudentId,
	count(distinct Plan.CourseId) - count(distinct Marks.CourseId) as Debts
from Students
left join Plan
on Plan.GroupId = Students.GroupId
left join Marks
on Students.StudentId = Marks.StudentId
and Plan.CourseId = Marks.CourseId
group by Students.StudentId;