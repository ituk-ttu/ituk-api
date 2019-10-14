create table application
(
    id                    serial primary key,
    first_name            varchar(255),
    last_name             varchar(255),
    personal_code         varchar(255),
    email                 varchar(255),
    student_code          varchar(255),
    curriculum            varchar(255),
    mentor_selection_code varchar(255),
    created_at            timestamptz default LOCALTIMESTAMP not null,
    updated_at            timestamptz default LOCALTIMESTAMP not null,
    processed_by_id       integer references "user" (id) on update cascade,
    mentor_id             integer references mentor_profile (user_id) on update cascade
);

create trigger updated_at
    before update
    on application
    for each row
execute procedure trigger_set_timestamp();