select StudentName, sum(Mark) AS SumMark
from Students
left join Marks
on Students.StudentId = Marks.StudentId 
group by Students.StudentId, Students.StudentName;