alter table main.user_details
    add if not exists wbs_number_of_rooms smallint;

alter table main.apartment
    drop if exists living_period;
