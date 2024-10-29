select StudentId from (
select distinct StudentId, GroupId
from Marks
cross join (
	select DISTINCT GroupId
	from PLAN
	natural join Lecturers
	where LecturerName = :LecturerName
) GroupIdByLecturerName1
except
select StudentId, GroupId from (
	select distinct StudentId, GroupIdByLecturerName2.CourseId, GroupId
	from Marks
	cross join (
		select distinct CourseId, GroupId
		from Plan
		natural join Lecturers
		where LecturerName = :LecturerName
	) GroupIdByLecturerName2
	except 
        select StudentId, CourseId, GroupId
        from Marks
        natural join (
			select distinct CourseId, GroupId
			from Plan
			natural join Lecturers
			where LecturerName = :LecturerName
		) GroupIdByLecturerName3
	) BigDel
) RealSt natural join Students;