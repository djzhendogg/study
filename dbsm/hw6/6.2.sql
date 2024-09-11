select GroupName, CourseName
from Courses C2, Groups G1
where exists (
	select GroupId, CourseId
	from Courses C1, Groups G
	where C2.CourseId = C1.CourseId
	and G1.GroupId = G.GroupId
	and not exists (
		select GroupId, CourseId
		from Students S, Courses C
		where C1.CourseId = C.CourseId
		and G.GroupId = S.GroupId
		and not exists (
			select StudentId, CourseId
			from Marks M
			where S.StudentId = M.StudentId
			and C.CourseId = M.CourseId
		)
	)
);