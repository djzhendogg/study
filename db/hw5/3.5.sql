select StudentId, StudentName, Plan.GroupId
from Students
natural join Marks
inner join Plan
on Plan.CourseId = Marks.CourseId
where Mark = :Mark
and LecturerId = :LecturerId;