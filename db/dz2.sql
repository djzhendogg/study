CREATE TABLE "faculty" (
    faculty_id INTEGER PRIMARY KEY,
    faculty_name VARCHAR (255) UNIQUE NOT NULL
);
CREATE TABLE "group" (
    group_id INTEGER PRIMARY KEY,
    group_name VARCHAR (7) UNIQUE NOT NULL,
    faculty_id INTEGER,
    CONSTRAINT faculty_fk
      FOREIGN KEY(faculty_id)
        REFERENCES faculty(faculty_id)
);
CREATE TABLE "student" (
    student_id INTEGER PRIMARY KEY,
    student_first_name VARCHAR (50) NOT NULL,
    student_second_name VARCHAR (50) NOT NULL,
    group_id  INTEGER,
    CONSTRAINT group_id_fk
      FOREIGN KEY(group_id)
        REFERENCES "group"(group_id)
);
CREATE TABLE "course" (
    course_id INTEGER PRIMARY KEY,
    course_name VARCHAR (255) NOT NULL,
    term INTEGER
);
CREATE TABLE "lecturer" (
    lecturer_id INTEGER PRIMARY KEY,
    lecturer_first_name VARCHAR (50) NOT NULL,
    lecturer_second_name VARCHAR (50) NOT NULL
);
CREATE TABLE "lecturer_course" (
    lecturer_course_id INTEGER PRIMARY KEY,
    course_id INTEGER NOT NULL REFERENCES "course" (course_id),
    lecturer_id INTEGER NOT NULL REFERENCES "lecturer" (lecturer_id)
);
CREATE TABLE "grades" (
    student_id INTEGER NOT NULL REFERENCES "student" (student_id),
    lecturer_course_id INTEGER NOT NULL REFERENCES "course" (course_id),
    grade SMALLINT NOT NULL
);