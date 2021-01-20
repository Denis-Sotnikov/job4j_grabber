create table grabber(
    id serial primary key,
    name text,
    text text,
    link text unique,
    date_of_created timestamp
);

alter table grabber alter column date_of_created type timestamp;