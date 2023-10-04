create table grades
(
    id         int primary key,
    text       text                        not null,
    created timestamp without time zone not null,
    updated timestamp without time zone not null
);
