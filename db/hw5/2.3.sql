select StudentId, StudentName, GroupName 
from Students natural join Groups
where GroupName = :GroupName;