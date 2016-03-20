DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2015-05-05 04:05:06', 'Завтрак', 600, 100000),
  ('2010-05-31 22:00:00', 'Ужин', 500, 100000),
  ('2008-10-25 10:00:00', 'Завтрак', 600, 100001),
  ('2015-11-25 15:00:00', 'Обед', 800, 100001),
  ('2011-01-01 14:00:00', 'Плотный обед', 1600, 100001),
  ('2011-01-01 10:00:00', 'Завтрак', 500, 100001);