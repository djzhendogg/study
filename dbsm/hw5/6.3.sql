select StudentId from Marks
except
select StudentId
from (
	select StudentId, CourseId from Marks
	cross join (
		select CourseId
		from Plan
		natural join Lecturers
		where LecturerName = :LecturerName
	) CoursesOfLecturer
	except
	select StudentId, CourseId from Marks
) NonFullMarksStudents;