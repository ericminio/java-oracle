#!/bin/bash

pwd
ls -la
docker ps -a
docker network ls

function execute {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && echo \"$1\" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost"
}
function executeFile {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost < $1"
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

executeFile /usr/local/src/service/migrations/1-create-table-event.sql
executeFile /usr/local/src/service/migrations/2-insert-events.sql
execute "select * from event;"
executeFile /usr/local/src/service/migrations/3-create-get-event-count.sql
execute "select exploration.get_event_count() as count from dual;"

java -version
mvn -v
mvn test -Doracle.host=oracle
