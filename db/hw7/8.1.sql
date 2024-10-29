-- postgresql 16
create or replace function no_extra_marks()
returns trigger as $$
begin
  if not exists (
    select 1
    from plan
    where GroupId = new.GroupId and CourseId = new.CourseId
  ) then
    raise exception 'Is ExtraMrak.';
  end if;
  return new;
end;
$$ language plpgsql;

create trigger NoExtraMarks
before insert on Marks
for each row
execute procedure no_extra_marks();