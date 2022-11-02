CREATE TABLE if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int
);

CREATE TABLE if not exists candidate (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int,
   photo bytea
);

CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name text,
  email varchar unique,
  password text
);