select StudentId, StudentName, GroupName
from Students
natural join Groups
inner join Faculties
on Groups.GroupFacultyId=Faculties.FacultyId
inner join Lecturers
on Lecturers.LecturerId=Faculties.FacultyId
where LecturerName = :LecturerName;