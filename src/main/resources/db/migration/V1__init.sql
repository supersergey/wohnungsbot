CREATE TYPE role AS ENUM ('OWNER', 'ADMIN', 'USER');

create table account
(
    id integer not null primary key unique,
    chat_id integer not null,
    username VARCHAR(128),
    role role not null
);
