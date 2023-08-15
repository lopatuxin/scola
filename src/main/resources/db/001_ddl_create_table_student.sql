--liquibase formatted sql

--changeset lopatuxin:1
create table student(
                        id serial primary key ,
                        name varchar(100) not null ,
                        email varchar(200) not null unique ,
                        password varchar(200) not null,
                        date_of_birth date not null,
                        role varchar(50) not null,
                        surname varchar(100) not null ,
                        date_of_registration date not null
);