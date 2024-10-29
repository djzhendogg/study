select distinct Students.StudentId, coalesce(Total, 0) as Total, coalesce(Passed, 0) as Passed, coalesce(Failed, 0) as Failed
from Students 
left join (
select distinct Students.StudentId,
       count(Plan.CourseId) as Total,
       sum(
            CASE WHEN Mark is not null
            THEN 1 
            ELSE 0 
            END
        ) as Passed,
        sum(case when Mark is null then 1 else 0 end) as Failed 
from Students 
natural join Plan
left join Marks 
on Marks.StudentId = Students.StudentId
and Plan.CourseId = Marks.CourseId
group by Students.StudentId
) SHasC on Students.StudentId = SHasC.StudentId;