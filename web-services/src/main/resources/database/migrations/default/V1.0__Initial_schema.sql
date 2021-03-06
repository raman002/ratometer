drop table if exists users cascade;

create sequence hibernate_sequence;

create table users
(
    users_id   bigint auto_increment,

    username   varchar not null,
    email      varchar not null,
    full_name  varchar not null,
    password   varchar not null,
    contact_no clob,

    uuid       uuid    not null default random_uuid(),
    version    int              default 0,

    is_active  boolean not null default true,
    is_deleted boolean not null default false,

    created_by bigint  not null default 1,
    updated_by bigint  not null default 1,

    created_on timestamp        default current_timestamp,
    updated_on timestamp        default current_timestamp,
    deleted_on timestamp,

    primary key (users_id)
);

drop table if exists role_master cascade;

create table if not exists role_master
(
    role_master_id bigint  not null auto_increment,
    role_name      clob,

    uuid           uuid    not null default random_uuid(),
    version        int              default 0,

    is_active      boolean not null default true,
    is_deleted     boolean not null default false,
    created_on     timestamp        default current_timestamp,

    primary key (role_master_id)
);

drop table if exists roles cascade;


create table if not exists roles
(
    roles_id       bigint  not null auto_increment,
    users_id       bigint  not null,
    role_master_id bigint  not null,

    uuid           uuid    not null default random_uuid(),
    version        int              default 0,

    is_active      boolean not null default true,
    is_deleted     boolean not null default false,

    created_by     bigint  not null default 1,
    updated_by     bigint  not null default 1,

    created_on     timestamp        default current_timestamp,
    updated_on     timestamp        default current_timestamp,
    deleted_on     timestamp,

    primary key (roles_id),
    foreign key (users_id) references users on delete cascade,
    foreign key (role_master_id) references role_master on delete cascade
);

drop table if exists teams_master;

create table teams_master
(
    teams_master_id bigint  not null auto_increment,
    uuid            uuid    not null default random_uuid(),
    version         int              default 0,

    name            clob    not null,

    is_active       boolean not null default true,
    is_deleted      boolean not null default false,

    created_by      bigint  not null default 1,
    updated_by      bigint  not null default 1,

    created_on      timestamp        default current_timestamp,
    updated_on      timestamp        default current_timestamp,

    primary key (teams_master_id)
);

drop table if exists teams;


create table teams
(
    teams_id         bigint not null auto_increment,
    users_id         bigint,
    teams_master_id  bigint not null,

    teams_master_uid uuid   not null ,
    uuid             uuid   not null default random_uuid(),
    version          int    not null default 0,

    created_by       bigint not null default 1,
    updated_by       bigint not null default 1,

    created_on       timestamp       default current_timestamp,
    updated_on       timestamp       default current_timestamp,

    primary key (teams_id)
);

drop table if exists categories_master;


create table categories_master
(
    categories_master_id bigint  not null auto_increment,
    uuid                 uuid    not null default random_uuid(),
    version              int              default 0,

    parent_category_id   bigint,
    option_order_id      int,
    name                 clob    not null,
    type                 clob    not null,

    is_active            boolean not null default true,
    is_deleted           boolean not null default false,

    primary key (categories_master_id)
);


drop table if exists rating;

create table rating
(
    rating_id   bigint  not null auto_increment,
    uuid        uuid    not null default random_uuid(),
    version     int              default 0,

    category_id bigint  not null,
    users_id    bigint  not null,
    quarter     int     not null,

    is_active   boolean not null default true,
    is_deleted  boolean not null default false,

    created_by  bigint  not null default 1,
    updated_by  bigint  not null default 1,

    created_on  timestamp        default current_timestamp,
    updated_on  timestamp        default current_timestamp,

    primary key (rating_id)
);
