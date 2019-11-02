alter table project_member alter column project_id drop not null;
alter table project_summary alter column project_id drop not null;
alter table project_budget alter column project_id drop not null;
alter table project_budget_row alter column project_budget_id drop not null;
