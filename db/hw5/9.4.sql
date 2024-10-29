select GroupName, avg(AvgMark) as AvgAvgMark
from Groups
left join (
	select GroupId,
		   avg(cast(Mark as float)) as AvgMark
	from Students
	left join Marks
	on Students.StudentId = Marks.StudentId 
	group by Students.StudentId, GroupId
) StudentAvg 
on Groups.GroupId = StudentAvg.GroupId
group by Groups.GroupId, GroupName;