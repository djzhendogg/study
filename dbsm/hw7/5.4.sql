create view StudentDebts(StudentId, Debts) as
select S.StudentId, (
    select count(distinct P.CourseId)
    from Students ST
    natural left join Plan P
	where S.StudentId = ST.StudentId
    and not exists (
        select CourseId
        from Marks M
        where M.StudentId = ST.StudentId
        and M.CourseId = P.CourseId
		and Mark is not null
    )
)
from Students S;