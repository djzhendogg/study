select StudentId, StudentName, GroupId
from Students
where GroupId in (
    select GroupId
    from Groups
    where GroupFacultyId in (
         select FacultyId
         from Faculties
         where FacultyName = :FacultyName
    )
);