select StudentId, StudentName, GroupId
from Students
inner join Marks
on Students.StudentId = Marks.StudentId
inner join Plan 
on Plan.CourseId = Marks.CourseId
inner join Lecturers
on Plan.LecturerId = Lecturers.LecturerId
where Mark = :Mark
and LecturerName = :LecturerName;