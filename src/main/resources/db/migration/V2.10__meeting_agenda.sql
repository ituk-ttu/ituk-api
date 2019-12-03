CREATE TABLE meeting_agenda (
    id             serial PRIMARY KEY        NOT NULL,
    created_at     timestamptz DEFAULT now() NOT NULL,
    updated_at     timestamptz               NOT NULL,
    deleted_at     timestamptz               NOT NULL
);

CREATE TABLE meeting_agenda_item (
    id                serial PRIMARY KEY        NOT NULL,
    meeting_agenda_id bigint                    NOT NULL,
    item              varchar                   NOT NULL,
    created_at        timestamptz DEFAULT now() NOT NULL,
    FOREIGN KEY (meeting_agenda_id) REFERENCES meeting_agenda (id)
);

ALTER TABLE general_meeting ADD COLUMN meeting_agenda_id bigint;
ALTER TABLE general_meeting ADD CONSTRAINT fk_general_meeting_meeting_agenda_id FOREIGN KEY (meeting_agenda_id) REFERENCES meeting_agenda (id);
