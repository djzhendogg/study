select StudentId
from Students
where StudentId not in (
	select StudentId 
	from Students, Plan, Lecturers
	where Plan.LecturerId = Lecturers.LecturerId
	and Plan.GroupId = Students.GroupId
	and LecturerName = :LecturerName
	and not exists (
		select StudentId, CourseId 
		from Marks 
		where Marks.StudentId = Students.StudentId
		and Marks.CourseId = Plan.CourseId
	)
);
