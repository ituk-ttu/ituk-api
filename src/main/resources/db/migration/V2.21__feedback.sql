

CREATE TABLE feedback (
    id         bigserial PRIMARY KEY,
    title      varchar     NOT NULL,
    "text"     varchar     NOT NULL,
    created_at timestamptz NOT NULL,
    deleted_at timestamptz
);
