## Запросы к таблице meals c разным index (1000 пользователей, у каждого за год по 3 записи в день):

### Проверьте у себя, цифры у всех разные!

### Скрипт по наполнению базы на PL/pgSQL:
```
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
```
### Без индекса
**время выполнения 200ms/150ms**

> DROP INDEX IF EXISTS meals_unique_user_datetime_idx;

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100000 AND date_time BETWEEN '2015-02-10' AND '2015-05-20'
          ORDER BY date_time DESC;
          
```
Sort  (cost=28292.17..28292.88 rows=286 width=31) (actual time=203.774..203.782 rows=298 loops=1)
  Sort Key: date_time DESC
  Sort Method: quicksort  Memory: 48kB
  ->  Seq Scan on meals  (cost=0.00..28280.50 rows=286 width=31) (actual time=0.023..203.714 rows=298 loops=1)
        Filter: ((date_time >= '2015-02-10 00:00:00'::timestamp without time zone) AND (date_time <= '2015-05-20 00:00:00'::timestamp without time zone) AND (user_id = 100000))
        Rows Removed by Filter: 1094702
Planning time: 0.102 ms
Execution time: 203.822 ms
```

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100003
          ORDER BY date_time DESC;

```
Sort  (cost=22858.59..22861.23 rows=1057 width=31) (actual time=147.481..147.481 rows=0 loops=1)
  Sort Key: date_time DESC
  Sort Method: quicksort  Memory: 25kB
  ->  Seq Scan on meals  (cost=0.00..22805.50 rows=1057 width=31) (actual time=147.474..147.474 rows=0 loops=1)
        Filter: (user_id = 100003)
        Rows Removed by Filter: 1095000
Planning time: 0.097 ms
Execution time: 147.514 ms
```

### Индекс по user_id;
**время выполнения 0.415ms/0.05ms**

> CREATE INDEX meals_idx ON meals (user_id);

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100000 AND date_time BETWEEN '2015-02-10' AND '2015-05-20'
          ORDER BY date_time DESC;

```
Sort  (cost=59.88..60.59 rows=286 width=31) (actual time=0.372..0.378 rows=298 loops=1)
  Sort Key: date_time DESC
  Sort Method: quicksort  Memory: 48kB
  ->  Index Scan using meals_user_id_idx on meals  (cost=0.43..48.21 rows=286 width=31) (actual time=0.048..0.307 rows=298 loops=1)
        Index Cond: (user_id = 100000)
        Filter: ((date_time >= '2015-02-10 00:00:00'::timestamp without time zone) AND (date_time <= '2015-05-20 00:00:00'::timestamp without time zone))
        Rows Removed by Filter: 797
Planning time: 0.123 ms
Execution time: 0.414 ms
```

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100003
          ORDER BY date_time DESC;

```
Sort  (cost=96.02..98.66 rows=1057 width=31) (actual time=0.020..0.020 rows=0 loops=1)
  Sort Key: date_time DESC
  Sort Method: quicksort  Memory: 25kB
  ->  Index Scan using meals_user_id_idx on meals  (cost=0.43..42.93 rows=1057 width=31) (actual time=0.013..0.013 rows=0 loops=1)
        Index Cond: (user_id = 100003)
Planning time: 0.095 ms
Execution time: 0.047 ms
```

### Составной индекс по user_id и date_time (ЗАВИСИТ ОТ ПОРЯДКА ЗАДАНИЯ КОЛОНОК!)
**время выполнения 0.110ms/0.035ms**

> DROP INDEX meals_idx;

> CREATE INDEX meals_idx ON meals (user_id, date_time);

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100000 AND date_time BETWEEN '2015-02-10' AND '2015-05-20'
          ORDER BY date_time DESC;

```
Index Scan Backward using meals_user_id_date_time_idx on meals  (cost=0.43..511.74 rows=286 width=31) (actual time=0.026..0.081 rows=298 loops=1)
  Index Cond: ((user_id = 100000) AND (date_time >= '2015-02-10 00:00:00'::timestamp without time zone) AND (date_time <= '2015-05-20 00:00:00'::timestamp without time zone))
Planning time: 0.130 ms
Execution time: 0.112 ms
```

> EXPLAIN ANALYZE
         SELECT * FROM meals
          WHERE user_id = 100003
          ORDER BY date_time DESC;

```
Index Scan Backward using meals_user_id_date_time_idx on meals  (cost=0.43..1795.68 rows=1057 width=31) (actual time=0.012..0.012 rows=0 loops=1)
  Index Cond: (user_id = 100003)
Planning time: 0.103 ms
Execution time: 0.033 ms
```
