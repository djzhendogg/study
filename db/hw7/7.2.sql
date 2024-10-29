create view AllMarks(StudentId, Marks) as
select
	Students.StudentId as StudentId,
	count(Mark) as Marks
from Students
left join (
	select StudentId, Mark
	from Marks 
	union all 
	select StudentId, Mark
	from NewMarks
) allM
on Students.StudentId = allM.StudentId
group by Students.StudentId;