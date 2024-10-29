select StudentName, CourseName
from Students 
natural join (
    select StudentId, CourseId
    from Students natural join Plan
    except
    select StudentId, CourseId
    from Students natural join Marks
    where Mark = 4 or Mark = 5
) NoFourFive natural join Courses;