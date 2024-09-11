select GroupName, SumMark
from Groups
left join (
	select GroupId, sum(StudentSumMark) as SumMark
	from Students
	left join (
		select StudentId, sum(Mark) as StudentSumMark
		from Marks
		group by StudentId
	) StudentSumMarks
	on StudentSumMarks.StudentId = Students.StudentId
	group by GroupId
) GroupSumMarks
on GroupSumMarks.GroupId = Groups.GroupId;