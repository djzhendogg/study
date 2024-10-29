insert into "faculty"
    (faculty_id, faculty_name) values
    (1, 'ИПКН'),
    (2, 'ХБК'),
    (3, 'ЦХИ'),
    (4, 'ФТМИ');
insert into "group"
    (group_id, group_name, faculty_id) values
    (1, 'M4138', 1),
    (2, 'A3142', 2),
    (3, 'B4138', 1);
insert into "student"
    (student_id, student_first_name, student_second_name,group_id) values
    (1, 'Иван', 'Иванов', 1),
    (2, 'Иван', 'Петров', 1),
    (3, 'Петр', 'Петров', 2),
    (4, 'Андрей', 'Петров', 2);
insert into "course"
    (course_id, course_name, term) values
    (1, 'Математический анализ', 1),
    (2, 'Математический анализ', 2);
insert into "course"
    (course_id, course_name) values
    (3, 'Английский язык');
insert into "lecturer"
    (lecturer_id, lecturer_first_name, lecturer_second_name) values
    (1, 'Иван','Иванов'),
    (2, 'Иван','Еегор'),
    (3, 'Иван','Еегор'),
    (4, 'Петр','Еегор');
insert into "lecturer_course"
    (lecturer_course_id, course_id, lecturer_id) VALUES
    (1, 1, 1),
    (2, 1, 2),
    (3, 2, 3);
insert into "grades"
    (student_id, lecturer_course_id, grade) values
    (1, 1, 2),
    (3, 1, 5),
    (1, 2, 2);