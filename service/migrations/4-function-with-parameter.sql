CREATE OR REPLACE PACKAGE function_with_parameter
AS

    FUNCTION count_by_type(
        value varchar2
    ) RETURN integer;

    FUNCTION count_by_label(
        value varchar2
    ) RETURN integer;

END function_with_parameter;
/

create or replace package body function_with_parameter
AS

    function count_by_type(
        value varchar2
    ) return integer
    as
        event_count integer;
    begin
        select count(*) into event_count from event where type=value;

        return event_count;
    end;


    function count_by_label(
        value varchar2
    ) return integer
    as
        event_count integer;
    begin
        select count(*) into event_count from event where label=value;

        return event_count;
    end;

END function_with_parameter;
/
