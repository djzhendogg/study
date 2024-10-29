select StudentId, StudentName, GroupId
from Students
natural join Groups
inner join Faculties
on Groups.GroupFacultyId=Faculties.FacultyId
where FacultyName = :FacultyName
except
select StudentId, StudentName, GroupId
from Students 
natural join Marks 
natural join Courses
where CourseName = :CourseName;