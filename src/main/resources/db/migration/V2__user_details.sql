create table user_details(
    id VARCHAR(128) not null primary key unique references account(id),
    first_last_name VARCHAR(128),
    phone VARCHAR(128),
    number_of_tenants SMALLINT,
    pets BOOLEAN,
    bundesland VARCHAR(128)
);