select GroupName, AvgAvgMark
from Groups
left join (
	select GroupId, avg(StudentAvgMark) as AvgAvgMark
	from Students
	left join (
		select StudentId, avg(cast(Mark as float)) as StudentAvgMark
		from Marks 
		group by StudentId
	) StudentAvgMarks
	on StudentAvgMarks.StudentId = Students.StudentId
	group by GroupId
) GroupAvgMarks
on GroupAvgMarks.GroupId = Groups.GroupId;