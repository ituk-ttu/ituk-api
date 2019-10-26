-- this migration file can be changed throughout the project
INSERT INTO "user" (first_name, last_name, email, card_number, password, student_code, status_id, curriculum, iban)
VALUES ('Tux', 'Linus', 'tux@ituk.ee', NULL, NULL, '199999IAPM', 1, 'IAPM', NULL);

insert into door (code) values ('ICT-360');
insert into door (code) values ('ICT-361');
insert into door (code) values ('ICO-210');
insert into door (code) values ('ICO front door');
insert into door (code) values ('ICT front door');

