#!/bin/bash

java -version
mvn -v
mvn clean package \
    -DskipTests \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle

cd target

java \
    -DPORT=8015 \
    -cp java-oracle-1.0.jar ericminio.javaoracle.main.StartServer 