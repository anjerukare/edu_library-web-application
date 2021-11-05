create table if not exists roles
(
    id serial
        constraint pk_roles
            primary key,
    name varchar(16) not null
);

alter table roles owner to postgres;

create unique index if not exists roles_pk
    on roles (id);

create table if not exists statuses
(
    id serial
        constraint pk_statuses
            primary key,
    name varchar(32) not null
);

alter table statuses owner to postgres;

create unique index if not exists statuses_pk
    on statuses (id);

create table if not exists tags
(
    id serial
        constraint pk_tags
            primary key,
    name varchar(16) not null,
    description varchar(32)
);

alter table tags owner to postgres;

create unique index if not exists tags_pk
    on tags (id);

create table if not exists users
(
    id serial
        constraint pk_users
            primary key,
    username varchar(16) not null,
    password varchar(72) not null,
    roleid integer default 3 not null
        constraint fk_users_r_users_h_roles
            references roles
            on update restrict on delete restrict
);

alter table users owner to postgres;

create table if not exists authors
(
    id serial
        constraint pk_authors
            primary key,
    surname varchar(32) not null,
    name varchar(16) not null,
    patronymic varchar(32),
    publisherid integer not null
        constraint fk_authors_r_users_p_users
            references users
            on update restrict on delete restrict,
    createdate date not null,
    moderatorid integer
        constraint fk_authors_r_moderat_users
            references users
            on update restrict on delete restrict,
    published boolean not null
);

alter table authors owner to postgres;

create unique index if not exists authors_pk
    on authors (id);

create index if not exists r_users_publish_authors_fk
    on authors (publisherid);

create index if not exists r_moderator_publish_author_fk
    on authors (moderatorid);

create table if not exists books
(
    id serial
        constraint pk_books
            primary key,
    name varchar(24) not null,
    annotation varchar(256) not null,
    coverurl varchar(128),
    bookurl varchar(128),
    publisherid integer not null
        constraint fk_books_r_user_cr_users
            references users
            on update restrict on delete restrict,
    createdate date default now() not null,
    moderatorid integer
        constraint fk_books_r_moderat_users
            references users
            on update restrict on delete restrict,
    published boolean default false not null
);

alter table books owner to postgres;

create table if not exists bookauthors
(
    bookid integer not null
        constraint fk_bookauth_r_books_h_books
            references books
            on update restrict on delete restrict,
    authorid integer not null
        constraint fk_bookauth_r_books_h_authors
            references authors
            on update restrict on delete restrict,
    constraint pk_bookauthors
        primary key (bookid, authorid)
);

alter table bookauthors owner to postgres;

create unique index if not exists r_books_have_authors_pk
    on bookauthors (bookid, authorid);

create index if not exists r_books_have_authors_fk
    on bookauthors (bookid);

create index if not exists r_books_have_authors2_fk
    on bookauthors (authorid);

create table if not exists bookstatuses
(
    userid integer not null
        constraint fk_bookstat_r_users_s_users
            references users
            on update restrict on delete restrict,
    bookid integer not null
        constraint fk_bookstat_r_books_m_books
            references books
            on update restrict on delete restrict,
    statusid integer
        constraint fk_bookstat_r_book_st_statuses
            references statuses
            on update restrict on delete restrict,
    date date not null,
    constraint pk_bookstatuses
        primary key (userid, bookid)
);

alter table bookstatuses owner to postgres;

create unique index if not exists bookstatuses_pk
    on bookstatuses (userid, bookid);

create index if not exists r_book_statuses_fk
    on bookstatuses (statusid);

create index if not exists r_users_set_book_statuses_fk
    on bookstatuses (userid);

create index if not exists r_books_may_have_status_fk
    on bookstatuses (bookid);

create table if not exists booktags
(
    bookid integer not null
        constraint fk_booktags_r_books_h_books
            references books
            on update restrict on delete restrict,
    tagid integer not null
        constraint fk_booktags_r_books_h_tags
            references tags
            on update restrict on delete restrict,
    constraint pk_booktags
        primary key (bookid, tagid)
);

alter table booktags owner to postgres;

create unique index if not exists r_books_have_tags_pk
    on booktags (bookid, tagid);

create index if not exists r_books_have_tags_fk
    on booktags (bookid);

create index if not exists r_books_have_tags2_fk
    on booktags (tagid);

create unique index if not exists books_pk
    on books (id);

create index if not exists r_user_create_books_fk
    on books (publisherid);

create index if not exists r_moderator_publish_book_fk
    on books (moderatorid);

create table if not exists favoritebooks
(
    userid integer not null
        constraint fk_favorite_r_users_h_users
            references users
            on update restrict on delete restrict,
    bookid integer not null
        constraint fk_favorite_r_books_c_books
            references books
            on update restrict on delete restrict,
    date date not null,
    constraint pk_favoritebooks
        primary key (userid, bookid)
);

alter table favoritebooks owner to postgres;

create unique index if not exists favoritebooks_pk
    on favoritebooks (userid, bookid);

create index if not exists r_users_have_favorite_books_fk
    on favoritebooks (userid);

create index if not exists r_books_can_be_favorite_fk
    on favoritebooks (bookid);

create table if not exists reviews
(
    id serial
        constraint pk_reviews
            primary key,
    bookid integer not null
        constraint fk_reviews_r_books_h_books
            references books
            on update restrict on delete restrict,
    userid integer not null
        constraint fk_reviews_r_reviews_users
            references users
            on update restrict on delete restrict,
    header varchar(32) not null,
    text varchar(1024) not null,
    score numeric not null
);

alter table reviews owner to postgres;

create unique index if not exists reviews_pk
    on reviews (id);

create index if not exists r_books_have_reviews_fk
    on reviews (bookid);

create index if not exists r_reviews_have_users_fk
    on reviews (userid);

create table if not exists topic
(
    id serial
        constraint pk_topic
            primary key,
    userid integer not null
        constraint fk_topic_r_users_c_users
            references users
            on update restrict on delete restrict,
    name varchar(64) not null,
    date date not null
);

alter table topic owner to postgres;

create unique index if not exists topic_pk
    on topic (id);

create index if not exists r_users_create_forums_fk
    on topic (userid);

create table if not exists topicreplies
(
    id serial
        constraint pk_topicreplies
            primary key,
    topicid integer not null
        constraint fk_topicrep_r_topics__topic
            references topic
            on update restrict on delete restrict,
    userid integer not null
        constraint fk_topicrep_r_users_p_users
            references users
            on update restrict on delete restrict,
    text varchar(1024) not null,
    date date not null
);

alter table topicreplies owner to postgres;

create unique index if not exists topicreplies_pk
    on topicreplies (id);

create index if not exists r_users_publish_replies_fk
    on topicreplies (userid);

create index if not exists r_topics_contain_replies_fk
    on topicreplies (topicid);

create unique index if not exists users_pk
    on users (id);

create index if not exists r_users_have_role_fk
    on users (roleid);
