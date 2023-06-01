# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/drive/folders/1Urw8CidiVJDIXd9IwyadjBxPmpsL_MCr">Материалы занятия</a>

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW5

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW5: Spring Profiles. Spring Data JPA](https://drive.google.com/file/d/1dlhXeQr0fi0XymEFyBG-TXv5hpPgXtlT)

<details>
  <summary><b>Краткое содержание</b></summary>
  
Перед просмотром разбора домашнего задания еще раз ознакомьтесь с материалом по ссылке:

- <a href="https://jira.spring.io/browse/DATAJPA-601">readOnly и Propagation.SUPPORTS</a> (без транзакции не будет оптимизации по флагу `readOnly` при выполнении JDBC и в управлении ресурсами Spring's JPA, в том числе выключение `flush`)

В нашем приложении над репозиториями **CrudMealRepository** и **CrudUserRepository** указана аннотация
**@Transactional(readOnly = true)**, что предполагает выполнение всех операций в репозитории внутри транзакции на
чтение. В таком случае, для операций, которые вносят изменения в базу данных, нам необходимо переопределить транзакцию и указать над модифицирующим методом аннотации:
> @Transactional  
> @Modifying

В реализации **SimpleJpaRepository<T, ID>**, которая используется Spring Data JPA, транзакционность описывается точно
так же, как и в нашем приложении (все операции выполняются в транзакции на чтение, а для модифицирующих методов
транзакции переопределяются).

#### Для реализации репозитория для работы с Meal через Spring Data JPA:

1. Создаем интерфейс `CrudMealRepository`, который должен быть унаследован от `JpaRepository<Meal, Integer>`
2. Этот интерфейс помечаем аннотацией `@Transactional(readonly = true)`
3. Основная функциональность репозитория будет реализована прямо в интерфейсе (такой подход называется *интерфейсное
   программирование*) - Spring Data JPA автоматически создаст прокси для нашего интерфейса с реализацией необходимой
   функциональности.
4. По аналогии с `DataJpaUserRepository` создаем класс `DataJpaMealRepository`, который имплементируют `MealRepository`. В
   этот класс через конструктор спрингом будут внедрены `crudMealRepository` и `crudUserRepository`. Все методы
   реализуются по аналогии с `DataJpaUserRepository`. Для сохранения еды в методе `save(Meal mea, int userId)` сначала
   проверяем, что еда принадлежит аутентифицированному пользователю и после этого присваиваем еде ссылку на `User`,
   которому она принадлежит. Чтобы не загружать этого user из базы, используем метод `getById(int id)`, который вернет
   ссылку на прокси-объект user с указанным `id`.

#### Разделение конфигурации на профили

Spring позволяет настроить несколько профилей, которые позволяют переключать его функциональность.  
У нас уже настроено два профиля для переключения между базами данных:

- `postgres` - для работы PostgreSQl
- `hsqldb` - для работы с базой данных в памяти

Для реализаций способа работы с базой данных настроим еще три профиля:

- `jdbc`
- `jpa`
- `dataJpa`

Общие для нескольких профилей свойства можно выносить в общий блок, перечислив в декларации профилей их наименования
через запятую. Следует обратить особое внимание на то, что тег `<beans>` в котором объявляются профили и их
конфигурация, может располагаться только в самом конце файла конфигурации после всех остальных настроек. Intellij Idea
предоставляет интерфейс для переключения между профилями Spring, настроенными в конфигурации (такое переключение влияет
только на отображение файла конфигурации, но не оказывает никакого влияния на запуск и работу приложения).

#### Тесты для всех реализаций репозитория

Все настройки логирования и определения времени выполнения тестов вынесем в общий абстрактный базовый класс
`AbstractServiceTest`, от которого будут унаследованы остальные тестовые классы сервисов. Также над этим суперклассом
указывается аннотация `@ActiveProfiles(resolver = ActiveDbProfileResolver.class)`, которая будет автоматически
определять необходимый профиль базы данных при запуске тестов в зависимости от наличия драйвера БД в classpath.  
Его наследниками будут классы `AbstractMealServiceTest` и `AbstractUserServiceTest`, в которые мы переносим
соответствующие тесты.  
Далее по цепочке наследования создадим тестовые классы для различных реализаций репозитория. Аннотация `@ActiveProfile`
наследуется и может быть дополнена в классах — наследниках, поэтому в созданных тестовых классах `(Jpa | DataJpa |
Jdbc) .. ServiceTest` с помощью этих аннотаций укажем конкретные профили Spring, соответствующие тестируемой
функциональности.
> При изменении профиля maven и изменении иерархии классов, удалении классов — обязательно нужно запускать `mvn clean`

</details>

#### Apply 6_01_HW5_data_jpa.patch

Транзакция начинается, когда встречается первая `@Transactional`. С default propagation `REQUIRED`
остальные `@Transactional` просто участвуют в первой. Поэтому ставим аннотацию сверху `DataJpaMealRepository.save()`,
чтобы все обращения к базе внутри метода были в одной транзакции. Аналогично, если из сервиса собирается несколько
запросов к репозиториям, `@Transactional` ставится над методом сервиса.

#### Apply 6_02_HW5_profile_test.patch

**Для IDEA в `spring-db.xml` не забудьте выставить Spring Profiles. Например `datajpa, postgres`**

> - Заменил `description.getMethodName()` на `getDisplayName()` в выводе результатов тестов. После `printResult()` буфер сбрасывается в 0, чтобы не накапливать изменения.

#### Apply 6_03_extract_rules.patch

> Вынес измерение времени и сводку в отдельный класс `TimingRules`

[JUnit Rules External Resources](https://carlosbecker.com/posts/junit-rules/#external-resources)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [HW5: Optional](https://drive.google.com/file/d/1S6gOOzLV9ndSbPiSuyEmqhEy3xZoUpXZ)

<details>
  <summary><b>Краткое содержание</b></summary>

#### Определим профиль Spring в SpringMain:

При создании `XmlApplicationContext`, когда мы в конструктор передаем настройки в xml - Spring сразу считывает и
применяет конфигурации и дальнейшее изменение профилей не оказывает на контекст никакого влияния, поэтому в данном
классе сначала создаем `GenericXmlApplicationContext` без параметров, после чего указываем для него необходимые профили с
помощью

 ```java
 appCtx.getEnviroment().setActiveProfiles(Profiles.POSTGRES,Profiles.DATAJPA)
 ```

Затем передаем контексту наши конфигурации и обновляем его. В данном случае Spring считает конфигурации, и настроит
контекст в соответствии с указанными профилями.

#### Для корректного запуска приложения определим профили Spring в MealServlet:

Профиль Spring можно задать аналогичным способом. Также, вместо `GenericXmlApplicationContext` можно создать
`ClassPathXmlApplicationContext`. Самый короткий способ задать профили — создать `ClassPathXmlApplicationContext`, передав
ему в конструкторе конфигурации и установив флаг конструктора `refresh` в `false`. В данном случае спринг не будет
парсить контекст сразу при инициализации и мы можем задать для него требуемые профили и вручную обновить контекст, после
чего спринг поднимет и настроит контекст соответствующим образом:

 ```java
 springContext=new ClassPathXmlApplicationContext(new String[]{"spring/spring-app.xml","spring/spring-db.xml"}, false);
 ```

Так как HSQLDB (в данной версии) не работает с Java Time API, в отличие от PostgreSQL, необходимо разделить реализации репозитория для
работы в двух различных профилях БД. Для данных профилей будет различаться только способ получения даты и времени
употребления Meal. В соответствии с паттерном Template Method - создадим классы-наследники, где для
соответствующих профилей будет реализовываться абстрактный `protected` метод суперкласса, который будет возвращать
`LocalDateTime` - для PostgreSQL и `TimeStamp` для HSQLDB. Базовый класс `JdbcMealRepository` будет дженериком (типизированным классом),
объект которого возвращается этим `protected` методом. Классы-наследники являются внутренними
статическими классами, они помечены аннотациями `@Repository` и являтются бинами спринга. В `@Profile` над ними мы указываем
соответствующий профиль спринга в зависимости от БД - Spring создаст бин только для тех классов, которые будут относиться к активным профилям.
> последние версии драйвера HSQLDB поддерживает работу с Java Date Time Api, поэтому теперь можно просто обновить версию драйвера в `pom.xml`

#### Получение Meal вместе с User

1. В `MealService` внедряем `UserRepository` и в одной транзакции сначала
   загружаем из БД `Meal`, затем загружаем соответствующего `User` и вручную устанавливаем для еды ее владельца. Так как
   `@Transactional` объявлена на уровне сервиса — несмотря на то, что мы работаем с двумя разными репозиториями, операции
   по получению из базы Meal и User выполнятся в одной транзакции благодаря тому, что аннотация имеет атрибут
   `propagation`, который по умолчанию устанавливается как `required` - то есть, если внутри одной транзакции будет
   исполняться другой транзакционный метод, то для его выполнения не будет открываться новая транзакция — он будет
   выполняться во внешней транзакции.

2. Способ: получение Meal с User в `MealRepository` через `FETCH JOIN`. Так как нам требуется реализовать получение еды с ее
   владельцем только для `DataJpaMealRepository`, в интерфейсе `MealRepository` объявим `default UserMeal getWithUser(...)`,
   который для всех реализаций этого интерфейса при вызове этого метода будет выбрасывать исключение
   `UnsupportedOperationException()`. В `DataJpaMealRepository` переопределим этот дефолтный метод: этот метод пометим
   аннотацией `@Query` и в скобках укажем HQL запрос получения Meal из базы. При этом, так как запрос содержит `JOIN
   FETCH`, таблицы meals и users будут объединяться на уровне базы (обычный JOIN) и Spring Data за один запрос загрузит
   из базы Meal вместе с ее User.

#### Получение User вместе с Meals

Для того чтобы получить из базы User вместе со всеми его Meals, для начала добавим `List<Meal> meals` в класс `User`. 
Эта коллекция будет помечена аннотацией `@OneToMany`, которая говорит об отношении один-ко-многим. Мы видим, что
у Meal есть ссылка на User, а у User есть ссылка на коллекцию его meal, и эти ссылки помечены соответствующими
аннотациями — такое отношение называется BiDirectional. Пользователя с его едой получаем способом, аналогичным
предыдущему шагу - с помощью HQL запроса с применением fetch join. JPA 2.0 позволяет использовать в таких случаях
UniDirectional отношение со стороны
@OneToMany [Unidirectional OneToMany](https://en.wikibooks.org/wiki/Java_Persistence/OneToMany#Unidirectional_OneToMany.2C_No_Inverse_ManyToOne.2C_No_Join_Table_.28JPA_2.x_ONLY.29).
Обратите внимание на то, что нужно **объявлять поле только в том случае, когда ваше приложение их использует!**


#### Проблема **<a href="http://stackoverflow.com/questions/97197/what-is-the-n1-selects-issue">N+1</a>**

Проблема **N + 1** возникает, когда выполняется N дополнительных SQL-запросов для получения тех же данных, которые можно
получить при выполнении одного SQL-запроса. Чем больше значение N, тем больше запросов будет выполнено и тем больше
влияние на производительность. Например, в нашем случае при получении списка пользователей сначала будут загружены все
пользователи, и затем для каждого пользователя будет выполнен запрос в базу для получения его ролей. Таким образом, если
бы у нас было 1000 пользователей, то будет выполнен 1 запрос для получения всех пользователей и дополнительно 1000
запросов для получения ролей каждого пользователя, итого 1000 + 1 запрос.
> Некоторые способы решения проблемы N+1:
> 1. Получать сущность из базы данных через запрос с использованием `JOIN FETCH` - в таком случае таблицы будут объединяться на уровне БД и вся информация будет получена сразу.
> 2. Пометить коллекцию сущности, для получения которой выполняются дополнительные запросы, аннотацией `@Fetch(FetchMode.SUBSELECT)`. В этом случае
     > сначала будет загружена коллекция пользователей и затем будет выполнен один дополнительный запрос, который получит роли всех этих пользователей.
> 3. Пометить коллекцию аннотацией `BatchSize(size = 200)` - в данном случае сначала будет выполнен запрос на получение всех пользователей, и затем дополнительными запросами будут загружаться
     > роли этих пользователей — по 200 пользователей за запрос.
> 4. JPA 2.1 предоставляет для решения этой проблемы функциональность `@EntityGraph` - на уровне сущности мы можем объявить граф полей, которые будут загружаться
     > из базы данных совместно с сущностью. При определении последующих запросов мы можем указать наименование нужного нам Entity Graph, и JPA сформирует запрос в соответствии с этим графом и загрузит только описанные в нем поля.
     `@EntityGraph` можно определять не только на уровне сущности, это можно сделать непосредственно в интерфейсе через параметр аннотации `attributePath`, например: `@EntityGraph(attributePaths = {"meals", "roles"})`
>
</details>

#### Apply 6_04_HW5_optional_fix_jdbc_profiles.patch

- <a href="http://javarticles.com/2013/12/spring-profiles.html">Spring Profiles</a>. <a href="https://www.javacodegeeks.com/2013/10/spring-4-conditional.html">Spring 4 Conditional</a>.
- дополнительно:
    - зайдите в исходники `@Profile` и посмотрите (подебажьте) его реализацию через `@Conditional(ProfileCondition.class)`.
    - [реализация через Java Config и Profiles на уровне методов](http://stackoverflow.com/a/43645463/548473)

#### Apply 6_05_update_hsqldb.patch

В реальном проекте часто проблему можно решить простым обновлением версии: <a href="http://hsqldb.org/">new HSQLDB version supports Java 8 time API</a>

#### Apply 6_06_HW5_optional_fetch_join.patch

> - Добавил проверки и тесты на `NotFound` для `MealService.getWithUser` и  `UserService.getWithMeals`
> - Убрал `CascadeType.REMOVE`, в уроке далее будет про Cascade.

- <a href="http://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby">JPA JoinColumn vs mappedBy</a>
-  <a href="https://en.wikibooks.org/wiki/Java_Persistence/OneToMany#Unidirectional_OneToMany.2C_No_Inverse_ManyToOne.2C_No_Join_Table_.28JPA_2.x_ONLY.29">Unidirectional OneToMany</a>

#### Apply 6_07_HW5_graph_batch_size.patch
- **<a href="http://stackoverflow.com/questions/97197/what-is-the-n1-selects-issue">N+1 selects issue</a>**

- <a href="https://docs.oracle.com/javaee/7/tutorial/persistence-entitygraphs002.htm">Using Named Entity Graphs</a>
    - [Entity Graph в Spring Data JPA](https://sysout.ru/entity-graph-v-spring-data-jpa/)
    - [`EntityGraphType.FETCH` vs `LOAD`](http://stackoverflow.com/questions/31978011/what-is-the-diffenece-between-fetch-and-load-for-entity-graph-of-jpa)
- <a href="https://dou.ua/lenta/articles/jpa-fetch-types/">Стратегии загрузки коллекций в JPA</a>
- <a href="https://dou.ua/lenta/articles/hibernate-fetch-types/">Стратегии загрузки коллекций в Hibernate</a>

> Когда мы достаем всех юзеров с ролями без `@BatchSize`, делается запрос юзеров (1), и на каждого юзера идет в базу запрос ролей (+N). C `@BatchSize(size = 200)` делается запрос на юзеров (1), и затем роли достаются пачками для 200 юзеров (+ N/200).

## Занятие 6:

### Добавил тесты на валидацию

> - К сожалению, в JUnit <a href="https://github.com/junit-team/junit4/pull/778">нет `ExpectedException.expectRootCause`</a>. `AbstractServiceTest.validateRootCause()` сделал через [JUnit 4.13 assertThrows](https://stackoverflow.com/a/2935935/548473).

> ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Откуда у нас берется `ConstraintViolationException` в тестах на валидацию? Для каких наших исключений он является рутом?

Прежде всего - пользуйтесь дебагом! Исключение легко увидеть в методе `getRootCause()`. Если подебажить выполение
Hibernate валидации, то можно найти, где обрабатываются аннотации валидации и место
в `org.hibernate.cfg.beanvalidation.BeanValidationEventListener.validate()`, где
бросается `ConstraintViolationException`.  
Cамое простое - поставить брекпойнт в конструкторах `ConstraintViolationException` или в `ValidationException` и
запустить тест `createWithException` в дебаге.

#### Apply 6_08_add_test_validation.patch

**Тесты валидации для Jdbc не работают, нужно будет починить в HW6 (в реализация Jdbc валидация отсутствует)**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFeTV0SUFfblk5NE0">Кэш Hibernate</a>

> Кэш мигрировал на 3.x

<details>
  <summary><b>Краткое содержание</b></summary>

#### Уровни кэширования Hibernate

Hibernate cache — это 3 уровня кеширования:

- Кеш первого уровня (First-level cache) - включен по умолчанию всегда, его нельзя отключить, кэширует сущности на
  уровне сессии;
- Кеш второго уровня (Second-level cache) - используется на уровне фабрики сессий, по умолчанию всегда отключен и не
  имеет реализации в Hibernate, для его использования нужно самостоятельно подключать сторонние реализации;
- Кеш запросов (Query cache) - по умолчанию отключен, включается определением дополнительных параметров в
  конфигурационном файле. В нем кэшируются идентификаторы объектов, которые соответствуют совокупности параметров
  совершенного ранее запроса;

Для кэширования определяются 4 стратегии, которые определяют его поведение в определенных ситуациях:

- Read-only
- Read-write
- Nonstrict-read-write
- Transactional

#### Подключение кэш 2 уровня Hibernate

Существует множество сторонних реализаций кэша, которые можно подключить к Hibernate. Мы будем использовать одну из
самых распространенных - EhCache. Для того чтобы подключить к Hibernate EhCache, в файл `pom.xml` нужно добавить
дополнительную зависимость:

````
 <dependency>
         <groupId>org.hibernate</groupId>
         <artifactId>hibernate-jcache</artifactId>
         <version>${hibernate.version}</version>
 </dependency>
````
Теперь кэш подключен и его осталось лишь сконфигурировать в файле spring-db.xml для профилей 
"datajpa, jpa" :
````
<entry key="#{T(org.hibernate.cache.jcache.ConfigSettings).PROVIDER}" value="org.ehcache.jsr107.EhcacheCachingProvider"/>

<!--Кэш может делить свои области на регионы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).CACHE_REGION_FACTORY}" value="org.hibernate.cache.jcache.internal.JCacheRegionFactory"/>

<!--Включаем кэш второго уровня (по умолчанию value = false)-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).USE_SECOND_LEVEL_CACHE}" value="true"/>

<!--Можно подключить кэширование запросов, по умолчанию оно отключено. Оставим отключенным, value = false-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).USE_QUERY_CACHE}" value="false"/>
````  
Сам кэш более тонко настраивается в отдельном файле `ehcache.xml`. В нем мы указываем какие
таблицы будут кэшироваться, количество элементов, время и множество других параметров.

Чтобы указать Hibernate какие сущности будут кэшироваться, их нужно пометить аннотацией `@Cache` и в скобках
указать необходимую стратегию кэширования. Такая аннотация предоставляется как JPA, так и Hibernate, аннотация 
Hibernate позволяет определять дополнительные параметры кэширования.  
Так как мы пометили User `@Cache`, то сущности будут заноситься в кэш второго уровня, но не будет кэшироваться коллекция
ролей. Чтобы роли тоже кэшировались нужно так же пометить это свойство пользователя аннотацией `@Cache`.  
Теперь, при запуске тестов мы столкнемся с частой проблемой — так как перед каждым тестом мы повторно заполняем базу
тестовыми данными, и делаем мы это в обход Hibernate - содержимое кэша 2 уровня и базы данных будут различаться. 
Поэтому перед каждым тестом дополнительно нужно кэш инвалидировать — специально для этого мы создадим
утильный класс `JpaUtil`, где определим метод, который будет получать текущую Session Factory и инвалидировать кэш Hibernate. 
Объект этого класса внедрили в тесты, и, так как в `spring-db.xml` мы определили, что этот бин будет создаваться только для профилей *jpa, datajpa*, 
тесты для JDBC реализации перестанут работать. `JpaUtil` отсутствует в профиле `jdbc` и не может быть разрезолвен при поднятии Spring контекста.
</details>

#### Apply 6_09_hibernate_cache.patch

**Теперь уже все `JdbcUserServiceTest` тесты поломались (добавил `@Ignore`). Требуется починить в HW6**

- <a href="http://habrahabr.ru/post/135176/">Уровни кэширования Hibernate</a>
- <a href="http://habrahabr.ru/post/136375/">Hibernate Cache. Практика</a>
- <a href="http://www.tutorialspoint.com/hibernate/hibernate_caching.htm">Hibernate - Caching</a>
- Починка тестов: <a href="http://stackoverflow.com/questions/1603846/hibernate-2nd-level-cache-invalidation-when-another-process-modifies-the-databas">инвалидация кэша Hibernate</a>
- [Hibernate User Guide: Caching](http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#caching)
- [Hibernate 5, Ehcache 3.x](https://www.boraji.com/index.php/hibernate-5-jcache-ehcache-3-configuration-example)
- Ресурсы:
   - **<a href="https://www.youtube.com/watch?list=PLYj3Bx1JM6Y7BKivc3eZwRUhWwBmbIFXg&v=V-ljsrVy6pE">Hibernate performance tuning (Mikalai Alimenkou /Igor Dmitriev)</a>**
   -  <a href="http://stackoverflow.com/questions/3663979/how-to-use-jpa2s-cacheable-instead-of-hibernates-cache">JPA2 @Cacheable vs Hibernate @Cache</a>
   -  <a href="http://vladmihalcea.com/2015/06/08/how-does-hibernate-query-cache-work/">How does Hibernate Query Cache work</a>
   -  <a href="https://www.javacodegeeks.com/2014/06/pitfalls-of-the-hibernate-second-level-query-caches.html">Pitfalls of the Hibernate Second-Level / Query Caches</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFVmdpNDJSNXRTWUE">Cascade. Auto generate DDL.</a>

<details>
<summary><b>Краткое содержание</b></summary>
При создании таблиц `user_roles` и `meals` для внешнего ключа мы указывали свойство `ON DELETE CASCADE`. Это означает, что при удалении
пользователя база данных автоматически будет удалять все записи, которые на него ссылались по внешнему ключу. Существует также
свойство `ON UPDATE CASCADE`, определив которое, при обновлении первичного ключа пользователя этот ключ обновлялся бы во всех зависимых
таблицах. При каскадных операциях на уровне базы данных могут возникнуть проблемы с консистентностью кэша второго уровня, так как все
операции производятся в обход Hibernate.
Hibernate тоже позволяет указывать `CascadeType` для управляемых сущностей (что не имеет абсолютно никакой связи с таким же свойством в базе данных).
 
> Для Hibernate допускается указывать `CascadeType` только для сущности @OneToMany - со стороны родителя!  
> Для этой цели со стороны дочерней сущности можно указать аннотацию @OnDelete над ссылкой на родительскую сущность.  
> Для коллекций элементов сущности все действия всегда распространяются каскадно!  

Действие, указанное в `CascadeType` при манипулировании сущностью — будут распространяться на все ее дочерние объекты.

> ALL - все возможные каскадные операции, выполняемые над исходной сущностью, применяются к дочерним сущностям.
> MERGE - если исходная сущность объединяется, слияние каскадно передается на дочерние сущности.
> PERSIST - если исходный объект сохраняется, сохранятся каскадно и дочерние объекты.
> REFRESH - если исходная сущность обновляется, каскадно обновятся и дочерние объекты.
> DELETE - при удалении исходной сущности удаляются и дочерние объекты.

Также для аннотации `@OneToMany` существует параметр `orphanRemoval`. Если установить этот параметр в true, то при удалении исходной сущности
все объекты, которые ранее на нее ссылались так же удалятся, если установить это значение в false, то в дочерних сущностях ссылка на 
исходную будет просто обнуляться.  

#### Генерация схемы базы данных по Java Entity
JPA 2.1 предоставляет возможность генерировать базу данных по сущностям. Для этого в spring-db.xml
укажем следующие параметры:  
```
<!--Действие, которое выполнится - схема будет сброшена и создана снова-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_ACTION}" value="drop-and-create"/>

<!--Расположение скриптов создания схемы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_CREATE_TARGET}" value="${TOPJAVA_ROOT}/config/ddl/create.ddl"/>

<!--Расположение скриптов сброса схемы-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_SCRIPTS_DROP_TARGET}" value="${TOPJAVA_ROOT}/config/ddl/drop.ddl"/>

<!--Можно настроить автоматическое обновление схемы базы данных при изменении сущностей-->
<entry key="#{T(org.hibernate.cfg.AvailableSettings).HBM2DDL_AUTO}" value="create"/>
```
> Автоматическую генерацию не рекомендуется использовать для реального приложения, так как генерируемые
> команды часто некорректны. Чтобы они были более правильными — нужно указывать дополнительные условия и ограничения
> в аннотациях при описании entity.
</details>

#### Apply 6_10_cascade_ddl.patch

#### Cascading

> Есть SQL ON .. CASCADE, которая выполняется в базе данных, и есть аннотация в Hibernate, исполняемая в приложении

- <a href="http://stackoverflow.com/questions/13027214">Do not use `CascadeType` for @ManyToOne</a>
- <a href="http://stackoverflow.com/questions/836569">CascadeType meaning</a>
- <a href="https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection">No cascade option on an ElementCollection, the target objects are always persisted, merged, removed with their parent.</a>
- <a href="http://stackoverflow.com/questions/21149660">Create ON DELETE CASCADE: `@OnDelete`</a>
- [Сascade for `@ElementCollection`](https://stackoverflow.com/a/62848296/548473)
- <a href="http://stackoverflow.com/questions/3087040">Hibernate second level cache and ON DELETE CASCADE in database schema</a>
- [`orphanRemoval=true` vs `CascadeType.REMOVE`](http://stackoverflow.com/a/19645397/548473)
- [JPA `cascade/orphanRemoval` doesn't work with `NamedQuery`](http://stackoverflow.com/questions/7825484/jpa-delete-where-does-not-delete-children-and-throws-an-exception)

#### Auto schema generation
- <a href="https://www.javacodegeeks.com/2015/03/jpa-database-schema-generation.html">JPA DATABASE SCHEMA GENERATION</a>
- <a href="http://stackoverflow.com/questions/7793395">hbm2ddl.auto and autoincrement</a>
- <a href="http://stackoverflow.com/questions/2585641">Hibernate/JPA DB Schema Generation Best Practices</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVE1jWkRucm1UTjA">Spring Web</a>

<details>
  <summary><b>Краткое содержание</b></summary>
Для работы с web с помощью Spring подключим к проекту следующие зависимости:  
 
```
<!--TomCat - web-контейнер. Позволяет работать с jsp, сервлетами -->
<dependency>
     <groupId>org.apache.tomcat</groupId>
     <artifactId>tomcat-servlet-api</artifactId>
     <version>${tomcat.version}</version>
     <scope>provided</scope>
</dependency>

<!--java standart tag library - не предоставляется TomCat, нужно добавлять вручную -->
<dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        
<!--Spring библиотека для работы с web -->
<dependency>
     <groupId>org.springframework</groupId>
     <artifactId>spring-web</artifactId>
     <version>${spring.version}</version>
</dependency>
```
При старте web-приложения в контейнере сервлетов требуется инициализировать контекст спринга.  
Запустить Spring можно с помощью `ContextLoaderListener`, который будет отслеживать работу веб-приложение и при инициализации сервлета
поднимать Spring context в методе `contextInitialized` и отключать контекст спринга при остановке
приложения в методе `contextDestroyed`.
Для этого нужно определить этот ContextListener в `web.xml`:

```
 <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
```
Чтобы Listener смог поднять контекст спринга — ему нужно указать в `web.xml` путь к конфигурационным файлам и
задать необходимые профили:

```
<context-param>
        <param-name>spring.profiles.default</param-name>
        <param-value>postgres,datajpa</param-value>
    </context-param>

    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>
            classpath:spring/spring-app.xml
            classpath:spring/spring-db.xml
        </param-value>
    </context-param>
```

Для каждого сервлета, при инициализации, после создания запускается метод `init(ServletConfig config)`, где мы можем получить текущий контекст Spring. 
Для web приложений определяется свой собственный `WebApplicationContext`, который может работать с сервлетами.
В `UserServlet` мы можем получить контекст с помощью метода `WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())`.
 Из полученного контекста мы можем получать бины Spring, например, объект `UserService`, и работать через него с пользователями.
</details>

#### Apply 6_11_spring_web.patch

> - Для сборки проекта в окне Maven отключите тесты (`Toggele 'Skip Tests' Mode`)
> - В `web.xml` задаются профили запуска по умолчанию: `<param-value>postgres,datajpa</param-value>`. **Если запускаетесь под HSQLDB, надо поменять на `hsqldb,datajpa`**.

- <a href="http://www.mkyong.com/servlet/what-is-listener-servletcontextlistener-example/">ServletContextListener</a>.
- <a href="https://docs.oracle.com/javaee/6/tutorial/doc/bnafi.html">Servlet Lifecycle</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFN3k0ZVk1MnF5TjQ">JPS, JSTL, internationalization</a>

<details>
  <summary><b>Краткое содержание</b></summary>
Ко всему, что находится в папке webapp, можно получить доступ из браузера.   
Для отображения пользователей создадим jsp страницу `userList`. Для работы со специализированными функциями и выражениями на 
странице импортируем некоторые библиотеки jstl(java standard tag library).  

#### Локализация
 - для локализации **стандартными средствами java** можно использовать *Bundle* - это набор файлов properties, 
где определены ключ и значение. В зависимости от локали автоматически будет выбран нужный файл properties, из которого по 
   ключу страница будет получать текст на нужном языке и подставлять в места, где указаны соответствующие ключи.
```html
<fmt:setBundle basename="messages.app"/>
```
 - Для локализации нашего приложения создадим в папке `resources/messages` два файла - `app.properties` и `app_ru.properties`, в которых 
мы и пропишем ключи и соответствующие им значения на русском и английском языках.
При этом нужно иметь в виду, что локализация в jpa/jstl не работает с UTF8, поэтому для отображения текста на кириллице приходится записывать
его в виде набора кодов unicode (intellij idea предоставляет нам удобный функционал для работы с этими кодами).

> **Это было до Java 9. Теперь можно не парится и писать напрямую в UTF-8** 

 - На каждой странице будут дублироваться верхняя часть(header) и нижняя часть(footer), поэтому сделаем их в виде фрагментов, которые будут
включаться в каждую страницу с помощью тега  
   ```< jsp:include page="fragments/bodyHeader.jsp"/ > ``` 
 - Для того чтобы на странице JSP понимало, с каким объектом оно работает (а в IDEA работали автодополнения), мы можем явно указать с каким типом 
объекта мы будем работать. Для этого мы используем тег:
```html
<jsp:useBean id="user" scope="page" type="ru.javawebinar.topjava.model.User"/>
```
После этого на странице мы сможем работать с объектом в java-вставках *< %  >* и с помощью expression language *${ }*.    
Без этого тэга приложение работать тоже будет, не будет IDEA интергации. Не забывайте про getter-ы, JSP обращается к объектам через них!

> Локаль приложения определяется на основе локали операционной системы и свойств браузера. Чтобы проверить работу
> локализации можно из браузера в заголовке запроса указать Content-Language:"en-US". Этот заголовок будет 
> считан в сервлете, и приложение определит требуемую локаль.

</details>

**Убедитесь, что [в настройках IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA#Поставить-кодировку-utf-8) кодировка везде UTF-8</a> до применения патча**
#### Apply 6_12_jsp_jstl_i18n.patch

> - Поменял `users/meals` в ключах локализации на `user/meal`. Понадобится при локализации ошибок (сделаем позже)
> - [Since Java 9 default encoding in properties files is UTF-8](https://docs.oracle.com/javase/9/intl/internationalization-enhancements-jdk-9.htm). Галочка `Transparent` и ASCII кода уже не нужны.


**Для работы с несколькими языками установите плагин `Resource Bundle Editor`**
  
- <a href="http://docs.oracle.com/javaee/1.3/tutorial/doc/JSPIntro8.html">Including Content in a JSP Page</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLTB3R3pKNFNEQmM">Динамическое изменение профиля при запуске.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Динамическое изменение профиля при запуске
Изначально мы определили профиль спринга в web.xml:
```xml
<context-param>
        <param-name>spring.profiles.default</param-name>
        <param-value>postgres,datajpa</param-value>
    </context-param>
```
При этом приложение будет деплоиться на сервер с указанными в конфигурации профилями.  
Чтобы определить профиль Spring при запуске без перекомпиляции проекта можно использовать системные опции, которые мы можем
задать при запуске проекта, указав их в параметрах запуска (в командной строке, а для Intellij Idea: Edit Run/Debug Configurations - VM options).  
В этом случае конфигурации по умолчанию будут переопределены и будут использованы заданные при запуске системные переменные.

</details>

    -Dspring.profiles.active="postgres,datajpa"

- <a href="http://stackoverflow.com/questions/10041410/default-profile-in-spring-3-1#answer-10041835">Set profiles in Spring 3.1</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdkFRRFdYa0NoWkU">Конфигурирование Tomcat через maven plugin. Jndi-lookup.</a>

<details>
  <summary><b>Краткое содержание</b></summary>
Многие настройки сервера(web-контейнера) можно вынести в отдельный файл-конфигурацию.
Настройки TomCat определим в отдельном файле `context.xml`

#### Настройка пула TomCat для соединения с базой данных
```xml
<!-- Имя ресурса, к которому мы будем получать доступ из приложения по JNDI
    <Resource name="jdbc/topjava"
              auth="Container"
              type="javax.sql.DataSource"
              
              Настройки подключения к базе данных
              url="jdbc:postgresql://localhost:5432/topjava"
              username="user"
              password="password"
              driverClassName="org.postgresql.Driver"
              
              Настройки пула коннектов к базе данных
              validationQuery="SELECT 1"
              maxTotal="10"
              minIdle="2"
              maxWaitMillis="20000"
              initialSize="2"
              maxIdle="5"
              testOnBorrow="true"
              removeAbandonedOnBorrow="true"
              testWhileIdle="true"/> -->
```
Для того чтобы TomCat при запуске создавал пул коннектов, требуется добавить maven плагин в
секцию **buid**

```xml
    <!-- http://stackoverflow.com/questions/4305935/is-it-possible-to-supply-tomcat6s-context-xml-file-via-the-maven-cargo-plugin#4417945 -->
            <!-- https://codehaus-cargo.github.io/cargo/Tomcat+9.x.html -->
            <plugin>
                <groupId>org.codehaus.cargo</groupId>
                <artifactId>cargo-maven3-plugin</artifactId>
                <version>1.9.5</version>
                <configuration>
                    <container>
                        <containerId>tomcat9x</containerId>
                        <systemProperties>
                            <file.encoding>UTF-8</file.encoding>

                            <!-- Активные профили Spring, с которыми будет запускаться приложение-->
                            <spring.profiles.active>tomcat,datajpa</spring.profiles.active>
                        </systemProperties>

                        <!-- Для создания пула коннектов томкату нужен драйвер postgres, поэтому добавляем его в зависимости-->
                        <dependencies>
                            <dependency>
                                <groupId>org.postgresql</groupId>
                                <artifactId>postgresql</artifactId>
                            </dependency>
                        </dependencies>
                    </container>
                    <configuration>
                        <configfiles>
                            <configfile>
                                
                                <!-- Путь к файлу, в котором определены настройки пула коннектов-->
                                <file>src/main/resources/tomcat/context.xml</file>
                                <todir>conf/Catalina/localhost/</todir>
                                <tofile>${project.build.finalName}.xml</tofile>
                            </configfile>
                        </configfiles>
                    </configuration>
                    <deployables>
                        <deployable>
                            <groupId>ru.javawebinar</groupId>
                            <artifactId>topjava</artifactId>
                            <type>war</type>
                            <properties>
                                <context>${project.build.finalName}</context>
                            </properties>
                        </deployable>
                    </deployables>
                </configuration>
            </plugin>
```  
В `spring-db.xml` создаем новый профиль "tomcat", для него будет создаваться бин `dataSource` с помощью соединения, 
которое будет получено из пула коннектов TomCat по JNDI. При этом в профиле мы можем указать расположение файла свойств,
в котором будут описаны дополнительные параметры пула коннектов контейнера сервлетов - у нас это файл `tomcat.properties`.  

</details>

С плагином мы можем сконфигурировать Tomcat прямо в `pom.xml` и запустить его с задеплоенным туда нашим приложением WAR
из командной строки без IDEA и без инсталляции Tomcat. По умолчанию он скачивает его из центрального maven-репозитория (
можно также указать свой в `<container><home>${container.home}</home></container>`). При запуске Tomcat из IDEA
запускается Tomcat, путь к которому мы прописали в конфигурации запуска (со своими настройками).

#### Apply 6_13_tomcat_pool_jndi_cargo.patch

> - для запуска в Tomcat 9 поменял `tomcat7-maven-plugin` на `cargo-maven3-plugin`.
> - в `pom.xml` вместо `context.xml.default` можно делать [индивидуальный контекст приложения](https://stackoverflow.com/a/60797999/548473)
> - Конфигурация сделана под postgres. Для HSQLDB нужно скорректировать `driverClassName` + `validationQuery="SELECT 1 FROM INFORMATION_SCHEMA.SYSTEM_USERS` в `context.xml` и `dependencies`.

> ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Томкат сам управляет пулом коннектов? На каждый запрос в браузере будет даваться свой коннект?
 
Да, в Томкате есть реализация пула коннектов `tomcat-jdbc` (мы его подключаем со `scope=provided`). Если запускаемся с профилем `tomcat`, приложение на каждую транзакцию (или операцию не в транзакции) берет коннект к базе из пула, сконфигурированного в подкладываемом Tomcat `context.xml`.

> ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Для чего мы делаем профиль `tomcat`? Возможно два варианта запуска приложения: либо cargo, либо tomcat? И если мы запускаем через tomcat то в `spring-db.xml` через `jee:jndi-lookup` подтягивается конфигурация tomcata из `\src\main\resources\tomcat\context.xml`?

1. Есть `cargo-maven3-plugin` который автоматически запускает Tomcat и деплоит туда наше приложение. Те это тоже деплой
   в Tomcat, но через Maven.
2. В xml конфигурации Tomcat можно настраивать ресурсы (кроме пула коннектов к БД могут быть, например, JMS или
   настройки Mail). Это никак не связано с `cargo` плагином. В Spring этот сконфигурированный ресурс контейнера
   сервлетов подлючается через `jee:jndi-lookup`. Тк у нас несколько вариантов конфигурирования `DataSource`, мы этот
   вариант сделали в `spring-db.xml` в профиле `tomcat`.
3. Плагин cargo позволяет задавать xml конфигурацию запускаемого Tomcat (у нас `src/main/resources/tomcat/context.xml`).
   И в параметрах запуска мы задаем активные профили Spring `tomcat,datajpa` через `spring.profiles.active`. Таким
   образом мы в плагине конфигурируем Tomcat, деплоим в него приложение и задаем приложению активные профили Spring
   для `DataSource` из конфигурации Tomcat.

Еще раз: плагин `cargo` и JNDI - это две не связанные между собой вещи, просто мы добавили их в проект в одном патче.   
Плагин запускается **после сборки проекта**. Запуск из командной строки:

     mvn clean package -DskipTests=true org.codehaus.cargo:cargo-maven3-plugin:1.9.5:run

Приложение деплоится в application context topjava: [http://localhost:8080/topjava](http://localhost:8080/topjava)

- <a href="https://codehaus-cargo.github.io/cargo/Maven+3+Plugin.html">Cargo Maven3 plugin</a>
- <a href="http://stackoverflow.com/questions/4305935/is-it-possible-to-supply-tomcat6s-context-xml-file-via-the-maven-cargo-plugin#4417945">Кастомизация context.xml в cargo-maven2-plugin</a>
- <a href="https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html"/>Tomcat JNDI Resources</a>
- <a href="https://commons.apache.org/proper/commons-dbcp/configuration.html">BasicDataSource Configuration</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQThUX2VyQXNiTHM">Spring Web MVC</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Работа Spring MVC основана на паттерне Front Controller (Единая точка входа). 
Все запросы поступают в единый собственный сервлет Spring, в котором происходит его перенаправление на нужный сервлет приложения.  
Для работы со Spring MVC нужно заменить зависимость `spring-web` на `spring-webmvc`:  
```xml
 <dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
</dependency>
```  
После этого в `web.xml` необходимо сконфигурировать единую точку входа - Spring `DispatcherServlet`, в который будут поступать все запросы 
к приложению:  
```xml
<servlet>
        <servlet-name>mvc-dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <!--Spring MVC имеет собственный контекст, контексты объединяются в цепочке. 
            В итоге у нас будет два контекста - первый - контекст web-приложения, в котором находятся контроллеры
            и который обрабатывает запросы к приложению, второй - основной контекст приложения, в котором происходит 
            бизнес-логика. Ниже мы указываем путь к конфигурации web-контекста приложения-->
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring/spring-mvc.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
</servlet>

<!--Все запросы к приложения будут поступать в "/", этот сервлет-->
    <servlet-mapping>
        <servlet-name>mvc-dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```  
> Различные контексты не имеют доступа к бинам друг друга (исключение - дочерние контексты могут получать доступ к бинам родителя, но не наоборот),
> каждый контекст поднимает для себя свои собственные экземпляры, поэтому нужно следить за конфигурацией бинов и поднимать их в соответствующем контексте.

Конфигурацию webmvc контекста и диспетчер-сервлета определим в файле `spring-mvc.xml`.

#### Сценарий обработки запроса
 1. Запрос поступает в dispatcher-servlet, в нем определен набор Handler Mappings - классы, которые обрабатывают запросы в зависимости от их типа.
- Соответствующий запросу Handler делегирует обработку запроса нужному контроллеру
- Контроллер необходимым образом обрабатывает запрос и возвращает View
- View отображает результат выполнения запроса

В нашем приложении будет два вида контроллеров: одни — работают с User Interface и отображают результат работы приложения в браузере, 
другие — работают по REST-интерфейсу. Контроллеры помечаются аннотацией `@Controller`.  
Паттерн и тип HTTP метода, по которым мы можем получить доступ к методу контроллера конфигурируются
с помощью аннотации `@RequestMapping(value = "/users", method = RequestMethod.GET)`.

> **В последних версиях Spring можно сделать проще: `@GetMapping("/users")`**

При этом Spring внедрит в метод объект `Model`, в который мы можем добавлять атрибуты и передавать их из слоя контроллера в слой представления.

Чтобы Spring MVC контекст мог осуществлять роутинг запросов по этим аннотациям, в конфигурации `spring-mvc.xml` нужно 
вручную включить поддержку аннотаций: 
```xml
<mvc:annotation-driven/>
```

Методы контроллера, помеченные аннотацией `@RequestMapping` (а также `@GetMapping, @PostMapping, @PutMapping, ..`) после обработки запроса должны возвращать имя представления, в которое
будет передана Model. Эта View отобразится как результат выполнения запроса. Чтобы в этих методах возвращать только название нужной View, в конфигурации нужно
определить `ViewResolver`, который автоматически к этому названию добавит путь к view в приложении и суффикс — формат view:  
```xml
  <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          p:prefix="/WEB-INF/jsp/"
          p:suffix=".jsp"/>
```

Для того чтобы приложение имело доступ к статическим ресурсам (например, стили) - нужно добавить дополнительную конфигурацию 
в `spring-mvc.xml`:
```xml
    <mvc:resources mapping="/resources/**" location="/resources/"/>
```
> Spring MVC имеет конфигурацию по умолчанию. Если в `spring-mvc.xml` мы не укажем никаких Handlers, то их стандартный набор
> будет создан автоматически и приложение будет работать. Как только мы добавляем собственные Handlers в конфигурацию — настройки
> по умолчанию переопределяются и будут созданы только те бины, которые мы определили, стандартные Handlers созданы не будут.

</details>

#### Apply 6_14_spring_webmvc.patch

Обработка запросов переезжает в `RootController`, сервлеты уже не нужны
> - Починил [путь к корню](http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page)
> - В Spring 4.3 ввели новые аннотации `@Get/Post/...Mapping` (сокращенный вариант `@RequestMapping`)

-  <a class="anchor" id="mvc"></a><a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc">Spring Web MVC</a>
- [Spring MVC + Spring Data JPA + Hibernate - CRUD Example](https://www.codejava.net/frameworks/spring/spring-mvc-spring-data-jpa-hibernate-crud-example)  
- [ContextLoaderListener vs DispatcherServlet](https://howtodoinjava.com/spring-mvc/contextloaderlistener-vs-dispatcherservlet/)
-  <a href="http://design-pattern.ru/patterns/front-controller.html">Паттерн Front Controller</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-servlet-context-hierarchy">Иерархия контекстов в Spring Web MVC</a>
-  <a href="http://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Сценарий обработки запроса</a>. <a href="https://web.archive.org/web/20130708211855/https://www.studytrails.com/frameworks/spring/spring-mvc.jsp">HandlerMappings</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-viewresolver">View Resolution</a>: прячем jsp под WEB-INF.
-  HandlerMapping: <a href="http://www.mkyong.com/spring-mvc/spring-mvc-simpleurlhandlermapping-example/">SimpleUrlHandlerMapping</a>, <a href="http://www.mkyong.com/spring-mvc/spring-mvc-beannameurlhandlermapping-example/">BeanNameUrlHandlerMapping</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-caching-static-resources">Маппинг ресурсов.</a>
-  Ресурсы:
  -  <a href="http://www.mkyong.com/spring-mvc/spring-mvc-hello-world-example/">Spring MVC hello world</a>
  -  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-servlet-special-bean-types">Special bean types in the WebApplicationContext</a>

> Настройки `Project Structure->Modules->Spring`:

![image](https://user-images.githubusercontent.com/11200258/112018359-8aa1eb80-8b3f-11eb-942f-a8eb7938de41.png)

> ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) В `web.xml` мы инициализируем `DispatcherServlet`, передавая ему параметром `spring-mvc.xml`. Получается, что `DispatcherServlet` парсит `spring-mvc.xml` и находит в нем context?

Да, можно подебажить родителя (`FrameworkServlet.initWebApplicationContext()`). После инициализации
сервлет `DispatcherServlet` раскидывает все запросы по контроллерам (бинам контекста Спринга).
См. <a href="http://design-pattern.ru/patterns/front-controller.html">паттерн Front Controller</a>.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUEctTkRSMWNvRjg">Spring Internationalization</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Spring нормально работает с кириллицей, замена русских символов на их коды уже не требуется (как и с JDK 9).
Spring также автоматически может изменять локаль приложения, для этого в конфигурации `spring-mvc.xml` ему нужно
определить `ReloadableResourceBundleMessageSource`, который будет отвечать за локализацию и указать для него путь к Bundles с локализованными данными.  
В страницах JSP мы также должны указать, что теперь будем работать не через JSTL, а через Spring локализацию.
Для этого в страницах удаляем тег `fmt:setBundle`. Теперь Spring автоматически будет подставлять сообщения в зависимости от локали.
  Но сейчас Spring работает на основании JDK `ResourceBundle` и он игнорирует свойство *p:cacheSeconds="5"*, так как ресурсы
интернационализации будут кэшироваться Java. Чтобы ресурсы не кэшировались нужно использовать бин `ReloadableResourceBundleMessageSource` с путем к локализации, отличным от classpath приложения.

```xml
<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource"
        p:cacheSeconds="5"
        p:defaultEncoding="UTF-8">
    <property name="basenames" value="file:///#{systemEnvironment[TOPJAVA_ROOT]}/config/messages/app"/>
    <property name="fallbackToSystemLocale" value="false"/>
    </bean>
```
Теперь ресурсы интернационализации не будут кэшироваться и их можно будет менять во время работы приложения "на ходу". 
</details>

#### Apply 6_15_spring_i18n.patch

**Внимание: проверьте, что переменная окружения `TOPJAVA_ROOT` настроена!**
> - В локализации поменял <a href="https://stackoverflow.com/questions/4441682/what-to-use-for-localization-in-jsps-with-spring">`fmt:message` на `spring:message`</a>
> - Выбор языка зависит от языка операционной системы и заголовка `Accept-Language`. Добавил в `spring-mvc.xml` `messageSource` параметр [`fallbackToSystemLocale`](http://stackoverflow.com/questions/4281504/spring-local-sensitive-data).
Он управляет выбором, куда переключаться при выборе `en` и отсутствии `app_en.properties`: локаль операционной системы или `app.properties` (`fallbackToSystemLocale=false`). Переключение локалей будем реализовывать в конце проекта.  

#### Для тестирования локали [можно поменять `Accept-Language`](https://stackoverflow.com/questions/7769061/how-to-add-custom-accept-languages-to-chrome-for-pseudolocalization-testing). Для Хрома в `chrome://settings/languages` перетащить нужную локаль наверх или поставить [плагин Locale Switcher](https://chrome.google.com/webstore/detail/locale-switcher/kngfjpghaokedippaapkfihdlmmlafcc)

-  <a href="http://learningviacode.blogspot.ru/2012/07/reloadable-messagesources.html">Reloadable MessageSources</a>
-  <a href="http://nginx.com/resources/admin-guide/serving-static-content/">nginx: Serving Static Content</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
>  Кэш hibernate надстраивается над ehcache или живет самостоятельно?

- <a href="https://akorsa.ru/2016/11/kak-rabotaet-kesh-v-hibernate/">Understanding Hibernate Caching</a>:
Hibernate supports following open-source cache implementations out-of-the-box: EHCache (Easy Hibernate Cache), OSCache (Open Symphony Cache), Swarm Cache, and JBoss Tree Cache.

> Где конфигурируется интернализация для jstl (т.е. файл, где задаются app, app_ru.properties)? Достаточно указать в страницах bundle и путь в ресурсы?

`<fmt:setBundle basename="messages.app"/>` означает, что ресурсы будут искаться в `classpath:messages/app(_xx)/properties`:
<a href="http://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/fmt/setBundle.html">Tag setBundle</a>: fully-qualified resource name, which has the same form as a fully-qualified class name.
После сборки проекта maven их можно найти в `target/classes` или `target/topjava/WEB-INF/classes`.

> Отлично, что она все пишет на том языке, который пришел в заголовке запроса. А если я хочу выбрать?

Выбор языка зависит от языка операционной системы и заголовка `Accept-Language`. Параметр `fallbackToSystemLocale`, который управляет выбором, когда с `Accept-Language: en,en-US;` не находится локализация `app_en.properties`. Для переключения локали используется <a href="http://www.codejava.net/java-ee/jstl/jstl-format-tag-setlocale">JSTL Format Tag fmt:setLocale</a>. Мы будем реализовывать переключение локалей в Spring i18n в конце проекта.

> Мы создаем бин, где получаем dataSource по имени `<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/topjava"/>`.
Но там не указан класс, как в других dataSource. Получается, по имени jdbc/topjava нам уже отдается готовый объект dataSource, и мы как бы помещаем его в бин?

Здесь используется namespace `jee:jndi-lookup`, который прячет под собой классы реализации. JNDI объект DataSource конфигурируется в `src/main/resources/tomcat/context.xml`

> В плагине прописан профиль `<spring.profiles.active>tomcat,datajpa</spring.profiles.active>`, а в web.xml `<param-value>postgres,datajpa</param-value>`.
Какой же реально отрабатывает?

См. видео урока "Динамическое изменение профиля при запуске". В плагине мы задаем параметры JVM запуска Tomcat

> A `@NamedQuery` или `@Query` подвержены кэшу запросов? Т.е. если мы поставим _USE_QUERY_CACHE_value_="true", будет ли Hibernate их кэшировать?

Чтобы запрос кэшировался, кроме true в конфигурации [нужно еще явно выставить запросу _setCacheable_](http://vladmihalcea.com/2015/06/08/how-does-hibernate-query-cache-work/).    
По поводу кэширования `@NamedQuery` нашел [`@QueryHint`](https://docs.jboss.org/jbossas/docs/Clustering_Guide/5/html/ch04s02s03.html)

> Почему messages мы кладем в config и используем system environment? Разве так делают в реальном проекте? Не будешь же вписывать на сервере эти переменные каждый раз, если проект куда-то будет переезжать. Можно по-другому, кроме systemEnvironment['TOPJAVA_ROOT'], задать путь от корня проекта?

1. messages нам нужны в runtime (при работе приложения). Проект к собранному и задеплоенному в Tomcat war отношения никакого уже не имеет и на этом сервере он обычно не находится. Если ресурсы нужны только при сборке и тестировании, то путь к корню для одномодульного maven проекта можно задать как `${project.basedir}`, но для многомодульного проекта (а все реальные проекты многомодульные) это путь к корню своего модуля.
2. В "реальном приложении" делается совершенно по-разному:
  - нести с собой в classpath, но ресурсы нельзя будет динамически (без передеплоя) обновлять
  - класть в war (не в classpath) и обновлять в развернутом TOMCAT_HOME/webapps/[appname]/...
  - класть в зафиксированное определенное место (например, в home: `~` или в путь от корня `/app/config`). Можно задавать фиксированный пусть в пропертях профиля maven и фильтровать ресурсы (maven resources), чтобы они попали в проперти проекта.
  - делать через переменную окружения, как у нас
  - задавать в параметрах запуска JVM как системную переменную через -D..
  - располагать в преференсах (для unix это home, для windows - registry): <a href="http://java-course.ru/articles/preferences-api/">использование Preferences API</a>
  - держать настройки в DB

   Часто в одном приложении используют несколько способов для разных видов конфигураций.

> Не происходит ли дублирования при кэшировании пользователей чрез Hibernate и `@Cacheable`?

`@Cacheable` кэширует результат запроса `getAll()`, т.е. список юзеров. Hibernate кэширует юзеров по отдельности, т.е., грубо говоря, делает мапу id->User. Можно назвать это дублированием. Нужно ли будет такое в реальном приложении? Все смотрится из логики запросов и их частоты, вполне вероятно, что нет. Как-то мы писали приложение для Дойчебанка (аналог skype на GWT, т.е. на экране небольшое окошко) - там было 5(!!!) уровней кэширования, первый вообще в базе.

> У меня стоит Томкат 8-й версии, в помнике у нас 9-й прописан, но всё работает. Почему?

В `pom.xml` мы подключаем `tomcat-servlet-api` со `scope=provided`, что означает, что он используется только для компиляции и не идет в war. Т.к. мы не используем никаких фич Tomcat 9.x, то наш код совместим с Tomcat 8.x. При запуске через `cargo-maven2-plugin` Tomcat 9 загружается из maven-репозитория.

> Откуда `@Transactional` вытягивает класс для работы с транзакцией, в составе какого бина он идет?

1. Если в контексте Spring есть `<tx:annotation-driven/>`, то подключается `BeanPostProcessors`, который проксирует классы (и методы), помеченные `@Transactional`.
2. По умолчанию для TransactionManager используется бин с `id=transactionManager`

---------------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW06
- 1.1 Починить тесты `InMemoryAdminRestControllerSpringTest/InMemoryAdminRestControllerTest`  (добавлять `spring-mvc.xml` в контекст не стоит, т.к. в новой версии Spring для этого требуется `WebApplicationContext`. Можно просто поправить `inmemory.xml`).
- 1.2 Починить тесты Jdbc (исключить валидацию в тестах Jdbc)
  - <a href="http://iliachemodanov.ru/ru/blog-ru/12-tools/57-junit-ignore-test-by-condition-ru">org.junit.Assume</a>
  - [How to get active Profiles in Spring Application](https://stackoverflow.com/questions/9267799/548473)
- 1.3 Перенести функциональность `MealServlet` в `JspMealController` контроллер (по аналогии с `RootController`).
`MealRestController` у нас останется, с ним будем работать позже. 
  - 1.3.1 разнести запросы на update/delete/.. по разным методам (попробуйте вообще без параметра `action=`). Можно по аналогии с `RootController#setUser` принимать `HttpServletRequest request` (аннотации на параметры и адаптеры для `LocalDate/Time` мы введем позже). 
  - 1.3.2 в одном контроллере нельзя использовать другой. Чтобы не дублировать код, можно сделать наследование контроллеров от абстрактного класса.
  - 1.3.3 добавить локализацию и `jsp:include` в `mealForm.jsp / meals.jsp`

### Optional
- 2.1 Добавить транзакционность (`DataSourceTransactionManager`) в Jdbc-реализации  
- 2.2 Добавить еще одну роль к юзеру Admin (будет 2 роли: `USER, ADMIN`).
- 2.3 В `JdbcUserRepository` добавить реализацию ролей юзера (добавлять можно одним запросом с JOIN и `ResultSetExtractor/ RowCallbackHandler`, либо двумя запросами (отдельно `users` и отдельно `roles`). [Объяснение SQL JOIN](http://www.skillz.ru/dev/php/article-Obyasnenie_SQL_obedinenii_JOIN_INNER_OUTER.html)
  - 2.3.1 В реализации `getAll` НЕ делать запрос ролей для каждого юзера (N+1 select)
  - 2.3.2 При save посмотрите на <a href="https://www.mkyong.com/spring/spring-jdbctemplate-batchupdate-example/">batchUpdate()</a>
- 2.4 Добавить проверку ролей в `UserTestData.USER_MATCHER.assertMatch` и починить ВСЕ тесты (тесты должны проходить для юзера с несколькими ролями)  
- 2.5 Добавить валидацию для `Jdbc..Repository` через Bean Validation API (для JPA это делается автоматически при сохранении в базу, для Jdbc мы должны это делать вручную).  Оптимизировать код.
   - Валидацию `@NotNull` для `Meal.user` пока можно закомментировать. На 10-м уроке решим проблему через [Jackson JSON Views](http://www.baeldung.com/jackson-json-view-annotation)
   - [Валидация данных при помощи Bean Validation API](https://alexkosarev.name/2018/07/30/bean-validation-api/) 

### Optional 2 (повышенной сложности)
- 3 Отключить Spring кэш в `UserService` в тестах через `NoOpCacheManager` и для кэша Hibernate 2-го уровня `hibernate.cache.use_second_level_cache=false`. 
  - [JPA 2.0 disable session cache for unit tests](https://stackoverflow.com/a/58963737/548473)
  - [Example of PropertyOverrideConfigurer](https://www.concretepage.com/spring/example_propertyoverrideconfigurer_spring)
  - [Spring util schema](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#xsd-schemas-util)      

---------------------
## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW06
- 1: Неверная кодировка UTF-8 с Spring обычно решается фильтром `CharacterEncodingFilter`:
```
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
- 2: **Если не поднимается контекст Spring, смотрим причину вверху самого нижнего исключения.** Все ошибки на отсутствия бина в контексте или его нескольких реализациях относятся к пониманию основ: Spring application context. Если нет понимания этих основ, двигаться дальше нельзя, нужно вернуться к видео Спринг, где объясняется, что это такое. Также пересмотрите видео [Тестирование UserService через AssertJ](https://drive.google.com/file/d/1SPMkWMYPvpk9i0TA7ioa-9Sn1EGBtClD). Начиная с 11.30 как раз разбираются подобные ошибки.
- 3: Если неправильно формируется url относительно контекста приложения (например, `/topjava/meals/meals`), посмотрите на
  -  <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">Relative paths in JSP</a>
  -  <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">Spring redirect: prefix</a>
- 4: При проблемах с запуском Томкат проверьте запущенные процессы `java`, нет ли в `TOMCAT_HOME\webapps` приложения каталога `topjava`, логи tomcat (нет ли проблем с доступом к каталогам или контекстом Spring)
- 5: Если создаете неизменяемые List или Map, пользуйтесь `List.of()/ Map.of()`
- 6: В MealController общую часть `@RequestMapping(value = "/meals")` лучше вынести на уровень класса
- 7: Проверьте `@Transactional(readOnly = true)` сверху `Jdbc..Repository`
- 8: Проверьте, что `config\messages\app_ru.properties` у вас в кодировке UTF-8 (в любом редакторе/вьюере или при отключенном [Transparent native-to-ascii conversion](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%B4%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D1%83-utf-8) в IDEA).
- 9: Учтите, что роли у юзеров можно менять/добавлять/удалять
- 10: Убедитесь, что все методы UserService корректно работают с юзерами, у которых несколько ролей (**запусти наши тесты для Admin с 2-мя ролями**)
