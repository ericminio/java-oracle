CREATE OR REPLACE PACKAGE exploration
AS

    FUNCTION get_event_count RETURN NUMBER;

END exploration;
/

create or replace package body exploration
AS

    function get_event_count return number
    as
        event_count number;
    begin
        select count(*) into event_count from event;

        return event_count;
    end;

END exploration;
/
