# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkxqbVpwZUd5anQ2TXE4bm5HbXhtVmkxMUxFSjhNQ1hXYVVTTTZEMzkzN2s">Материалы занятия</a>

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW3

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW03: meals + JdbcMealRepository](https://drive.google.com/file/d/1_IIYBP5l2aHxHaY_j1bqFWj1maACvNpa)
- [Последовательность столбцов в составном индексе](https://ru.wikipedia.org/wiki/Индекс_(базы_данных)#Последовательность_столбцов_в_составном_индексе)
- Дополнительно: для `JdbcMealRepository.save` можно использовать [CombinedSqlParameterSource](https://stackoverflow.com/questions/13339171/548473)
- В ответе на [Why is SELECT * considered harmful?](https://stackoverflow.com/questions/3639861) есть случаи, когда она допустима (наш случай): `when "*" means "a row"`

#### `SpringMain, InMemoryAdminRestControllerTest, InMemoryAdminRestControllerSpringTest` починим в патче `4_7_create_inmemory_test_ctx` (видео 4)

#### **Apply 4_1_HW3.patch**
> Таблицы [принято именовать в единственном числе](https://stackoverflow.com/questions/338156/548473) (`users` - исключение, тк это ключевое слово).  
Переименовал таблицы в `user_role` и `meal`. Если у вас остались старая база с множественными именами - удалите их вручную.

### [Сравнение времени выполнения для разных индексов](meals_index.md)
- <a href="http://stackoverflow.com/questions/970562/postgres-and-indexes-on-foreign-keys-and-primary-keys">На id как на primary key индекс создается автоматически</a>
- Все запросы в таблицу meals у нас идут с `user_id`
- По полю `date_time` не только есть запросы, но и сортируем результат. То есть это поле - хороший кандидат для индексирования
- Следует иметь в виду, что индексы ускоряют операции чтения, но замедляют вставку и удаление. Поэтому необходим анализ в реальном приложении
- [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habrahabr.ru/post/203320/)
- [Оптимизация запросов. Часть 2](https://habrahabr.ru/post/203386/)
- [Оптимизация запросов. Часть 3](https://habrahabr.ru/post/203484/)
- [Документация Postgres: индексы](https://postgrespro.ru/docs/postgresql/9.6/indexes.html)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW3

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [HW03 Optional: Meals tests](https://drive.google.com/file/d/1RfO0Irz8ayw2ivnjffUol20BQrKpu-jg)

#### **Apply 4_2_HW3_optional.patch**
> Убрал `throws Exception` из тестов. IDEA по умолчанию перестала их добавлять.
> В `MealServiceTest.updateNotOwn` добавил дополнительную проверку, что еда в тесте не модифицировалась.

#### **Apply 4_3_tests_refactoring.patch**
> - Переименовал класс генерации матчеров в `MatcherFactory`
> - Переименовал статический метод генерации ([Блох Джошуа, "Java. Эффективное программирование."](http://javaops.ru/view/books)) на `usingIgnoringFieldsComparator`.

#### **Apply 4_4_HW3_fix_logging.patch**
- [Вызов статического метода из конфигурации спринга](https://stackoverflow.com/a/27296470/548473) 

## Занятие 4:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU005ZzBNZmZnTVU">Методы улучшения качества кода</a>

#### Поменяйте в `readme.md` сверху ссылку на свой `Codacy Badge` с сайта Codacy: `https://app.codacy.com/gh/[github_accaunt]/topjava/settings`

> - Плагины проверки качества кода теперь объединены в один **QAPlug**
> - <a href="https://www.codacy.com">Codacy Check code</a> (проверка стиля и поиск багов в коде).
>    - добавил [Codacy configuration file](https://support.codacy.com/hc/en-us/articles/360005097654-Ignore-files-from-Codacy-analysis) для исключения из проверок содержимого `webapp` и `READ.me` (на нашем проекте он выдает на них кучу ошибок)
>    - после правок паттернов можно сделать [повторный анализ](https://support.codacy.com/hc/en-us/articles/213840489-How-do-I-reanalyze-my-project-), с результатами тормозит  

#### Сделайте `push` для отображения результатов текущего состояния проекта.

#### **Apply 4_5_improve_code.patch**
> - Добавил проверки предусловий `Assert.notNull` в сервисы
> - Добавил конфигурацию `.codacy.yml`
> - Ввел удобный метод `AbstractBaseEntity.id()`
 
- <a href="https://ru.wikipedia.org/wiki/Контрактное_программирование">Контрактное программирование</a>, <a href="http://neerc.ifmo.ru/wiki/index.php?title=Программирование_по_контракту">Программирование по контракту</a>
- <a href="https://www.sw-engineering-candies.com/blog-1/comparison-of-ways-to-check-preconditions-in-java">Comparison Preconditions in Java</a>
- IDEA Settings -> Plugins -> Browse repositories... Add [QAPlug: PMD/FindBugs/Checkstyle/Hammurapi](https://qaplug.com/about/)
  - Tools -> QAPlug -> Analyze Code...
- IDEA [Analyze | Inspect Code](https://www.jetbrains.com/help/idea/running-inspections.html)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=1faq0dtek-RcBENfISkod35PGU5jcuOsB">Spring: инициализация и популирование DB</a>
#### **Apply 4_6_init_and_populate_db.patch**
-  [Инициализация базы при старте приложения](https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc-initializing-datasource-xml)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNTNWV04weDBGSmc">Подмена контекста при тестировании</a>
#### **Apply 4_7_create_inmemory_test_ctx.patch**
> Переименовал `mock.xml` в `inmemory.xml`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.1 [Основные определения ORM. Hibernate. JPA](https://drive.google.com/file/d/1S--FEm2R2LSgjN1ALAj7sN4AAFJ7rt4r)
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.2 <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVWZYcHoyUF9qX2M">ORM. Hibernate. JPA.</a>
![image](https://user-images.githubusercontent.com/13649199/139490982-0e8d6a6a-f1b6-4db0-8c61-f49f2056c274.png)

<a href="https://en.wikipedia.org/wiki/Entity%E2%80%93relationship_model">Entity</a>- класс (объект Java), который в ORM маппится в таблицу DB.
> - Убрал дублирование объявления `unique` для `User.email`

[EntityManager](https://jsehelper.blogspot.com/2016/04/java-persistence-api-jpa-2.html) - это по сути прокси-обертка над Hibernate Session, которая создается каждый раз при открытии транзакции.

- ВНИМАНИЕ: патч меняет `postgres.properties`, в котором у вас, возможно, свои креденшелы к базе
- `hibernate-core` с 5.2.x включает `hibernate-entitymanager` и `hibernate-java8`, то есть конверторы Time API уже не нужны
>    -  <a href="http://stackoverflow.com/questions/23718383/jpa-support-for-java-8-new-date-and-time-api">JPA support for Java 8 new date and time API</a>
>    -  <a href="http://stackoverflow.com/questions/31965179/whats-new-in-hibernate-5">What's new in Hibernate 5?</a>
>    -  <a href="http://stackoverflow.com/a/33001846/548473">JPA support for Java 8 new date and time API</a>
- [EL implementation provided by the container. In a Java SE you have to add an implementation as dependency to your POM file](http://hibernate.org/validator/documentation/getting-started/#unified-expression-language-el): добавил `javax.el` зависимость со `scope=provided`

#### **Apply 4_8_add_jpa.patch**
> - **[Настройка JPA в IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA#jpa).   
ПРОВЕРЬТЕ, что у вас не подтянулись Java EE libraries, все зависимости в проект попадают только через Maven! Перед настройкой сначала подтяните его зависимости**
> - `indexes` и `uniqueConstraints` в Entities (у нас `User`) используются только при создании таблицы средствами JPA (автогенерации БД при запуске приложения).
 В случае, если таблицы создаются скриптом, эти опции будут проигнорированы. У нас они дублируют ограничения в `initDB.sql` и будут использоваться, когда мы будем смотреть на автогенерацию DDL по модели на 7-м занятии. 
> - Тесты и приложение ломаются. `MealServiceTest` починится после выполнения HW04 (`JpaMealRepository`)
> - Если вы используете Java 9 и выше, то возникают проблемы с `JAXBException` (пакет `java.xml.bind`). [См. решение](https://www.concretepage.com/forum/thread?qid=531

- [JPQL в JPA](https://russianblogs.com/article/74321542380/)
- Дополнительно:
  -  [ORM](http://ru.wikipedia.org/wiki/ORM)
  -  [JPA и Hibernate в вопросах и ответах](http://habrahabr.ru/post/265061/)
  - [Hibernate — о чем молчат туториалы](https://habr.com/ru/post/416851/)
  - [Наследование в Hibernate: выбор стратегии](https://habrahabr.ru/post/337488/)
  - [Entity Lifecycle Model in JPA & Hibernate](https://thorben-janssen.com/entity-lifecycle-model/)
    - [Field vs property access](http://stackoverflow.com/a/6084701/548473)
    - [Hibernate: введение и написания Hello world приложения](https://web.archive.org/web/20200810114404/http://www.quizful.net/post/Hibernate-3-introduction-and-writing-hello-world-application)
    - [15 reasons why we need to choose Hibernate over JDBC](https://web.archive.org/web/20211201122631/https://habiletechnologies.com/blog/reasons-to-choose-hibernate-over-jdbc/#fin_form_pop)
    - [Hibernate or JDBC](https://stackoverflow.com/questions/1353137/548473)
    -  [Mapping: описание модели Hibernate (hbm.xml/annotation)](http://en.wikibooks.org/wiki/Java_Persistence/Mapping).
    -  [used in Playframework](https://ru.wikipedia.org/wiki/Hibernate_(библиотека)">Hibernate). Другие ORM: [TopLink](http://en.wikipedia.org/wiki/TopLink), [EсlipseLink](http://en.wikipedia.org/wiki/EclipseLink)
    -  [Jakarta Persistence (JPA, english wiki)](https://en.wikipedia.org/wiki/Java_Persistence_API)
    -  [Стратегии генерации PK](http://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing)
    -  [hibernate-validator](http://validator.hibernate.org).
    -  [Описание связей в модели. Ленивая загрузка объекта.](https://web.archive.org/web/20170514002949/http://java.devcolibri.com:80/post/15)
    -  [JPA definitions](http://docs.jboss.org/hibernate/entitymanager/3.6/reference/en/html/architecture.html#d0e61)
    -  [Spring expressions: выражения в конфигурации](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions)
    -  [HQL](https://proselyte.net/tutorials/hibernate-tutorial/hibernate-query-language), [JPQL](http://ru.wikipedia.org/wiki/Java_Persistence_Query_Language)
    -  Динамические запросы (которые формируются в коде): [Unified Queries for Java](http://www.querydsl.com/), [JPA Criteria API](http://www.objectdb.com/java/jpa/query/criteria)
    -  [Using the Java 8 Date Time Classes with JPA](https://web.archive.org/web/20170608194049/https://bitbucket.org/montanajava/jpaattributeconverters)

#### **Apply 4_9_add_named_query_and_transaction.patch**
> Чтобы посмотреть информацию о транзакциях (открытие/закрытие и пр.), можно выставить в конфигурации logback 
`<logger name="org.springframework.orm.jpa.JpaTransactionManager" level="debug"/>`

-  <a href="http://ru.wikipedia.org/wiki/Транзакция_(информатика)">Транзакция. ACID. Уровни изоляции транзакций.</a>
-  <a href="http://www.tutorialspoint.com/spring/spring_transaction_management.htm">Spring Transaction Management</a>
-  <a href="http://habrahabr.ru/post/232381/">`@Transactional` в тестах. Настройка EntityManagerFactory</a>
-  [Эффективное управление транзакциями в Spring](https://habr.com/ru/company/otus/blog/431508/)

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png)  Зачем надо начинать транзакцию, если речь идет только о чтении данных? Начало транзакции при выполнении операции чтения всего лишь добавит лишних накладных расходов 
(см. [Стратегии работы с транзакциями, pаспространенные ошибки](http://web.archive.org/web/20170314073834/https://www.ibm.com/developerworks/ru/library/j-ts1/index.html))

Вот ответ от Oliver Drotbohm, автора Spring-Data на предложение работать без транзакций для операций чтения (`propagation=Propagation.SUPPORTS`): [Improve performance with Propagation.SUPPORTS for readOnly operation](https://jira.spring.io/browse/DATAJPA-601). Коротко:
- Статья устаревшая и неверно упрощает многие вещи. Есть множество вещей, которые влияют на производительность
- Без транзакции не будет оптимизации по флагу `readOnly` при выполнении JDBC и в управлении ресурсами Spring's JPA (в том числе выключение `flush`)
См. [Non-transactional data access and the auto-commit mode](https://developer.jboss.org/wiki/Non-transactionalDataAccessAndTheAuto-commitMode)

Справочник:
   - <a href="https://www.youtube.com/watch?v=dFASbaIG-UU">Видео: Вячеслав Круглов — Как начинающему Java-разработчику подружиться со своей базой данных?</a>
   - <a href="http://www.youtube.com/watch?v=YzOTZTt-PR0">Видео: Николай Алименков — Босиком по граблям Hibernate</a>
   - <a href="https://www.youtube.com/watch?v=b52Qz6qlic0">Видео: Николай Алименков — Сделаем Hibernate снова быстрым</a>
   - <a href="https://www.ibm.com/developerworks/ru/library/j-ts2/">Стратегии работы с транзакциями</a>
   - <a href="http://www.byteslounge.com/tutorials/spring-transaction-propagation-tutorial">Spring transaction propagation tutorial</a>
   - <a href="https://dzone.com/refcardz/getting-started-with-jpa">Getting Started with JPA</a>
   - <a href="http://en.wikibooks.org/wiki/Java_Persistence">Java Persistence</a>
   - <a href="https://web.archive.org/web/20220519045236/https://easyjava.ru/category/data/jpa/">Разделы по Java Persistence API</a>
   - <a href="http://docs.spring.io/spring-framework/docs/4.0.x/spring-framework-reference/html/transaction.html">Spring Framework transaction management</a>
   - <a href="http://www.baeldung.com/persistence-with-spring-series/">Spring Persistence Tutorial</a>
   - <a href="http://www.objectdb.com/java/jpa/persistence/managed#Entity_Object_Life_Cycle">Working with JPA Entity Objects</a>
   - <a href="http://habrahabr.ru/post/208400/">Принципы работы СУБД. MVCC</a>
   - <a href="https://ru.wikipedia.org/wiki/MVCC">MVCC</a>


###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSTJEQ1Rvd3Jvc2c">Добавляем поддержку HSQLDB</a>

#### **Apply 4_10_add_hsqldb.patch**
> - Переделал `jdbc.initLocation`: IDEA не "тупит", если путь к скрипту полностью прописывать в пропертях.    

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

>  Есть несколько аналогичных "встроенных" баз данных. H2, HSQLDB, Derby, SQLite. Почему был выбран HSQLDB?

Просто с ней приходилось работать. HSQLDB и H2 наиболее популярны. В миграции на spring-boot будем использовать H2.
Здесь интересное краткое описание <a href="https://web.archive.org/web/20220519045402/https://easyjava.ru/data/vstraivaemye-bazy-dannyx-v-java/">встраиваемых баз данных в Java</a>. 
В HSQLDB нет репликаций, кластеризации и объем данным ограничен несколькими TB. Для большого количества приложений она подходит и для продакшена. См.
- <a href="http://stackoverflow.com/questions/4152911/what-is-hsqldb-limitations">What is HSQLDB limitations?</a>
- <a href="https://habrahabr.ru/sandbox/23199/">HSQLDB в режиме in-process</a>

> Чистого JPA не существует, т. е. это всего лишь интерфейс, спецификация? Говорим JPA, подразумеваем какой-то ORM фрэймворк? А что тогда используют чистый jdbc, Spring-jdbc, MyBatis? MyBatis не реализует JPA?

<a href="https://ru.wikipedia.org/wiki/ORM">ORM</a> это технология связывания БД и объектов приложения, а <a href="https://ru.wikipedia.org/wiki/Java_Persistence_API">JPA</a> - это JavaEE спецификация (API) этой технологии.
Реализации JPA - Hibernate, OpenJPA, EclipceLink, но, например, Hibernate может работать по собственному API (без JPA, которая появилась позже). Spring-JDBC, MyBatis, JDBI не реализуют JPA - это обертки к JDBC. Все ORM и JPA также реализованы поверх JDBC.

> В зависимостях maven `hibernate-entitymanager` тянет за собой `jboss-logging`. Как будет происходить логирование?

<a href="http://stackoverflow.com/questions/11639997/how-do-you-configure-logging-in-hibernate-4-to-use-slf4j">How do you configure logging in Hibernate 4 to use SLF4J</a>: в нашем проекте автоматически подхватывается `logback-classic`.

> В чем преимущество Hibernate?

Hibernate (как любая ORM) реализует мапинг таблиц в объекты Java. Когда мы добавим роли пользователю, вы увидите, насколько код будет проще, чем в jdbc. Также см. <a href="https://www.sitepoint.com/5-reasons-to-use-jpa-hibernate/">5 Reasons to Use JPA / Hibernate</a>

> Чем отличается `@Column(nullable = false)`  от  `@NotNull` и есть ли необходимость указывать обе аннотации ?

`@Column(nullable = false)` - это атрибуты колонки таблицы базы. `@NotNull` - это валидация, которая происходит в приложении перед вставкой в базу. Если колонка ненулевая, то `NOT NULL` обязательна. Валидация опциональна. Также см.
<a href="http://stackoverflow.com/questions/7439504/">@NotNull vs @Column(nullable = false)</a>

> почему мы в бине `entityManagerFactory` не указали диалект базы данных?

Он [автоматически определяется из `DataSource` драйвера](http://stackoverflow.com/a/39817822/548473)

> В чем разница между `persist` и `merge`

<a href="http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge">Подробный ответ со Stackoverflow</a> с объяснением разницы. Упрощенно:
  - `merge`, в отличие от `persist`, делает запрос в базу данных, если entity нет в текущей сессии
  - entity, переданный в `merge`, не меняется. Нужно использовать возвращаемый результат

> `em.merge` при отсутствии старой записи (несуществующий `id`) создает новую. Т. е. в `JpaUserRepository` нарушается логика

В Hibernate есть такая бага: https://hibernate.atlassian.net/browse/HHH-1661, https://stackoverflow.com/questions/34249483
- [Hibernate unexpectedly issues INSERT instead of throwing the javax.persistence.OptimisticLockException, when a nonexistent entity is passed to merge()](https://stackoverflow.com/questions/34249483)
- [Should Hibernate Session#merge do an insert when receiving an entity with an ID?](https://stackoverflow.com/questions/21489300)

Если это действительно наш критичный бизнес-кейс (например, с многопоточным удалением entity), то нужно искать варианты обходного решения.
Если это не бизнес-кейс (попытка поломать или ошибка UI), то оставляем как есть (обычно на практике не парятся).

> Почему в проекте транзакционность сделана в слое репозитория, а не сервиса? Транзакциями удобнее пользоваться на слое сервисов, так как здесь  реализуется бизнес логика и бывает нужно делать несколько операций в одной транзакции.

С классической точки зрения все транзакции действительно объявляются на уровне сервиса. Мы будем использовать в логике сервиса несколько запросов и тогда сделаем дополнительную транзакцию на методе сервисе. Новая транзакция при этом не создается (по умолчанию используется `Propagation.REQUIRED`, который поддерживают существующую), поэтому несколько `@Transactional` аннотаций ведут себя как одна. Я использую подход `spring-data-jpa` (будет на следующем занятии): в репозитории транзакции объявлять удобно, тк не надо думать о них в сервисах.

--------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW4

- 1: Сделать из `Meal` Hibernate entity
  - <a href="http://stackoverflow.com/questions/17137307">Hibernate Validator: @NotNull, @NotEmpty, @NotBlank</a>
  - <a href="https://en.wikibooks.org/wiki/Java_Persistence/ManyToOne">Реализация ManyToOne</a>
- 2: Имплементировать и протестировать `JpaMealRepository`. Проверьте, нет ли в запросах ненужных данный (лишних `JOIN`)

### Optional

- 3: Добавить в тесты `MealServiceTest` функциональность `@Rule`:
  - 3.1: вывод в лог времени выполнения каждого теста
  - 3.2: вывод сводки в конце класса: имя теста - время выполнения
-  <a href="https://github.com/junit-team/junit/wiki/Rules">JUnit @Rules</a>

---------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации
-  1: Т.к. JPA работает с объектами, мы не можем использовать `userId` для сохранения. Можно сделать, например, так:

        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);

   При этом от `User` нам нужен только `id`. Над `id` создается lazy прокси, который обращается к базе при запросе любого поля. Т. е. у нас запроса в базу за юзером не будет: проверьте по логам Hibernate.

**Внимание: проверять запросы Hibernate нужно через run. Если делаете debug и брекпойнт, то могут делаться лишние запросы к базе (дебаггер дергает `toString`)**
   
- 2: В JPQL запросах можно писать: `m.user.id=:userId`
- 3: При реализации `JpaMealRepository` предпочтительно не использовать `try-catch` в логике реализации. Но, если очень хочется, то ловить только специфические исключения (напр. `NoResultException`), чтобы, например, при отсутствии коннекта к базе приложение отвечало адекватно.
- 4: Мы будем смотреть генерацию db-скриптов из модели. Для корректной генерации нужно в `Meal` добавить `uniqueConstraints`
- 5: При записи в базу через `namedQuery` валидация энтити не работает, только валидация в БД.
- 6: Результат `AssertionError` печатает результаты через `toString`, и поля в выводе могут не совпадать с полями сравнения (`toString` одинаковый, при этом сравнение идет по другим полям и вылетает ошибка)
- 7: Если нашему приложению `Meal.user` не требуется, не следует включать его в тесты. В следующем уроке мы потренируемся разными способами доставать зависимости `Meal.user` и `User.meals`

--------------------------------
## [Выпускной проект](graduation.md)
Новая информация плохо оседает в голове, когда дается в виде патчей. Поэтому, чтобы она стала "твоей", нужно еще раз проделать это самостоятельно. Домашнее задание на этом уроке небольшое, а полученных знаний уже достаточно для начала работы над выпускным проектом, основанным на нашем стеке.
Выпускной проект делайте параллельно с нашим: прошли тему занятия - сделали ее в выпускном. Не следует забегать вперед, но и не отставайте!
- Для проекта я взял реальное тестовое задание, поэтому жалоб на неясность формулировок принимать не буду. Сделайте как поняли. Представьте, что это **ваше тестовое задание на работу**
- Общение по выпускному в канале Slack *#graduation*
- **Обязательно проверяйся [по рекомендациям в конце выпускного](graduation.md#-Рекомендации)**
- По завершению вы сможете занести этот проект в свое портфолио и резюме как собственный без всяких оговорок

### Успехов в выполнении!
