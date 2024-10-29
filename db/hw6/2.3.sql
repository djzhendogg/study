select StudentId, StudentName, GroupName
from Students, Groups
where Students.GroupId = Groups.GroupId
and GroupFacultyId in (
    select FacultyId
    from Faculties
    where DeanId in (
		select LecturerId 
		from Lecturers
		where LecturerName = :LecturerName
	)
);