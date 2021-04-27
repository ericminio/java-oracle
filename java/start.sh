#!/bin/bash

function execute {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && echo \"$1\" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost"
}

ready=0
while [ "$ready" != "1" ]
do
    echo "waiting for Oracle";
    execute "select 'yes' as ORACLE_IS_READY from dual;" > /tmp/init.output
    ready=`grep -- "---" /tmp/init.output | wc -l`
    sleep 1;
done;
echo "Oracle is ready";

java -version
mvn -v
mvn test -Doracle.host=oracle
