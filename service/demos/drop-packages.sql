begin
    for i in (select 'drop package body ' || object_name as stmt from user_objects where object_type='PACKAGE_BODY') loop
        execute immediate i.stmt;
    end loop;
end;
/
show errors;

begin
    for i in (select 'drop package ' || object_name as stmt from user_objects where object_type='PACKAGE') loop
        execute immediate i.stmt;
    end loop;
end;
/
show errors;
