create or replace type example_any_type as object(value number(15,4))
/
create or replace type example_array_type as table of example_any_type
/

create or replace package any_package
as
    function any_function return example_array_type;
end any_package;
/
