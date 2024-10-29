CREATE TABLE course (
    course_id INTEGER PRIMARY KEY,
	course_name VARCHAR (255) NOT NULL
);
CREATE TABLE faculty_dean (
    faculty_dean_id INTEGER PRIMARY KEY
);
CREATE TABLE group_faculty (
    group_faculty_id INTEGER PRIMARY KEY,
    group_faculty_name VARCHAR (255) UNIQUE NOT NULL,
    faculty_dean_id INTEGER NOT NULL REFERENCES faculty_dean (faculty_dean_id)
);
CREATE TABLE study_group (
    group_id INTEGER PRIMARY KEY,
    group_name VARCHAR (50) UNIQUE NOT NULL,
    group_faculty_id INTEGER NOT NULL REFERENCES group_faculty (group_faculty_id)
);

CREATE TABLE lecturer_faculty (
    lecturer_faculty_id INTEGER PRIMARY KEY,
    lecturer_faculty_name VARCHAR (255) UNIQUE NOT NULL,
    faculty_dean_id INTEGER NOT NULL REFERENCES faculty_dean (faculty_dean_id)
);
CREATE TABLE lecturer (
    lecturer_id INTEGER PRIMARY KEY,
    lecturer_name VARCHAR (255) NOT NULL,
    lecturer_faculty_id INTEGER NOT NULL REFERENCES lecturer_faculty (lecturer_faculty_id)
);
CREATE TABLE lecturer_course_group (
    group_id INTEGER NOT NULL REFERENCES study_group (group_id),
  	course_id INTEGER NOT NULL REFERENCES course (course_id),
    lecturer_id INTEGER NOT NULL REFERENCES lecturer (lecturer_id)
);

CREATE TABLE student (
    student_id INTEGER PRIMARY KEY,
    student_name VARCHAR (255) NOT NULL,
    group_id INTEGER NOT NULL REFERENCES study_group (group_id)
);
CREATE TABLE marks (
	student_id INTEGER NOT NULL REFERENCES student (student_id),
	course_id INTEGER NOT NULL REFERENCES course (course_id),
  	mark INTEGER NOT NULL
);