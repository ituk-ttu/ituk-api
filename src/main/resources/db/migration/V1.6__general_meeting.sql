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
    id serial primary key,
    user_id      integer references "user" (id),
    meeting_id   integer references general_meeting (id),
    participated boolean default false,
    mandatory    boolean default false
);

create trigger updated_at
    before update
    on general_meeting
    for each row
execute procedure trigger_set_timestamp();