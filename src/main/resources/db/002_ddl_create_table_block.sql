--liquibase formatted sql

--changeset lopatuxin:2
create table block(
                      id serial primary key ,
                      name varchar(100) not null ,
                      description text

);