CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      name TEXT,
                      created timestamp
);

CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT,
                           cityId int references city (id),
                           created timestamp
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name TEXT UNIQUE,
                       email TEXT,
                       password TEXT
);

create table city
(
    id            serial primary key,
    name          text
);