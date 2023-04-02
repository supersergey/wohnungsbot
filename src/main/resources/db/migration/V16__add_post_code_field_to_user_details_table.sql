alter table main.user_details
    add post_code varchar(8);

create index user_details_post_code_index
    on main.user_details (post_code);

