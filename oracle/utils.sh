#!/bin/bash

function execute {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && echo \"$1\" | /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost"
}

function executeFile {
    docker exec oracle bash -c "export ORACLE_HOME=/u01/app/oracle/product/11.2.0/xe && /u01/app/oracle/product/11.2.0/xe/bin/sqlplus -S SYSTEM/oracle@localhost < $1"
}
