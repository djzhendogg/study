select StudentId
from Students
where StudentId not in (
	select Students.StudentId
	from Marks, Plan, Students
	where Students.GroupId = Plan.GroupId
	and Students.StudentId = Marks.StudentId
	and Marks.CourseId = Plan.CourseId
	and LecturerId in (
		select LecturerId
		from Lecturers
		where LecturerName = :LecturerName
	)
);