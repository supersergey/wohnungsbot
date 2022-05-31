create table user_details(
    username VARCHAR(128) not null primary key unique references account(username),
    phone VARCHAR(128),
    number_of_tenants SMALLINT,
    pets BOOLEAN,
    bundes_land VARCHAR(128)
);