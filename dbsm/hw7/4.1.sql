insert into Marks (StudentId, CourseId, Mark)
select StudentId, CourseId, Mark
from NewMarks NM
where not exists (
	select StudentId, CourseId
	from Marks M
	where NM.StudentId = M.StudentId
	and NM.CourseId = M.CourseId
);