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
type example_type_one as object(value number(15,4))
type example_type_three as object(value date)
type example_type_two as object(value varchar2(10))


TEXT
--------------------------------------------------------------------------------
PACKAGE example
AS
    FUNCTION hello(value1 varchar2) RETURN number;
    FUNCTION world(value2 number) RETURN varchar2;
    FUNCTION one(value3 date) RETURN date;
    FUNCTION two(value4 example_type_one) RETURN example_type_one;
    FUNCTION three(
	value5 example_type_one,
	value6 example_type_two
    ) RETURN example_type_two;
END example;

11 rows selected.

total 32
drwxr-xr-x   10 root     root           340 May  1 21:47 .
drwxr-xr-x    4 root     root           136 May  1 15:28 ..
-rw-r--r--    1 root     root          2433 May  1 21:47 Example.java
-rw-r--r--    1 root     root          1562 May  1 21:47 ExampleTypeOne.java
-rw-r--r--    1 root     root          1602 May  1 21:47 ExampleTypeThree.java
-rw-r--r--    1 root     root          1513 May  1 21:47 ExampleTypeTwo.java
-rw-r--r--    1 root     root           399 May  1 21:45 clean.sql
-rw-r--r--    1 root     root           393 May  1 21:30 create-package.sql
-rw-r--r--    1 root     root           425 May  1 21:35 create-types.sql
-rwxr-xr-x    1 root     root          1067 May  1 21:46 run.sh
```
