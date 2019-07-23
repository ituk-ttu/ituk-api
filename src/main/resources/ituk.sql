create sequence seq_user_id
  as integer
  maxvalue 2147483647;


create sequence seq_doorpermissions_id
  as integer
  maxvalue 2147483647;


create sequence seq_doorpermissions_log_id
  as integer
  maxvalue 2147483647;


create sequence seq_application_id
  as integer
  maxvalue 2147483647;


create sequence seq_recoverykeys_id
  as integer
  maxvalue 2147483647;


create sequence seq_resource_id
  as integer
  maxvalue 2147483647;


create table door
(
  code varchar(255) not null
    constraint door_pkey
      primary key
);


create table resources
(
  id        integer default nextval('seq_resource_id'::regclass) not null
    constraint resources_pkey
      primary key,
  name      varchar(255),
  comment   text,
  url       varchar(255),
  createdat timestamp                                            not null,
  updatedat timestamp                                            not null,
  authorid  integer
    constraint authorid
      unique
);


create table userstatus
(
  statusid    integer      not null
    constraint userstatus_pkey
      primary key,
  statusname  varchar(255) not null,
  description varchar(255)
);


create table "user"
(
  id          integer   default nextval('seq_user_id'::regclass) not null
    constraint users_pkey
      primary key,
  firstname   varchar(255),
  lastname    varchar(255),
  email       varchar(255),
  cardnumber  varchar(255),
  telegram    varchar(255),
  password    varchar(255),
  studentcode varchar(255),
  statusid    integer
    constraint users_userstatus_statusid_fk
      references userstatus
      on update cascade on delete set null,
  curriculum  varchar(255),
  iban        varchar(35),
  mentorid    integer,
  admin       boolean   default false                            not null,
  archived    boolean   default false                            not null,
  createdat   timestamp default LOCALTIMESTAMP                   not null,
  updatedat   timestamp default LOCALTIMESTAMP                   not null
);


create table mentor
(
  userid     integer                          not null
    constraint mentors_users_id_fk
      references "user",
  curriculum varchar(255),
  text       text,
  gif        varchar(255),
  quote      text,
  enabled    boolean,
  createdat  timestamp default LOCALTIMESTAMP not null,
  updatedat  timestamp default LOCALTIMESTAMP not null
);


create unique index mentors_userid_uindex
  on mentor (userid);

create table applications
(
  id                  integer   default nextval('seq_application_id'::regclass) not null
    constraint applications_pkey
      primary key,
  firstname           varchar(255),
  lastname            varchar(255),
  personalcode        varchar(255),
  email               varchar(255),
  studentcode         varchar(255),
  curriculum          varchar(255),
  mentorselectioncode varchar(255),
  createdat           timestamp default LOCALTIMESTAMP                          not null,
  updatedat           timestamp default LOCALTIMESTAMP                          not null,
  processedbyid       integer
    constraint processedbyid
      unique
    constraint applications_users_id_fk
      references "user",
  mentorid            integer
    constraint mentorid
      unique
    constraint applications_mentors_userid_fk
      references mentor (userid)
);


create table doorpermissions
(
  id       integer default nextval('seq_doorpermissions_id'::regclass) not null
    constraint doorpermissions_pkey
      primary key,
  doorcode varchar(255)
    constraint doorpermissions_door_code_fk
      references door,
  userid   integer
    constraint doorpermissions_users_id_fk
      references "user"
);


create table doorpermissionlogentry
(
  id         integer default nextval('seq_doorpermissions_log_id'::regclass) not null
    constraint doorpermissionslog_pkey
      primary key,
  updatedat  timestamp,
  change     text,
  modifiedby integer
    constraint doorpermissionlogentry_user_id_fk
      references "user"
);


create table recoverykeys
(
  id        integer default nextval('seq_recoverykeys_id'::regclass) not null
    constraint recoverykeys_pkey
      primary key,
  key       varchar(255),
  createdat timestamp                                                not null,
  updatedat timestamp                                                not null,
  userid    integer
    constraint userid
      unique
    constraint recoverykeys_users_id_fk
      references "user"
      on update cascade
);


create function trigger_set_timestamp() returns trigger
  language plpgsql
as
$$
BEGIN
  NEW.updatedat = NOW();
  RETURN NEW;
END;
$$;


create trigger updatedat
  before update
  on "user"
  for each row
execute procedure trigger_set_timestamp();

create trigger updatedat
  before update
  on mentor
  for each row
execute procedure trigger_set_timestamp();

create trigger updatedat
  before update
  on applications
  for each row
execute procedure trigger_set_timestamp();

create trigger updatedat
  before update
  on doorpermissionlogentry
  for each row
execute procedure trigger_set_timestamp();

create trigger updatedat
  before update
  on recoverykeys
  for each row
execute procedure trigger_set_timestamp();


