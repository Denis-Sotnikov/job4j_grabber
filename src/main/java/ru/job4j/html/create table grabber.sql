create table grabber(
    id serial primary key,
    name text,
    text text,
    link text unique,
    date_of_created date
)