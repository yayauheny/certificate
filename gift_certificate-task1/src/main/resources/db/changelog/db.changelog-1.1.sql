--liquibase formatted sql

--changeset yayauheny:1
CREATE TABLE IF NOT EXISTS users
(
    id        SERIAL PRIMARY KEY,
    username  VARCHAR(32) UNIQUE NOT NULL,
    firstname VARCHAR(32)        NOT NULL,
    lastname  VARCHAR(32)        NOT NULL,
    tel       VARCHAR(32)        NOT NULL,
    address   VARCHAR(32)        NOT NULL
    );

--changeset yayauheny:2
CREATE TABLE IF NOT EXISTS orders
(
    id             SERIAL PRIMARY KEY,
    user_id        INT REFERENCES users (id) ON DELETE CASCADE,
    certificate_id INT REFERENCES gift_certificate (id) ON DELETE CASCADE,
    price          DECIMAL NOT NULL,
    buy_date       TIMESTAMP NOT NULL
    );