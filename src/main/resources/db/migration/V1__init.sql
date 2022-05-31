CREATE TYPE role AS ENUM ('OWNER', 'ADMIN', 'USER');

create table account
(
    username VARCHAR(128) not null primary key unique,
    role role not null
);
