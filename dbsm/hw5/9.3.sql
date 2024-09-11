select GroupName, AvgMark
from Groups
left join (
	select GroupId, sum(cast(StudentSumMark as float)) / sum(CountSumMark) as AvgMark
	from Students
	left join (
		select StudentId, sum(Mark) as StudentSumMark, count(Mark) as CountSumMark
		from Marks
		group by StudentId
	) StudentSumMarks
	on StudentSumMarks.StudentId = Students.StudentId
	group by GroupId
) GroupAvgMarks
on GroupAvgMarks.GroupId = Groups.GroupId;