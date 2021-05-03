CREATE OR REPLACE PACKAGE example
AS
    FUNCTION hello(value1 varchar2) RETURN number;
    FUNCTION world(value2 number) RETURN varchar2;
    FUNCTION one(value3 date) RETURN date;
    FUNCTION two(value4 example_type_one) RETURN example_type_one;
    FUNCTION three(
        value5 in example_type_one, 
        value6 in example_type_two
    ) RETURN example_type_two;
END example;
/
show errors;