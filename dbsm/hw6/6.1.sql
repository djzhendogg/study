select GroupId, CourseId
from Courses C1, Groups G
where not exists (
	select S.GroupId, C.CourseId
	from Students S, Courses C
	where G.GroupId = S.GroupId
	and C1.CourseId = C.CourseId
	and not exists (
		select StudentId, CourseId
		from Marks M
		where S.StudentId = M.StudentId
		and C.CourseId = M.CourseId
	)
);