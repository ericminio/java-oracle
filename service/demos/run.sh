#!/bin/bash

source /usr/local/src/oracle/utils.sh

rm /usr/local/src/service/demos/*.java

cd /usr/local/src/service/code
mvn clean package -Doracle.host=oracle

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

execute "select text from all_source where type='TYPE' and name like 'EXAMPLE_TYPES_%' order by name, line;"
cat /usr/local/src/service/demos/ExampleTypesOne.java
cat /usr/local/src/service/demos/ExampleTypesTwo.java
cat /usr/local/src/service/demos/ExampleTypesThree.java

execute "select text from all_source where type='PACKAGE' and name='EXAMPLE' order by line;"
cat /usr/local/src/service/demos/Example.java

cd /usr/local/src/service/code