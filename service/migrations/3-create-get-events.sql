create or replace function get_event_count return number
as
    event_count number;
begin
    select count(*) into event_count from event;

    return event_count;
end;
/