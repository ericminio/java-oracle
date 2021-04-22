@@/usr/local/src/oracle/utils.sql

execute drop_table_if_exists('event');

create table event(
    type varchar2(10) not null,
    label varchar2(50) not null
);
