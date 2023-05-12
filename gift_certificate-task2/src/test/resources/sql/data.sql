INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'fitness', 'free fitness classes for a month', 50.25, 30, '2022-09-30T17:57:55.955', null),
       (2, 'spa', '4 hours of free spa treatments', 26.0, 15, '2022-08-10T18:37:55.955', '2022-09-30T17:57:55.955'),
       (3, 'skydiving', 'free parachute jump', 40.35, 20, '2022-09-15T16:32:55.955', null),
       (4, 'park', 'one-time visit to the water park for free', 50.25, 30, '2022-09-30T17:57:55.955', null),
       (5, 'diving', 'month of free diving lessons', 150.25, 60, '2022-09-06T12:37:55.655', '2022-09-15T16:32:55.955'),
       (6, 'cosmetics', '$100 free cosmetics', 100, 45, '2022-09-22T11:32:55.955', null),
       (7, 'swimming', 'free access to the pool for a month', 45.50, 30, '2022-09-30T09:21:55.955',
        '2022-09-22T11:32:55.955'),
       (8, 'mountaineering', 'free climbing lessons', 50.25, 30, '2022-09-18T18:33:55.955', null),
       (9, 'products', '$50 free products', 50.0, 10, '2022-08-24T22:57:55.955', null),
       (10, 'karting', '5 trips for free', 50.25, 30, '2022-09-30T14:30:55.955', '2022-10-01T10:00:55.955');
SELECT SETVAL('gift_certificate_id_seq', (SELECT max(id) FROM gift_certificate));

INSERT INTO tag (id, name)
VALUES (1, 'sport'),
       (2, 'health'),
       (3, 'travel'),
       (4, 'beauty'),
       (5, 'extreme'),
       (6, 'relaxation');
SELECT SETVAL('tag_id_seq', (SELECT max(id) FROM tag));

INSERT INTO certificate_tag (certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (2, 2),
       (2, 6),
       (3, 1),
       (3, 5),
       (4, 6),
       (5, 1),
       (5, 2),
       (5, 3),
       (6, 4),
       (7, 1),
       (7, 2),
       (8, 1),
       (8, 2),
       (8, 3),
       (8, 5),
       (10, 5);

INSERT INTO users (id, username, firstname, lastname, tel, address)
VALUES (1, 'ruslan@mail.ru', 'Ruslan', 'Niyazov', '375295857929', 'Gavrilova 1'),
       (2, 'ivan@mail.ru', 'Ivan', 'Ivanov', '375297841278', 'Stafeeva 16'),
       (3, 'petr@mail.ru', 'Petr', 'Petrov', '375336307328', 'Laktionova 115');
SELECT SETVAL('users_id_seq', (SELECT max(id) FROM users));