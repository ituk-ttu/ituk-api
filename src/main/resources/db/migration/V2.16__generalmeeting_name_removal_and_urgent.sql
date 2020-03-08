ALTER TABLE general_meeting
    DROP COLUMN name;

ALTER TABLE general_meeting
    ADD urgent boolean DEFAULT FALSE;
