UPDATE application a
SET user_id = (SELECT id FROM "user" WHERE a.email = "user".email LIMIT 1)
WHERE 1 = 1;
