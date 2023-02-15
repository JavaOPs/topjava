TRUNCATE user_roles;
TRUNCATE users CASCADE;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
                                              ('User', 'user@yandex.ru', 'password'),
                                              ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
                                           ('ROLE_USER', 100000),
                                           ('ROLE_ADMIN', 100001);