select avg(Mark)
from Plan
natural join Students
natural join Marks
natural join Groups
where GroupFacultyId in (
	select FacultyId from Faculties
	where FacultyName = :FacultyName
)
and CourseId in (
	select CourseId from Courses
	where CourseName = :CourseName
);