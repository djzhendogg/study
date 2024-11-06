-- 1
create index Marks_m_cid on Marks using btree (Mark, CourseId);

-- 2
create unique index Plan_lid_b on Plan using btree (LecturerId);

-- 3
create unique index Groups_gid_b on Groups using btree (GroupId);