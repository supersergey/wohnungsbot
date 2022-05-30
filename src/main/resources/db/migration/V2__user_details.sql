create table user_details(
    login VARCHAR(128) not null primary key unique references account(login),
    user_name VARCHAR(128),
    phone VARCHAR(128),
    number_of_tenants SMALLINT,
    pets BOOLEAN,
    bundes_land VARCHAR(128)
);