#!/bin/bash

source /usr/local/src/oracle/utils.sh

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
mvn clean test \
    -Doracle.url=jdbc:oracle:thin:@oracle:1521:XE \
    -Doracle.username=system \
    -Doracle.password=oracle
