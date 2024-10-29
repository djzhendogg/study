select StudentName, CourseName
from (
	select distinct Students.StudentId, Marks.CourseId
	from Students, Marks, Plan
	where Students.StudentId = Marks.StudentId
	and Students.GroupId = Plan.GroupId
    and Marks.CourseId= Plan.CourseId
	and Mark <=2
) Arrears, Students, Courses
where Arrears.StudentId = Students.StudentId
and Arrears.CourseId = Courses.CourseId;