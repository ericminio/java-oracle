-- we will create the type we need

create type example_any_type as object(value number(15,4))
/
create type example_array_type as table of example_any_type
/

