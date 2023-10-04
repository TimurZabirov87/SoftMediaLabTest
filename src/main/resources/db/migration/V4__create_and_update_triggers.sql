create function trigger_set_timestamps_for_updated_entry() returns trigger
    language plpgsql
as
$$
begin
    new.created = old.created;
    new.updated = now();
    return new;
end;
$$;

create function trigger_set_timestamps_for_new_entry() returns trigger
    language plpgsql
as
$$
declare
    now_timestamp timestamp := now();
begin
    new.created = now_timestamp;
    new.updated = now_timestamp;
    return new;
end;
$$;

create trigger set_timestamp_update
    before update
    on grades
    for each row
execute procedure trigger_set_timestamps_for_updated_entry();

create trigger set_timestamp_insert
    before insert
    on grades
    for each row
execute procedure trigger_set_timestamps_for_new_entry();

create trigger set_timestamp_update
    before update
    on students
    for each row
execute procedure trigger_set_timestamps_for_updated_entry();

create trigger set_timestamp_insert
    before insert
    on students
    for each row
execute procedure trigger_set_timestamps_for_new_entry();


create trigger set_timestamp_update
    before update
    on student_grades
    for each row
execute procedure trigger_set_timestamps_for_updated_entry();

create trigger set_timestamp_insert
    before insert
    on student_grades
    for each row
execute procedure trigger_set_timestamps_for_new_entry();