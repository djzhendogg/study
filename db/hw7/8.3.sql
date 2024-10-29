-- postgresql 16
create or replace function preserve_marks()
returns trigger as $$
begin
    if old.Mark is not null and new.Mark < old.Mark then
        raise notice 'Trying to decrease mark.';
        return old;
    end if;
    return new;
end;
$$ language plpgsql;

create trigger PreserveMarks
before update on Marks
for each row
execute procedure preserve_marks();