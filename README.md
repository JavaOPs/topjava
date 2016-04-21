Java Enterprise Online Project 
===============================

[![Codacy Badge](https://api.codacy.com/project/badge/grade/0aa43bd345364fab8d455f04e367fe0c)](https://www.codacy.com/app/javawebinar/topjava06)


Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

## <a href="https://github.com/JavaOPs/topjava" target=_blank>Вступительное занятие</a>
### <a href="https://github.com/JavaOPs/topjava/description.md">Описание и план проекта</a>
### <a href="https://github.com/JavaOPs/topjava/wiki">Требования к участникам, Wiki</a>
### <a href="http://topjava.herokuapp.com/" target=_blank>Демо разрабатываемого приложения</a>

### Test UserMealRestController (application deployed with context `topjava`).
> For windows use `Git Bash`

#### get All
`curl -s http://localhost:8080/topjava/rest/profile/meals`

#### get 100003
`curl -s http://localhost:8080/topjava/rest/profile/meals/100003`

#### get between
`curl -s "http://localhost:8080/topjava/rest/profile/meals/between?startDateTime=2015-05-30T08:00:00&endDateTime=2015-05-30T16:00:00"`

#### get not found
`curl -s -v http://localhost:8080/rest/meals/100008`

#### delete
`curl -s -X DELETE http://localhost:8080/rest/meals/100002`

#### create
`curl -s -v -X POST -d '{"dateTime":"2015-06-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/topjava/rest/profile/meals`

#### update
`curl -s -v -X PUT -d '{"dateTime":"2015-05-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/topjava/rest/profile/meals/100003`