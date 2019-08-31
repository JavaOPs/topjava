# TopJava Release Notes
### Topjava 17
- Удалил `Impl` из названий репозиториев
- Удалил интерфейсы к сервисам, использую классы
- Добавил `AdminRestController.enable`, вызов через PATCH метод
- Добавил валидацию для jdbc через Bean Validation API
- Перенес работу в UI с профилем из `RootController` в `ProfileUIController`
- `SLF4JBridgeHandler` инициализирую только в профиле `postgres`

### Topjava 16
- Выделил общий код реализации хранения в памяти в `InMemoryBaseRepositoryImpl`
- Сделал подтверждение для удаления записей
- Обновились видео 7-го занятия. [Выложил его как пример занятия, некоторые видео открыты](https://github.com/JavaOPs/topjava/blob/master/doc/lesson07.md)
- Сделали валидации дублирования email через `WebDataBinder` и `Validator`

### Topjava 15
- Миграция на Servlet API 4.0 / Tomcat 9.x
- [Миграция на JDK11](http://javaops.ru/view/resources/jdk8_11)
- JUnit5 fix: <a href="https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven">junit-platform-surefire-provider не нужен</a></h4>
- Рефакторинг тестов: 
  - в `RootControllerTest.testUsers` для проверки используем `AssertionMatcher` адаптер
  - вместо `content().json()` от `jsonassert` десериализуем json и используем сравнения через `AssertJ`
- В javascript место глабальных переменных и одинаковой функции обновления таблицы задаю их в объекте контекст, который передаю в `makeEditable()` как параметр
- Починил `back` в браузере после логина. Кнопки входа и регистрации отображаю только для `isAnonymous()`

### Topjava 14
- [Миграция на JUnit 5](http://javaops.ru/view/resources/junit5)
- Для измерения времени в тестах использую [Spring StopWatch](https://www.logicbig.com/how-to/code-snippets/jcode-spring-framework-stopwatch.html)
- `SimpleJdbcInsert` и `NamedParameterJdbcTemplate` конструируются (и берут настройки) из `jdbcTemplate`
- `AuthorizedUser` зарефакторился в `SecurityUtil`
- В javascript [заменил `var` на `let/const`](https://learn.javascript.ru/let-const). [Поддержка 95% браузеров](https://caniuse.com/#feat=const)
- Подправил UI фильтрации и заголовка страниц, добавилась кнопка `Cancel` в профиль
- Починил [баг в FireFox](https://bugzilla.mozilla.org/show_bug.cgi?id=884693): пустой ответ по ajax
- Сделал вход в приложение при нажании кнопок `Зайти как ...`
- Добавил регистрацию пользователя по REST
- Преименовал js файлы согласно [javascript filename naming convention](https://stackoverflow.com/questions/7273316/what-is-the-javascript-filename-naming-convention)
- Сделал проверку startTime/endTime на фильтре времени (после обновления datetimepicker до 2.5.20)

### Topjava 13
- [Миграция на Botstrap 4](https://getbootstrap.com/docs/4.1/migration/)
- Добавил [Responsive behaviors](https://getbootstrap.com/docs/4.1/components/navbar/#responsive-behaviors) - при уменшении ширины экрана навигация сворачивается в кнопку
- Для отображения цвета еды и выключенного юзера использую [data-* атрибуты](https://developer.mozilla.org/ru/docs/Web/Guide/HTML/Using_data_attributes)
- В `inputField.tag` передаю как параметр код для локализации label, а в `i18n.jsp` передаю как параметр `page`. См. [JSP include action with parameter example](https://beginnersbook.com/2013/12/jsp-include-with-parameter-example)

### Topjava 12
- [Миграция на Spring 5](http://javaops.ru/view/resources/spring5)
- обновил версии: Ehcache 3.x, datatables, datetimepicker
- добавил видео решений HW0 с одним проходом
- поправил видео [Обзор Spring Framework. Spring Context](https://drive.google.com/file/d/1fBSLGEbc7YXBbmr_EwEHltCWNW_pUmIH). Дописал про Constructor injection.
- заменил видео про тетсирование сервисов. Вместо самодельных матчеров стали использовать [AssertJ](http://joel-costigliola.github.io/assertj/index.html). Видео [Тестирование UserService через AssertJ](https://drive.google.com/open?id=1SPMkWMYPvpk9i0TA7ioa-9Sn1EGBtClD), время 1:53
- сделал [видео с jQuery конвертерами и дефолтными группами валидации при сохранении в базу](https://drive.google.com/open?id=1tOMOdmaP5OQ7iynwC77bdXSs-13Ommax)
- сделал [видео с новым `DelegatingPasswordEncoder` и Json READ/WRITE access](https://drive.google.com/file/d/1XZXvOThinzPw4EhigAUdo8-MWT_g8wOt/view?usp=sharing)
- убрал `AccessType.PROPERTY` для `AbstractBaseEntity.id` (см. [fixed HHH-3718](https://hibernate.atlassian.net/browse/HHH-3718))
- удалил `PasswordUtil`, возвращаю статус `NO_CONTENT` для REST delete, убрал группы валидации в `UserTo`
- заменил в jQuery [success на done](https://stackoverflow.com/a/22213543/548473)
- вместо `lang.jsp` сделал общий `bodyHeader.jsp`

### Topjava 11
- добавил
  - доп. решение HW1 через одним return и O(N)
  - раскрасил лог ([Logback layouts coloring](https://logback.qos.ch/manual/layouts.html#coloring))
- рефакторинг
  - починил коммит формы по cancel (`history.back()`) в FireFox
  - заменил неработающий  DependencyCi на [VersionEye](https://www.versioneye.com/) c проверкой зависимостей на uptodate
  - починил `CrudUserRepository.getWithMeals()` через `@EntityGraph`. С неколькими ролями (у админа) еда дублируется
  - починил тесты контроллеров с профилем JDBC (`JpaUtil` отсутствует в контексте JDBC)
  - переименовал `meal.jsp/user.jsp` в `mealForm.jsp/userForm.jsp`
  - в `InMemoryMealRepositoryImpl.save()` сделал update атомарным
  - переименовал методы сервисов `save` в `create`
  - переименовал и cделал классы `BaseEntity` и `NamedEntity` абстрактными
  - обновил Noty и API с ним до 3.1.0. Добавил glyphicon в сообщения Noty
  - заменил `MATCHER_WITH_EXCEED` на валидацию через [JSONassert](https://github.com/skyscreamer/JSONassert).
  - поменял Deprecated валидаторы `org.hibernate.validator.constraints` на `javax.validation.constraints`
  - убрал пароль из результатов REST через [@JsonProperty READ_ONLY / WRITE_ONLY](https://stackoverflow.com/questions/12505141/only-using-jsonignore-during-serialization-but-not-deserialization/12505165#12505165). Тесты на REST пришлось починить добавлением добавлением в JSON пароля как дополнительного параметра (`JsonUtil.writeWithExtraProps`)
  - **убрал JSON View и сделал преобразование времени на UI с помощью [jQuery converters](http://api.jquery.com/jQuery.ajax/#using-converters)**
  - **поменял [группу валидации по умолчанию при сохранении через JPA](https://stackoverflow.com/questions/16930623/16930663#16930663).** Теперь 
  все валидаторы в модели работаю по умолчанию (`groups` не требуется).
  - Добавил в `ErrorInfo` тип ошибки `ErrorType` + i18n.
  
- правки
  - переименовал `ModelMatcher` в `BeanMatcher` и починил: можно сравнивать только упорядоченные коллекции (List)
  - поменял зависимость `org.hibernate:hibernate-validator`  на `org.hibernate.validator:hibernate-validator` (warning при сборке)
  
### Topjava 10
- добавил
  -  доступ к AuthorizedUser через [`@AuthenticationPrincipal`](http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#mvc-authentication-principal) и [authentication Tag](http://docs.spring.io/spring-security/site/docs/current/reference/html/taglibs.html#the-authentication-tag)
  - [Обработку 404 NotFound](https://stackoverflow.com/questions/18322279/spring-mvc-spring-security-and-error-handling)
  - локализацию ошибок валидации
  - проверки json в тестах через [JSONassert](https://github.com/skyscreamer/JSONassert) и [через jsonPath](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/)
  - [логирование от Postgres Driver](http://stackoverflow.com/a/43242620/548473)
  - в `.travis.yml` [сборку только ветки master](https://docs.travis-ci.com/user/customizing-the-build#Building-Specific-Branches)
  - [защиту от кэширование ajax запросов в IE](https://stackoverflow.com/a/4303862/548473)
  - обработку запрета модификации системный юзеров через универсальный `ApplicationException`
 - рефакторинг
   - сделал `@EntityGraph` через `attributePaths`
   - реализаовал обработку дублирования `user.email` и `meal.dateTime` через [Controller Based Exception Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling)
   - поменял отключение транзакционности в тестах через `@Transactional(propagation = Propagation.NEVER)`
   - сделал выбор в сервлете через switch
   - [все логгирование сделал через {} форматирование](http://stackoverflow.com/questions/10555409/logger-slf4j-advantages-of-formatting-with-instead-of-string-concatenation) и поправил его в контроллерах (поле проверки id)
   - [перешел на конструктор DI](http://stackoverflow.com/questions/39890849/what-exactly-is-field-injection-and-how-to-avoid-it)
   - в `ModelMatcher` переименовал `Comparator` -> `Equality`
   - [заинлайнил все лямбды](http://stackoverflow.com/questions/19718353/is-repeatedly-instantiating-an-anonymous-class-wasteful) (компараторы, ModelMatcher.equality)
   - поменялась реализация `JdbcUserRepositoryImpl.getAll()`
   - на UI кнопки в таблице заменились на линки, поправил сообщения локализации
   - [сделал кастомизацию JSON (@JsonView) и валидацию (groups)](https://drive.google.com/file/d/0B9Ye2auQ_NsFRTFsTjVHR2dXczA) для данных еды, отдаваемых на UI
   - в `JdbcUserRepositoryImpl` поменял `MapSqlParameterSource` на `BeanPropertySqlParameterSource`
- удалил   
  - зависимость `javax.transaction.jta` (уже не нужна)
  -  `${spring.version}` в `pom.xml` зависимостях (уже есть в `spring-framework-bom`)
  - distinct из запроса Hibernate на пользователей с ролями. [Оптимизация запроса distinct: 15.16.2](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct) 
  - лишние `<div>` тэги (`shadow` и `view-box`)

### Topjava 9
- добавил
  - выбор профиля базы через `ActiveProfilesResolver`/`AllActiveProfileResolver` на основе драйвера базы в classpath
  - видео <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFVmdpNDJSNXRTWUE">Cascade. Auto generate DDL.</a>
  - проверку на правильность id в Ajax/Rest контроллерах (<a href="http://stackoverflow.com/a/32728226/548473">treat IDs in REST body</a>)
  - тесты на валидацию входных значений контроллеров и <a href="http://hibernate.org/validator/documentation/getting-started/#unified-expression-language-el">зависимость на имплементацию</a>
  - <a href="http://getbootstrap.com/components/#glyphicons">Bootstrap Glyphicons</a>
- рефакторинг
  - переименовал `TimeUtil` в `DateTimeUtil`
  - переименовал `ExceptionUtil` в `ValidationUtil`
  - заменил валидацию <a href="http://stackoverflow.com/questions/17137307">`@NotEmpty` на `@NotBlank`</a>
  - заменил `CascadeType.REMOVE` на <a href="http://stackoverflow.com/questions/21149660">`@OnDelete`</a>
  - изменил `JdbcUserRepositoryImpl.getAll()`
  - обновил jQuery до 3.x, исключил из зависимостей webjars ненужные jQuery
  - cделал <a href="http://stackoverflow.com/questions/436411/where-should-i-put-script-tags-in-html-markup/24070373#24070373">загрузку скриптов асинхронной</a>
  - фильтр еды сделал в [Bootstrap Panels](http://getbootstrap.com/components/#panels)
  - вместо `Persistable` ввел интерфейс `HasId` и наследую от него как Entity, так и TO
  - сделал универсальную обработку исключений дублирования email и dateTime

### Topjava 8
- добавил:
  - [защиту от XSS (Cross-Site Scripting)](http://stackoverflow.com/a/40644276/548473)
  - интеграцию с <a href="https://dependencyci.com/">Dependency Ci</a> и <a href="https://travis-ci.org/">Travis Ci</a> 
  - локализацию календаря
  - сводку по результатам тестов
  - примеры запросов curl в `config/curl.md`
  - <a href="https://datatables.net/examples/styling/bootstrap.html">DataTables/Bootstrap 3 integration</a>
  - тесты на профиль деплоя Heroku (общее количество JUnit тестов стало 102)
- удалил зависимость `jul-to-slf4j`
- рефакторинг
  - переименовал все классы `UserMeal**` в `Meal**`, JSP
  - переименовал `LoggedUser` в `AuthorizedUser`
  - починил работа с PK Hibernate в случае ленивой загрузки (баг <a href="https://hibernate.atlassian.net/browse/HHH-3718">HHH-3718</a>)
  - поменял в `BaseEntity` `equals/hashCode/implements Persistable`
  - в `InMemoryMealRepositoryImpl` выделил метод `getAllStream` 
  - перенес проверки пердусловий `Assert` из `InMemory` репозиториев в сервисы
  - переименовал классы _Proxy*_ на более адекватные _Crud*_
  - поменял реализацию `JpaMealRepositoryImpl.get`, добавил в JPA модель `@BatchSize`
  - вместо `@RequestMapping` ввел Spring 4.3 аннотации `@Get/Post/...Mapping`
  - поменял авторизацию в тестах не-REST контроллеров
  - перенес вызовы `UserUtil.prepareToSave` из `AbstractUserController` в `UserServiceImpl`
  - зарефакторил обработку ошибок (`ExceptionInfoHandler`)

### Topjava 7
- добавил:
  - [JPA 2.1 EntityGraph](https://docs.oracle.com/javaee/7/tutorial/persistence-entitygraphs002.htm)
  - [Jackson @JsonView](https://habrahabr.ru/post/307392/)
  - валидацию объектов REST
  - [i18n в JavaScript](http://stackoverflow.com/a/6242840/548473)
  - проверку предусловий и видео <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU005ZzBNZmZnTVU">Методы улучшения качества кода</a>
  - интеграцию с <a href="https://www.codacy.com/">проверкой кода в Codacy</a>
  - [сравнение вермени исполнения запросов при различных meals индексах](https://drive.google.com/open?id=0B9Ye2auQ_NsFX3RLcnJCWmQ2Y0U)
- tomcat7-maven-plugin плагин перключили на Tomcat 8 (cargo-maven2-plugin)
- рефакторинг 
  - обработка ошибок сделал с array
  - матчеров тестирования (сделал автоматические обертки и сравнение на основе передаваемого компаратора)
  - вынес форматирование даты в `functions.tld`

### Topjava 3-6
- добавил
  - [выпускной проект](https://drive.google.com/open?id=0B9Ye2auQ_NsFcG83dEVDVTVMamc)
  - в таблицу meals составной индекс 
  - константы `Profiles.ACTIVE_DB`, `Profiles.DB_IMPLEMENTATION`
  - проверки и тесты на `NotFound` для `UserMealService.getWithUser` и  `UserService.getWithMeals`
  - в MockMvc фильтр CharacterEncodingFilter
  - защиту от межсайтовой подделки запроса, видео <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNDlPZGdUNThzNUU">Межсайтовая подделка запроса (CSRF)</a>
  - ограничение на диапазон дат для фильтра еды
- рефакторинг
  - UserMealsUtil, ProfileRestController, компараторов в репозитоии 
  - `LoggedUser` отнаследовал от `org.springframework.security.core.userdetails.User`
  - переименовал `DbTest` в `AbstractServiceTest` и перенес сюда `@ActiveProfiles`
  - сделал выполнение скриптов в тестах через аннотацию `@Sql`
  - вместо использования id и селектора сделал обработчик `onclick`
  - изменил формат ввода даты в форме без 'T'
- убрал
  - `LoggerWrapper`
  - <a href="http://dandelion.github.io">Dandelion обертку к datatables</a>
- обновил 
  - Hibernate до 5.x и Hibernate Validator, добавились новые зависимости и `jackson-datatype-hibernate5`
  - datatables API (1.10)
  - Postgres драйвер. Новый драйвер поддерживает Java 8 Time API, разделил реализацию JdbcMealRepositoryImpl на Java8 (Postgresql) и Timestamp (HSQL)
