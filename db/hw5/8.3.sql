select GroupName, sum(Mark) AS SumMark
from Groups 
left join Students
on Students.GroupId = Groups.GroupId 
left join Marks
on Students.StudentId = Marks.StudentId 
group by Groups.GroupId, GroupName;