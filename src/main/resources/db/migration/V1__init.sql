CREATE TYPE role AS ENUM ('OWNER', 'ADMIN', 'USER');

create table account
(
    id bigserial not null primary key unique,
    chat_id bigserial not null,
    username VARCHAR(128),
    role role not null
);
