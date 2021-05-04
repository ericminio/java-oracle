create type example_type_one as object(value number(15,4))
/
create type example_type_two as object(value varchar2(10))
/
create type example_type_three as object(value date)
/
create type example_type_record as object (
    id          number,
    label       varchar2(15),
    creation    date
)
/
create type example_type_array as table of example_type_record
/
create type example_type_partial as object (
    page                number(6),
    records_per_page    number(6),
    records_in_page     number(6),
    total_records       number(6),
    results             example_type_array
)
/
show errors;