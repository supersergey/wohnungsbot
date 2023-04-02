create table post_code(
    id varchar(8) not null primary key,
    plz_name varchar(256) not null,
    kreis_code varchar(8) not null,
    land_code varchar(8) not null,
    land_name varchar(64) not null,
    kreis_name varchar(128) not null
);
