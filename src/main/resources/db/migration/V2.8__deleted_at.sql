ALTER TABLE application ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE general_meeting ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE mentor_profile ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE project ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE project_summary ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE recovery_key ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE resource ADD COLUMN deleted_at timestamptz DEFAULT NULL;
ALTER TABLE "user" ADD COLUMN deleted_at timestamptz DEFAULT NULL;
