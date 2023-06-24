DELETE FROM user_role;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, registered)
VALUES ('User', 'user@yandex.ru', 'password', '2020-01-01 10:00:00'),
       ('Admin', 'admin@gmail.com', 'admin', '2020-01-01 10:00:00'),
       ('Guest', 'guest@gmail.com', 'guest', '2020-01-01 10:00:00');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES ('100000', '2020-01-30 10:00:00', 'Завтрак', '500'),
       ('100000', '2020-01-30 13:00:00', 'Обед', '1000'),
       ('100000', '2020-01-30 20:00:00', 'Ужин', '500'),
       ('100000', '2020-01-31 00:00:00', 'Еда на граничное значение', '100'),
       ('100000', '2020-01-31 10:00:00', 'Завтрак', '1000'),
       ('100000', '2020-01-31 13:00:00', 'Обед', '500'),
       ('100000', '2020-01-31 20:00:00', 'Ужин', '410');
