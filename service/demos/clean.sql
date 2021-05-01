begin
    for i in (select 'drop package ' || object_name as stmt from user_objects where object_type='PACKAGE') loop
        execute immediate i.stmt;
    end loop;
end;
/
show errors;

drop type custom_type_nesting;
drop type custom_type_nested;

begin
    for i in (select 'drop type ' || type_name as stmt from user_types) loop
        execute immediate i.stmt;
    end loop;
end;
/
show errors;