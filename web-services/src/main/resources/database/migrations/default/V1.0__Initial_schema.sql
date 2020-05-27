create extension if not exists "uuid-ossp";

drop table if exists users cascade;

drop sequence if exists users_id_seq;
create sequence if not exists users_id_seq;

create table users
(
    users_id   bigint  not null default nextval('users_id_seq'),

    username   text    not null unique,
    email      text    not null unique,
    full_name  text    not null,
    password   text    not null,
    contact_no text,

    uuid       uuid    not null default uuid_generate_v4(),
    version    int              default 0,

    is_active  boolean not null default true,
    is_deleted boolean not null default false,

    created_by bigint  not null default 1,
    updated_by bigint  not null default 1,

    created_on timestamp        default current_timestamp,
    updated_on timestamp        default current_timestamp,
    deleted_on timestamp        default current_timestamp,

    primary key (users_id)
);
alter sequence users_id_seq owned by users.users_id;

drop sequence if exists role_master_id_seq;
create sequence if not exists role_master_id_seq;

drop table if exists role_master cascade;

create table if not exists role_master
(
    role_master_id bigint  not null default nextval('role_master_id_seq'),
    role_name      text unique,

    uuid           uuid    not null default uuid_generate_v4(),
    version        int              default 0,

    is_active      boolean not null default true,
    is_deleted     boolean not null default false,
    created_on     timestamp        default current_timestamp,

    primary key (role_master_id)
);
alter sequence role_master_id_seq owned by role_master.role_master_id;

drop table if exists roles cascade;

drop sequence if exists roles_id_seq;
create sequence if not exists roles_id_seq;

create table if not exists roles
(
    roles_id       bigint  not null default nextval('roles_id_seq'),
    users_id       bigint  not null,
    role_master_id bigint  not null,

    uuid           uuid    not null default uuid_generate_v4(),
    version        int              default 0,

    is_active      boolean not null default true,
    is_deleted     boolean not null default false,

    created_by     bigint  not null default 1,
    updated_by     bigint  not null default 1,

    created_on     timestamp        default current_timestamp,
    updated_on     timestamp        default current_timestamp,
    deleted_on     timestamp        default current_timestamp,

    primary key (roles_id),
    foreign key (users_id) references users on delete cascade,
    foreign key (role_master_id) references role_master on delete cascade
);
alter sequence roles_id_seq owned by roles.roles_id;

drop table if exists teams_master;

drop sequence if exists teams_master_id_seq;
create sequence if not exists teams_master_id_seq;

create table teams_master
(
    teams_master_id bigint  not null default nextval('teams_master_id_seq'),
    uuid            uuid    not null default uuid_generate_v4(),
    version         int              default 0,

    name            text    not null,

    is_active       boolean not null default true,
    is_deleted      boolean not null default false,

    created_by      bigint  not null default 1,
    updated_by      bigint  not null default 1,

    created_on      timestamp        default current_timestamp,
    updated_on      timestamp        default current_timestamp,

    primary key (teams_master_id)
);
alter sequence teams_master_id_seq owned by teams_master.teams_master_id;

drop table if exists teams;

drop sequence if exists teams_id_seq;
create sequence if not exists teams_id_seq;

create table teams
(
    teams_id   bigint not null default nextval('teams_id_seq'),
    users_id   bigint,

    uuid       uuid   not null default uuid_generate_v4(),

    created_by bigint not null default 1,
    updated_by bigint not null default 1,

    created_on timestamp       default current_timestamp,
    updated_on timestamp       default current_timestamp,

    primary key (teams_id)
);
alter sequence teams_id_seq owned by teams.teams_id;

drop table if exists categories_master;

drop sequence if exists categories_master_id_seq;
create sequence if not exists categories_master_id_seq;

create table categories_master
(
    categories_master_id bigint  not null default nextval('categories_master_id_seq'),
    uuid                 uuid    not null default uuid_generate_v4(),
    version              int              default 0,

    parent_category_id   bigint,
    name                 text    not null,
    type                 text    not null,

    is_active            boolean not null default true,
    is_deleted           boolean not null default false,

    primary key (categories_master_id)
);
alter sequence categories_master_id_seq owned by categories_master.categories_master_id;


drop table if exists rating;

drop sequence if exists rating_id_seq;
create sequence if not exists rating_id_seq;

create table rating
(
    rating_id    bigint   not null default nextval('rating_id_seq'),
    uuid         uuid     not null default uuid_generate_v4(),
    version      int               default 0,

    category_id  bigint   not null,
    users_id     bigint   not null,

    is_active    boolean  not null default true,
    is_deleted   boolean  not null default false,

    created_by   bigint   not null default 1,
    updated_by   bigint   not null default 1,

    created_on   timestamp         default current_timestamp,
    updated_on   timestamp         default current_timestamp,

    primary key (rating_id)
);

alter sequence rating_id_seq owned by rating.rating_id;