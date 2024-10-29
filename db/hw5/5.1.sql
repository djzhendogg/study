select StudentName, CourseName
from Students
left join Plan
on Students.GroupId = Plan.GroupId
natural join Courses;