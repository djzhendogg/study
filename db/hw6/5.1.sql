select distinct Students.StudentId
from Students, Plan, Marks, Lecturers
where Students.GroupId = Plan.GroupId
and Students.StudentId = Marks.StudentId
and Plan.CourseId = Marks.CourseId
and Lecturers.LecturerId = Plan.LecturerId
and LecturerName = :LecturerName;