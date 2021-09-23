CREATE TABLE IF NOT EXISTS posts(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(256) NOT NULL,
    text    VARCHAR(10000),
    link    VARCHAR(256) NOT NULL,
    created TIMESTAMP
);

create table IF NOT EXISTS rabbit (
    id           SERIAL PRIMARY KEY,
    created_date bigint
);