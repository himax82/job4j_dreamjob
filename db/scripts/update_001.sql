CREATE TABLE if not exists post (
                      id SERIAL PRIMARY KEY,
                      name varchar(200),
                      created timestamp
);

create table if not exists city
(
    id            serial primary key,
    name          varchar(200)
);

CREATE TABLE if not exists candidate (
                           id SERIAL PRIMARY KEY,
                           name varchar(200),
                           cityId int references city (id),
                           created timestamp
);

CREATE TABLE if not exists users (
                       id SERIAL PRIMARY KEY,
                       name varchar(200),
                       email varchar(200) UNIQUE,
                       password varchar(200)
);

