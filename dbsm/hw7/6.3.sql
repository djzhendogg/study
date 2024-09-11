-- postgresql 15.0-1

create function checkMark() returns trigger as $checkMark$
	begin
		if new.Mark < old.Mark then
			new.Mark := old.Mark;
		end if;
		return new;
	end;
$checkMark$ language plpgsql;

create trigger PreserveMarks before update on Marks
for each row 
execute function checkMark();