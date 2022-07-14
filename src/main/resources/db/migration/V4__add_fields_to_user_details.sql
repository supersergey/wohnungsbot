alter table main.user_details
    add district text;

alter table main.user_details
    add family_members text;

alter table main.user_details
    add ready_to_move bool;

alter table main.user_details
    add foreign_languages text;

alter table main.user_details
    add allergies text;

update main.user_details
    set family_members = '', ready_to_move = true, foreign_languages = '', allergies = ''
    where true;
