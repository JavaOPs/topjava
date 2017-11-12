# Онлайн проекта <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfjVnUVhINEg0d09Nb3JsY2ZZZmpsSWp3bzdHMkpKMmtPTlpjckxyVzg0SWc">Материалы занятия</a>

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Fix/Refactoring
> В [README.md](https://raw.githubusercontent.com/JavaWebinar/topjava/master/README.md) заменил нерабочий [DependencyCi](https://dependencyci.com) на  [VersionEye](https://www.versioneye.com) (в том числе проверка зависимостей на актуальность)

**Сделайте интеграцию в своем проекте самостоятельно**
 
#### Apply 7_0_1_update_version.patch
> Согласно [рапорту VersionEye](https://www.versioneye.com/user/projects/59a1dbe46725bd0037d98369) обновил версии зависимостей 

#### Apply 7_0_2_fix_user.patch
> - В новой версии `hibernate-validator` некоторые валидаторы `org.hibernate.validator.constraints` стали *@Deprecated*. Поменял на `javax.validation.constraints`
> - Внес в конструктор registered
> - Делаю копию `roles`, чтобы роли нельзя было изменить после инициализации.

#### Apply 7_0_3_fix_matcher.patch
> Поправил `BeanMatcher`: можно сравнивать только упорядоченные коллекции (List) 

#### Apply 7_0_4_mapping_change.patch
> В Spring 4.3 ввели новые аннотации `@Get/Post/...Mapping` (сокращенный вариант `@RequestMapping`)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW6

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFbUhMdTdESkpFekE">HW6</a>
#### Apply 7_01_HW6_fix_tests.patch
> - Добавил  `AbstractServiceTest.isJpaBased()` и `Assume.assumeTrue(isJpaBased())` в `AbstractMealServiceTest.testValidation()`. 
> - Как вариант можно было вместо наследования от `AbstractJpaUserServiceTest` сделать `@Autowired(required = false) JpaUtil` (в этом случае, если в профиле jpa/datajpa не объявить `JpaUtil`, тестах будет NPE)
> - В новой версии Spring классы `spring-mvc` требуют `WebApplicationContext`, поэтому поправил `mock.xml`

   
#### Apply 7_02_HW6_meals.patch
При переходе на AJAX `JspMealController` удалим за ненадобностью, а `getAll()` по GET `/meals` останется в `RootController`.

-  <a href="http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page">Root path in JSP</a>

#### Apply 7_03_HW6_fix_relative_url_utf8.patch
-  <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">Relative paths in JSP</a>
-  <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">Spring redirect: prefix</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFaXViWkkwYkF0eW8">HW6 Optional</a>
#### Apply 7_04_HW6_optional_add_role.patch
#### Apply 7_05_hint_distinct.patch
- В `JpaUserRepositoryImpl.getByEmail` DISTINCT попадает в запрос, хотя он там не нужен. Это просто указание Hibernate не дублировать данные.
Для оптимизации можно указать Hibernate делать запрос без distinct: [15.16.2. Using DISTINCT with entity queries](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct)


#### Apply 7_06_HW6_optional_jdbc.patch
> - реализовал в `JdbcUserRepositoryImpl.getAll()` доставание ролей через лямбду  
> - в `insertRoles` поменял метод `batchUpdate` и сделал проверку на empty 
   
Еще интересные jdbc реализации:
 - в `getAll()/ get()/ getByEmail()` делать запросы с `LEFT JOIN` и сделать `ResultSetExtractor`  
 - подключить зависимость `spring-data-jdbc-core`. Там есть готовый `OneToManyResultSetExtractor`. Можно посмотреть, как он реализован. 
 - зависимый от БД: доставать агрегированные роли и делать им `split(",")`:
```
SELECT u.*, string_agg(r.role, ',') AS roles
FROM users u
  JOIN user_roles r ON u.id=r.user_id
GROUP BY u.id
```

## Занятие 7:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQXhBN1pqa3FyOUE">Тестирование Spring MVC</a>
#### Apply 7_07_controller_test.patch
> - в `MockMvc` добавился `CharacterEncodingFilter`
> - добавил `AllActiveProfileResolver`

-  <a href="https://github.com/hamcrest/hamcrest-junit">Hamcrest</a>
-  <a href="http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/">Unit Testing of Spring MVC Controllers</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFWE5oSmJFZGZBRlE">REST контроллеры</a>
#### Apply 7_08_rest_controller.patch
 
-  <a href="https://ru.wikipedia.org/wiki/JSON">JSON (JavaScript Object Notation</a>
-  <a href="https://spring.io/understanding/rest">Understanding REST</a>
-  <a href="http://www.infoq.com/articles/springmvc_jsx-rs">JAX-RS vs Spring MVC</a>
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-ann-requestmapping">Request mapping</a>
-  Ресурсы:
   - <a href="http://habrahabr.ru/post/144011/">RESTful API для сервера – делаем правильно (Часть 1)</a>
   - <a href="http://habrahabr.ru/post/144259/">RESTful API для сервера – делаем правильно (Часть 2)</a>
   - <a href="https://www.youtube.com/playlist?list=PLtDz82bWepMPLi_e9YbatLRpm0z4uOs_U">И. Головач. RestAPI</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQmNwOXJ6RFk4M1U">Тестирование REST контроллеров. Jackson.</a>
#### Apply 7_09_rest_test_jackson.patch
-  https://github.com/FasterXML/jackson-databind

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNWEyVGJIU2JMTFE">jackson-datatype-hibernate. Тестирование через матчеры.</a>
    
#### Apply 7_10_jackson_hibernate.patch
-  <a href="https://www.sghill.net/how-do-i-write-a-jackson-json-serializer-deserializer.html">Jackson JSON Serializer & Deserializer</a>
-  Сериализация hibernate lazy-loading с помощью <a href="https://github.com/FasterXML/jackson-datatype-hibernate">jackson-datatype-hibernate</a>

#### Apply 7_11_jackson_jsr310.patch
>  Переводим сериализацию-десериализацию LocalDateTime на jsr310 и стандартный формат ISO-8601

-  <a href="https://geowarin.github.io/jsr310-dates-with-jackson.html">Handle Java 8 dates with Jackson</a>

#### Apply 7_12_test_with_matcher.patch
-  <a href="http://habrahabr.ru/post/259055/">Тестируем Spring Rest контроллеры</a>: проверка JSON-содержимого ответа через собственный ResultMatcher

> При запуске из Maven тесты начинают зависеть от порядка запуска.  Не проходил тест `ProfileRestControllerTest.testGet`, который шел за `AdminRestControllerTest.testUpdate`: после отката транзакции по `@Transactional` Hibernate кэш не восстанавливал роль для USER (доставалась null). 
Добавил очистку кэша Hibernate в `AbstractControllerTest.setUp`.

- Если не использовать `ModelMatcher`, то тестировать приходится каждое поле, как в `RootControllerTest`.
- Матчеры у нас были зарефакторенны: введен `Wrapper` и `Comparator`. Устройство матчеров может показаться сложным (он основан на достаточно сложных hamcrest matchers). Как это работает можно разбираться, только если самому на их основе что-то имплементировать. Мы используем Spring MVC и Hibernate не заботясь о том, насколько они сложные в реализации. Использование матчеров также достаточно просто.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVXNmOUdBbUxxWVU">Тестирование через SoapUi. UTF-8</a>
#### Apply 7_13_soapui_utf8_converter.patch
- Инструменты тестирования REST:
  - <a href="http://www.soapui.org/">SoapUi</a>
  - <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a>
(для Windows можно использовать Git Bash)
  - <a href="https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop">Postman</a>
  - <a href="https://www.jetbrains.com/help/idea/rest-client-tool-window.html">IDEA: Tools->Test RESTful Web Service</a>
  - [Insomnia REST client](https://insomnia.rest/)
  
**Импортировать проект в SoapUi из config\Topjava-soapui-project.xml. Response смотреть в формате JSON.**
   
-  Проверка UTF-8: <a href="http://localhost:8080/topjava/rest/profile/text">http://localhost:8080/topjava/rest/profile/text</a>
-  <a href="http://forum.spring.io/forum/spring-projects/web/74209-responsebody-and-utf-8">ResponseBody and UTF-8</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> При выполнении тестов через MockMvc никаких изменений на базе не видно, почему оно не сохраняет?

`AbstractControllerTest` аннотируется `@Transactional` - это означает, что тесты идут в транзакции и после каждого теста JUnit делает rollback базы.

> Что получается в результате выполнения запроса `SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email`? В чем разница в SQL без `DISTINCT`. 

Запросы можно посмотреть в логах - SQL тот же. Те `DISTINCT` влияет на то, как Hibernate обрабатывает дублирующие записи (с `DISTINCT` их исключает). Результат можно посмотреть в тестах или приложении, поставив брекпойнт.

> После патча 7_11 тест ProfileRestControllerTest.testGet нам оставляет поле meals:null хотя мы задали чтоб не сериализовались null поля. Почему его оставляет сериализатор? (конечно если выставим NON_EMPTY то уберет)

Там lazy коллекция, а не null. Думаю, что Hibernate5Module ее обнуляет уже после логики NOT_NULL (JacksonObjectMapper).

>  В чем заключается расширение функциональности hamcrest в нашем тесте, что нам пришлось его отдельно от JUnit прописывать?

hamcrest-all используется в проверках `RootControllerTest`: `org.hamcrest.Matchers.*`

>  Jackson мы просто подключаем в помнике и спринг будет с ним работать без любых других настроек?

Да, Spring смотрит в classpath и если видит там Jackson, то подключает интеграцию с ним

>  Где-то слышал, что любой ресурс по REST должен однозначно идентифицироватьcя через url, без параметров. Правильно ли задавать URL для фильтрации в виде `http://localhost/topjava/rest/meals/filter/{startDate}/{startTime}/{endDate}/{endTime}` ?

Так делают, только при отношении <a href="https://ru.wikipedia.org/wiki/Диаграмма_классов#.D0.90.D0.B3.D1.80.D0.B5.D0.B3.D0.B0.D1.86.D0.B8.D1.8F">агрегация</a>, например если давать админу право смотреть еду любого юзера, URL мог бы быть похож на `http://localhost/topjava/rest/users/{userId}/meals/{mealId}`. В случае критериев, поиска или странчных данных они передаются как параметр.

> Что означает конструкция в `JsonUtil`: `reader.<T>readValues(json)`;

См. <a href="https://docs.oracle.com/javase/tutorial/java/generics/methods.html">Generic Methods</a>. Когда компилятор не может вывести тип, можно его уточнить при вызове generic метода. Не важно static или нет.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW07

- 1: Добавить тесты контроллеров:
  - 1.1 `RootControllerTest.testMeals` для `meals.jsp`
  - 1.2 `ResourceControllerTest` для `style.css` (проверить status и ContentType)
- 2: Реализовать `MealRestController` и протестировать его через `MealRestControllerTest`
  - 2.1 cледите чтобы url в тестах совпадал с параметрами в методе контроллера. Можно добавить логирование `<logger name="org.springframework.web" level="debug"/>` для проверки маршрутизации.
  - 2.2 в параметрах `getBetween` принимать `LocalDateTime` (конвертировать через Spring, <a href="http://blog.codeleak.pl/2014/06/spring-4-datetimeformat-with-java-8.html">@DATETIMEFORMAT WITH JAVA 8 DATE-TIME API</a>), а передавать в тестах в формате `ISO_LOCAL_DATE_TIME` (например `'2011-12-03T10:15:30'`). Параметры трактовать для нашего демо: раздельная фильтрация по дате и по времени.
 
#### Optional
- 3: Переделать `MealRestController.getBetween` на параметры `LocalDate/LocalTime` c раздельной фильтрацией по времени/дате, работающий при `null` значениях (см. демо и `JspMealController.getBetween`). Заменить `@DateTimeFormat` на свои LocalDate/LocalTime конверторы или форматтеры.
  -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#core-convert">Spring Type Conversion</a>
  -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html#format">Spring Field Formatting</a>
  -  <a href="http://stackoverflow.com/questions/13048368/difference-between-spring-mvc-formatters-and-converters">Difference between Spring MVC formatters and converters</a>
- 4: Протестировать `MealRestController` через любой инструмент (SoapUi, curl, IDEA Test RESTful Web Service, Postman)

**На следующем занятии используется JavaScript/jQuery. Если у вас там пробелы, <a href="https://github.com/JavaOPs/topjava#html-javascript-css">пройдите его основы</a>**

---------------------
## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW07
- 1: для тестирования в `MealRestController` списка `MealWithExceeded` сделайте `BeanMatcher<MealWithExceed>`
- 2: Ошибка в тесте _Invalid read array from JSON_ обычно расшифровывается немного ниже: читайте внимательно.
- 3: <a href="https://urvanov.ru/2016/12/03/jackson-и-неизменяемые-объекты/">Jackson и неизменяемые объекты</a>
- 4: <a href="http://www.baeldung.com/jackson">Jackson JSON Tutorial</a>
- 5: Если у meal, приходящий в контроллер, поля null, проверьте `@RequestBody` перед параметром (данные приходят в формате JSON)
- 6: При проблемах с собственным форматтером убедитесь, что в конфигурации `<mvc:annotation-driven...` не дублируется
- 7: **Проверьте выполение ВСЕХ тестов через maven**. В случае проблем проверьте, что не портите константу из `MealTestData`
- 8: `@Autowired` в тестах нужно делать в том месте, где класс будет использоваться. Общий принцип: не размазывать код по классам, объявление переменных держать как можно ближе к ее использованию, группировать (не смешивать) код с разной функциональностью.
