-- postgresql 15.0-1

create function noExtraMarksOnDeleteCourse() returns trigger as $noExtraMarksOnDelete$
	begin
		delete from Marks M
		where M.CourseId = old.CourseId;
		delete from Plan P
		where P.CourseId = old.CourseId;
		return old;
	end;
$noExtraMarksOnDelete$ language plpgsql;

create function noExtraMarksOnInsertMark() returns trigger as $noExtraMarksOnInsertMark$
	begin
		if exists (
			select *
			from Students
			natural join Plan
			where StudentId = new.StudentId
			and CourseId = new.CourseId
		) then
			return new;
		elsif (TG_OP = 'UPDATE') then
			return old;
		end if;
		return null;
	end;
$noExtraMarksOnInsertMark$ language plpgsql;

create trigger NoExctraMarksOnDeleteCourse before delete on Courses
for each row 
execute function noExtraMarksOnDeleteCourse();

create trigger NoExtraMarksOnInsertMark before insert on Marks
for each row 
execute function noExtraMarksOnInsertMark();