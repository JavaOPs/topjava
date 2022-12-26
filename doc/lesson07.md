# [Онлайн стажировка Spring 5/JPA Enterprise (TopJava)](http://javaops.ru/view/topjava)
## [Почему мы?](http://javaops.ru/#why)

## REST, REST-контроллеры, тестирование контроллеров Spring MVC 
# Для просмотра открыты видео [4](#--4-миграция-на-junit-5), [5](#-5-принципы-rest-rest-контроллеры), [6](#-6-тестирование-rest-контроллеров-jackson), [7](#-7-кастомизация-jackson-object-mapper), [8](#user-content--8-тестирование-rest-контроллеров-через-jsonassert-и-матчеры)
- Не стоит стремиться прочитать все ссылки урока, их можно использовать как справочник. Гораздо важнее пройти основной материал урока и сделать домашнее задание
- Обязательно посмотри <a href="https://github.com/JavaOPs/topjava/wiki/Git#Правила-работы-с-патчами-на-проекте">правила работы с патчами на проекте</a>
- Делать Apply Patch лучше по одному непосредственно перед видео на эту тему, а при просмотре видео сразу отслеживать все изменения кода проекта по изменению в патче (`Version Control -> Local Changes -> Ctrl+D`)
- При первом Apply удобнее выбрать имя локального ченджлиста Name: Default. Далее все остальные патчи также будут в него попадать.
- Код проекта обновляется и не всегда совпадает с видео (можно увидеть, как развивался проект). Изменения в проекте указываю после соответствующего патча.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW6

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. HW6
#### Apply 7_01_HW6_fix_tests.patch

#### Apply 7_02_HW6_meals.patch

> сделал фильтрацию еды через `get`: операция идемпотентная, можно делать в браузере обновление по F5

### Внимание: чиним пути в следующем патче

#### Apply 7_03_HW6_fix_relative_url_utf8.patch

- <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">
  Relative paths in JSP</a>
- <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">
  Spring redirect: prefix</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. HW6 Optional

#### Apply 7_04_HW6_optional_add_role.patch

#### `JdbcUserServiceTest` отвалились. Будем чинить в `7_06_HW6_jdbc_transaction_roles.patch`

#### Apply 7_05_fix_hint_graph.patch

- В `JpaUserRepositoryImpl.getByEmail` DISTINCT попадает в запрос, хотя он там не нужен. Это просто указание Hibernate
  не дублировать данные. Для оптимизации можно указать Hibernate делать запрос без
  distinct: [15.16.2. Using DISTINCT with entity queries](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct)
