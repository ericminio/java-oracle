create or replace package any_package
as
    function any_function return example_array_type;
end any_package;
/
create or replace package body any_package
is
    function any_function return example_array_type
    is
        r example_array_type;
    begin
        r := example_array_type();
        return r;
    end;
end any_package;
/