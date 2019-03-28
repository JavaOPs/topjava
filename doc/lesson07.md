# [Онлайн стажировка Spring 5/JPA Enterprise (Topjava)](http://javaops.ru/view/topjava)
## REST, REST контроллеры, тестирование Spring MVC контроллеров (несколько видео открыто)
## [Формат обучения на стажировке](http://javaops.ru/#format)
- Не стоит стремиться прочитать все ссылки урока, их можно использовать как справочник. Гораздо важнее пройти основной материал урока и сделать Домашнее Задание
- Обязательно посмотри <a href="https://github.com/JavaOPs/topjava/wiki/Git#Правила-работы-с-патчами-на-проекте">правила работы с патчами на проекте</a>
- Делать Apply Patch лучше по одному, непосредственно перед видео на эту тему, а при просмотре видео сразу отслеживать все изменения кода проекта по изменению в патче (`Version Control->Local Changes-> Ctrl+D`)
- При первом Apply удобнее выбрать имя локального ченджлиста Name: Default. Далее все остальные патчи также будут в него попадать.
- Код проекта обновляется и не всегда совпадает с видео (можно увидеть как развивался проект). Изменения в проекте указываю после соответствующего патча.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW6

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. HW6
#### Apply 7_01_HW6_fix_tests.patch
> - Добавил  `AbstractServiceTest.isJpaBased()` и `Assume.assumeTrue(isJpaBased())` в `AbstractMealServiceTest.testValidation()`. 
> - Как вариант можно было вместо наследования от `AbstractJpaUserServiceTest` сделать `@Autowired(required = false) JpaUtil` (в этом случае, если в профиле jpa/datajpa не объявить `JpaUtil`, в тестах будет NPE)
> - В новой версии Spring классы `spring-mvc` требуют `WebApplicationContext`, поэтому поправил `inmemory.xml`

   
#### Apply 7_02_HW6_meals.patch
При переходе на AJAX `JspMealController` удалим за ненадобностью, возвращение всей еды `meals()` останется в `RootController`.

#### Apply 7_03_HW6_fix_relative_url_utf8.patch
-  <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">Relative paths in JSP</a>
-  <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">Spring redirect: prefix</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. HW6 Optional
#### Apply 7_04_HW6_optional_add_role.patch
`JdbcUserServiceTest` отвалились. Будем чинить в `7_06_HW6_optional_jdbc.patch`

