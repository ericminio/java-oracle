#!/bin/bash

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
executeFile /usr/local/src/service/migrations/3-create-get-events.sql
execute "select get_event_count() as count from dual;"

pwd
ls -la
docker ps -a
docker network ls

docker stop java7
docker rm java7
docker run --rm --name java7 --network java-oracle_default java:7 java -version
docker run --rm --name java7 --network java-oracle_default java:7 mvn -v
docker run --rm --name java7 --network java-oracle_default -v "$DIR:/usr/local/src" --workdir /usr/local/src/service/code  java:7 mvn test

docker stop java8
docker rm java8
docker run --rm --name java8 --network java-oracle_default java:8 java -version
docker run --rm --name java8 --network java-oracle_default java:8 mvn -v
docker run --rm --name java8 --network java-oracle_default -v "$DIR:/usr/local/src" --workdir /usr/local/src/service/code  java:8 mvn test
