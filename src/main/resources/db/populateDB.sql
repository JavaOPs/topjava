DELETE FROM user_roles;
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
  ('2015-05-05 04:05:06', 'Ужин', 600, 100000),
  ('2010-05-31 22:00:00', 'Завтрак', 500, 100000),
  ('2015-12-12 12:00:00', 'Обед', 800, 100001);
