select StudentName, CourseName
from Students S, Courses C
where exists (
	select StudentId, CourseId
	from Plan P
	where S.GroupId = P.GroupId
	and C.CourseId = P.CourseId
	and not exists (
		select StudentId, CourseId
		from Marks M
		where S.StudentId = M.StudentId
		and C.CourseId = M.CourseId
		and M.Mark > 2
	)
);