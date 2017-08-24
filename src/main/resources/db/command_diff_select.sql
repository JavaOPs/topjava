DROP INDEX IF EXISTS meals_unique_user_datetime_idx;

EXPLAIN ANALYZE SELECT * FROM meals WHERE user_id = 100000 AND date_time BETWEEN '2015-02-10' AND '2015-05-20' ORDER BY date_time DESC;

EXPLAIN ANALYZE SELECT * FROM meals WHERE user_id = 100003 ORDER BY date_time DESC;

CREATE INDEX meals_idx ON meals (user_id);

EXPLAIN ANALYZE SELECT * FROM meals WHERE user_id = 100000 AND date_time BETWEEN '2015-02-10' AND '2015-05-20' ORDER BY date_time DESC;