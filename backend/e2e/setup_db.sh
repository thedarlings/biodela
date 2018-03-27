#!/usr/bin/env bash
set -e
psql -c 'create database biodela;' -U postgres

psql -c "CREATE TABLE users ( \
user_id serial primary key, \
user_name varchar(255) NOT NULL, \
password varchar(255) NOT NULL,\
email varchar(255));"  -U postgres biodela

psql -c "INSERT INTO users (user_name, password, email) VALUES \
('admin', '\$31\$16\$fKCs6_JmXHQA1tvDYEdEpT5gfA_dsboQWfJttryqB98', 'admin@biodela.nu');" \
 -U postgres biodela

psql -c "CREATE TABLE tickets (\
ticket_id serial primary key,\
code varchar(255) NOT NULL,\
expiry_date date NOT NULL,\
created_at timestamp default current_timestamp,\
provider integer NOT NULL,\
user_id integer,\
used boolean);" -U postgres biodela