select StudentName, CourseName
from Students 
natural join (
    select distinct StudentId, CourseId
    from Students natural join Plan
    except
    select distinct StudentId, CourseId
    from Students natural join Marks
) NoMark natural join Courses;