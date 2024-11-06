-- 1. Полная информация о курсе, по которому наибольшее колличество долгов после закрытия зачетки.
select CourseId, CourseName
from Courses
natural join Marks
where Mark < 3
group by CourseId, CourseName
order by count(Mark) desc
limit 1;

-- 2. Информация о группе, курсе и лекторе, с максимальным id лекторакоторый ведет курсы.
select CourseName, GroupName, LecturerName
from plan 
natural join Lecturers
natural join Groups
natural join Courses
where LecturerId = (
	select max(LecturerId)
	from Plan
);


-- 3. Имя и группа студентов, пренадлежащих группам в диапазоне.
select StudentName, GroupName
from Students
natural join Groups
where GroupId > 3
and GroupId < 9;