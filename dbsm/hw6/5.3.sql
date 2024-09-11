select StudentId
from Students S
where not exists (
	select distinct S.StudentId
	from Plan P
	where not exists (
		select StudentId, CourseId
		from Marks M
		where M.StudentId = S.StudentId
		and M.CourseId = P.CourseId
	)
	and P.LecturerId in (
		select LecturerId
		from Lecturers
		where LecturerName = :LecturerName
	)
);