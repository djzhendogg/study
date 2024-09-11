select StudentName, CourseName
from Students
natural join (
  select distinct GroupId, CourseName
  from Plan
  natural join Courses
) PlanWithNames;