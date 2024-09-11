create view Debts(StudentId, Debts) as
select S.StudentId, count(distinct CourseId) as Debts
from Students S
natural join Plan P
natural left join Marks M
where Mark is NULL
group by S.StudentId;