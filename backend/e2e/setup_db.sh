#!/usr/bin/env bash

if [[ -z "${DATABASE_URL}" ]]; then
    psql -U postgres -c 'create database biodela;'
    PSQL_COMMAND="psql -U postgres -d biodela -c"
else
    PSQL_COMMAND="psql ${DATABASE_URL} -c"
fi

${PSQL_COMMAND} "CREATE TABLE users ( \
user_id serial primary key, \
user_name varchar(255) NOT NULL, \
password varchar(255) NOT NULL,\
email varchar(255));"

${PSQL_COMMAND} "INSERT INTO users (user_name, password, email) VALUES \
('admin', '\$31\$16\$fKCs6_JmXHQA1tvDYEdEpT5gfA_dsboQWfJttryqB98', 'admin@biodela.nu');"

${PSQL_COMMAND} "CREATE TABLE tickets (\
ticket_id serial primary key,\
code varchar(255) NOT NULL,\
expiry_date date NOT NULL,\
created_at timestamp default current_timestamp,\
provider integer NOT NULL,\
user_id integer,\
used boolean);"