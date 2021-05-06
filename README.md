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

Which eventually produces
```
TYPE_NAME
------------------------------
EXAMPLE_TYPE_ARRAY
EXAMPLE_TYPE_ONE
EXAMPLE_TYPE_PARTIAL
EXAMPLE_TYPE_RECORD
EXAMPLE_TYPE_THREE
EXAMPLE_TYPE_TWO

6 rows selected.


OBJECT_NAME
--------------------------------------------------------------------------------
EXAMPLE

INFO: Generating class for package
INFO: -> company.name.Example
INFO: Generating classes for types
INFO: -> generating company.name.ExampleTypeArray
INFO: -> generating company.name.ExampleTypeOne
INFO: -> generating company.name.ExampleTypePartial
INFO: -> generating company.name.ExampleTypeRecord
INFO: -> generating company.name.ExampleTypeThree
INFO: -> generating company.name.ExampleTypeTwo
total 26
drwxrwxrwx    1 root     root          4096 May  6 18:42 .
drwxrwxrwx    1 root     root          4096 May  6 18:39 ..
-rwxr-xr-x    1 root     root          2952 May  6 18:42 Example.java
-rwxr-xr-x    1 root     root          1770 May  6 18:42 ExampleTypeArray.java
-rwxr-xr-x    1 root     root          1562 May  6 18:42 ExampleTypeOne.java
-rwxr-xr-x    1 root     root          4317 May  6 18:42 ExampleTypePartial.java
-rwxr-xr-x    1 root     root          2741 May  6 18:42 ExampleTypeRecord.java
-rwxr-xr-x    1 root     root          1602 May  6 18:42 ExampleTypeThree.java
-rwxr-xr-x    1 root     root          1513 May  6 18:42 ExampleTypeTwo.java
```
