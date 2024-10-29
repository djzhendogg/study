select StudentName, CourseName
from Students 
natural join (
	select GroupId, CourseId
	from Plan
	natural join Lecturers
	where LecturerName = :LecturerName
) natural join Courses;