select StudentId, StudentName, GroupName
from Students, Groups
where Students.GroupId = Groups.GroupId
and Students.GroupId in (
    select GroupId from Plan
    where CourseId in (
		select CourseId 
		from Courses
		where CourseName = :CourseName
	)
)
and StudentId not in (
	select StudentId 
	from Marks
	where CourseId in (
        select CourseId 
        from Courses
		where CourseName = :CourseName
	)
);