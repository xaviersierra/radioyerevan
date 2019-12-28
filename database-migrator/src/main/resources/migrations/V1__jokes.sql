create table if not exists jokes (
    id serial primary key,
    question text not null,
    answer text not null,
    times_used int not null default 0
);
