create table "session"
(
    id      serial primary key,
    user_id integer unique not null references "user" (id),
    created_at   timestamptz  default LOCALTIMESTAMP not null
)
