# Онлайн проект <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/drive/folders/0B9Ye2auQ_NsFfmctT3oyNW1qaVhDb2p0bGpyTFVlaUJ2VVpOdVgtWF9KTUFBMWFaR2xVYVE">Материалы занятия</a>

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте
#### Apply 0_fix.patch
> - Сделал классы `BaseEntity` и `NamedEntity` абстрактными
> - Переименовал в тестах методы `save` в `create`  

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW4

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVFVVUGctMUxxSkE">Разбор вопросов</a>
- <a href="http://stackoverflow.com/questions/8994864/how-would-i-specify-a-hibernate-pattern-annotation-using-a-regular-expression">Validate by RegExp</a>
- <a href="http://www.objectdb.com/java/jpa/persistence/managed#Entity_Object_Life_Cycle">Working with JPA Entity Objects</a>
- <a href="https://en.wikibooks.org/wiki/Java_Persistence/Relationships">Java Persistence/Relationships</a>
- <a href="http://articles.javatalks.ru/articles/17">Использование ThreadLocal переменных</a>
- <a href="http://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge">Merge vs Persist</a>
- <a href="http://www.youtube.com/watch?v=1KphwODu1gg">Видео: работа в ZK с OpenJPA (в чем Hibernate хуже)</a>
- <a href="https://developer.jboss.org/wiki/OpenSessionInView">Паттерн- открытие транзакции в фильтре</a> и <a href="http://stackoverflow.com/questions/1103363/why-is-hibernate-open-session-in-view-considered-a-bad-practice">почему это bad-practice</a>
- <a href="https://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing#Sequence_Strategies">Sequence Strategies</a>
- <a href="http://stackoverflow.com/questions/9470442/why-is-the-hibernate-default-generator-for-postgresql-sequencegenerator-not?lq=1">SequenceGenerator/IdentityGenerator in PostgreSql</a>

> `EntityManager` это по сути прокси обертка над Hibernate Session, которая создается каждый раз при открытии транзакции. 

- Дополнительно: еще есть редкий случай ручного управления `@PersistenceContext(type = PersistenceContextType.EXTENDED)`, когда он используется в нескольних транзакциях (long-running session or session-per-conversation). Ни разу не сталкивался.
    - <a href="https://techblog.bozho.net/spring-and-persistencecontexttype-extended/">Spring and PersistenceContextType.EXTENDED</a>
    - <a href="http://stackoverflow.com/questions/2547817/what-is-the-difference-between-transaction-scoped-persistence-context-and-extend">Transaction-scoped vs Extended Persistence</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNFMyMGJCZWE4elk">HW4: JPA. @Rule</a>
#### Apply 1_HW4.patch

> - В `Meal` добавил `@SuppressWarnings("JpaQlInspection")`. <a href="https://jazzy.id.au/2008/10/30/list_of_suppresswarnings_arguments.html">Other warnings</a>
> - Поменял реализацию `JpaMealRepositoryImpl.get()` (вместо `@NamedQuery`), реализация стали проще

#### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Сравните запросы в базу `MealServiceTest.testGet()` до и после патча 2-fix-hibernate-issue!
#### Apply 2_fix_hibernate_issue.patch
> - Сделал доступ к полю `BaseEntity.id` через `AccessType.PROPERTY`: <a href="https://hibernate.atlassian.net/browse/HHH-3718">hibernate bug with proxy initialization when using `AccessType.FIELD`</a>   
>   - <a href="http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access">Which is better, field or property access?</a>
>   - Т.к. обращаться к PROPERTY полям нужно только через getter, сделал в BaseEntity `private id`
> - Поправил `equals()` с учетом Lazy проксирования
>   - <a href="http://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma">JPA hashCode()/equals() dilemma</a>
>   - <a href="http://blog.xebia.com/advanced-hibernate-proxy-pitfalls/">Hibernate Proxy Pitfalls</a>

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Почему над `AbstractBaseEntity` осталось `@Access(AccessType.FIELD)` ? Почему при запросе `user.id` нам не нужно нужно вытаскивать его из базы?

`AccessType.FIELD` делает доступ по всем полям `AbstractBaseEntity` (кроме переопределенного id) и всех классах-наследниках. При загрузке `Meal` hibernate на основе поля `meal.user_id` делает ленивую прокcи к `User`, у которой нет ничего, кроме `id`. Из за бага хибернайт обращается за юзером в базу, хотя ничего кроме его id нам не надо. `@Access(value = AccessType.PROPERTY)` над id позволяет обойти баг и не делать лишний запрос в базу.

#### Apply 3_HW4_optional.patch

