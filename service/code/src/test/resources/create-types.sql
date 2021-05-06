create or replace type example_any_type as object(value number(15,4))
/
create or replace type example_array_type as table of example_any_type
/

