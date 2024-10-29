select Students.StudentId, StudentName, Students.GroupId
from Students
left join Marks 
on Students.StudentId = Marks.StudentId
left join Plan 
on Marks.CourseId = Plan.CourseId
left join Lecturers 
on Plan.LecturerId = Lecturers.LecturerId
where Mark = :Mark
and LecturerName = :LecturerName;