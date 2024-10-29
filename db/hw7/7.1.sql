create view StudentMarks(StudentId, Marks) as
select
	Students.StudentId as StudentId,
	count(Mark) as Marks
from Students
left join Marks
on Students.StudentId = Marks.StudentId
group by Students.StudentId;