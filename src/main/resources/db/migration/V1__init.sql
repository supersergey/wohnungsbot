CREATE TYPE role AS ENUM ('OWNER', 'ADMIN', 'USER');

create table account
(
    id bigint not null primary key unique,
    chat_id bigint not null,
    username VARCHAR(128),
    role role not null
);
