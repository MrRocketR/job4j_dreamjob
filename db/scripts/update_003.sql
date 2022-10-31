CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  name text,
  email varchar(255) unique,
  password text
);