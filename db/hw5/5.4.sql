select StudentName, CourseName
from Students 
natural join (
    select GroupId, CourseId
    from Plan
    natural join Lecturers
    where LecturerName = :LecturerName
) ByLN natural join Courses;