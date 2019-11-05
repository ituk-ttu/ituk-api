create table recovery_password
(
    id serial primary key,
    email        varchar(255) unique,
    password     varchar(255)
);