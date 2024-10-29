select StudentName, CourseName
from Students 
natural join (
    select distinct GroupId, CourseId
    from Plan
        natural join Lecturers
        inner join Faculties
        on Faculties.FacultyId = Lecturers.LecturerFacultyId
    where FacultyName = :FacultyName
) ByFN natural join Courses;