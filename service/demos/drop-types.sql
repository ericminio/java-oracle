create or replace procedure drop_type_if_exists(name varchar2)
is 
    c int;
begin
    execute immediate 'drop type ' || name;
    exception
        when others then
            if sqlcode != -2303 then
                raise;
            end if;
end;
/
create or replace procedure drop_all_types is
begin
    for i in (select type_name from user_types order by type_name) loop
        drop_type_if_exists(i.type_name);
    end loop;
end;
/
show errors;

drop_all_types;
/
drop_all_types;
/
show errors;