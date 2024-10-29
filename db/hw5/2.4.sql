select StudentId, StudentName, GroupName
from Students
natural join Groups
inner join Faculties
on Groups.GroupFacultyId=Faculties.FacultyId
where FacultyName = :FacultyName;