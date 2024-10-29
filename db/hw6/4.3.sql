select StudentName, CourseName
from (
    select distinct StudentId, CourseId
    from Plan, Students
    where Students.GroupId = Plan.GroupId
    and not exists (
        select StudentId, CourseId
        from Marks
        where Students.StudentId = Marks.StudentId
        and Plan.CourseId = CourseId
        and Mark > 2
    )
) Arrears, Students, Courses
where Arrears.StudentId = Students.StudentId
and Arrears.CourseId = Courses.CourseId;
