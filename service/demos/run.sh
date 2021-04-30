#!/bin/bash

rm /usr/local/src/service/demos/*.java

cd /usr/local/src/service/code
mvn clean package -Doracle.host=oracle

function execute {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && echo \"$1\" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost"
}


cd target
java \
    -Doracle.host=oracle \
    -DtypeNamePrefix=example_types_ \
    -DjavaPackage=company.name \
    -DoutputFolder=/usr/local/src/service/demos \
    -cp java-oracle-1.0-jar-with-dependencies.jar ericminio.javaoracle.GenerateTypeAdapters
java \
    -Doracle.host=oracle \
    -DoraclePackage=example \
    -DjavaPackage=company.name \
    -DoutputFolder=/usr/local/src/service/demos \
    -cp java-oracle-1.0-jar-with-dependencies.jar ericminio.javaoracle.GeneratePackageAdapter 

ls -la /usr/local/src/service/demos

execute "select text from all_source where type='TYPE' and name='EXAMPLE_TYPE' order by line;"
cat /usr/local/src/service/demos/ExampleTypesOne.java
cat /usr/local/src/service/demos/ExampleTypesTwo.java

execute "select text from all_source where type='PACKAGE' and name='EXAMPLE' order by line;"
cat /usr/local/src/service/demos/Example.java

cd /usr/local/src/service/code