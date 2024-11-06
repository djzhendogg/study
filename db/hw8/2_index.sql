create index Courses_cn_cid on Courses using btree (CourseName, CourseId);

create index Faculties_fn_fid on Faculties using btree (FacultyName, FacultyId);

create unique index Marks_cid_sid_m on Marks using btree btree (CourseId, StudentId, Mark);

create index Students_sid_gid on Students using btree (StudentId, GroupId);

create index Groups_fid_gid on Groups using btree (GroupFacultyId, GroupId);

create unique index Plan_sid_cid on Plan using btree (GroupId, CourseId);