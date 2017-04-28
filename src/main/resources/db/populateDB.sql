DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals ( user_id, datetime, description,calories) VALUES
  (100000, '2015-05-30 10:00:00.000000', 'завтрак',1000),
  (100000, '2015-05-30 15:00:00.000000', 'обед',1000),
  (100000, '2015-05-30 19:00:00.000000', 'ужин',1000),
  (100000, '2015-05-31 10:00:00.000000', 'завтрак',1000),
  (100000, '2015-05-31 15:00:00.000000', 'обед',1000),
  (100000, '2015-05-31 19:00:00.000000', 'ужин',1000),
  (100001, '2015-05-30 10:00:00.000000', 'завтрак',1000),
  (100001, '2015-05-30 15:00:00.000000', 'обед',1000),
  (100001, '2015-05-30 19:00:00.000000', 'ужин',1000);