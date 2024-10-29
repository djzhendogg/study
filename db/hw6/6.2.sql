select GroupName, CourseName
from Groups, Courses C1
where not exists (
	select GroupId, CourseId
	from Students, Courses C2
	where Students.GroupId = Groups.GroupId
	and C1.CourseId = C2.CourseId
	and not exists (
		select StudentId, CourseId
		from Marks
		where Marks.StudentId = Students.StudentId
		and Marks.CourseId = C2.CourseId
	)
);