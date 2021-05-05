[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java7.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java8.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)
[![Build Status](https://github.com/ericminio/java-oracle/actions/workflows/java11.yml/badge.svg)](https://github.com/ericminio/java-oracle/actions)

# Run the tests
```
docker-compose up java7 
docker-compose up java8 
docker-compose up java11 
```

# Usage

Study [service/demos/run.sh](service/demos/run.sh).

One way to see it running:
- `docker-compose run java8 bash` 
- `/usr/local/src/service/demos/run.sh`

```
TEXT
--------------------------------------------------------------------------------
type example_any_type as object(value number(15,4))
type example_array_type as table of example_any_type
type example_type_array as table of example_type_record
type example_type_one as object(value number(15,4))
type example_type_partial as object (
    page                number(6),
    records_per_page    number(6),
    records_in_page     number(6),
    total_records       number(6),
    results             example_type_array
)

TEXT
--------------------------------------------------------------------------------
type example_type_record as object (
    id          number,
    label       varchar2(15),
    creation    date
)
type example_type_three as object(value date)
type example_type_two as object(value varchar2(10))

18 rows selected.


TEXT
--------------------------------------------------------------------------------
PACKAGE example
AS
    FUNCTION hello(value1 varchar2) RETURN number;
    FUNCTION world(value2 number) RETURN varchar2;
    FUNCTION one(value3 date) RETURN date;
    FUNCTION two(value4 example_type_one) RETURN example_type_one;
    FUNCTION three(
        value5 in example_type_one,
        value6 in example_type_two
    ) RETURN example_type_two;
    FUNCTION search(

TEXT
--------------------------------------------------------------------------------
        id      number,
        page    number
    ) RETURN example_type_partial;
END example;

15 rows selected.

INFO: Generating class for package
INFO: -> company.name.Example
INFO: Generating classes for types
INFO: -> company.name.ExampleTypeRecord
INFO: -> company.name.ExampleTypeOne
INFO: -> company.name.ExampleTypeTwo
INFO: -> company.name.ExampleTypePartial
INFO: -> company.name.ExampleTypeArray
INFO: -> company.name.ExampleTypeThree
total 26
drwxrwxrwx    1 root     root          4096 May  5 18:25 .
drwxrwxrwx    1 root     root             0 Apr 28 16:11 ..
-rwxr-xr-x    1 root     root          2952 May  5 18:25 Example.java
-rwxr-xr-x    1 root     root          1770 May  5 18:25 ExampleTypeArray.java
-rwxr-xr-x    1 root     root          1562 May  5 18:25 ExampleTypeOne.java
-rwxr-xr-x    1 root     root          4317 May  5 18:25 ExampleTypePartial.java
-rwxr-xr-x    1 root     root          2741 May  5 18:25 ExampleTypeRecord.java
-rwxr-xr-x    1 root     root          1602 May  5 18:25 ExampleTypeThree.java
-rwxr-xr-x    1 root     root          1513 May  5 18:25 ExampleTypeTwo.java
-rwxr-xr-x    1 root     root           553 May  3 17:05 clean.sql
-rwxr-xr-x    1 root     root           491 May  4 23:47 create-package.sql
-rwxr-xr-x    1 root     root           609 May  4 22:41 create-types.sql
-rwxr-xr-x    1 root     root          1032 May  4 22:33 run.sh
```
