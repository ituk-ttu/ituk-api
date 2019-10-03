create table door
(
    code varchar(255) primary key
);

create table door_permission
(
    id        serial primary key,
    door_code varchar(255) references door (code),
    user_id   integer references "user" (id)
);

create table door_permission_log_entry
(
    id          serial primary key,
    change      text,
    updated_at  timestamp,
    modified_by integer references "user" (id)
);

create trigger updated_at
    before update
    on door_permission_log_entry
    for each row
execute procedure trigger_set_timestamp();