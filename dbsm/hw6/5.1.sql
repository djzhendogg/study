select distinct S.StudentId
from Students S, Marks M, Plan P
where S.GroupId = P.GroupId
and S.StudentId = M.StudentId
and M.CourseId = P.CourseId
and P.LecturerId in (
    select LecturerId
	from Lecturers
	where LecturerName = :LecturerName
);