select StudentId, StudentName, GroupId
from Students natural join Plan natural join Lecturers natural join Marks
where Mark = :Mark
and LecturerName = :LecturerName;