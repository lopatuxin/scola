--liquibase formatted sql

--changeset lopatuxin:3
create table lesson(
                       id serial primary key ,
                       name varchar(100) not null ,
                       description text,
                       block_id int references block(id)
)