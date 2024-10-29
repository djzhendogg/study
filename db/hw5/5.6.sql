select StudentName, CourseName
from Students 
natural join (
	select distinct GroupId, CourseId
	from Plan 
	natural join Lecturers 
	natural join Groups
	where LecturerFacultyId != GroupFacultyId 
) FacultyDiff natural join Courses;