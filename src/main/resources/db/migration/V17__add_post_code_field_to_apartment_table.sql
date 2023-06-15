alter table main.apartment
    add post_code varchar(8);

create index apartment_post_code_index
    on main.apartment (post_code);
