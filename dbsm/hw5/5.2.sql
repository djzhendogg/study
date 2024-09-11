select StudentName, CourseName
from Students
natural join Plan
natural join Courses
left join Marks
where Mark is null;