> - <a href="http://stackoverflow.com/a/33001846/548473">Hibernate 5.2.x already include Java 8 date and time types (JSR-310)</a>
> - <a href="http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev#27868954">Stopwatch</a>
> - Добавил сводку "имя теста - время выполнения" в конце класса

## Занятие 5:

### Раскрасил лог (в Spring Boot по умолчанию он тоже colored)
#### Apply 4_log_colored.patch
- [Logback layouts coloring](https://logback.qos.ch/manual/layouts.html#coloring)
- Дополнительно: [use colored output only when logging to a real terminal](https://stackoverflow.com/questions/31046748)


### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZENCVEhDMkZiV00">Транзакции</a>
-  <a href="https://ru.wikipedia.org/wiki/Транзакция_(информатика)">wiki Транзакция</a>
-  <a href="https://jira.spring.io/browse/DATAJPA-601">readOnly и Propagation.SUPPORTS</a>
- Ресурсы:
  - <a href="http://blog.jhades.org/how-does-spring-transactional-really-work/">How does Spring @Transactional Really Work</a>
  - <a href="https://www.ibm.com/developerworks/ru/library/j-ts1/">Стратегии работы с транзакциями: Распространенные ошибки</a>
  - <a href="http://stackoverflow.com/questions/8490852/spring-transactional-isolation-propagation">Spring @Transactional - isolation, propagation</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNW0yVWhXcGNPU2M">Профили Maven и Spring</a>
#### Apply 5_profiles_connection_pool.patch
> - **Галочка в XML профиле влияет только на отображение в IDEA и никак на выполнение кода.**
> - **`tomcat-jdbc` со `scope=provided` не работает в Tomcat 7.x, поставьте Tomcat 8.x**
> - `Profiles.ACTIVE_DB` задает активный профиль базы (postgres/hsqldb)
> - `Profiles.REPOSITORY_IMPLEMENTATION` определяет реализацию репозитория при запуске приложения (для тестов задаются через `@ActiveProfiles`).

> Для переключения на HSQLDB необходимо:
>  - поменять в окне Maven Projects профиль (Profiles) на `hsqldb` и сделать `Reimport All Maven Projects` (1я кнопка)
>  - поменять `Profiles.ACTIVE_DB = HSQLDB`
>  - почистить проект `mvn clean` (фаза `clean` не выполняется автоматически, чтобы каждый раз не перекомпилировать весь проект)

Для корректного отображения неактивного профиля в IDEA проверьте флаг _Inactive profile highlighting_ и сделайте проекту clean
 
![image](https://cloud.githubusercontent.com/assets/13649199/25120020/29935958-2425-11e7-8363-1ff027426f64.png)

> Вопрос: почему после этого патча не поднимется Spring при запуске приложения в Tomcat? (будем чинить в ДЗ п.6)
 
- <a href="https://dzone.com/articles/using-spring-profiles-xml">Using Spring Profiles in XML Config</a>
- <a href="https://www.mkyong.com/spring/spring-profiles-example/">Spring Profiles example</a>

### Автоматический выбор профиля базы: [`ActiveProfilesResolver`](http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver)
#### Apply 6_profile_resolver.patch
> Сделал автоматический выбор профиля базы при запуске приложения (тестов) в зависимости от присутствия драйвера базы в classpath (`Profiles.getActiveDbProfile()`)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFTWJOdHduOWtNcTA">Пул коннектов</a>
-  Выбор реализации пула коннектов: <a href="http://www.jolbox.com/">BoneCP</a>, <a href="https://commons.apache.org/proper/commons-dbcp/">Commons Database Connection Pooling</a>, <a href="https://github.com/brettwooldridge/HikariCP">HikariCP</a>
-  <a href="https://habrahabr.ru/post/269023/">Самый быстрый пул соединений на java (читаем комменты)</a>
-  <a href="http://blog.ippon.fr/2013/03/13/improving-the-performance-of-the-spring-petclinic-sample-application-part-3-of-5">Tomcat pool</a>


### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYVdyMFYxRUR6bWM">Spring Data JPA</a>
#### Apply 7_spring_data_jpa.patch
> - Переименовал классы _Proxy*_ на более адекватные _Crud*_
> - В `spring-framework-bom` мы уже задали версию Spring. Убрал из остальных зависимостей. 

-  <a class="anchor" id="datajpa"></a><a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>
-  Замена AbstractDAO: <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.repositories">JPA Repositories</a>
-  Разрешение зависимостей: <a href="http://howtodoinjava.com/2014/02/18/maven-bom-bill-of-materials-dependency/">Maven BOM [Bill Of Materials] Dependency</a>
-  <a href="https://habrahabr.ru/post/232381/#datajpa">Делегирование (в конце статьи)</a>
-  <a href="https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa">Getting started with Spring Data JPA</a>
-  <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">Query methods</a>
-  <a href="http://jeeconf.com/archive/jeeconf-2013/materials/spring-data/">Spring Data – новый взгляд на persistence (JeeConf)</a>
-  <a href="https://www.youtube.com/watch?v=nwM7A4TwU3M">Евгений Борисов — Spring Data? Да, та!</a>
-  Ресурсы:
   -  <a href="https://github.com/spring-projects?query=spring-data">Github repositories</a>
   -  <a href="http://www.petrikainulainen.net/spring-data-jpa-tutorial">Spring Data JPA Tutorial</a>
   -  <a href="https://blog.42.nl/articles/spring-data-jpa-with-querydsl-repositories-made-easy/">Spring Data JPA with QueryDSL</a>
   -  [SpEL support in Spring Data JPA @Query](https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions)

![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы:

> Когда нужно ставить аннотацию `@Modifying`? Почему она стоит только над delete, но не над save?

`@Modifying` ставится на модифицирующие `@Query`. Реализацию save можно посмотреть в `org.springframework.data.jpa.repository.support.SimpleJpaRepository#save()`, там она есть.

> Зачем мы переопределяем `@Override Meal save` в `CrudUserRepository`. Без этого все работает.

Можно не переопределять. Сделал только для явного указания всех используемых методов из наследуемого `CrudRepository`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFajd2Y2RLQVVJWUU">Spring кэш</a>
#### Apply 8_spring_cache.patch
> - В случае проблем с размещением кэш в `java.io.tmpdir` (нет доступа) можно
>    - запусть IDEA _as Administrator_
>    - захардкодить путь в _src\main\resources\cache\ehcache.xml_, например `<diskStore path="D\tmp">`
>    - [поменять `java.io.tmpdir`](http://stackoverflow.com/questions/1924136/environment-variable-to-control-java-io-tmpdir)

-  <a href="http://habrahabr.ru/post/113945/">Кеширование в Spring Framework</a>
-  <a href="http://www.ehcache.org/">EHCACHE</a>
-  Ресурсы:
   -  <a href="http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html">Spring cache Abstraction</a>
   -  <a href="http://habrahabr.ru/post/25140/">Распределённая система кеша ehcache</a>
   -  Починка JUnit: <a href="http://stackoverflow.com/questions/10013288/another-unnamed-cachemanager-already-exists-in-the-same-vm-ehcache-2-5">один кэш на JVM</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> В <a href="https://github.com/spring-projects/spring-petclinic/tree/master/src/main/java/org/springframework/samples/petclinic">spring-petclinic</a> `DataJpa` реализована без дополнительных классов. В таком виде как у них, spring data смотрится, конечно, намного лаконичней других реализаций, но у нас получилось  вдвое больше кода, чем с тем же jpa или jdbc. Плюс только пожалуй в том, что query находятся прямо в репозитории, а  не где-то там в другом пакете. Так что получается, spring data лучше подходит для простейших crud без всяких "фишек"? или в чем его достоинство для больших и сложных проектов?

Достоинство DATA-JPA по сравнению например с JPA: есть типизация, готовые реализации типовых методов CRUD а также paging, data-common. Мы можем переключить реализацию JPA, например, на mongoDb (`PagingAndSortingRepository`, от которого наследуется `JpaRepository` находится в `spring-data-common`).
Соответственно его методы будут поддерживаться всеми реализациями `spring-data-common`, JPA одна из них и пр. Подробнее о них есть в видео <a href="http://jeeconf.com/archive/jeeconf-2013/materials/spring-data/">Spring Data – новый взгляд на persistence</a>.
Дополнительное проксирование в DATA-JPA - моя "фишка" для устранения минусов этого фреймворка: невозможность дебага, привязка к интерфейсу JpaRepository, перенос логики Repository в слой сервисов.
Для большого приложения выигрыш этого стоит. Для небольших (тестовых) приложений дополнительных классов можно не делать.

> Почему мы для InMemory не сделали отдельного профиля? Почему их не удалить вообще?

Реализация InMemory является примером как в test делать моки с подменой контекста. Для них сделали отдельный `mock.xml` и запускаемый проект ничего не должен о них знать. У нас учебный проект, в котором 4 реализации репозиториев, в реальном такого не будет.

> А как делать транзакционность для реализации jdbc?

Будем делать на следующем уроке

--------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZFdWWFdwams0eGM">Домашнее задание HW05</a>

- 1: Имплементировать `DataJpaMealRepositoryImpl`
- 2: Разделить реализации Repository по профилям Spring: `jdbc`, `jpa`, `datajpa` (общее в профилях можно объединять, например `<beans profile="datajpa,jpa">`). 
  - 2.1: Профили выбора DB (`postgres/hsqldb`) и реализации репозитория (`jdbc/datajpa/jpa`) независимы друг от друга и при запуске задать приложения (тестов) нужно задать тот и другой. 
  - 2.2: Для интеграции с IDEA не забудте выставить в `spring-db.xml` справа вверху в `Change Profiles...` профили, например `datajpa, postgres`
  - 2.3: Общие части для всех в `spring-db.xml` можно оставить как есть без профилей вверху файла (до первого `<beans profile=` ).
- 3: Сделать тесты всех реализаций (`jdbc, jpa, datajpa`) через наследование (без дублирования)
  -  3.1 **сделать один базовый класс для `MealServiceTest` и `UserServiceTest`**.
- 4: Запустить все тесты: `mvn test` (в IDEA Maven Lifecycle - test, кнопку skipTest отжать)

#### Optional

- 5: Разделить `JdbcMealRepositoryImpl` для HSQLDB (она не умеет работать с Java8 Time API) и Postgres через `@Profile` (для Postgres оставить `LocalDateTime`). Цель задания -  потренироваться с [паттерном шаблонный метод](https://refactoring.guru/ru/design-patterns/template-method) и профилями Spring. Бины Spring мы разделяем (фильтруем) по разным профилям с помощью `beans profile` в xml конфигурации и `@Profile` (те мы конфигурируем, какие бины попадут в контекст Spring в зависимости от активных профилей приложения). 
Абстрактные классы не создаются и в контекст не попадают. Профили, заданные в `@Profile` пересекаются с активными профилями приложения: если пересечение есть, то бин включается в контекст (cм. реализацию `@Profile` и в ней `ProfileCondition`, можно подебажить). Например при сконфигуренном `@Profile({"postgres","jdbc"})` бин попадет в контекст, если в профилях запущенного приложения есть хотя бы один из них (нарпимер "jdbc"). После выполнения разделения можно предложить решение проще.
- 6: Починить `MealServlet` и использовать в `SpringMain` реализацию DB: добавить профили. Попробуйте поднять Spring контекст без использования `spring.profiles.active`.
- 7: Сделать и протестировать в сервисах методы (тесты и реализация только для `DataJpa`)
  - 7.1:  достать по id пользователя вместе с его едой
  - 7.2:  достать по id еду вместе с пользователем
  - 7.3:  обращения к DB сделать в одной транзакции (можно сделать разные варианты). <a href="https://en.wikibooks.org/wiki/Java_Persistence/OneToMany">Java Persistence/OneToMany</a>

---------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW05
- 1: Для того, чтобы не запускались родительские классы тестов нужно сделать их `abstract`
- 2: В реализациях `JdbcMealRepository` **код не должен дублироваться**. Если вы возвращаете тип `Object`, посмотрите в сторону <a href="http://www.quizful.net/post/java-generics-tutorial">дженериков</a>.
- 3: В `MealServlet/SpringMain` в момент `setActiveProfiles` контекст спринга еще не должен быть инициализирован, иначе выставление профиля уже ничего не будет делать.
- 4: Если у метода нет реализации, то стандартно бросается `UnsupportedOperationException`.
- 5: Для уменьшения количества кода при реализации _Optional_ (п. 7, только `DataJpa`) попробуйте сделать `default` метод в интерфейсе
- 6: В Data-Jpa метод для ссылки на entity (аналог `em.getReference`) : `T getOne(ID id)`
- 7: Проверьте, что в `DataJpaMealRepositoryImpl` все обращения к DB выполняются в **одной транзакции**
- 8: Для `достать по id пользователя вместе с его едой` я в `User` добавил `List<Meal> meals`
- 9: Проверьте, что все тесты запускаются из Maven (имена классов тестов удовлетворяют соглашению) и итоги тестов класса выводятся корректно (не копятся)
- 10: `@ActiveProfiles` принимает в качестве параметра строку, либо **массив** строк. В тестах можно задавать несколько `@ActiveProfiles` в разных классах, они суммируются 
- 11: В релизации 7.1 учесть, что у юзера может отсутствовать еда
- 12: [Ordering a join fetched collection in JPA using JPQL/HQL](http://stackoverflow.com/questions/5903774/ordering-a-join-fetched-collection-in-jpa-using-jpql-hql)
- 13: `<beans profile=` в конфигурации контекста должны находиться после всех остальных объявлений.

