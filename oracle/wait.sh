#!/bin/bash

function current_dir {
    echo $( cd "$( dirname "$1" )" >/dev/null 2>&1 && pwd )    
}
DIR=$(current_dir ${BASH_SOURCE[0]})

source $DIR/utils.sh

ready=0
while [ "$ready" != "1" ]
do
    echo "waiting for Oracle";
    execute "select 'yes' as ORACLE_IS_READY from dual;" > /tmp/init.output
    ready=`grep -- "---" /tmp/init.output | wc -l | xargs`
    sleep 1;
done;
echo "Oracle is ready";

