# Онлайн-проект <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkxqbVpwZUd5anQ2TXE4bm5HbXhtVmkxMUxFSjhNQ1hXYVVTTTZEMzkzN2s">Материалы занятия</a>

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 4_0_fix.patch
- в `InMemory...Test` классах был перепутан порядок аргументов `expected` и `actual`. Достаточно частая ошибка, обращайте внимание.
- небольшие правки

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW3

> `SpringMain, InMemoryAdminRestControllerTest, InMemoryAdminRestControllerSpringTest` починим в патче **4_6_create_inmemory_test_ctx.patch (видео 4)**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdVhaMklZQVNkUGc">JdbcMealRepository + MealServiceTest</a>
#### **Apply 4_1_HW3.patch**
> - В `JdbcUserRepository` поменял `MapSqlParameterSource` на `BeanPropertySqlParameterSource` (поля для вставки определяются через отражение в бине и метаданные в SQL запросе). 
 В `JdbcMealRepository` остается `MapSqlParameterSource`, т.к. в отсутствует `Meal.userId`. См. дополнительно [CombinedSqlParameterSource](https://www.codota.com/java/spring/scenarios/549bbb5dda0a9536b85ad5f3/org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource?tag=spring1b)
> - Новый Postgres драйвер <a href="https://jdbc.postgresql.org/documentation/head/8-date-time.html">поддерживает Java 8 Date and Time</a>. Преобразования c `Timestamp` уже не нужны.
> - В meals добавил составной индекс `INDEX meals_unique_user_datetime_idx ON meals(user_id, date_time)` для повышения скорости запросов по этим полям

**Примечание**: в ответе на [Why is SELECT * considered harmful?](https://stackoverflow.com/questions/3639861) есть случаи, когда она допустима (наш случай): `when "*" means "a row"`

- <a href="http://www.techonthenet.com/postgresql/between.php">POSTGRESQL: BETWEEN CONDITION</a>
- **[Сравнение времени выполнения для разных индексов](meals_index.md)**
  - <a href="http://stackoverflow.com/questions/970562/postgres-and-indexes-on-foreign-keys-and-primary-keys">На id как на primary key индекс создается автоматически</a>.
  - все запросы в таблицу meals у нас идут с `user_id`
  - по полю `date_time` также есть запросы + мы по нему сортируем список результатов, те они - хорошие кандидаты для индексирования.
  - следует иметь в виду: индексы ускоряют операции чтения, но замедляют вставку и удаление, поэтому необходим анализ в реальном приложении
  - [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habrahabr.ru/post/203320/)
  - [Оптимизация запросов. Часть 2](https://habrahabr.ru/post/203386/)
  - [Оптимизация запросов. Часть 3](https://habrahabr.ru/post/203484/)
  - [Документация Postgres: индексы](https://postgrespro.ru/docs/postgresql/9.6/indexes.html)

#### **Apply 4_2_HW3_optional.patch**
> Удалил лишние `MealsUtil.MEALS`

#### **Apply 4_3_HW3_fix_logging.patch**
> При запуске одновременно `User-` и `MealServiceTest` (нарпимер через `mvn test`) `SLF4JBridgeHandler` добавится 2 раза и логи postgres во втором тесте дублируются.
Перенес инициализацию в приложение, т.к логи могут понадобится не только в тестах, но и здесь.
- см. [вызов статического метода из конфигурации спринга](https://stackoverflow.com/a/27296470/548473) 

## Занятие 4:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU005ZzBNZmZnTVU">Методы улучшения качества кода</a>
- Добавьте в своем `readme.md` сверху две строчки `Codacy Badge` и `Build Status`, по аналогии c моим [README.md](https://github.com/JavaWebinar/topjava/blob/master/README.md) ([Raw](https://raw.githubusercontent.com/JavaWebinar/topjava/master/README.md)). `Codacy Badge` берется с сайта `codacy -> Settings`
  - <a href="https://www.codacy.com">Codacy Check code</a> (проверка стиля и поиск багов в коде).
     - добавил [Codacy configuration file](https://support.codacy.com/hc/en-us/articles/360005097654-Ignore-files-from-Codacy-analysis) для ислючения из проверок содержимого `webapp` и `READ.me` (на нашем проекте он выдает на них кучу ошибок)
     - после правок паттернов можно сделать [повторный анализ](https://support.codacy.com/hc/en-us/articles/213840489-How-do-I-reanalyze-my-project-). С результатами тормозит.  
  - <a href="https://travis-ci.org/">Сборку и тесты Travis</a> (результат выполнения тестов проекта)
     - [Что такое travis-ci.org](https://habr.com/post/140344/)
     - [Travis CI Tutorial](https://dzone.com/articles/travis-ci-tutorial-java-projects)
     - <a href="https://docs.travis-ci.com/user/languages/java/">Сборка Java проекта</a>
  - Сервис по проверке `maven` зависимостей VersionEye [закрыли](https://blog.versioneye.com/2017/10/26/the-start-of-a-new-journey). Ищу замену...
#### Сделайте `push` для отображения результатов текущего состояния проекта.

#### **Apply 4_4_improve_code.patch**
Для пояснения материала видео сделал проверку предусловий по разному: `Objects.requireNonNull` и `Assert.notNull`. В реальном проекте везде используются один подход.

> - Перенес проверки предусловий `Assert` из `InMemory`-репозиториев в сервисы
> - Добавил конфигурацию `.travis.yml` и `.codacy.yml`

- <a href="https://ru.wikipedia.org/wiki/Контрактное_программирование">Контрактное программирование</a>, <a href="http://neerc.ifmo.ru/wiki/index.php?title=Программирование_по_контракту">Программирование по контракту</a>
- <a href="https://www.sw-engineering-candies.com/blog-1/comparison-of-ways-to-check-preconditions-in-java">Comparison Preconditions in Java</a>
- IDEA Settings -> Plugins -> Browse repositories... Add [QAPlug: PMD/FindBugs/Checkstyle/Hammurapi](https://qaplug.com/about/)
  - Tools -> QAPlug -> Analyze Code...
- IDEA [Analyze | Inspect Code](https://www.jetbrains.com/help/idea/running-inspections.html)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=1faq0dtek-RcBENfISkod35PGU5jcuOsB">Spring: инициализация и популирование DB</a>
#### **Apply 4_5_init_and_populate_db.patch**
-  [Инициализация базы при старте приложения](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc-initializing-datasource-xml)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNTNWV04weDBGSmc">Подмена контекста при тестировании</a>
#### **Apply 4_6_create_inmemory_test_ctx.patch**
> Переименовал `mock.xml` в `inmemory.xml`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVWZYcHoyUF9qX2M">ORM. Hibernate. JPA.</a>
<a href="https://en.wikipedia.org/wiki/Entity%E2%80%93relationship_model">Entity</a>- класс (объект Java), который в ORM маппится в таблицу DB.

> - ВНИМАНИЕ: патч меняет `postgres.properties`, в котором у вас возможно свои креденшелы к базе.
> - `hibernate-core` с 5.2.x включает `hibernate-entitymanager` и `hibernate-java8`, конверторы Time API уже не нужны.
>    -  <a href="http://stackoverflow.com/questions/23718383/jpa-support-for-java-8-new-date-and-time-api">JPA support for Java 8 new date and time API</a>
>    -  <a href="http://stackoverflow.com/questions/31965179/whats-new-in-hibernate-5">What's new in Hibernate 5?</a>
>    -  <a href="http://stackoverflow.com/a/33001846/548473">JPA support for Java 8 new date and time API</a>
> - [EL implementation provided by the container. In a Java SE you have to add an implementation as dependency to your POM file](http://hibernate.org/validator/documentation/getting-started/#unified-expression-language-el): добавил `javax.el` зависимость со `scope=provided`

#### **Apply 4_7_add_jpa.patch**
> - **Внимание: при [настройке JPA в IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%94%D0%BE%D0%B1%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-jpa) НЕ скачивайте библиотеку javaee.jar (и любую другую). Все зависимости в проект попадают только через Maven.**
> - Тесты и приложение ломаются. `MealServiceTest` починится после выполнения HW04 (`JpaMealRepository`)
> - Если вы используете Java 9 и выше, то возникают проблемы с `JAXBException` (пакет `java.xml.bind`). [См. решение](https://www.concretepage.com/forum/thread?qid=531)
- Дополнительно:
    -  <a href="http://ru.wikipedia.org/wiki/ORM">ORM</a>.
    -  <a href="http://habrahabr.ru/post/265061/">JPA и Hibernate в вопросах и ответах</a>
    - [Hibernate — о чем молчат туториалы](https://habr.com/ru/post/416851/)
    - [Наследование в Hibernate: выбор стратегии](https://habrahabr.ru/post/337488/)
    - <a href="https://easyjava.ru/data/jpa/jpa-entitymanager-upravlyaem-sushhnostyami/">JPA EntityManager: управляем сущностями</a>
    - [Field vs property access](http://stackoverflow.com/a/6084701/548473)
    - <a href="http://www.quizful.net/post/Hibernate-3-introduction-and-writing-hello-world-application">Hibernate: введение и написания Hello world приложения</a>
    - [15 reasons why we need to choose Hibernate over JDBC](https://habiletechnologies.com/blog/reasons-to-choose-hibernate-over-jdbc)
    -  <a href="http://en.wikibooks.org/wiki/Java_Persistence/Mapping">Mapping: описания модели Hibernate (hbm.xml/annotation)</a>.
    -  <a href="https://ru.wikipedia.org/wiki/Hibernate_(библиотека)">Hibernate</a>. Другие ORM: <a href="http://en.wikipedia.org/wiki/TopLink">TopLink</a>, <a href="http://en.wikipedia.org/wiki/EclipseLink">EсlipseLink</a>, <a href="http://en.wikipedia.org/wiki/Ebean">EBean</a> (<a href="http://www.playframework.com/documentation/2.2.x/JavaEbean">used in Playframework</a>).
    -  <a href="http://ru.wikipedia.org/wiki/Java_Persistence_API">JPA (wiki)</a>. <a href="https://en.wikipedia.org/wiki/Java_Persistence_API">JPA (english wiki)</a>. <a href="http://www.jpab.org/All/All/All.html">JPA Performance Benchmark</a>
    -  <a href="http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing">Стратегии генерации PK</a>
    -  <a href="http://validator.hibernate.org">hibernate-validator</a>. <a href="http://stackoverflow.com/questions/14730329/jpa-2-0-exception-to-use-javax-validation-package-in-jpa-2-0">JSR-303 -> JSR-349</a>
    -  <a href="https://web.archive.org/web/20170514002949/http://java.devcolibri.com:80/post/15">Описание связей в модели. Ленивая загрузка объекта.</a>
    -  <a href="http://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/architecture.html#d0e61">JPA definitions</a>
    -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions">Spring expressions: выражения в конфигурации</a>
    -  <a href="http://docs.jboss.org/hibernate/orm/4.2/devguide/en-US/html/ch11.html">HQL</a>/ <a href="http://ru.wikipedia.org/wiki/Java_Persistence_Query_Language">JPQL</a>.
    -  Динамические запросы (которые формируются в коде): <a href="http://www.objectdb.com/java/jpa/query/criteria">JPA Criteria API</a>. <a href="http://www.querydsl.com/">Unified Queries for Java</a>
    -  <a href="https://bitbucket.org/montanajava/jpaattributeconverters">Using the Java 8 Date Time Classes with JPA</a>

#### **Apply 4_8_add_named_query_and_transaction.patch**

-  <a href="http://ru.wikipedia.org/wiki/Транзакция_(информатика)">Транзакция. ACID. Уровни изоляции транзакций.</a>
-  <a href="http://www.tutorialspoint.com/spring/spring_transaction_management.htm">Spring Transaction Management</a>
-  <a href="https://jira.spring.io/browse/DATAJPA-601">readOnly и Propagation.SUPPORTS</a>
-  <a href="http://habrahabr.ru/post/232381/">`@Transactional` в тестах. Настройка EntityManagerFactory</a>

Справочник:
   - <a href="https://www.youtube.com/watch?v=dFASbaIG-UU">Видео: Вячеслав Круглов — Как начинающему Java-разработчику подружиться со своей базой данных?</a>
   - <a href="http://www.youtube.com/watch?v=YzOTZTt-PR0">Видео: Николай Алименков — Босиком по граблям Hibernate</a>
   - <a href="https://www.ibm.com/developerworks/ru/library/j-ts2/">Стратегии работы с транзакциями</a>
   - <a href="https://easyjava.ru/tag/jpa/">Примеры работы с JPA</a>
   - <a href="http://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial">Spring transaction propagation tutorial</a>
   - <a href="https://dzone.com/refcardz/getting-started-with-jpa">Getting Started with JPA</a>
   - <a href="http://en.wikibooks.org/wiki/Java_Persistence">Java Persistence</a>
   - <a href="https://easyjava.ru/category/data/jpa/">Разделы по Java Persistence API</a>
   - <a href="http://docs.spring.io/spring-framework/docs/4.0.x/spring-framework-reference/html/transaction.html">Spring Framework transaction management</a>
   - <a href="http://www.baeldung.com/persistence-with-spring-series/">Spring Persistence Tutorial</a>
   - <a href="http://www.objectdb.com/java/jpa/persistence/managed#Entity_Object_Life_Cycle">Working with JPA Entity Objects</a>
   - <a href="http://www.ibm.com/developerworks/ru/library/j-ts1/">Стратегии работы с транзакциями: Распространенные ошибки</a>
   - <a href="http://habrahabr.ru/post/208400/">Принципы работы СУБД. MVCC</a>
   - <a href="https://ru.wikipedia.org/wiki/MVCC">MVCC</a>


###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSTJEQ1Rvd3Jvc2c">Добавляем поддержку HSQLDB</a>

#### **Apply 4_9_add_hsqldb.patch**

> - ВНИМАНИЕ: патч меняет `postgres.properties`
> - IDEA может `${jdbc.initLocation}` подчеркивать красным - тупит...

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

>  Есть несколько аналогичных "встроенных" баз данных. H2, HSQLDB, Derby, SQLite. Почему был выбран HSQLDB?

Просто с ней приходилось работать. HSQLDB и H2 наиболее популярны, в новом курсе по spring-boot планирую использовать H2.
Здесь интересное краткое описание <a href="http://easyjava.ru/data/vstraivaemye-bazy-dannyx-v-java/">встраиваемых баз данных в Java</a>. 
В HSQLDB нет репликаций, кластеризации, и объем данным ограничен несколькими TB. Для большого количества приложений она подходит и для продакшена. См.
- <a href="http://stackoverflow.com/questions/4152911/what-is-hsqldb-limitations">What is HSQLDB limitations?</a>
- <a href="https://habrahabr.ru/sandbox/23199/">HSQLDB в режиме in-process</a>

> Чистого JPA не существует, т.е. это всего лишь интерфейс, спецификация? Говорим JPA, подразумеваем какой-то ORM фрэймворк? А что тогда используют чистый jdbc, Spring-jdbc, MyBatis? MyBatis не реализует JPA?

<a href="https://ru.wikipedia.org/wiki/ORM">ORM</a> это технология связывания БД и объектов приложения, а <a href="https://ru.wikipedia.org/wiki/Java_Persistence_API">JPA</a> - это JavaEE спецификация (API) этой технологии.
Реализации JPA - Hibernate, OpenJPA, EclipceLink, но, например, Hibernate может работать по собственному API (без JPA, которая появилась позже). Spring-JDBC, MyBatis, JDBI не реализуют JPA, это обертки к JDBC. Все ORM и JPA также реализованы поверх JDBC.

> В зависимостях maven `hibernate-entitymanager` тянет за собой `jboss-logging`. Как будет происходить логгирование?

<a href="http://stackoverflow.com/questions/11639997/how-do-you-configure-logging-in-hibernate-4-to-use-slf4j">How do you configure logging in Hibernate 4 to use SLF4J</a>: в нашем проекте автоматически подхватывается `logback-classic`.

> В чем преимущество Hibernate?

Hibernate (как любая ORM) реализует маппинг таблиц в объекты Java. Когда мы добавим роли пользователю, вы увидите, насколько код будет проще, чем в jdbc. Также см. <a href="https://www.sitepoint.com/5-reasons-to-use-jpa-hibernate/">5 Reasons to Use JPA / Hibernate</a>

> Чем отличается `@Column(nullable = false)`  от  `@NotNull` и есть ли необходимость указывать обе аннотации ?

`@Column(nullable = false)` - это атрибуты колонки таблицы базы. `@NotNull` - это валидация, которая происходит в приложении перед вставкой в базу. Если колонка ненулевая, то `NOT NULL` обязательна. Валидация - опциональна. Также см.
<a href="http://stackoverflow.com/questions/7439504/">@NotNull vs @Column(nullable = false)</a>

> почему мы в бине `entityManagerFactory` не указали диалект базы данных?

Он [автоматически определяется из `DataSource` драйвера](http://stackoverflow.com/a/39817822/548473)

> В чем разница между `persist` и `merge`

<a href="http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge">Подробный ответ со Stackovwrflow</a> с объяснением разницы. Упрощенно:
  - `merge`, в отличие от `persist`, делает запрос в базу данных, если entity нет в текущей сессии
  - entity, переданный в `merge`, не меняется. Нужно использовать возвращаемый результат

> `em.merge` - при отсутствии старой записи (несуществующий `id`) создает новую. Т. е. в `JpaUserRepository` нарушается логика

В Hibernate есть такая бага: https://hibernate.atlassian.net/browse/HHH-1661, https://stackoverflow.com/questions/34249483
- [Hibernate unexpectedly issues INSERT instead of throwing the javax.persistence.OptimisticLockException, when a nonexistent entity is passed to merge()](https://stackoverflow.com/questions/34249483)
- [Should Hibernate Session#merge do an insert when receiving an entity with an ID?](https://stackoverflow.com/questions/21489300)

Если это действительно наш критичный бизнес-кейс (например, с многопоточным удалением entity) нужно искать варианты обходного решения.
Если же это результат неверного запроса, то оставляем как есть (обычно на практике не парятся)

> Почему в проекте транзакционность сделана в слое репозитория, а не сервиса? Транзакциями удобнее пользоваться на слое сервисов, так как здесь  реализуется бизнес логика и бывает нужно делать несколько операций в одной транзакции.

С классической точки зрения все транзакции действительно объявляются на уровне сервиса. Мы будем использовать в логике сервиса несколько запросов и тогда сделаем дополнительную транзакцию на методе сервисе. Новая транзакция при этом не создается (по умолчанию используется `Propagation.REQUIRED`, который поддерживают существующую), поэтому несколько `@Transactional` аннотаций ведут себя как одна. Я использую подход `spring-data-jpa` (будет на следующем занятии): в репозитории транзакции объявлять удобно, тк не надо думать о них в сервисах.

--------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW4

- 1: Сделать из `Meal` Hibernate entity
  - <a href="http://stackoverflow.com/questions/17137307">Hibernate Validator: @NotNull, @NotEmpty, @NotBlank</a>
  - <a href="https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne">Реализация ManyToOne</a>
- 2: Имплементировать и протестировать `JpaMealRepository`.

#### Optional

- 3: Добавить в тесты `MealServiceTest` функциональность `@Rule`:
  - 3.1: проверку Exception
  - 3.2: вывод в лог времени выполнения каждого теста
  - 3.3: вывод сводки в конце класса: имя теста - время выполнения
-  <a href="https://github.com/junit-team/junit/wiki/Rules">JUnit @Rules</a>
-  <a href="http://blog.qatools.ru/junit/junit-rules-tutorial#expectedexcptn">замена ExpectedException</a>

---------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации
-  1: Т.к. JPQL работает с объектами, мы не можем использовать `userId` для сохранения. Можно сделать, например, так:

        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

   При этом от `User` нам нужен только `id`. Над `id` создается lazy прокси, который обращается к базе при запросе любого поля. Т.е. у нас запроса в базу за юзером не будет- проверьте по логам Hibernate.

**Внимание: проверять запросы Hibernate нужно через run. Если делаете debug и брекпойнт, то могут делаться лишние запросы к базе (дебаггер дергает `toString`)**
   
- 2: В JPQL запросах можно писать: `m.user.id=:userId`
- 3: При реализации `JpaMealRepository` предпочтительно не использовать `try-catch` в логике реализации. Но если очень хочется, то ловить только специфические исключения (напр. `NoResultException`), чтобы, например, при отсутствии коннекта к базе приложение отвечало адекватно.
- 4: Мы будем смотреть генерацию db-скриптов из модели. Для корректной генерации нужно в `Meal` добавить `uniqueConstraints`
- 5: При записи в базу через `namedQuery` валидация энтити не работает, только валидация в БД.
- 6: Результат `AssertionError` печатает результаты через `toString`, который может не совпадать с полями сравнения.
- 7: Если нашему приложению `Meal.user` не требуется, не следует включать его в тесты. В следующем уроке мы потренируемся разными способами доставать зависимости `Meal.user` и `User.meals`
- 8: Старые версии IDEA тупят по поводу проверки `BETWEEN`. Обновитесь либо не обращайте внимания.

--------------------------------
## [Выпускной проект](graduation.md)
Новая информация плохо оседает в голове, когда дается в виде патчей, поэтому, чтобы она стала "твоей", нужно еще раз проделать это самостоятельно. Домашнее задание на этом уроке небольшое, а полученных знаний уже достаточно, чтобы после его выполнения начинать делать выпускной проект, основанный на нашем стеке.
Выпускной проект делаете в паралели с нашим: прошли тему занятия - сделали ее в выпускном. Не следует забегать вперед, но и не отставайте!
- Для проекта я взял реальное тестовое задание, поэтому жалоб не неясность формулировок принимать не буду- сделайте как поняли. Представьте, что это **ваше тестовое задание на работу**.
- Общение по выпусному в канале Slack *#graduation*
- **Обязательно проверяйся [по рекомендациям в конце выпускного](graduation.md#-Рекомендации)**
- По завершению ты сможешь занести этот проект в свое портфолио и резюме как собственный, без всяких оговорок.

### Успехов в выполнении!
