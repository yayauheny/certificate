--liquibase formatted sql

--changeset yayauheny:1
CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(32) UNIQUE NOT NULL,
    description      VARCHAR(128)        NOT NULL,
    price            DECIMAL            NOT NULL,
    duration         INT                NOT NULL,
    create_date      TIMESTAMP          NOT NULL,
    last_update_date TIMESTAMP
    );

--changeset yayauheny:2
CREATE TABLE IF NOT EXISTS tag
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
    );

--changeset yayauheny:3
CREATE TABLE IF NOT EXISTS certificate_tag
(
    certificate_id INT REFERENCES gift_certificate (id) ON DELETE CASCADE,
    tag_id         INT REFERENCES tag (id) ON DELETE CASCADE,
    PRIMARY KEY (certificate_id, tag_id)
    );