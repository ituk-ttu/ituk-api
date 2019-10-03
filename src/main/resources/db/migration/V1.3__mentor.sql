create table mentor
(
    user_id      integer unique                     not null references "user" (id),
    curriculum   varchar(255),
    text         text,
    gif          varchar(255),
    quote        text,
    enabled      boolean,
    picture_name varchar(1000),
    created_at   timestamptz default LOCALTIMESTAMP not null,
    updated_at   timestamptz default LOCALTIMESTAMP not null
);

create trigger updated_at
    before update
    on mentor
    for each row
execute procedure trigger_set_timestamp();

