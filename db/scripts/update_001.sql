CREATE TABLE if not exists post (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      created timestamp
);

CREATE TABLE if not exists candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT,
                           cityId int references city (id),
                           created timestamp
);

CREATE TABLE if not exists users (
                       id SERIAL PRIMARY KEY,
                       name TEXT UNIQUE,
                       email TEXT,
                       password TEXT
);

create table if not exists city
(
    id            serial primary key,
    name          text
);