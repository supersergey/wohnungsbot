CREATE TYPE role AS ENUM ('OWNER', 'ADMIN', 'USER');

create table account
(
    id VARCHAR(128) not null primary key unique,
    chat_id VARCHAR(128) not null unique,
    username VARCHAR(128),
    role role not null
);
