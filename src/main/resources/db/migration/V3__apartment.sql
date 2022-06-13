create table apartment(
    id varchar(128) not null primary key unique,
    city varchar(128) not null,
    bundesland varchar(128) not null,
    min_tenants smallint not null,
    max_tenants smallint not null,
    description text,
    pets_allowed boolean not null,
    publicationStatus varchar(128) not null
);

create table apartment_account(
    account_id integer not null references account(id),
    apartment_id varchar(128) not null references apartment(id),
    apply_ts timestamptz not null,
    PRIMARY KEY(account_id, apartment_id)
);
