Get
curl -X GET "http://localhost:8080/topjava/rest/meals/100002"
{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500}

GetAll
curl -X GET "http://localhost:8080/topjava/rest/meals"
[{"id":100008,"dateTime":"2020-01-31T20:00:00","description":"Ужин","calories":510,"excess":true},{"id":100007,"dateTime":"2020-01-31T13:00:00","description":"Обед","calories":1000,"excess":true},{"id":100006,"dateTime":"2020-01-31T10:00:00","description":"Завтрак","calories":500,"excess":true},{"id":100005,"dateTime":"2020-01-31T00:00:00","description":"Еда на граничное значение","calories":100,"excess":true},{"id":100004,"dateTime":"2020-01-30T20:00:00","description":"Ужин","calories":500,"excess":false},{"id":100003,"dateTime":"2020-01-30T13:00:00","description":"Обед","calories":1000,"excess":false},{"id":100002,"dateTime":"2020-01-30T10:00:00","description":"Завтрак","calories":500,"excess":false}]

GetBetween
curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31&startTime=09:30:00&endDate=2020-01-31&endTime=13:30:00"
[{"id":100007,"dateTime":"2020-01-31T13:00:00","description":"Обед","calories":1000,"excess":true},{"id":100006,"dateTime":"2020-01-31T10:00:00","description":"Завтрак","calories":500,"excess":true}]

Create
curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\":\"2021-05-18T09:00:00\",\"description\":\"Fish and chips\", \"calories\":\"999\"}" http://localhost:8080/topjava/rest/meals
{"id":100013,"dateTime":"2021-05-18T09:00:00","description":"Fish and chips","calories":999}

Update
curl -X PUT -H "Content-Type: application/json" -d "{\"dateTime\":\"2021-05-18T09:00:00\",\"description\":\"Pirog\", \"calories\":\"1500\"}" http://localhost:8080/topjava/rest/meals/100013

Delete
curl -X DELETE "http://localhost:8080/topjava/rest/meals/100013"