#### Apply 7_05_fix_hint_graph.patch
- В `JpaUserRepositoryImpl.getByEmail` DISTINCT попадает в запрос, хотя он там не нужен. Это просто указание Hibernate не дублировать данные.
Для оптимизации можно указать Hibernate делать запрос без distinct: [15.16.2. Using DISTINCT with entity queries](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct)
- Тест `DataJpaUserServiceTest.testGetWithMeals()` не работает для admin (у админа 2 роли и еда при JOIN дублируется). `DISTINCT` при несколький JOIN не помогает.
Оставил в графе только `meals`. Корректно поставить тип `LOAD`, чтобы остальные ассоциации доставались по стратегии модели. Однако [с типом по умолчанию `FETCH` роли также достаются](https://stackoverflow.com/a/46013654/548473)  (похоже что бага).

#### Apply 7_06_HW6_optional_jdbc.patch
> - реализовал в `JdbcUserRepositoryImpl.getAll()` доставание ролей через лямбду
> - в `insertRoles` поменял метод `batchUpdate` и сделал проверку на empty 
   
Еще интересные JDBC реализации:
 - в `getAll()/ get()/ getByEmail()` делать запросы с `LEFT JOIN` и сделать реализацию `ResultSetExtractor`
 - подключить зависимость `spring-data-jdbc-core`. Там есть готовый `OneToManyResultSetExtractor`. Можно посмотреть, как он реализован. 
 - реализация, зависимая от БД (для postgres): доставать агрегированные роли и делать им `split(",")`:
```
...
```

## Занятие 7:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. Тестирование Spring MVC
#### Apply 7_07_controller_test.patch
> - в `MockMvc` добавился `CharacterEncodingFilter`
> - добавил `AllActiveProfileResolver`
> - реализация Ehcache нетранзакционная, после отката транзакции в тестах по `@Transactional` Hibernate кэш не восстанавливал роль для USER. Добавил очистку кэша Hibernate в `AbstractControllerTest.setUp` (с учетом того, что `Profiles.REPOSITORY_IMPLEMENTATION` можно переключить на `JDBC`).

-  <a href="https://github.com/hamcrest/hamcrest-junit">Hamcrest</a>
-  <a href="http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/">Unit Testing of Spring MVC Controllers</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  4. [Миграция на JUnit 5](https://www.youtube.com/watch?v=YmLzT-j1hU4)
#### Apply 7_08_JUnit5.patch
> [No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)

- [JUnit 5 homepage](https://junit.org/junit5)
- [Overview](https://junit.org/junit5/docs/snapshot/user-guide/#overview)
- [10 интересных нововведений](https://habr.com/post/337700)
- Дополнительно:
    - [Extension Model](https://junit.org/junit5/docs/current/user-guide/#extensions)
    - [A Guide to JUnit 5](http://www.baeldung.com/junit-5)
    - [Migrating from JUnit 4](http://www.baeldung.com/junit-5-migration)
    - [Before and After Test Execution Callbacks](https://junit.org/junit5/docs/snapshot/user-guide/#extensions-lifecycle-callbacks-before-after-execution)
    - [Conditional Test Execution](https://junit.org/junit5/docs/snapshot/user-guide/#writing-tests-conditional-execution)
    - [Third party Extensions](https://github.com/junit-team/junit5/wiki/Third-party-Extensions)
    - [Реализация assertThat](https://stackoverflow.com/questions/43280250)


### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Принципы REST. REST контроллеры](https://youtu.be/33_2yOck4ak)
#### Apply 7_09_rest_controller.patch
 
-  <a href="http://spring-projects.ru/understanding/rest/">Понимание REST</a>
-  <a href="https://ru.wikipedia.org/wiki/JSON">JSON (JavaScript Object Notation)</a>
- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- [10 Best Practices for Better RESTful](http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/)
- [Best practices for rest nested resources](https://stackoverflow.com/questions/20951419/what-are-best-practices-for-rest-nested-resources)
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestmapping">Request mapping</a>
-  Дополнительно:
   -  <a href="http://www.infoq.com/articles/springmvc_jsx-rs">JAX-RS vs Spring MVC</a>
   - <a href="http://habrahabr.ru/post/144011/">RESTful API для сервера – делаем правильно (Часть 1)</a>
   - <a href="http://habrahabr.ru/post/144259/">RESTful API для сервера – делаем правильно (Часть 2)</a>
   - <a href="https://www.youtube.com/watch?v=Q84xT4Zd7vs&list=PLoij6udfBncivGZAwS2yQaFGWz4O7oH48">И. Головач. RestAPI</a>
   - [value/name в аннотациях @PathVariable и @RequestParam](https://habr.com/ru/post/440214/)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. [Тестирование REST контроллеров. Jackson](https://drive.google.com/file/d/1aZm2qoMh4yL_-i3HhRoyZFjRAQx-15lO)
#### Apply 7_10_rest_test_jackson.patch
-  [Jackson databind github](https://github.com/FasterXML/jackson-databind)
-  [Jackson Annotation Examples](https://www.baeldung.com/jackson-annotations)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Кастомизация Jackson Object Mapper](https://drive.google.com/file/d/1CM6y1JhKG_yeLQE_iCDONnI7Agi4pBks)
    
#### Apply 7_11_jackson_object_mapper.patch
-  Сериализация hibernate lazy-loading с помощью <a href="https://github.com/FasterXML/jackson-datatype-hibernate">jackson-datatype-hibernate</a>
-  <a href="https://geowarin.github.io/jsr310-dates-with-jackson.html">Handle Java 8 dates with Jackson</a>
-  Дополнительно:
   - <a href="https://www.sghill.net/how-do-i-write-a-jackson-json-serializer-deserializer.html">Jackson JSON Serializer & Deserializer</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Тестирование REST контроллеров через JSONassert](https://drive.google.com/file/d/1oa3e0_tG57E71g6PW7_tfb3B61Qldctl)</a>
#### Apply 7_12_json_assert_tests.patch
- [JSONassert](https://github.com/skyscreamer/JSONassert)
- [Java Code Examples for ObjectMapper](https://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.databind.ObjectMapper)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. Тестирование через SoapUi. UTF-8
#### Apply 7_13_soapui_utf8_converter.patch
- Инструменты тестирования REST:
  - <a href="http://www.soapui.org/">SoapUi</a>
  - <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a>
(для Windows можно использовать Git Bash). Для работы с UTF-8 в Windows 10 нужны пляски с бубном: ["Язык и региональные стандарты" -> "Сопутствующие параметры" -> "Административные языковые параметры" -> "Изменить язык системы" -> галка "Бета-версия:Использовать Юникод (UTF-8) для поддержки языка во всем мире"](https://drive.google.com/open?id=1J1WquTv9wenJQ9ptMymXPYGnrvFzAV-L), перезагрузка.
  - <a href="https://www.getpostman.com/">Postman</a>
  - <a href="https://www.jetbrains.com/help/idea/rest-client-tool-window.html">IDEA: Tools->HTTP Client->...</a>
  - [Insomnia REST client](https://insomnia.rest/)

**Импортировать проект в SoapUi из config\Topjava-soapui-project.xml. Response смотреть в формате JSON.**

>  Проверка UTF-8: <a href="http://localhost:8080/topjava/rest/profile/text">http://localhost:8080/topjava/rest/profile/text</a>

<a href="http://forum.spring.io/forum/spring-projects/web/74209-responsebody-and-utf-8">ResponseBody and UTF-8</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> При выполнении тестов через MockMvc никаких изменений на базе не видно, почему оно не сохраняет?

`AbstractControllerTest` аннотируется `@Transactional` - это означает, что тесты идут в транзакции и после каждого теста JUnit делает rollback базы.

> Что получается в результате выполнения запроса `SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email`? В чем разница в SQL без `DISTINCT`. 

Запросы SQL можно посмотреть в логах. Те `DISTINCT` в `JPQL` влияет на то, как Hibernate обрабатывает дублирующие записи (с `DISTINCT` их исключает). Результат можно посмотреть в тестах или приложении, поставив брекпойнт.
По поводу `SQL DISTINCT` не стесняйтесь пользоваться google, например [оператор SQL DISTINCT](http://2sql.ru/novosti/sql-distinct/)

>  В чем заключается расширение функциональности hamcrest в нашем тесте, что нам пришлось его отдельно от JUnit прописывать?

hamcrest-all используется в проверках `RootControllerTest`: `org.hamcrest.Matchers.*`

>  Jackson мы просто подключаем в помнике и спринг будет с ним работать без любых других настроек?

Да, Spring смотрит в classpath и, если видит там Jackson, то подключает интеграцию с ним

>  Где-то слышал, что любой ресурс по REST должен однозначно идентифицироваться через url, без параметров. Правильно ли задавать URL для фильтрации в виде `http://localhost/topjava/rest/meals/filter/{startDate}/{startTime}/{endDate}/{endTime}` ?

Так делают, только при отношении <a href="https://ru.wikipedia.org/wiki/Диаграмма_классов#.D0.90.D0.B3.D1.80.D0.B5.D0.B3.D0.B0.D1.86.D0.B8.D1.8F">агрегация</a>. В случае критериев, поиска или страничных данных они передаются как параметр. Смотри также:
- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- <a href="http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/">10 Best Practices for Better RESTful

> Что означает конструкция в `JsonUtil`: `reader.<T>readValues(json)`;

См. <a href="https://docs.oracle.com/javase/tutorial/java/generics/methods.html">Generic Methods</a>. Когда компилятор не может вывести тип, можно его уточнить при вызове generic метода. Не важно static или нет.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW07

- 1: Добавить тесты контроллеров:
  - 1.1 `RootControllerTest.testMeals` для `meals.jsp`
  - 1.2 `ResourceControllerTest` для `style.css` (проверить status и ContentType)
- 2: Реализовать `MealRestController` и протестировать его через `MealRestControllerTest`
  - 2.1 cледите чтобы url в тестах совпадал с параметрами в методе контроллера. Можно добавить логирование `<logger name="org.springframework.web" level="debug"/>` для проверки маршрутизации.
  - 2.2 в параметрах `getBetween` принимать `LocalDateTime` (конвертировать через <a href="http://blog.codeleak.pl/2014/06/spring-4-datetimeformat-with-java-8.html">@DATETIMEFORMAT WITH JAVA 8 DATE-TIME API</a>), а передавать в тестах в формате `ISO_LOCAL_DATE_TIME` (например `'2011-12-03T10:15:30'`). Вызывать `super.getBetween()` пока без проверки на `null`, используя `toLocalDate()/toLocalTime()` (см. Optional п.3)

#### Optional
- 3: Переделать `MealRestController.getBetween` на параметры `LocalDate/LocalTime` c раздельной фильтрацией по времени/дате, работающий при `null` значениях (см. демо и `JspMealController.getBetween`). Заменить `@DateTimeFormat` на свои LocalDate/LocalTime конверторы или форматтеры.
  -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#core-convert">Spring Type Conversion</a>
  -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#format">Spring Field Formatting</a>
  -  <a href="http://stackoverflow.com/questions/13048368/difference-between-spring-mvc-formatters-and-converters">Difference between Spring MVC formatters and converters</a>
- 4: Протестировать `MealRestController` (SoapUi, curl, IDEA Test RESTful Web Service, Postman). Запросы `curl` занести в отдельный `md` файл (либо `README.md`)
  
**На следующем занятии используется JavaScript/jQuery. Если у вас там пробелы, <a href="https://github.com/JavaOPs/topjava#html-javascript-css">пройдите его основы</a>**

---------------------
## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW07
- 1: Ошибка в тесте _Invalid read array from JSON_ обычно расшифровывается немного ниже: читайте внимательно.
- 2: <a href="https://urvanov.ru/2016/12/03/jackson-и-неизменяемые-объекты/">Jackson и неизменяемые объекты</a>
- 3: <a href="http://www.baeldung.com/jackson">Jackson JSON Tutorial</a>
- 4: Если у meal, приходящий в контроллер, поля null, проверьте `@RequestBody` перед параметром (данные приходят в формате JSON)
- 5: При проблемах с собственным форматтером убедитесь, что в конфигурации `<mvc:annotation-driven...` не дублируется
- 6: **Проверьте выполение ВСЕХ тестов через maven**. В случае проблем проверьте, что не портите константу из `MealTestData`
- 7: `@Autowired` в тестах нужно делать в том месте, где класс будет использоваться. Общий принцип: не размазывать код по классам, объявление переменных держать как можно ближе к ее использованию, группировать (не смешивать) код с разной функциональностью.
- 8: Попробуйте в `RootControllerTest.testMeals` сделать сравнение через `model().attribute("meals", expectedValue)`. Учтите, что вывод результатов через `toString` к сравнению отношения не имеет
- 9: Посмотрите, нет ли в `MealTestData` методов, которые можно сделать общими (через generic и `TestUtil`)
