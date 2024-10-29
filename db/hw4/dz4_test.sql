insert into course
	(course_id, course_name) values
	(1, 'Матан'),
	(2, 'БД'),
	(3, 'Java'),
	(4, 'Физика');
insert into faculty_dean
	(faculty_dean_id) values
	(1),
	(2),
	(3),
	(4);
insert into group_faculty
	(group_faculty_id, group_faculty_name, faculty_dean_id) values
	(1, 'M_IPKN', 1),
	(2, 'Z_IPKN', 1),
	(3, 'B_HBK', 2),
	(4, 'G_HBK', 2);
insert into study_group
	(group_id, group_name, group_faculty_id) values
	(1, 'M40', 1),
	(2, 'Z41', 2),
	(3, 'B40', 3),
	(4, 'B41', 3);
insert into lecturer_faculty
	(lecturer_faculty_id, lecturer_faculty_name, faculty_dean_id) values
	(1, 'ВасяИПКН', 3),
	(2, 'ПетяИПКН', 3),
	(3, 'ДаняКТ', 3),
	(4, 'ВиноградХБК', 2);
insert into lecturer
	(lecturer_id, lecturer_name, lecturer_faculty_id) values
	(1, 'Вася', 1),
	(2, 'Вася', 1),
	(3, 'Валя', 4),
	(4, 'Денчик', 3);
insert into lecturer_course_group
	(group_id, course_id, lecturer_id) values
	(1, 1, 1),
	(1, 3, 2),
	(1, 2, 2),
	(2, 2, 2),
	(2, 1, 4),
	(4, 3, 2);
insert into student
	(student_id, student_name, group_id) values
	(1, 'Иван', 1),
	(2, 'Иван', 1),
	(3, 'Иван', 2),
	(4, 'Петр', 2),
	(5, 'Петр', 4);
insert into marks
	(student_id, course_id, mark) values
	(1, 1, 2),
	(1, 2, 5),
	(2, 1, 2),
	(2, 2, 5),
	(3, 2, 2),
	(5, 3, 5);