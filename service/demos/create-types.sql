begin    
    execute immediate 'drop package example';
end;
/

begin
    for i in (select 'drop type ' || type_name as stmt from user_types) loop
        execute immediate i.stmt;
    end loop;
end;
/

create or replace type example_type_one as object(value number(15,4))
/
create or replace type example_type_two as object(value varchar2(10))
/
create or replace type example_type_three as object(value date)
/
show errors;