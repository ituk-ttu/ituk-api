create table resource
(
    id         serial primary key,
    name       varchar(255),
    comment    text,
    url        varchar(255),
    created_at timestamptz default LOCALTIMESTAMP not null,
    updated_at timestamptz default LOCALTIMESTAMP not null,
    author_id  integer
);
