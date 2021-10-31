create sequence if not exists authorities_user_id_seq;

alter sequence authorities_user_id_seq owner to postgres;

create table if not exists books
(
    id         serial
    constraint books_pkey
    primary key,
    name       varchar(32)  not null,
    author     varchar(32)  not null,
    annotation varchar(512) not null,
    coverurl   varchar,
    bookurl    varchar
    );

alter table books
    owner to postgres;

create table if not exists users
(
    id       bigserial
    constraint users_pk
    primary key,
    username varchar(16)          not null,
    password varchar(64)          not null,
    enabled  boolean default true not null
    );

alter table users
    owner to postgres;

create table if not exists authorities
(
    userid    bigint default nextval('authorities_user_id_seq'::regclass) not null
    constraint fk_authorities
    references users,
    authority varchar(32)                                                 not null,
    constraint unique_authorities
    unique (userid, authority)
    );

alter table authorities
    owner to postgres;

alter sequence authorities_user_id_seq owned by authorities.userid;

create unique index if not exists unique_username_on_users
    on users (lower(username::text));

