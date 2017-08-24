drop function if exists populate_db();

create function populate_db() returns void as $$
declare
  user_id integer;
  meal_date timestamp;
begin
  DELETE FROM meals;
  DELETE FROM users;
  ALTER SEQUENCE global_seq RESTART WITH 100000;

  for i in 1..1000 loop
    INSERT INTO users (name, email, password)
    VALUES ('User' || i, 'user' || i || '@yandex.ru', 'password');

    user_id := currval('global_seq');

    for j in 1..365 loop
      meal_date := date '2014-12-31 10:00' + j;
      INSERT INTO meals (date_time, description, calories, user_id) VALUES
        (meal_date, 'Breakfast ' || j, 500, user_id);
      meal_date := meal_date + interval '4';
      INSERT INTO meals (date_time, description, calories, user_id) VALUES
        (meal_date, 'Lunch ' || j, 1000, user_id);
      meal_date := meal_date + interval '5';
      INSERT INTO meals (date_time, description, calories, user_id) VALUES
        (meal_date, 'Dinner ' || j, 500, user_id);
    end loop;
  end loop;
end;
$$ LANGUAGE plpgsql;

select populate_db();