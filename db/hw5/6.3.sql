select distinct StudentId from Marks
except
select StudentId from (
    select StudentId, AllCourseLecturer.CourseId
    from Marks
    cross join (
		select distinct CourseId
		from Plan
		natural join Lecturers
		where LecturerName = :LecturerName
	) AllCourseLecturer 
	except 
	select StudentId, CourseId from Marks
) BadSID;