-- this migration file can be changed throughout the project
INSERT  INTO "user" (first_name, last_name, email, card_number, password, student_code, status_id, curriculum, iban)
VALUES ('Tux', 'Linus', 'tux@ituk.ee', NULL, '$2a$12$A7bDfgysWzX3MTewp37eaOIXfk5ErSG2Y3AlgLlRI4QwjJXJXvYVG', '199999IAPM', 1, 'IAPM', NULL);
