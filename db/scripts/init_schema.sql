CREATE TABLE if not exists POST (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int
);

CREATE TABLE if not exists CANDIDATE (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp,
   city_id int,
   photo bytea
);

CREATE TABLE USERS (
  id SERIAL PRIMARY KEY,
  name text,
  email varchar unique,
  password text
);