- Бага [HINT_PASS_DISTINCT_THROUGH does not work if 'hibernate.use_sql_comments=true'](https://hibernate.atlassian.net/browse/HHH-13280). При `hibernate.use_sql_comments=false` все работает - в SELECT нет DISTINCT.
- Тест `DataJpaUserServiceTest.getWithMeals()` не работает для admin (у админа 2 роли, и еда при JOIN дублируется). ...    

#### Apply 7_06_HW6_jdbc_transaction_roles.patch

Еще интересные JDBC реализации: ...

### Валидация для `JdbcUserRepository` через Bean Validation API

#### Apply 7_07_HW6_optional_jdbc_validation.patch

- [Валидация данных при помощи Bean Validation API](https://alexkosarev.name/2018/07/30/bean-validation-api/).

На данный момент у нас реализована валидация сущностей только для jpa- и dataJpa-репозиториев. При работе
через JDBC-репозиторий может произойти попытка записи в БД некорректных данных, что приведет к `SQLException` из-за нарушения
ограничений, наложенных на столбцы базы данных. Для того чтобы перехватить невалидные данные еще до
обращения в базу, воспользуемся API *javax.validation* (ее реализация `hibernate-validator` используется для проверки данных в Hibernate и будет использоваться в Spring Validation, которую подключим позже).
В `ValidationUtil` создадим один потокобезопасный валидатор, который можно переиспользовать (см. *javadoc*).  
С его помощью в методах сохранения и обновления сущности в jdbc-репозиториях мы можем производить валидацию этой сущности: `ValidationUtil.validate(object);`
Чтобы проверка не падала, `@NotNull Meal.user` пришлось пока закомментировать. Починим в 10-м занятии через `@JsonView`.

### Отключение кэша в тестах:

Вместо наших приседаний с `JpaUtil` и проверкой профилей мы можем ... 

#### Apply 7_08_HW06_optional2_disable_tests_cache.patch

- [Example of PropertyOverrideConfigurer](https://www.concretepage.com/spring/example_propertyoverrideconfigurer_spring)
- [Spring util schema](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#xsd-schemas-util)

## Занятие 7:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. Тестирование Spring MVC</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Тестирование Spring MVC

Для более удобного сравнения объектов в тестах мы будем использовать библиотеку *Harmcrest* с Matcher'ами, которая
позволяет делать сложные проверки. С *JUnit* по умолчанию подтягивается *Harmcrest core*, но нам потребуется расширенная версия:
в `pom.xml` из зависимости JUnit исключим дочернюю `hamcrest-core` и добавим  `hamcrest-all`.

Для тестирования web создадим вспомогательный класс `AbstractControllerTest`, от которого будут наследоваться все
тесты контроллеров. Его особенностью будет наличие `MockMvc` - эмуляции Spring MVC для тестирования web-компонентов.
Инициализируем ее в методе, отмеченном `@PostConstruct`:

 ```
mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(CHARACTER_ENCODING_FILTER).build();
 ```

Для того чтобы в тестах контроллеров не популировать базу перед каждым тестом, пометим этот базовый тестовый класс аннотацией `@Transactional`. 
Теперь каждый тестовый метод будет выполняться в транзакции, которая будет откатываться после окончания метода и возвращать базу данных в исходное
состояние. Однако теперь в работе тестов могут возникнуть нюансы, связанные с пропагацией транзакций: все
транзакции репозиториев станут вложенными во внешнюю транзакцию теста. При этом, например, кэш первого уровня станет работать не
так, как ожидается. Т. е. при таком подходе нужно быть готовыми к ошибкам: мы их увидим и поборем в тестах на обработку ошибок на последних занятиях TopJava.

#### UserControllerTest

Создадим тестовый класс для контроллера юзеров, он должен наследоваться от `AbstractControllerTest`. 
В `MockMvc` используется [паттерн проектирования Builder](https://refactoring.guru/ru/design-patterns/builder).

 ```
    mockMvc.perform(get("/users"))        // выполнить HTTP метод GET к "/users"
        .andDo(print())                   // распечатать содержимое ответа
        .andExpect(status().isOk())       // от контроллера ожидается ответ со статусом HTTP 200(ok)
        .andExpect(view().name("users"))  // контроллер должен вернуть view с именем "users"
        .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp")) // ожидается, что клиент должен быть перенаправлен на "/WEB-INF/jsp/users.jsp"
        .andExpect(model().attribute("users", hasSize(2))) // в модели должен быть атрибут "users" размером = 2 ...
        .andExpect(model().attribute("users", hasItem(     // ... внутри которого есть элемент ...
        allOf(                                             
        hasProperty("id", is(START_SEQ)),                  // ...  с аттрибутом id = START_SEQ
        hasProperty("name", is(USER.getName()))            //...   и name = user
    )
   )));
}
 ```

В параметры метода `andExpect()` передается реализация `ResultMatcher`, в которой мы определяем, как должен быть обработан ответ контроллера.

</details>

#### Apply 7_09_controller_test.patch

> - в `MockMvc` добавился `CharacterEncodingFilter`
> - добавил [`AllActiveProfileResolver`](//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver) для возвращения массива профилей 
> - сделал вспомогательный метод `AbstractControllerTest.perform()`

- <a href="https://github.com/hamcrest/hamcrest-junit">Hamcrest</a>
- <a href="http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/">Unit Testing of Spring MVC Controllers</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  4. [Миграция на JUnit 5](https://drive.google.com/open?id=16wi0AJLelso-dPuDj6xaGL7yJPmiO71e)

<details>
  <summary><b>Краткое содержание</b></summary>

Для миграции на 5-ю версию JUnit в файле `pom.xml` поменяем зависимость `junit` на `junit-jupiter-engine` ([No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)). 
Актуальную версию всегда можно посмотреть [в центральном maven-репозитории](https://search.maven.org/search?q=junit-jupiter-engine), берем только релизы (..-Mx означают предварительные milestone версии) 
Изменять конфигурацию плагина `maven-sureface-plugin` в новых версиях JUnit уже не требуется.
JUnit5 не содержит в себе зависимости от *Harmcrest* (которую нам приходилось вручную 
отключать для JUnit4 в предыдущих шагах), поэтому исключение `hamcrest-core` просто удаляем. 
В итоге у нас останутся зависимости JUnit5 и расширенный Harmcrest.  
Теперь мы можем применить все нововведения пятой версии в наших тестах:  
 1. Для всех тестов теперь мы можем удалить `public`.  
 2. Аннотацию `@Before` исправим на `@BeforeEach` - теперь метод, который будет выполняться перед
каждым тестом, помечается именно так.  
 3. В JUnit5 работа с исключениями похожа на JUnit4 версии 4.13: вместо ожидаемых исключений в параметрах аннотации `@Test(expected = Exception.class)` используется метод `assertThrows()`,
в который первым аргументом мы передаем ожидаемое исключение, а вторым аргументом — реализацию функционального интерфейса `Executable` (код, 
в котором ожидается возникновение исключения).
 4. Метод `assertThrows()` возвращает исключение, которое было выброшено в переданном ему коде. Теперь мы можем получить это исключение, извлечь из него сообщение с помощью
 `e.getMessage()` и сравнить с ожидаемым.
 5. Для теста на валидацию при проверке предусловия, только при выполнении которого 
будет выполняться следующий участок кода (например, в нашем случае тесты на валидацию выполнялись
только в jpa профиле), теперь нужно пользоваться утильным методом `Assumptions` (нам уже не требуется).  
 6. Проверку Root Cause - причины, из-за которой было выброшено пойманное исключение, мы будем делать позднее, при тестах на ошибки.  
 7. Из JUnit5 исключена функциональность `@Rule`, вместо них теперь нужно использовать `Extensions`, которые
могут встраиваться в любую фазу тестов. Чтобы добавить их в тесты, пометим базовый тестовый класс аннотацией `@ExtendWith`.  
    
JUnit предоставляет нам набор коллбэков — интерфейсов, которые будут исполняться в определенный момент тестирования.
Создадим класс `TimingExtension`, который будет засекать время выполнения тестовых методов.  
Этот класс будет имплементировать маркерные интерфейсы — коллбэки JUnit:  
   - `BeforeTestExecutionCallback` - коллбэк, который будет вызывать методы этого интерфейса перед каждым тестовым методом.  
   - `AfterTestExecutionCallback` - методы этого интерфейса будут вызываться после каждого тестового метода;  
   - `BeforeAllCallback` - методы перед выполнением тестового класса;  
   - `AfterAllCallback` - методы после выполнения тестового класса;  

Осталось реализовать соответствующие методы, которые описываются в каждом из этих интерфейсов, они и будут вызываться JUnit в нужный момент:  
 - в методе `beforeAll` (который будет вызван перед запуском тестового класса) создадим спринговый утильный секундомер `StopWatch` для текущего тестового класса;  
 - в методе `beforeTestExecution` (будет вызван перед тестовым методом) - запустим секундомер;  
 - в методе `afterTestExecution` (будет вызван после тестового метода) - остановим секундомер.  
 - в методе `afterAll` (который будет вызван по окончанию работы тестового класса) - выведем результат работы этого секундомера в лог;  

8. Аннотации `@ContextConfiguration` и `@ExtendWith(SpringExtension.class)` (замена `@RunWith`) мы можем заменить одной `@SpringJUnitConfiguration` (старые версии IDEA ее не понимают)

</details>

#### Apply 7_10_JUnit5.patch

> - [No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)
> - [Наконец пофиксили баг с `@SpringJUnitConfig`](https://youtrack.jetbrains.com/issue/IDEA-166549)

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

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Принципы REST. REST контроллеры](https://drive.google.com/open?id=1e4ySjV15ZbswqzL29UkRSdGb4lcxXFm1)

<details>
  <summary><b>Краткое содержание</b></summary>

#### Принципы REST, REST-контроллеры

> [REST](http://spring-projects.ru/understanding/rest/) - архитектурный стиль проектирования распределенных систем (типа клиент-сервер).

Чаще всего в REST-сервер и клиент общаются посредством обмена JSON-объектами через HTTP-методы GET/POST/PUT/DELETE/PATCH.  
Особенностью REST является отсутствие состояния (контекста) взаимодействий клиента и сервера.  

В нашем приложении есть контроллеры для Admin и для User. Чтобы сделать их REST-контроллерами,
заменим аннотацию `@Controller` на `@RestController`

> Не поленитесь зайти через Ctrl+click в `@RestController`: к аннотации `@Controller` добавлена `@ResponseBody`. Т. е. ответ от нашего приложения будет не View, а данные в теле ответа.

В `@RequestMapping`, кроме пути для методов контроллера (`value`), добавляем параметр `produces = MediaType.APPLICATION_JSON_VALUE`.
Это означает, что в заголовки ответа будет добавлен тип `ContentType="application/json"` - в ответе от контроллера будет приходить JSON-объект.  

> Чтобы было удобно использовать путь к этому контроллеру в приложении и в тестах, 
> выделим путь к нему в константу REST_URL, к которой можно будет обращаться из других классов

1. Метод `AdminRestController.getAll` пометим аннотацией `@GetMapping` - маршрутизация к методу по HTTP GET.
   
2. Метод `AdminRestController.get` пометим аннотацией `@GetMapping("/{id}")`.  
В скобках аннотации указано, что к основному URL контроллера будет добавляться `id` пользователя - переменная, которая передается в запросе непосредственно в URL.  
   Соответствующий параметр метода нужно пометить аннотацией `@PathVariable` (если имя в URL и имя аргумента метода не совпадают, в параметрах аннотации дополнительно нужно будет уточнить
   имя в URL. Если они совпадают, [этого не требуется](https://habr.com/ru/post/440214/).  
   
3. Метод создания пользователя `create` отметим аннотацией `@PostMapping` - маршрутизация к методу по HTTP POST.
   В метод мы передаем объект `User` в теле запроса (аннотация `@RequestBody`) в формате JSON (`consumes = MediaType.APPLICATION_JSON_VALUE`).
   При создании нового ресурса правило хорошего тона - вернуть в заголовке ответа URL созданного ресурса.
   Для этого возвращаем не `User`, а `ResponseEntity<User>`, который мы можем с помощью билдера `ServletUriComponentsBuilder` дополнить заголовком ответа `Location` и вернуть статус `CREATED(201)` 
   (если пойти в код `ResponseEntity.created` можно докопаться до сути, очень рекомендую смотреть в исходники кода).  
   
4. Метод `delete` помечаем `@DeleteMapping("/{id}")` - HTTP DELETE. 
   Он ничего не возвращает, поэтому помечаем его аннотацией `@ResponseStatus(HttpStatus.NO_CONTENT)`. Статус ответа будет HTTP.204;  
   
5. Над методом обновления ставим `@PutMapping` (HTTP PUT). В аргументах метод принимает `@RequestBody User user` и `@PathVariable int id`.

6. Метод поиска по `email` также помечаем `@GetMapping` и, чтобы не было конфликта маршрутизации с методом `get()`,
   указываем в URL добавку `/by`. В этот метод `email` передается как параметр запроса (аннотация `@RequestParam`).

> **Все это СТАНДАРТ архитектурного стиля REST. НЕ придумывайте ничего своего в своих выпускных проектах! Это очень большая ошибка - не придерживаться стандартов API.**

7. `ProfileRestController` выполняем аналогичным способом с учетом того, что пользователь имеет доступ только к своим данным.

Если на данном этапе попытаться запустить приложение и обратиться к какому-либо методу контроллера, сервер ответит нам ошибкой со статусом 406,
так как Spring не знает, как преобразовать объект User в JSON...

</details>

#### Apply 7_11_rest_controller.patch

- <a href="http://spring-projects.ru/understanding/rest/">Понимание REST</a>
- <a href="https://ru.wikipedia.org/wiki/JSON">JSON (JavaScript Object Notation)</a>
- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- [10 Best Practices for Better RESTful](https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291)
- [Best practices for rest nested resources](https://stackoverflow.com/questions/20951419/what-are-best-practices-for-rest-nested-resources)
- <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-requestmapping">
  Request mapping</a>
- [Лучшие практики разработки REST API: правила 1-7,15-17](https://tproger.ru/translations/luchshie-praktiki-razrabotki-rest-api-20-sovetov/)
- Дополнительно:
    - [Подборка практик REST](https://gist.github.com/Londeren/838c8a223b92aa4017d3734d663a0ba3)
    - <a href="http://www.infoq.com/articles/springmvc_jsx-rs">JAX-RS vs Spring MVC</a>
    - <a href="http://habrahabr.ru/post/144011/">RESTful API для сервера – делаем правильно (Часть 1)</a>
    - <a href="http://habrahabr.ru/post/144259/">RESTful API для сервера – делаем правильно (Часть 2)</a>
    - <a href="https://www.youtube.com/watch?v=Q84xT4Zd7vs&list=PLoij6udfBncivGZAwS2yQaFGWz4O7oH48">И. Головач.
      RestAPI</a>
    - [value/name в аннотациях @PathVariable и @RequestParam](https://habr.com/ru/post/440214/)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. [Тестирование REST контроллеров. Jackson.](https://drive.google.com/open?id=1aZm2qoMh4yL_-i3HhRoyZFjRAQx-15lO)

<details>
  <summary><b>Краткое содержание</b></summary>

Для работы с JSON добавляем в `pom.xml` зависимость `jackson-databind`.    
Актуальную версию библиотеки можно посмотреть в [центральном maven-репозитории](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-databind).  
Теперь Spring будет автоматически использовать эту библиотеку для сериализации/десериализации объектов в JSON (найдя ее в *classpath*).  
Если сейчас запустить приложение и обратиться к методам REST-контроллера, то оно выбросит `LazyInitializationException`.
Оно возникает из-за того, что у наших сущностей есть лениво загружаемые поля, отмеченные `FetchType.LAZY` - при загрузке сущности из базы вместо такого поля подставится Proxy, который и должен вернуть
реальный экземпляр этого поля при первом же обращении. Jackson при сериализации в JSON использует все поля сущности, 
и при обращении к *Lazy*-полям возникает исключение, так как сессия работы с БД в этот момент уже закрыта, и нужный объект
не может быть инициализирован. Чтобы Jackson игнорировал эти поля, пометим их аннотацией `@JsonIgnore`.  

Теперь при запуске приложения REST-контроллер будет работать. Но при получении JSON-объектов мы можем увидеть, что Jackson сериализовал объект
через геттеры (например, в ответе есть поле `new` от метода `Persistable.isNew()`).
Чтобы учитывались только поля объектов, добавим над `AbstractBaseEntity`:  
````java
@JsonAutoDetect(fieldVisibility = ANY, // jackson видит все поля
        getterVisibility = NONE, // ... но не видит геттеров
        isGetterVisibility = NONE, //... не видит геттеров boolean-полей
        setterVisibility = NONE) // ... не видит сеттеров
````
Теперь все сущности, унаследованные от базового класса, будут сериализоваться/десериализоваться через поля. 

</details>

#### Apply 7_12_rest_test_jackson.patch

- [Jackson databind github](https://github.com/FasterXML/jackson-databind)
- [Jackson Annotation Examples](https://www.baeldung.com/jackson-annotations)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Кастомизация Jackson Object Mapper](https://drive.google.com/open?id=1CM6y1JhKG_yeLQE_iCDONnI7Agi4pBks)

<details>
  <summary><b>Краткое содержание</b></summary>

Сейчас, чтобы не сериализовать *Lazy*-поля, мы должны пройтись по каждой сущности и 
вручную пометить их аннотацией `@JsonIgnore`. Это неудобно, засоряет код и допускает возможные ошибки. К тому же,
при некоторых условиях, нам иногда нужно загрузить и в ответе передать эти *Lazy*-поля.  
Чтобы запретить сериализацию Lazy-полей для всего проекта, подключим в `pom.xml` библиотеку `jackson-datatype-hibernate`.  
Также изменим сериализацию/десериализацию полей объектов в JSON: не через аннотацию `@JsonAutoDetect`, а в классе `JacksonObjectMapper`, который
унаследуем от `ObjectMapper` (стандартный Mapper, который использует Jackson) и сделаем в нем другие настройки.
В конструкторе:
- регистрируем `Hibernate5Module` - модуль `jackson-datatype-hibernate`, который не делает сериализацию ленивых полей.  
- модуль для корректной сериализации `LocalDateTime` в поля JSON - `JavaTimeModule` модуль библиотеки `jackson-datatype-jsr310`
- запрещаем доступ ко всем полям и методам класса и потом разрешаем доступ только к полям
- не сериализуем null-поля (`setSerializationInclusion(JsonInclude.Include.NON_NULL)`)

Чтобы подключить наш кастомный `JacksonObjectMapper` в проект, в конфигурации `spring-mvc.xml` к 
настройке `<mvc:annotation-driven>` добавим `MappingJackson2HttpMessageConverter`, который будет использовать наш маппер.  

</details>


#### Apply 7_13_jackson_object_mapper.patch

- Сериализация hibernate lazy-loading с помощью <a href="https://github.com/FasterXML/jackson-datatype-hibernate">
  jackson-datatype-hibernate</a>
- <a href="https://geowarin.github.io/jsr310-dates-with-jackson.html">Handle Java 8 dates with Jackson</a>
- Дополнительно:
    - <a href="https://www.sghill.net/how-do-i-write-a-jackson-json-serializer-deserializer.html">Jackson JSON
      Serializer & Deserializer</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Тестирование REST-контроллеров через JSONassert и Матчеры](https://drive.google.com/open?id=1oa3e0_tG57E71g6PW7_tfb3B61Qldctl)

<details>
  <summary><b>Краткое содержание</b></summary>

Сейчас в тестах REST-контроллера мы проводим проверку только на статус ответа и тип возвращаемого контента. Добавим проверку содержимого ответа.

#### 7_14_json_assert_tests

Чтобы сравнивать содержимое ответа контроллера в виде JSON и сущность, воспользуемся библиотекой
`jsonassert`, которую подключим в `pom.xml` со scope *test*.

Эта библиотека при сравнении в тестах в качестве ожидаемого значения ожидает от 
нас объект в виде JSON-строки. Чтобы вручную не преобразовывать объекты в JSON и не 
хардкодить их в виде строк в наши тесты, воспользуемся Jackson.  
Для преобразования объектов в JSON и обратно создадим утильный класс `JsonUtil`, в котором
с помощью нашего `JacksonObjectMapper` и будет конвертировать объекты.  
И мы сталкиваемся с проблемой: `JsonUtil` - утильный класс и не является
бином Spring, а для его работы требуется наш кастомный маппер, который находится под управлением
Spring и расположен в контейнере зависимостей. Поэтому, чтобы была возможность получить
наш маппер из других классов, сделаем его синглтоном и сделаем в нем статический
метод, который будет возвращать его экземпляр. Теперь `JsonUtil` сможет его получить.  
И нам нужно указать Spring, чтобы он не создавал второй экземпляр этого объекта, а клал в свой контекст существующий. 
Для этого в конфигурации `spring-mvc.xml` определим factory-метод, с помощью которого Spring должен
получить экземпляр (instance) этого класса:  
```xml
<bean class="ru.javawebinar.topjava.web.json.JacksonObjectMapper" id="objectMapper" factory-method="getMapper"/>
```  
а в конфигурации `message-converter` вместо создания бина просто сошлемся на сконфигурированный `objectMapper`.  

Метод `ContentResultMatchers.json()` из `spring-test` использует библиотеку `jsonassert` для сравнения 2-х JSON строк: одну из ответа контроллера и вторую - 
JSON-сериализация `admin` без поля `registered` (это поле инициализируется в момент создания и отличается). 
В методе `JsonUtil.writeIgnoreProps` мы преобразуем объект `admin` в мапу, удаляем из нее игнорируемые поля и снова сериализуем в JSON.

Также сделаем тесты для утильного класса `JsonUtil`. В тестах мы записываем
объект в JSON-строку, затем конвертируем эту строку обратно в объект и сравниваем с исходным. И то же самое делаем со списком объектов.

#### 7_15_tests_refactoring

**`RootControllerTest`**

Сделаем рефакторинг `RootControllerTest`. Ранее мы в тесте получали модель, доставали из нее сущности и с помощью `hamcrest-all` 
производили по одному параметру их сравнение с ожидаемыми значениями. 
Метод `ResultActions.andExpect()` позволяет передавать реализацию интерфейса `Matcher`, в котором можно делать любые сравнения.
Функциональность сравнения списка юзеров по ВСЕМ полям у нас уже есть - мы просто делегируем сравнение объектов в `UserTestData.MATCHER`. 
При этом нам больше не нужен `harmcrest-all`, нам достаточно только `harmcrest-core`.  

**`MatcherFactory`**

Теперь вместо `jsonassert` и сравнения JSON-строк в тестах контроллеров сделаем сравнения JSON-объектов через `MatcherFactory`. 
Преобразуем ответ контроллера из JSON в объект и сравним с эталоном через уже имеющийся у нас матчер.  
Вместо сравнения JSON-строк в метод `andExpect()` мы будем передавать реализации интерфейса `ResultMatcher` из `MATCHER.contentJson(..)`.

`MATCHER.contentJson(..)` принимают ожидаемый объект и возвращают для него `ResultMatcher` с реализацией единственного метода `match(MvcResult result)`, 
в котором делегируем сравнение уже существующим у нас матчерам.
Мы берем JSON-тело ответа (`MatcherFactory.getContent`), десериализуем его в объект (`JsonUtil.readValue/readValues`) и сравниваем через имеющийся `MATCHER.assertMatch`
десериализованный из тела контроллера объект и ожидаемое значение.  

> Методы из класса `TestUtil` перенес в `MatcherFactory`, лишние удалил. 

**`AdminRestControllerTest`**

- `getByEmail()` - сделан по аналогии с тестом `get()`. Дополнительно нужно добавить в строку URL параметры запроса.
- `delete()` - выполняем HTTP.DELETE. Проверяем статус ответа 204. Проверяем, что пользователь удален. 
  
> Раньше я получал всех users из базы и проверял, что среди них нет удаленного. При этом тесты становятся чувствительными ко всем users в базе и ломаются при добавлении/удалении новых тестовых данных.

- `update()` - выполняем HTTP.PUT. В тело запроса подаем сериализованный `JsonUtil.writeValue(updated)`. После выполнения проверяем, что объект в базе обновился.
- `create()` - выполняем HTTP.POST аналогично `update()`. Но сравнить результат мы сразу не можем, т. к. при создании объекта ему присваивается `id`.  
  Поэтому мы извлекаем созданного пользователя из ответа (`MATCHER.readFromJson(action)`), получаем его `id`, и уже с этим `id` эталонный объект мы можем сравнить с объектом в ответе контроллера и со
  значением в базе.
- `getAll()` - аналогично `get()`. Список пользователей из ответа в формате JSON сравниваем с эталонным списком (`MATCHER.contentJson(admin, user)`).

Тесты для `ProfileRestController` выполнены аналогично.

</details>

#### Apply 7_14_json_assert_tests.patch

> - В `JsonUtil.writeIgnoreProps` вместо цикла по мапе сделал `map.keySet().removeAll`

- [JSONassert](https://github.com/skyscreamer/JSONassert)
- [Java Code Examples for ObjectMapper](https://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.databind.ObjectMapper)

#### Apply 7_15_tests_refactoring.patch

> - Методы из класса `TestUtil` перенес в `MatcherFactory`, лишние удалил. 
> - Раньше в тестах я для проверок получал всех users из базы и сравнивал с эталонным списком. При этом тесты становятся чувствительными ко всем users в базе и ломаются при добавлении/удалении новых тестовых данных.

- [Java @SafeVarargs Annotation](https://www.baeldung.com/java-safevarargs)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. Тестирование через SoapUI. UTF-8

<details>
  <summary><b>Краткое содержание</b></summary>

SoapUI - это один из инструментов для тестирования API приложений, которые работают по REST и по SOAP.  
Он позволяет нам по протоколу HTTP дернуть методы нашего API и увидеть ответ контроллеров.  

Если в контроллер мы добавим метод, который в теле ответа будет возвращать текст на кириллице, то увидим, что кодировка теряется.
Для сохранения кодировки используем `StringHttpMessageConverter`, который конфигурируем в `spring-mvc.xml`.
При этом мы должны явно указать, что конвертор будет работать только с текстом в кодировке *UTF-8*.

</details>

#### Apply 7_16_soapui_utf8_converter.patch

- Инструменты тестирования REST:
    - <a href="https://www.soapui.org/downloads/soapui/">SoapUI</a>
    - <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью
      Curl</a>.  
      Для Windows 7 можно использовать Git Bash, с Windows 10 v1803 можно прямо из консоли. Возможны проблемы с UTF-8:
        - [CURL doesn't encode UTF-8](https://stackoverflow.com/a/41384903/548473)
        - [Нстройка кодировки в Windows](https://support.socialkit.ru/ru/knowledge-bases/4/articles/11110-preduprezhdenie-obnaruzhenyi-problemyi-svyazannyie-s-raspoznavaniem-russkih-simvolov)
    - **[IDEA: Tools->HTTP Client->...](https://www.jetbrains.com/help/idea/rest-client-tool-window.html)**
    - <a href="https://www.getpostman.com/">Postman</a>
    - [Insomnia REST client](https://insomnia.rest/)

**Импортировать проект в SoapUI из `config\Topjava-soapui-project.xml`. Response смотреть в формате JSON.**

> Проверка UTF-8: <a href="http://localhost:8080/topjava/rest/profile/text">http://localhost:8080/topjava/rest/profile/text</a>

[ResponseBody and UTF-8](http://web.archive.org/web/20190102203042/http://forum.spring.io/forum/spring-projects/web/74209-responsebody-and-utf-8)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Зачем у нас и UI-контроллеры, и REST-контроллеры? То есть в общем случае backend-разработчику недостаточно предоставить REST API и RestController?

В общем случае нужны и те, и другие. REST обычно используют для отдельного UI (например, на React или Angular) или для
интеграции / мобильного приложения. У нас REST-контроллеры используются только для тестирования. UI-контроллеры используем для
нашего приложения на JSP шаблонах. Таких сайтов без богатой UI логики тоже немало. Например https://javaops.ru/ :)  
Разница в запросах:

- для UI используются только GET и POST
- при создании/обновлении в UI мы принимаем данные из формы `application/x-www-form-urlencoded` (посмотрите
  вкладку `Network`, не в формате JSON)
- для REST API запросы GET, POST, PUT, DELETE, PATCH и возвращают только данные (обычно JSON)

...и в способе авторизации:

- для REST API у нас будет базовая авторизация
- для UI - через cookies

Также часто бывают смешанные сайты, где есть и отдельное JS приложение, и шаблоны.

> При выполнении тестов через MockMvc никаких изменений на базе не видно, почему оно не сохраняет?

`AbstractControllerTest` аннотируется `@Transactional` - это означает, что тесты идут в транзакции, и после каждого
теста JUnit делает rollback базы.

> Что получается в результате выполнения запроса `SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email`? В чем разница в SQL без `DISTINCT`.

Запросы SQL можно посмотреть в логах. Т. е. `DISTINCT` в `JPQL` влияет на то, как Hibernate обрабатывает дублирующиеся
записи (с `DISTINCT` их исключает). Результат можно посмотреть в тестах или приложении, поставив брекпойнт. По
поводу `SQL DISTINCT` не стесняйтесь пользоваться google,
например, [оператор SQL DISTINCT](http://2sql.ru/novosti/sql-distinct/)

> В чем заключается расширение функциональности hamcrest в нашем тесте, что нам пришлось его отдельно от JUnit прописывать?

hamcrest-all используется в проверках `RootControllerTest`: `org.hamcrest.Matchers.*`

> Jackson мы просто подключаем в помнике, и Spring будет с ним работать без любых других настроек?

Да, Spring смотрит в classpath и если видит там Jackson, то подключает интеграцию с ним.

> Где-то слышал, что любой ресурс по REST должен однозначно идентифицироваться через url без параметров. Правильно ли задавать URL для фильтрации в виде `http://localhost/topjava/rest/meals/filter/{startDate}/{startTime}/{endDate}/{endTime}` ?

Так делают только при
отношении <a href="https://ru.wikipedia.org/wiki/Диаграмма_классов#.D0.90.D0.B3.D1.80.D0.B5.D0.B3.D0.B0.D1.86.D0.B8.D1.8F">
агрегация</a>, например, если давать админу право смотреть еду любого юзера, URL мог бы быть похож
на `http://localhost/topjava/rest/users/{userId}/meals/{mealId}` (не рекомендуется, см. ссылку ниже). В случае критериев
поиска или страничных данных они передаются как параметр. Смотри также:

- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- <a href="https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291">10 Best Practices
  for Better RESTful
- [REST resource hierarchy (если кратко: не рекомендуется)](https://stackoverflow.com/questions/15259843/how-to-structure-rest-resource-hierarchy)

> Что означает конструкция в `JsonUtil`: `reader.<T>readValues(json)`;

См. <a href="https://docs.oracle.com/javase/tutorial/java/generics/methods.html">Generic Methods</a>. Когда компилятор
не может вывести тип, можно его уточнить при вызове generic метода. Неважно, static или нет.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW07

- 1: Добавить тесты контроллеров:
    - 1.1 `RootControllerTest.getMeals` для `meals.jsp`
    - 1.2 Сделать `ResourceControllerTest` для `style.css` (проверить `status` и `ContentType`)
- 2: Реализовать `MealRestController` и протестировать его через `MealRestControllerTest`
    - 2.1 следите, чтобы url в тестах совпадал с параметрами в методе контроллера. Можно добавить
      логирование `<logger name="org.springframework.web" level="debug"/>` для проверки маршрутизации.
    - 2.2 в параметрах `getBetween` принимать `LocalDateTime` (конвертировать
      через <a href="http://blog.codeleak.pl/2014/06/spring-4-datetimeformat-with-java-8.html">@DateTimeFormat with Java
      8 Date-Time API</a>), пока без проверки на `null` (используя `toLocalDate()/toLocalTime()`, см. Optional п. 3). В
      тестах передавать в формате `ISO_LOCAL_DATE_TIME` (
      например `'2011-12-03T10:15:30'`).

### Optional

- 3: Переделать `MealRestController.getBetween` на параметры `LocalDate/LocalTime` c раздельной фильтрацией по
  времени/дате, работающий при `null` значениях (см. демо и `JspMealController.getBetween`)
  . Заменить `@DateTimeFormat` на свои LocalDate/LocalTime конверторы или форматтеры.
    - <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#core-convert">Spring Type
      Conversion</a>
    - <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#format">Spring Field
      Formatting</a>
    - <a href="http://stackoverflow.com/questions/13048368/difference-between-spring-mvc-formatters-and-converters">
      Difference between Spring MVC formatters and converters</a>
- 4: Протестировать `MealRestController` (SoapUI, Curl, IDEA Test RESTful Web Service, Postman). Запросы `curl` занести
  в отдельный `md` файл (или `README.md`)
- 5: Добавить в `AdminRestController` и `ProfileRestController` методы получения пользователя вместе с
  едой (`getWithMeals`, `/with-meals`).
    - [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

### Optional 2

- 6: Сделать тесты на методы контроллеров `getWithMeals()` (п. 5)

**На следующем занятии используется JavaScript/jQuery. Если у вас там
пробелы, <a href="https://github.com/JavaOPs/topjava#html-javascript-css">пройдите его основы</a>**
---------------------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: Ошибка в тесте _Invalid read array from JSON_ обычно расшифровывается немного ниже: читайте внимательно.
- 2: <a href="https://urvanov.ru/2016/12/03/jackson-и-неизменяемые-объекты/">Jackson и неизменяемые объекты</a> (для
  сериализации `MealTo`)
- 3: Если у meal, приходящий в контроллер, поля `null`, проверьте `@RequestBody` перед параметром (данные приходят в
  формате JSON)
- 4: При проблемах с собственным форматтером убедитесь, что в конфигурации `<mvc:annotation-driven...` не дублируется
- 5: **Проверьте выполнение ВСЕХ тестов через maven**. В случае проблем проверьте, что не портите эталонный объект
  из `MealTestData`
- 6: `@Autowired` в тестах нужно делать в том месте, где класс будет использоваться. Общий принцип: не размазывать код
  по классам, объявление переменных держать как можно ближе к ее использованию, группировать (не смешивать) код с разной
  функциональностью.
- 7: Попробуйте в `RootControllerTest.getMeals` сделать сравнение через `model().attribute("meals", expectedValue)`.
  Учтите, что вывод результатов через `toString` к сравнению отношения не имеет.
