select StudentId, StudentName, s.GroupId
from Students s
natural join Marks
inner join Plan
on Plan.CourseId = Marks.CourseId
where Mark = :Mark
and LecturerId = :LecturerId;