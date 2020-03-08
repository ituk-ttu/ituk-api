TRUNCATE project_budget_row;

ALTER TABLE project_budget_row drop column project_budget_id;
ALTER TABLE project_budget_row add column project_id integer references project (id) not null;

DROP TABLE project_budget;

TRUNCATE project_member;

ALTER TABLE project_member drop column project_id;
ALTER TABLE project_member add column project_summary_id integer references project_summary (id) not null;