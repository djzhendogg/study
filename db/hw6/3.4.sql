select StudentName, CourseName
from (
	select StudentId, CourseId
	from Students, Plan, Groups, Lecturers
	where Students.GroupId = Plan.GroupId
	and Students.GroupId = Groups.GroupId
	and Plan.GroupId = Groups.GroupId
	and Lecturers.LecturerId = Plan.LecturerId
	and GroupFacultyId != LecturerFacultyId
) DfferentFaculty, Students, Courses
where DfferentFaculty.StudentId = Students.StudentId
and DfferentFaculty.CourseId = Courses.CourseId;