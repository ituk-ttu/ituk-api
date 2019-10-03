create table door
(
    code varchar(255) primary key
);

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

create table user_status
(
    id          serial       not null,
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
    status_id    integer                            references user_status (id)
                                                        on update cascade on delete set null,
    curriculum   varchar(255),
    iban         varchar(35),
    mentor_id    integer,
    admin        boolean     default false          not null,
    archived     boolean     default false          not null,
    created_at   timestamptz default LOCALTIMESTAMP not null,
    updated_at   timestamptz default LOCALTIMESTAMP not null
);

create table mentor
(
    user_id      integer                            not null references "user" (id),
    curriculum   varchar(255),
    text         text,
    gif          varchar(255),
    quote        text,
    enabled      boolean,
    picture_name varchar(1000),
    created_at   timestamptz default LOCALTIMESTAMP not null,
    updated_at   timestamptz default LOCALTIMESTAMP not null
);

create unique index mentors_userid_uindex
    on mentor (user_id);

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
    mentor_id             integer references mentor (user_id) on update cascade
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



create table recovery_key
(
    id         serial primary key,
    key        varchar(255),
    created_at timestamptz default LOCALTIMESTAMP not null,
    updated_at timestamptz default LOCALTIMESTAMP not null,
    user_id    integer unique references "user" (id) on update cascade
);

create table general_meeting
(
    id         serial primary key,
    name       varchar(255),
    date       timestamp,
    created_at timestamptz default LOCALTIMESTAMP not null,
    updated_at timestamptz default LOCALTIMESTAMP not null
);

create table meeting_participation
(
    user_id      integer references "user" (id),
    meeting_id   integer references general_meeting (id),
    participated boolean default false,
    mandatory    boolean default false
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
    on application
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on door_permission_log_entry
    for each row
execute procedure trigger_set_timestamp();

create trigger updated_at
    before update
    on recovery_key
    for each row
execute procedure trigger_set_timestamp();
