CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      name TEXT
);

CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT
);

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       name TEXT UNIQUE,
                       email TEXT,
                       password TEXT
);