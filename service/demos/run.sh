#!/bin/bash

source /usr/local/src/oracle/utils.sh

cd /usr/local/src/service/code
mvn clean package \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle

cd target

rm /usr/local/src/service/demos/generated-from-database/*.java
executeFile /usr/local/src/work/drop-packages.sql
executeFile /usr/local/src/work/drop-types.sql
executeFile /usr/local/src/service/demos/create-types.sql
executeFile /usr/local/src/service/demos/create-package.sql
execute "select type_name from user_types order by type_name;"
execute "select object_name from user_objects where object_type='PACKAGE';"
java \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle \
    -DoraclePackage=example \
    -DtypeNamePrefix=example_type_ \
    -DjavaPackage=company.name \
    -DoutputFolder=/usr/local/src/service/demos/generated-from-database \
    -cp java-oracle-1.0-jar-with-dependencies.jar ericminio.javaoracle.GenerateAdapters 

cd /usr/local/src/service/demos/generated-from-database
ls -la
