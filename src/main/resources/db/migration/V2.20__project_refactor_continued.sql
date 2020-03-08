TRUNCATE project_budget_row;

ALTER TABLE project_budget_row drop column project_id;
ALTER TABLE project_budget_row add column project_summary_id integer references project_summary (id) not null;