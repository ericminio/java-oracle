#!/bin/bash

source ./utils.sh

ready=0
while [ "$ready" != "1" ]
do
    echo "waiting for Oracle";
    execute "select 'yes' as ORACLE_IS_READY from dual;" > /tmp/init.output
    ready=`grep -- "---" /tmp/init.output | wc -l | xargs`
    sleep 1;
done;
echo "Oracle is ready";

