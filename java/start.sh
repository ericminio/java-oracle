#!/bin/bash

/usr/local/src/oracle/wait.sh

java -version
mvn -v
mvn clean test \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle
