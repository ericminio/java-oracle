#!/bin/bash

source /usr/local/src/oracle/utils.sh

cd /usr/local/src/service/code
mvn clean package \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.user=system/oracle

cd target
mkdir -p /usr/local/src/service/demos/generated-from-database
rm /usr/local/src/service/demos/generated-from-database/*.java
executeFile /usr/local/src/work/drop-packages.sql
executeFile /usr/local/src/work/drop-types.sql
executeFile /usr/local/src/service/demos/create-types.sql
executeFile /usr/local/src/service/demos/create-package.sql
execute "select type_name from user_types order by type_name;"
execute "select object_name from user_objects where object_type='PACKAGE';"
java \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.user=system/oracle \
    -DoraclePackage=example \
    -DtypeNamePrefix=example_type_ \
    -DjavaPackage=company.name \
    -DoutputFolder=/usr/local/src/service/demos/generated-from-database \
    -cp ~/.m2/repository/com/oracle/database/jdbc/ojdbc6/11.2.0.4/ojdbc6-11.2.0.4.jar:java-oracle-1.0.jar \
    ericminio.javaoracle.main.GenerateAdaptersFromDatabase 

ls -la /usr/local/src/service/demos/generated-from-database
