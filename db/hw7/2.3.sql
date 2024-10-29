delete from Students
where StudentId in (
    select Students.StudentId 
    from Students
    natural join Plan
    left join Marks
	on Students.GroupId = Plan.GroupId
	and Plan.CourseId = Marks.CourseId
	and Students.StudentId = Marks.StudentId
    where Mark is NULL
	and Students.GroupId in (
		select GroupId from
		Groups, Faculties
		where Groups.GroupFacultyId = Faculties.FacultyId
		and FacultyName = :FacultyName
	)
    group by Students.StudentId
	having count(Plan.CourseId) >= 2
);