create table user_status
(
    id          serial primary key,
    status_name varchar(255) unique not null,
    description varchar(1000)
);

create table "user"
(
    id           serial primary key,
    first_name   varchar(255),
    last_name    varchar(255),
    email        varchar(255),
    card_number  varchar(255),
    password     varchar(255),
    student_code varchar(255),
    status_id    integer                             references user_status (id)
                                                         on update cascade on delete set null,
    curriculum   varchar(255),
    iban         varchar(35),
    role         varchar(255) default 'MEMBER'       not null,
    archived     boolean      default false          not null,
    created_at   timestamptz  default LOCALTIMESTAMP not null,
    updated_at   timestamptz  default LOCALTIMESTAMP not null
);

create trigger updated_at
    before update
    on "user"
    for each row
execute procedure trigger_set_timestamp();


create table recovery_key
(
    id         serial primary key,
    key        varchar(255),
    created_at timestamptz default LOCALTIMESTAMP not null,
    updated_at timestamptz default LOCALTIMESTAMP not null,
    user_id    integer unique references "user" (id) on update cascade
);


create trigger updated_at
    before update
    on recovery_key
    for each row
execute procedure trigger_set_timestamp();
