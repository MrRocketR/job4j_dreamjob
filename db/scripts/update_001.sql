CREATE TABLE if not exists post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created timestamp
);