alter table application drop column id_code;

alter table "user" rename column id_code to personal_code;
