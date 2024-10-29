select GroupName, avg(cast(Mark as float)) as AvgMark
from Groups
left join Students
on Groups.GroupId  = Students.GroupId 
left join Marks
on Students.StudentId = Marks.StudentId
group by Groups.GroupId, GroupName;