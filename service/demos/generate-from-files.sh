#!/bin/bash

source /usr/local/src/oracle/utils.sh

cd /usr/local/src/service/code
mvn clean package \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle

cd target

mkdir -p /usr/local/src/service/demos/generated-from-files
rm /usr/local/src/service/demos/generated-from-files/*.java
java \
    -DpackageFile=/usr/local/src/service/demos/create-package.sql \
    -DtypesFile=/usr/local/src/service/demos/create-types.sql \
    -DjavaPackage=company.name \
    -DoutputFolder=/usr/local/src/service/demos/generated-from-files \
    -cp java-oracle-1.0.3.jar ericminio.javaoracle.main.GenerateAdaptersFromFiles 

ls -la /usr/local/src/service/demos/generated-from-files
