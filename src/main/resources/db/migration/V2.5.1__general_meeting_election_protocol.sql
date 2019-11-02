ALTER TABLE general_meeting
    ADD COLUMN election boolean default false;

ALTER TABLE general_meeting
    ADD COLUMN protocol_url text;
