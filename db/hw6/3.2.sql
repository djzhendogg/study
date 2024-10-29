select StudentName, CourseName
from Students, Courses
where exists (
	select StudentId, CourseId
	from Plan
	where Students.GroupId = Plan.GroupId
	and Courses.CourseId = Plan.CourseId
	union
	select StudentId, CourseId
	from Marks
	where Students.StudentId = Marks.StudentId
	and Courses.CourseId = Marks.CourseId
);