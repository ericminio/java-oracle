#!/bin/bash

java -version
mvn -v
mvn clean package \
    -DskipTests \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle

cd target

java -DPORT=$PORT -cp java-oracle-1.0.1.jar ericminio.javaoracle.main.StartServer 