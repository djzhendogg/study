select StudentId, StudentName, GroupName
from Students S, Groups G
where S.GroupId = G.GroupId
and S.StudentId not in (
    select StudentId
	from Marks
	where CourseId in (
	    select CourseId
		from Courses
		where CourseName = :CourseName
	)
)
and S.GroupId in (
    select GroupId
	from Plan
	where CourseId in (
	    select CourseId
		from Courses
		where CourseName = :CourseName
	)
);