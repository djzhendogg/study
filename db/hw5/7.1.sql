select distinct Marks.CourseId, GroupId
from Marks
cross join Students
except 
select CourseId, GroupId from (
	select distinct Students.StudentId, CourseId, GroupId
	from Marks
	cross join Students
	except 
	select distinct StudentId, CourseId, GroupId
	from Marks
	natural join Students
) BigDel;