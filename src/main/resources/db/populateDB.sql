DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, datetime, calories, user_id) VALUES
  ('Завтрак', '2018-06-20 10:00:00', 500,   100000),
  ('Обед',    '2018-06-20 14:00:00', 500,   100000),
  ('Ужин',    '2018-06-20 19:00:00', 510,   100000),
  ('Завтрак', '2018-06-21 10:10:00', 500,   100000),
  ('Обед',    '2018-06-21 14:10:00', 1250,  100000),
  ('Ужин',    '2018-06-21 12:10:00', 750,   100000),
  ('Завтрак', '2018-06-21 10:10:00', 500,   100001),
  ('Обед',    '2018-06-21 14:10:00', 150,   100001),
  ('Ужин',    '2018-06-21 12:10:00', 750,   100001);