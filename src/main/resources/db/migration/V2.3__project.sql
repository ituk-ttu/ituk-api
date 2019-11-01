create table project
(
    id              serial primary key,
    title           text not null,
    date_start      date not null,
    date_end        date,
    description     text,
    project_lead_id integer references "user" (id)
);

create table project_member
(
    id      serial primary key,
    name    varchar(255),
    user_id integer references "user" (id)
);

create table project_summary
(
    id               serial primary key,
    created_at       timestamptz default LOCALTIMESTAMP not null,
    created_by       integer references "user" (id)     not null,
    positive_summary text,
    negative_summary text,
    confirmed_by     integer references "user" (id),
    project_id       integer references project (id)    not null
);

create table project_budget
(
    id         serial primary key,
    project_id integer references project (id) not null
);

create table project_budget_row
(
    id                serial primary key,
    project_budget_id integer references project_budget (id) not null,
    description       varchar(100)                           not null,
    ituk_cost         decimal(10, 2) default (0.0),
    faculty_cost      decimal(10, 2) default (0.0)
);
