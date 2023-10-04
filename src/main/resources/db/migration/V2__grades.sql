create table grades
(
    id         int primary key,
    text       text                        not null,
    created timestamp without time zone not null,
    updated timestamp without time zone not null
);

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

INSERT INTO grades (id, text, created, updated)
VALUES (2, 'неуд', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'уд', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'хор', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'отл', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
