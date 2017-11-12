# Онлайн проект <a href="https://github.com/JavaWebinar/topjava">Topjava</a>
 
### <a href="https://drive.google.com/drive/u/0/folders/0B9Ye2auQ_NsFT1NxdTFOQ1dvVnM">Материалы занятия (скачать все патчи можно через Download папки patch)</a>

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 3_0_1_correction.patch
- Пофиксил багу c `history.back()` в `meal.jsp` для **FireFox** (коммит формы по `cancel`).
- Добавил еще один способы вывести `dateTime` через стандартную JSTL функцию `replace`  (префикс `fn` в шапке также надо поменять)
- Переименовал `meal.jsp` в `mealForm.jsp`
- Упростил `flatMap` в `MealsUtil.getFilteredWithExceededInOneReturn` 
- Мелкие правки

#### Apply 3_0_2_constructor_di_.patch
- Сделал для `UserServiceImpl` *Constructor Injection*. Выигрыш: final поля, независимость от контейнера
  - [Field vs Constructor vs Setter DI](http://stackoverflow.com/questions/39890849/what-exactly-is-field-injection-and-how-to-avoid-it)
  - [Перевод "Field Dependency Injection Considered Harmful"](https://habrahabr.ru/post/334636/)
  - [Inject 2 beans of same type](https://stackoverflow.com/a/2153680/548473)
  - [Implicit constructor injection for single-constructor scenarios](https://spring.io/blog/2016/03/04/core-container-refinements-in-spring-framework-4-3#implicit-constructor-injection-for-single-constructor-scenarios)
    - Аннотации `@Autowired` над конструктором оставил. IDEA понимает и думаю лучше делать это явным. 
> В контроллерах *Constructor Injection* делать не стал, добавляется лишний код (попробуйте сделать сами). На каждом проекте свои правила, универсальных нет.   
 
## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW2
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdDhnNHFMU2dKQzQ">HW2</a>
> **ВНИМАНИЕ! При удалении класса из исходников он (скомпилированный) остается в target (и classpath). В этом случае (или в любом другом, когда проект начинает глючить) сделайте `maven clean`.**

> Изменения в проекте добавил под патчами
  
#### Apply 1-HW2-repository.patch
> - В репозиториях по другому инстанциировал компараторы. [Оптимизация анонимных классов](http://stackoverflow.com/questions/19718353) не требуется! Почитайте комменты от Holger: *Java 8 relieves us from the need to think about such things at all*.
> - Зарефакторил `<T extends Comparable<? super T>> DateTimeUtil.isBetween(T value, T start, T end)`. Дженерики означают, что мы принимаем экземпляры класса, который имплементит компаратор, который умеет сравнивать T или суперклассы от T
> - В `InMemoryMealRepositoryImpl.save()` сделал обновление атомарным. В варианте `computeIfPresent` (см. псевдокод в `Map.computeIfPresent`) удаление элемента `if (newValue == null) remove` не используем, т.к. возвращаем ненулевое новое значение. Используем только атомарноный `put`, если элемент присутствует, те вместо 2х операций, разнесенных во времени
>   -  1  проверка `get(meal.getId(), userId)`
>   -  2 `meals.put(meal.getId(), meal)`
между которыми может быть например операция удаления этой еды из другого потока делаем одну атомарную операцию
>   -  1 `meals.computeIfPresent`. `ConcurrentHashMap` в отличии от `HashMap` делает операции атомарно.

#### Apply 2-HW2-meal-layers.patch
> - Перенес обработку null-дат  в `MealRestController.getBetween()`
> - По аналогии с `AbstractUserController` добавил проверку id пользователя, пришедшего в `MealRestController` (`checkIdConsistent`, `checkNew`)

#### Apply 3-HW2-optional-MealServlet.patch
> - Убрал логирование (уже есть в контроллере)
> - `checkIdConsistent` позволяет в контроллере обновлять еду с `id=null`

#### Apply 4-HW2-optional-filter.patch
> - Вместо `MealServlet.resetParam` (перемещение параметров фильтрации в атрибуты запроса для отображения в `meals.jsp`), достаю их в jsp напрямую из запроса через [`${param.xxx}`](https://stackoverflow.com/a/1890462/548473)
> - Сессию будем разбирать, когда будем делать авторизацию.  


#### Apply 5-HW2-optional-select-user.patch

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFMGRVM0QyblJtNGc">Вопросы по API и слоям приложения</a>
- <a href="http://stackoverflow.com/questions/21554977/should-services-always-return-dtos-or-can-they-also-return-domain-models">Should services always return DTOs, or can they also return domain models?</a>
- <a href="http://stackoverflow.com/questions/31644131/spring-dto-dao-resource-entity-mapping-goes-in-which-application-layer-cont/35798539#35798539">Mapping Entity->DTO goes in which application layer: Controller or Service?</a>

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы по HW2

> Что делает `repository.computeIfAbsent / computeIfPresent` ?

Всегда пробуйте ответить на вопрос сами. Дастоточно просто зайти по Ctrl+мышка в реализацию и посмотреть javadoc и **их дефолтную реализацию**

> Почему выбрана реализация `Map<userId, Map<mealId,Meal>>` а не `Meal.userId + Map<mealId,Meal>` ?

В данном случае двойная мапа - самый эффективный способ хранения, который не требует итерирования (перебора всех значений).

## Занятие 3:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFOU8wWlpPVE05STA">Коротко о жизненном цикле Spring контекста.</a>
#### Apply 6-bean-life-cycle.patch
-  <a href="http://habrahabr.ru/post/222579/">Spring изнутри. Этапы инициализации контекста.</a>
-  Ресурсы:
   -  <a href="http://vk.com/javawebinar?z=video-58538268_169373158%2Fvideos-58538268">Евгений Борисов. Spring, часть 1</a>
   -  <a href="http://vk.com/javawebinar?z=video-58538268_169373162%2Fvideos-58538268">Евгений Борисов. Spring, часть 2</a>
   -  <a href="http://www.slideshare.net/taemonz/spring-framework-core-23721778">Презентация Spring framework core</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFODlkU1B0QnNnSGs">Тестирование через JUnit.</a>
> **ВНИМАНИЕ!! Перед накаткой патча создайте каталог test (из корня проекта путь `\src\test`), иначе часть файлов попадет в `src\main`.**
> - в maven JUnit плагине <a href="http://stackoverflow.com/questions/17656475/maven-source-encoding-in-utf-8-not-working/17671104#17671104">поменял кодировку на UTF-8</a> 
> - поменял `@RunWith`: `SpringRunner` is an alias for the `SpringJUnit4ClassRunner`

#### Apply 7-add-junit.patch
#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос: почему проект упадет при попытке открыть страничку еды (в логе смотреть самый верх самого нижнего исключения)?

-  <a href="http://junit.org/">JUnit 4</a>
-  <a href="http://habrahabr.ru/post/120101/">Тестирование в Java. JUnit</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFai1veG9qaFZlZ2s">Spring Test</a>
#### Apply 8-add-spring-test.patch
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#testing">Spring Testing</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVlNYczhnSU9JdXc">Базы данных. Обзор NoSQL и Java persistence solution без ORM.</a>
-  <a href="https://ru.wikipedia.org/wiki/PostgreSQL">PostgreSQL</a>.
-  [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc)
-  <a href="http://java-course.ru/begin/postgresql/">Установка PostgreSQL</a>. **ВНИМАНИЕ! с postgres 9.6 возможны проблемы.** 
-  Чтобы избежать проблем с правами и именами каталогов, [**рекомендуют установить postgres в простой каталог, например `C:\Postgresql`**.  И при проблемах создать каталог data на другом диске.](https://stackoverflow.com/questions/43432713/postgresql-installation-on-windows-8-1-database-cluster-initialisation-failed). Если Unix, проверить права папки.
    
> Создать в pgAdmin новую базу `topjava` и новую роль `user`, пароль `password`

![image](https://cloud.githubusercontent.com/assets/13649199/18809406/118f9c48-8283-11e6-8f10-d8291517a497.png)

-  <a href="http://alexander.holbreich.org/2013/03/nosql-or-rdbms/">NoSQL or RDBMS.</a> <a href="http://habrahabr.ru/post/77909/">Обзор NoSQL систем</a>. <a href="http://blog.nahurst.com/visual-guide-to-nosql-systems">CAP</a>
-  <a href="http://db-engines.com/en/ranking">DB-Engines Ranking</a>
-  <a href="http://ru.wikipedia.org/wiki/Java_Database_Connectivity">JDBC</a>
-  Обзор Java persistence solution без ORM: <a href="http://commons.apache.org/proper/commons-dbutils/">commons-dbutils</a>,
            <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html">Spring JdbcTemplate</a>,
            <a href="http://en.wikipedia.org/wiki/MyBatis">MyBatis</a>, <a href="http://www.jdbi.org/">JDBI</a>, <a href="http://www.jooq.org/">jOOQ</a>
- Основы:
  - <a href="https://ru.wikipedia.org/wiki/Реляционная_СУБД">Реляционная СУБД</a>
  - <a href="http://habrahabr.ru/post/103021/">Реляционные базы</a>
  - <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5qdMQLXiIwGLTLyUHkTqi2">Уроки по JDBC</a>
  - <a href="http://postgresguide.com/">Postgres Guide</a>
  - <a href="http://www.postgresqltutorial.com">PostgreSQL Tutorial</a>
  - <a href="http://campus.codeschool.com/courses/try-sql">Try SQL</a>
  - <a href="http://java-course.ru/begin/database01/">Базы данных на Java</a>
  - <a href="http://java-course.ru/begin/database02/">Возможности JDBC — второй этап</a>
- Дополнительно:
  - [Документация к PostgreSQL 9.6](https://postgrespro.ru/docs/postgresql/9.6/index.html)
  - [Книги по PostgreSQL](https://postgrespro.ru/education/books)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQWtHYU1qTDlMWVE">Настройка Database в IDEA.</a>
#### Apply 9-add-postgresql.patch
-  <a href="http://habrahabr.ru/company/JetBrains/blog/204064/">Настройка Database в IDEA</a> и запуск SQL.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFMGNWUXhaVzdlU0k">Скрипты инициализации базы. Spring Jdbc Template.</a>
> Изменение: в `JdbcUserRepositoryImpl.getByEmail` заменил `queryForObject` на `query`. Загляните в код: `queryForObject` бросает `EmptyResultDataAccessException` вместо нужного нам `null`.  

#### Apply 10_db_implementation.patch
-  Подключение <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html">Spring Jdbc</a>.
-  Конфигурирование DataSource. <a href="http://www.mkyong.com/spring/spring-propertyplaceholderconfigurer-example/">Property Placeholder</a>

>  Проверьте, что в контекст Spring проекта включены оба файла конфигурации

![image](https://cloud.githubusercontent.com/assets/13649199/24730713/eb21456a-1a6d-11e7-997c-fb4ad728ba45.png)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B4dIHS3wRAhhQUJMMFU0VnRrUUE">Подготовка тестовых данных и тестирование UserService.</a>
> - `ModelMatcher` переименовал в `BeanMatcher` 
> - обертку для сравнения инстансов при тестировании перенес в сам матчер (`BeanMatcher.Wrapper`). Он сравнивает инстансы по переданному как параметр компаратору. Класс из видео `UserTestData.TestUser` стал не нужен. 

#### Apply 11-test-UserService.patch

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVmZaSm9UMktXUnc">Логирование тестов.</a>
#### Apply 12-test-logging.patch
> - Новый PostgreSQL JDBC Driver [логирует через java.util.logging](https://github.com/pgjdbc/pgjdbc#changelog).  [Направил логирование в SLF4J](http://stackoverflow.com/a/43242620/548473)
> - Поменял формат вывода. См. [Logback Layouts](https://logback.qos.ch/manual/layouts.html)
 
#### Apply 13-fix-servlet.patch
> Приложение перестало работать, тк. для репозитория мы используем заглушку `JdbcMealRepositoryImpl`
 
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 11. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNDlOQVpOWF82OTA">Ответы на Ваши вопросы</a>
-  Что такое REST? <a href="http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/">10 Best Practices for Better RESTful API</a>
-  Зачем нужна сортировка еды?
-  Можно ли обойтись без `MapSqlParameterSource`?
-  Как происходит работа с DB в тестах?
-  Как реализовывать RowMapper?
-  Мои комментарии: решения проблем разработчиком.
-  Нужен ли разработчику JavaScript?

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> Какая разница между @BeforeClass and @Before? 

`@BeforeClass` выполняется один раз после загрузки класса (поэтому метод может быть только статический), `@Before` перед каждым тестом.
Также: для чистоты тестов экземпляр тестового класса пересоздается перед каждым тестом: http://stackoverflow.com/questions/6094081/junit-using-constructor-instead-of-before

> Тесты в классе в каком-то определенном порядке выполняются ("сверху вниз" например)?

Порядок по умолчанию неопределен, каждый тест должен быть автономен и не зависеть от других. См. также http://stackoverflow.com/questions/3693626/how-to-run-test-methods-in-specific-order-in-junit4 

> Обязательно ли разворачивать postgreSQL?

Желательно: хорошая и надежная ДБ:) Если совсем не хочется - можно работать со своей любимой RDBMS (поправить `initDB.sql`) или работать c postgresql в heroku (креденшелы к нему есть в `postgres.properties`). На следующем уроке добавим HSQLDB, она не требует установки.

> Зачем начали индексацию с 100000?

Тут нет "как принято". Так удобно вставлять в базу (если будет потребность) записи вручную не мешая счетчику.

> Из 5-го видео - "Логика в базе - большое зло". Можно чуть поподробней об этом?

- Есть успешные проекты с логикой в базе. Те все относительно.
- Логика в базе - это процедуры и триггеры. Нет никакого ООП, переиспользовать код достаточно сложно, никагого рефакторинга, поиска по коду и других плюшек IDE. Нельзя делать всякие вещи типа кэширования, хранения в сесии - это все для логики на стороне java. Например json можно напрямую отдать в процедуру и там парсить и вставлять в таблицы или наоборот - собирать из таблиц и возвращать.
А затем потребуется некоторая логика на стороне приложения и все равно придется этот json дополнительно разпарсивать в java.
Я на таком проекте делал специальную миграцию, чтобы процедуры мигрировать не как sql скрипты, а каждую процедуру хранить как класс с историей изменений. Если логика: триггеры и простые процедуры записи-чтения, которые не требуют переиспользования кода или
проект небольшой это допустимо, иначе проект становится трудно поддерживать. Также иногда используют [View](http://postgresql.ru.net/gruber/ch20.html) для разграничения доступа. Например, для финансовых систем, таблицы проводок доступны только  для админ учеток, а View просто не дадут увидеть (тем более изменить) данны обычному оператору на уровне СУБД.

> У JUnit есть ассерты и у спринга тоже. Можно ли обойтись без JUnit?

Предусловия и JUnit-тесты совершенно разные вещи. Один другого не заменит, у нас будут предусловия в следующем уроке.

> Я так понял VARCHAR быстрее, чем TEXT, когда мы работаем с небольшими записями. Наши записи будут небольшими (255). Почему вы приняли решение перейти на TEXT?

В отличие от MySql в Postgres  VARCHAR и TEXT - тоже самое: http://stackoverflow.com/questions/4848964/postgresql-difference-between-text-and-varchar-character-varying

> Зачем при создании таблицы мы создаем `CREATE UNIQUE INDEX` и `CREATE INDEX`. При каких запросах он будет использоваться?

UNIQUE индекс нужен для обеcпечения уникальности, DB не даст сделать одинаковый индекс. Индексы используется для скорости выполнения запросов. Обычно они задействуются, когда в запросе есть условия, на которые сделан индекс. Узнать по конкретному запросу можно  запросив план запроса: см. <a href="https://habrahabr.ru/post/203320">Оптимизация запросов. Основы EXPLAIN в PostgreSQL</a>. На измерение производительности с индексами посмотрим в следующем уроке.

> А это нормально, что у нас в базе у meals есть userId, а в классе - нет?

Что значит - "нормально"? Приложение работает. Ненормально, когда в приложении есть "лишний" код, который не используется. Для ORM он нам понадобится- мы `Meal.user` добавим.

> Почему мы использует один sequence на разные таблицы?

Мы будем использовать Hibernate, по умолчанию он делает глобальный sequence на все таблицы. В этом подходе есть <a href="http://stackoverflow.com/questions/1536479/asking-for-opinions-one-sequence-for-all-tables">как плюсы, так и минусы</a>, из плюсов - удобно делать ссылки в коде и в таблицах на при наследовании и мапы в коде. В дополнение: <a href="http://stackoverflow.com/questions/6633384/can-i-configure-hibernate-to-create-separate-sequence-for-each-table-by-default">Configure Hibernate to create separate sequence for each table by default</a>.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW03
- 1 Понять, почему перестали работать `SpringMain, InMemoryAdminRestControllerTest, InMemoryAdminRestControllerSpringTest`
- 2 Дополнить скрипты создания и инициализации базы таблицой MEALS. Запустить скрипты на вашу базу (через Run). Порядок таблиц при DROP и DELETE важен, если они связаны fk. Проверьте, что ваши скрипты работают
- 3 Реализовать через Spring JDBC Template `JdbcMealRepositoryImpl`
  - 3.1. сделать каждый метод за один SQL запрос
  - 3.2. `userId` в класс `Meal` вставлять НЕ надо (для UI и REST это лишние данные, userId это id залогиненного пользователя)
  - 3.3. `JbdcTemplate` работает через сеттеры. Вместе с конструктором по умолчанию их нужно добавить в `Meal` 
  - 3.4. Cписок еды должен быть отсортирован (тогда мы его сможем сравнивать с тестовыми данными). Кроме того это требуется для UI и API: последняя еда наверху.
- 4 Проверить работу MealServlet, запустив приложение

#### Optional
- 5 Сделать `MealServiceTest` из `MealService` и реализовать тесты.
> По `Ctrl+Shift+T` (выбрать JUnit4) можно создать тест для конкретного класса, выбрав для него нужные методы. Тестовый класс создастся в папке `test` в том же пакете, что и тестируемый. 
  - 5.1 Сделать тестовые данные `MealTestData`, АНАЛОГИЧНЫЕ пропопулированным в `populateDB.sql`. Сравниваем данные через `MealTestData.MATCHER`
  - 5.2 Сделать тесты на чужую еду (delete, get, update) с тем чтобы получить `NotFoundException`.
- 6 Предложить решение, как почнинить `SpringMain, InMemory*Test`. `InMemory*Test` должны использовать реализацию в памяти
- 7 Сделать индексы к таблице `Meals`: запретить создавать у одного и того-же юзера еду с одинаковой dateTime.
Индекс на pk (id) postgres создает автоматически: <a href="http://stackoverflow.com/questions/970562/postgres-and-indexes-on-foreign-keys-and-primary-keys">Postgres and Indexes on Foreign Keys and Primary Keys</a>
  - <a href="http://postgresguide.com/performance/indexes.html">Postgres Guide: Indexes</a>
  - [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habrahabr.ru/post/203320/)
  - [Оптимизация запросов. Часть 2](https://habrahabr.ru/post/203386/)
  - [Оптимизация запросов. Часть 3](https://habrahabr.ru/post/203484/)

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Как правильно придумать индекс для базы? Указать в нем все поля, комбинация которых создает по смыслу уникальную запись, или какие-то еще есть условия?

Индекс нужно делать по тем полям, по которым будут искаться записи (участвуют в WHERE, ORDER BY). Уникальность - совсем не обязательное условие. Индексы ускоряют поиск по определенным полям таблицы. Они не бесплатные (хранятся в памяти, замедляется вставка), поэтому на всякий случай их делать не надо. Также не строят индексы на колонки с малым процентом уникальности (например поле "М/Ж"). Поля индекса НЕ КОММУТАТИВНЫ и порядок полей в описании индекса НЕОБХОДИМО соблюдать (в силу использования B-деревьев и их производных как поисковый механизм индекса). При построении плана запроса EXPLAIN учитывается количество записей в базе, поэтому вместо индексного поиска (Index Scan) база может выбрать последовательный (Seq Scan). Проверить, работают ли индексы можно <a href="http://stackoverflow.com/questions/14554302/postgres-query-optimization-forcing-an-index-scan">отключив Seq Scan</a>. Также см. <a href="https://dba.stackexchange.com/a/27493/3684">Queries on the first field of composite index</a>

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Решение проблем

> Из каталога `main` не видятся классы/ресурсы в `test`

Все что находится в `test` используется только для тестов и недоступно в основном коде.  

> Из `IDEA` не видятся ресурсы в каталоге `test`

- Сделайте Reimport All в Maven окне

![image](https://cloud.githubusercontent.com/assets/13649199/18831806/7e43bedc-83f0-11e6-97db-67d4e1a7599f.png)

> В UserServiceImpl и MealServiceImpl подчеркнуты красным repository, ошибка: Could not autowire. There is more than one bean of 'MealRepository' type.

- Spring test контекст не надо включать в Spring Facets проекта, там должны быть только `spring-app.xml` и `spring-db.xml`. Для тестовых контекстов поставьте чекбокс `Check test files` в Inspections. 

![image](https://cloud.githubusercontent.com/assets/13649199/18831817/8a858f22-83f0-11e6-837e-bf5317b33b3a.png)

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Проверка по HW03 (сначала сделайте самостоятельно!)

- 1: В `MealTestData` еду делайте константами. Не надо `Map` конструкций!
- 2: SQL  case-insensitive, не надо писать в стиле Camel. В POSTGRES возможны case-sensitive значения, их надо в кавычки заключать (обычно не делают).
- 3: ЕЩЕ РАЗ: `InMemory` тесты должны идти на `InMemory` репозитории
- 4: **Проверьте, что возвращает `JdbcMealRepositoryImpl` при обновлении чужой еды**
- 5: В реализации `JdbcMealRepositoryImpl` одним SQL запросом используйте возвращаемое `update` значение `the number of rows affected`
- 6: При тестировании не портите констант из `MealTestData`
- 7: Для `MealTestData.MATCHER` можно использовать конструктор `BeanMatcher` без параметров (`equality` по умолчанию)
- 8: Проверьте, что все, что относится к тестам, ноходится в каталоге `test` (не попадает в сборку проекта)
