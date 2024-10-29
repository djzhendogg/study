update Students
set Marks = (
	select count(CourseId)
	from Marks
	where Students.StudentId = Marks.StudentId
)
where GroupId in (
	select GroupId 
	from Groups, Faculties
	where Groups.GroupFacultyId = Faculties.FacultyId
	and FacultyName = :FacultyName
);