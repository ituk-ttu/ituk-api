CREATE OR REPLACE FUNCTION truncate_all_tables_except_for_flyway()
    RETURNS VOID
AS
'
    DECLARE
        table_record RECORD;
    BEGIN
        FOR table_record IN SELECT *
                            FROM information_schema.tables
                            WHERE table_schema = ''public''
                              AND table_type = ''BASE TABLE''
                              AND table_name NOT IN (''flyway_schema_history'')
            LOOP
                EXECUTE format(''TRUNCATE TABLE %I CASCADE'', table_record.table_name);
            END LOOP;
    END;
'
    LANGUAGE plpgsql;

SELECT truncate_all_tables_except_for_flyway();
