#!/bin/bash

cd /usr/local/src/service/code
mvn clean package -Doracle.host=oracle
echo ""

cd target
java -Doracle.host=oracle -DoraclePackage=exploration -DjavaPackage=company.name -DoutputFolder=/usr/local/src/service/demos/output -cp java-oracle-1.0-jar-with-dependencies.jar ericminio.javaoracle.ExtractPackage 

ls -la /usr/local/src/service/demos/output
cat /usr/local/src/service/demos/output/Exploration.java

cd /usr/local/src/service/code