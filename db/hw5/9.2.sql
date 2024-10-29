select StudentName, avg(cast(Mark as float)) as AvgMark
from Students
left join Marks
on Students.StudentId = Marks.StudentId 
group by Students.StudentId, Students.StudentName;