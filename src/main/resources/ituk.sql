create table door
(
    code varchar(255) primary key
);

create table resources
(
    id         serial primary key,
    name       varchar(255),
    comment    text,
    url        varchar(255),
    created_at timestamp not null,
    updated_at timestamp not null,
    author_id  integer
);

create table user_status
(
    status_id   serial       not null,
    status_name varchar(255) not null,
    description varchar(255)
);

create table "user"
(
    id           serial primary key,
    first_name   varchar(255),
    last_name    varchar(255),
    email        varchar(255),
    card_number  varchar(255),
    telegram     varchar(255),
    password     varchar(255),
    student_code varchar(255),
    status_id    integer
        constraint users_user_status_status_id_fk
            references user_status
            on update cascade on delete set null,
    curriculum   varchar(255),
    iban         varchar(35),
    mentor_id    integer,
    admin        boolean   default false          not null,
    archived     boolean   default false          not null,
    created_at   timestamp default LOCALTIMESTAMP not null,
    updated_at   timestamp default LOCALTIMESTAMP not null
);

create table mentor
(
    user_id    integer                          not null
        constraint mentors_users_id_fk
            references "user",
    curriculum varchar(255),
    text       text,
    gif        varchar(255),
    quote      text,
    enabled    boolean,
    created_at timestamp default LOCALTIMESTAMP not null,
    updated_at timestamp default LOCALTIMESTAMP not null
);

create unique index mentors_userid_uindex
    on mentor (user_id);

create table applications
(
    id                    serial primary key,
    first_name            varchar(255),
    last_name             varchar(255),
    personal_code         varchar(255),
    email                 varchar(255),
    student_code          varchar(255),
    curriculum            varchar(255),
    mentor_selection_code varchar(255),
    created_at            timestamp default LOCALTIMESTAMP not null,
    updated_at            timestamp default LOCALTIMESTAMP not null,
    processed_by_id       integer
        constraint processed_by_id
            unique
        constraint applications_users_id_fk
            references "user",
    mentor_id             integer
        constraint mentor_id
            unique
        constraint applications_mentors_userid_fk
            references mentor (user_id)
);


create table door_permissions
(
    id        serial primary key,
    door_code varchar(255)
        constraint door_permissions_door_code_fk
            references door,
    user_id   integer
        constraint door_permissions_users_id_fk
            references "user"
);


create table door_permission_log_entry
(
    id          serial primary key,
    updated_at  timestamp,
    change      text,
    modified_by integer
        constraint door_permission_log_entry_user_id_fk
            references "user"
);



create table recovery_keys
(
    id         serial primary key,
    key        varchar(255),
    created_at timestamp not null,
    updated_at timestamp not null,
    user_id    integer
        constraint user_id
            unique
        constraint recovery_keys_users_id_fk
            references "user"
            on update cascade
);

create table general_meeting
(
    id         serial primary key,
    name       varchar(255),
    date       timestamp,
    created_at timestamp not null,
    updated_at timestamp not null
);

create function trigger_set_timestamp() returns trigger
    language plpgsql
as
$$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$;

alter function trigger_set_timestamp() owner to ilja;

create trigger updated_at
    before update
    on "user"
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on mentor
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on applications
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on door_permission_log_entry
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on recovery_keys
    for each row
execute procedure trigger_set_timestamp();


