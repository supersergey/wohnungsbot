alter table main.user_details
    add family_members text default '' not null;

alter table main.user_details
    add ready_to_move bool default true not null;

alter table main.user_details
    add foreign_languages text default '' not null;

alter table main.user_details
    add allergies text default '' not null;
