# Онлайн проекта <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkF5c1hiWmstT0prODdtZkVuNFlMZmdtN3J0OUcyY0lkT2NlVzlUMXRUUlk">Материалы занятия</a>

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW5

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUVZobXRzNWFzUW8">HW5: Spring Profiles. Spring Data JPA</a>

#### Apply 01_HW5_data_jpa.patch

Транзакция начинается, когда встречается первый `@Transactional`. С default propagation `REQUIRED` остальные `@Transactional` просто участвуют в первой. Поэтому ставим ее сверху `DataJpaMealRepositoryImpl.save()`, чтобы все обращения к базе внутри метода были в одной транзакции. Анологично, если из сервиса собирается несколько запросов в репозитории, он ставится над методом сервиса.

> **!! Внимание! Перед накаткой патча переименуйте (`Shift+F6`) `MealServiceTest.resultLog` в `MealServiceTest.log` (моя ошибка с патчем `5_4_log_colored`). Или потренируйтесь с merge при накатке патча (пригодится).**

#### Apply 02_HW5_profile_test.patch
**Для IDEA не забудте выставить Spring Profiles в `spring-db.xml`: нарпимер `datajpa, postgres`**

> - `DbTest` переименован в `AbstractServiceTest` и сюда перенес `@ActiveProfiles(resolver = ActiveDbProfileResolver.class)`
> - Заменил `description.getMethodName()` на `getDisplayName()` в выводе результатов тестов. После `printResult()` буфер сбрасывается в 0, чтобы не накапливать изменения. 

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFVlpFS2l5bURjU2M">HW5: Optional</a>

#### Apply 03_HW5_optional_fix_jdbc_profiles.patch
> Сделал классы `Java8JdbcMealRepositoryImpl` и `TimestampJdbcMealRepositoryImpl` внутренними

- <a href="http://javarticles.com/2013/12/spring-profiles.html">Spring Profiles</a>. <a href="https://www.javacodegeeks.com/2013/10/spring-4-conditional.html">Spring 4 Conditional</a>.
- зайдите в исходники `@Profile` и посмотрите (подебажте) его  реализацию через `@Conditional(ProfileCondition.class)`.
- дополнительно: [реализация через Java Config и Profiles на уровне методов](http://stackoverflow.com/a/43645463/548473)

#### Apply 04_HW5_update_hsqldb.patch
В реальном проекте часто проблему можно решить простым обновлением версии: <a href="http://hsqldb.org/">new HSQLDB version supports Java 8 time API</a>

#### Apply 05_HW5_optional_fetch_join.patch
> - Добавил проверки и тесты на `NotFound` для `MealService.getWithUser` и  `UserService.getWithMeals`
> - Убрал `CascadeType.REMOVE`, в уроке далее будет про Cascade.

-  <a href="http://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby">JPA JoinColumn vs mappedBy</a>
-  <a href="https://en.wikibooks.org/wiki/Java_Persistence/OneToMany#Unidirectional_OneToMany.2C_No_Inverse_ManyToOne.2C_No_Join_Table_.28JPA_2.x_ONLY.29">Unidirectional OneToMany</a>

#### Apply 06_HW5_graph_batch_size.patch
> Сделал `@EntityGraph` [через `attributePaths`](https://spring.io/blog/2015/09/04/what-s-new-in-spring-data-release-gosling)

- **<a href="http://stackoverflow.com/questions/97197/what-is-the-n1-selects-issue">N+1 selects issue</a>**
- <a href="https://docs.oracle.com/javaee/7/tutorial/persistence-entitygraphs002.htm">Using Named Entity Graphs</a>
  - [JPA ENTITY GRAPHS](http://www.radcortez.com/jpa-entity-graphs/)
  - [`EntityGraphType.FETCH` vs `LOAD`](http://stackoverflow.com/questions/31978011/what-is-the-diffenece-between-fetch-and-load-for-entity-graph-of-jpa)
- <a href="https://dou.ua/lenta/articles/jpa-fetch-types/">Стратегии загрузки коллекций в JPA</a>
- <a href="https://dou.ua/lenta/articles/hibernate-fetch-types/">Стратегии загрузки коллекций в Hibernate</a>

> Когда мы достаем всех юзеров с ролями без `@BatchSize` делается запрос юзеров (1) и на каждого юзера идет в базу запрос ролей (+N).
C `@BatchSize(size = 200)` делается запрос на юзеров (1) и затем роли достаются пачками для 200 юзеров (+ N/200).

## Занятие 6:

### Добавил тесты на валидацию
> - к сожалению в JUnit <a href="https://github.com/junit-team/junit4/pull/778">нет `ExpectedException.expectRootCause`</a>, а `expectCause` нам не подходит. В 13 JUnit обещают `expectThrows()`, а пока сделал вручную:
`AbstractServiceTest.validateRootCause()`
 
 > ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Откуда у нас берется ConstraintViolationException в тестах на валидацию? Для каких наших исключений он является рутом? 
 
 Прежде всего - пользуйтесь дебагом! Исключение легко увидеть в методе `getRootCause()`. Если подебажить выполение Hibernate валидации, то можно найти, где обрабатываются аннотации валидации и место в `org.hibernate.cfg.beanvalidation.BeanValidationEventListener.validate()`, где бросается `ConstraintViolationException`.

#### Apply 07_add_test_validation.patch
**Тесты валидации для Jdbc не работают, нужно будет починить в HW6 (в реализация Jdbc валидация отсутствует)**


### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFeTV0SUFfblk5NE0">Кэш Hibernate</a>
#### Apply 08_hibernate_cache.patch
**Теперь уже все Jdbc тесты поломались. Требуется починить в HW6**

-  <a href="http://habrahabr.ru/post/135176/">Уровни кэширования Hibernate</a>
-  <a href="http://habrahabr.ru/post/136375/">Hibernate Cache. Практика</a>
-  <a href="http://www.tutorialspoint.com/hibernate/hibernate_caching.htm">Hibernate - Caching</a>
-  Починка тестов: <a href="http://stackoverflow.com/questions/1603846/hibernate-2nd-level-cache-invalidation-when-another-process-modifies-the-databas">инвалидация кэша Hibernate</a>
-  Ресурсы:
   - **<a href="https://www.youtube.com/watch?list=PLYj3Bx1JM6Y7BKivc3eZwRUhWwBmbIFXg&v=V-ljsrVy6pE">Hibernate performance tuning (Mikalai Alimenkou /Igor Dmitriev)</a>**
   -  Подключение <a href="http://www.ehcache.org/generated/2.9.0/html/ehc-all/#page/Ehcache_Documentation_Set%2Fco-hib_about_using_ehcache_with_hibernate.html%23wwconnect_header">кэша Hibernate 2-го уровня</a>
   -  <a href="http://stackoverflow.com/questions/3663979/how-to-use-jpa2s-cacheable-instead-of-hibernates-cache">JPA2 @Cacheable vs Hibernate @Cache</a>
   -  <a href="http://habrahabr.ru/post/25140/">Распределённая система кеша ehcache</a>
   -  <a href="http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#performance-cache-mapping">Cache mappings</a>
   -  <a href="http://vladmihalcea.com/2015/06/08/how-does-hibernate-query-cache-work/">How does Hibernate Query Cache work</a>
   -  <a href="http://blog.jhades.org/setup-and-gotchas-of-the-hibernate-second-level-and-query-caches/">Pitfalls of the Hibernate Second-Level / Query Caches</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFVmdpNDJSNXRTWUE">Cascade. Auto generate DDL.</a>
#### Apply 09_cascade_ddl.patch
#### Cascading
> Есть SQL ON .. CASCADE, которая выполняется в базе данных и есть аннотация в Hibernate, исполняемая в приложении

- <a href="http://stackoverflow.com/questions/13027214">Do not use `CascadeType` for @ManyToOne</a>
- <a href="http://stackoverflow.com/questions/836569">CascadeType meaning</a>
- <a href="https://en.wikibooks.org/wiki/Java_Persistence/ElementCollection">No cascade option on an ElementCollection, the target objects are always persisted, merged, removed with their parent.</a>
- <a href="http://stackoverflow.com/questions/21149660">Create ON DELETE CASCADE: `@OnDelete`</a>
- <a href="http://stackoverflow.com/questions/3087040">Hibernate second level cache and ON DELETE CASCADE in database schema</a>
- [`orphanRemoval=true` vs `CascadeType.REMOVE`](http://stackoverflow.com/a/19645397/548473)
- [JPA `cascade/orphanRemoval` doesn't work with `NamedQuery`](http://stackoverflow.com/questions/7825484/jpa-delete-where-does-not-delete-children-and-throws-an-exception)

#### Auto schema generation
- <a href="http://www.radcortez.com/jpa-database-schema-generation/">JPA DATABASE SCHEMA GENERATION</a>
- <a href="http://stackoverflow.com/questions/7793395">hbm2ddl.auto and autoincrement</a>
- <a href="http://stackoverflow.com/questions/2585641">Hibernate/JPA DB Schema Generation Best Practices</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVE1jWkRucm1UTjA">Spring Web</a>
#### Apply 10_spring_web.patch
> - Для сборки проекта в окне Maven отключите тесты (`Toggele 'Skip Tests' Mode`)
> - В `web.xml` добавляеются профили запуска по умолчанию: `<param-value>postgres,datajpa</param-value>`. Если запускаетесь под HSQLDB, надо поменять.

-  Добавляем в проект веб зависимости
-  Поднятие контекста Spring в веб приложении. <a href="http://www.mkyong.com/servlet/what-is-listener-servletcontextlistener-example/">ServletContextListener</a>. Задание дефолтного профиля.
-  Получение контекста Spring в веб-контейнере
-  <a href="https://docs.oracle.com/javaee/6/tutorial/doc/bnafi.html">Servlet Lifecycle</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFN3k0ZVk1MnF5TjQ">JPS, JSTL, internationalization</a>
#### Apply 11_jsp_jstl_i18n.patch
> Поменял `users\meals` в ключах локализации на `user\meal`. Понадобится при локализации ошибок (сделаем позже)
-  <a href="http://docs.oracle.com/javaee/1.3/tutorial/doc/JSPIntro8.html">Including Content in a JSP Page</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLTB3R3pKNFNEQmM">Динамическое изменение профиля при запуске.</a>
>  -Dspring.profiles.active="datajpa,postgres"

- <a href="http://stackoverflow.com/questions/10041410/default-profile-in-spring-3-1#answer-10041835">Set profiles in Spring 3.1</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8.   <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdkFRRFdYa0NoWkU">Конфигурирование Tomcat через maven plugin. Jndi-lookup.</a>
С плагином мы можем сконфигурировать Tomcat приямо в `pom.xml` и запустить его с задеплоенным туда нашим приложением WAR из командной строки 
без IDEA и без инсталляции Tomcat. По умолчанию он скачивает его из центрального maven репозитория (можно также указать свой в `<container><home>${container.home}</home></container>`).
При запуске Tomcat из IDEA запускается Tomcat,  путь к которому мы прописали в конфигурации запуска (со своими настройками). 

#### Apply 12_tomcat_pool_jndi_cargo.patch
> - для запуска в Tomcat 8 поменял `tomcat7-maven-plugin` на `cargo-maven2-plugin`.
> - плагин сконфигурирован под postgres. Для HSQLDB нужно скорректировать `dependencies` и `driverClassName` в `context.xml`

Запуск из коммандной строки:

     mvn clean package -DskipTests=true org.codehaus.cargo:cargo-maven2-plugin:1.6.4:run

- <a href="https://codehaus-cargo.github.io/cargo/Maven2+plugin.html">Cargo Maven2 plugin</a>
- <a href="http://stackoverflow.com/questions/4305935/is-it-possible-to-supply-tomcat6s-context-xml-file-via-the-maven-cargo-plugin#4417945">Катомизация context.xml в cargo-maven2-plugin</a>
- <a href="https://tomcat.apache.org/tomcat-8.0-doc/jndi-resources-howto.html"/>Tomcat JNDI Resources</a>
- <a href="https://commons.apache.org/proper/commons-dbcp/configuration.html">BasicDataSource Configuration</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQThUX2VyQXNiTHM">Spring Web MVC</a>
#### Apply 13_spring_webmvc.patch
> - Починил <a href="http://stackoverflow.com/questions/10327390/how-should-i-get-root-folder-path-in-jsp-page"> путь к корню</a>

-  <a class="anchor" id="mvc"></a><a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring Web MVC</a>
-  <a href="http://design-pattern.ru/patterns/front-controller.html">Паттерн Front Controller</a>
-  Добавляем DispatcherServlet и MVC application context.
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html#testcontext-ctx-management-ctx-hierarchies">Иерархия контекстов в Spring Web MVC</a>
-  <a href="http://www.tutorialspoint.com/spring/spring_web_mvc_framework.htm">Сценарий обработки запроса</a>. <a href="http://www.studytrails.com/frameworks/spring/spring-mvc.jsp">HandlerMappings</a>
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-viewresolver-resolver">View resolving</a>: прячем jsp под WEB-INF.
-  HandlerMapping: <a href="http://www.mkyong.com/spring-mvc/spring-mvc-simpleurlhandlermapping-example/">SimpleUrlHandlerMapping</a>, <a href="http://www.mkyong.com/spring-mvc/spring-mvc-beannameurlhandlermapping-example/">BeanNameUrlHandlerMapping</a>
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-config-static-resources">Маппинг ресурсов.</a>
-  Ресурсы:
   -  <a href="http://www.mkyong.com/spring-mvc/spring-mvc-hello-world-example/">Spring MVC hello world</a>
   -  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-webappctx-special-beans-tbl">Special bean types in the WebApplicationContext</a>

> ![](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) В `web.xml` мы инициализируем `DispatcherServlet`, передавая ему параметром `spring-mvc.xml`. Получается, что `DispatcherServlet` парсит `spring-mvc.xml` и находит в нем context?

Нет. `spring-mvc.xml` парсит какой-то из спринговых ApplicationContext (думаю что `XmlWebApplicationContext`).
`DispatcherServlet` это сервлет, который раскидывает запросы по контроллерам (бинам контекста спринга). См <a href="http://design-pattern.ru/patterns/front-controller.html">паттерн Front Controller</a>.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUEctTkRSMWNvRjg">Spring Internationalization</a>
#### Apply 14_spring_i18n.patch
- **Убедитесь что <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%B4%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D1%83-utf-8">в настройках IDEA кодировка везде UTF-8</a>**
- **Проверьте, что файлы локализации у вас в UTF-8 (в IDEA справа внизу в статусе есть кодировка и можно перекодировать).**

> - В локализации поменял <a href="http://forum.spring.io/forum/spring-projects/web/1077-differences-between-spring-message-and-fmt-message?p=451622#post451622">`fmt:message` на `spring:message`</a>
> - Выбор языка зависит от языка операционной системы и хедера `Accept-Language`. Добавил в `spring-mvc.xml` `messageSource` параметр [`fallbackToSystemLocale`](http://stackoverflow.com/questions/4281504/spring-local-sensitive-data).
Он управляет выбором, куда переключаться при выборе en и отсутствии `app_en.properties`: локаль операционной системы или `app.properties` (`fallbackToSystemLocale=false`). Переключение локалей будем реализовывать в конце проекта.  

#### Для тестирования локали [можно поменять `Accept-Language`](https://stackoverflow.com/questions/7769061/how-to-add-custom-accept-languages-to-chrome-for-pseudolocalization-testing). Для хрома в chrome://settings/languages перетащить нужную локаль наверх.

-  <a href="http://learningviacode.blogspot.ru/2012/07/reloadable-messagesources.html">Reloadable MessageSources</a>
-  <a href="http://nginx.com/resources/admin-guide/serving-static-content/">nginx: Serving Static Content</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
>  Кэш hibernate надстраивается над ehcache или он живет самостоятельно?

- <a href="http://mrbool.com/understanding-hibernate-caching/28721">Understanding Hibernate Caching</a>:
Hibernate supports following open-source cache implementations out-of-the-box: EHCache (Easy Hibernate Cache), OSCache (Open Symphony Cache), Swarm Cache, and JBoss Tree Cache.

> Где конфигурится интернализация для jstl (т.е. файл, где задаются app, app_ru.properties)? Достаточно указать в страницах бандл и путь в ресурсы?

`<fmt:setBundle basename="messages.app"/>` означает что ресурсы будут искаться в `classpath:messages\app(_xx)/properties`:
<a href="http://docs.oracle.com/javaee/5/jstl/1.1/docs/tlddocs/fmt/setBundle.html">Tag setBundle</a>: fully-qualified resource name, which has the same form as a fully-qualified class name.
После сборки проекта maven их можно найти в target\classes или target\topjava\WEB-INF\classes.

> Отлично, что она все пишет на том языке, который пришел в хидере запроса. А если я хочу выбрать?

Выбор языка зависит от языка операционной системы и хедера `Accept-Language`. Параметр `fallbackToSystemLocale`, который управляет выбором, когда с `Accept-Language: en,en-US;` не находится локализация `app_en.properties`. Для переключения локали используется <a href="http://www.codejava.net/java-ee/jstl/jstl-format-tag-setlocale">JSTL Format Tag fmt:setLocale</a>. Мы будем реализовывать переключение локалей в Spring i18n в конце проекта.

> Мы создаем бин, где получаем dataSource по имени `<jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/topjava"/>`.
Но там не указан класс, как в других dataSource? Получается по имени jdbc/topjava нам уже отдает готовый обьект dataSource и мы как бы помещаем его в бин?

Здесь используется namespace `jee:jndi-lookup`, который прячет под собой классы реализации. JNDI объект DataSource конфигурируется в `src/main/resources/tomcat/context.xml`

> В плагине прописан профиль `<spring.profiles.active>tomcat,datajpa</spring.profiles.active>`, а в web.xml `<param-value>postgres,datajpa</param-value>`.
Какой же реально отрабатывает?

См видео урока "Динамическое изменение профиля при запуске". В плагине мы задаем параметры JVM запуска Tomcat

> Почему мы не используем элемент `<context:annotation-config/>` в `spring-db.xml`?

В проекте у нас сейчас 2 Spring контекста: `spring-mvc.xml (см. web.xml, DispatcherServlet)` и родительский `spring-app.xml + spring-db.xml (web.xml, contextConfigLocation)`.
Грубо: 2 мапы, причем для mvc доступно все что есть в родителе. Те `spring-db.xml` не является отдельным самостоятельным контекстом и достаточно того, что `<context:annotation-config/>` у нас есть в `spring-app.xml`.

> В _ehcache.xml_ чем _<cache name="users"_ отличается от _<cache name="ru.javawebinar.topjava.model.User"_?

_user_ - это имя региона ecache, которое мы выбрали для кэширования c помощью Spring Cache `Collection<User> getAll()` в `UserServiceImpl`.
_ru.javawebinar.topjava.model.User_ - имя региона, которое использует Hibernate для кэширования содержимого таблицы _USERS_.
Мы можем оставить настройки по умолчанию, либо задать свои.

> A `@NamedQuery` или `@Query` подвержены кешу запросов? Т.е. если мы поставим _USE_QUERY_CACHE_value_="true" будет Hibernate их кешировать?

Чтобы запрос кэшировался, кроме true в конфигурации нужно еще явно выставить запросу _setCacheable_ (http://vladmihalcea.com/2015/06/08/how-does-hibernate-query-cache-work/). По поводу кэширования `@NamedQuery` нашел `@QueryHint`: https://docs.jboss.org/jbossas/docs/Clustering_Guide/5/html/ch04s02s03.html

> Почему messages мы кладем в config и используем system environment? разве так делают в реальном проекте? не будешь же вписывать на сервере эти переменные каждый раз, если проект куда-то будет переезжать. Можно по другому, кроме systemEnvironment['TOPJAVA_ROOT'] задать путь от корня проекта?

1. messages нам нужны в runtime (при работе приложения). Проект к собранному и задеплоенному в Tomcat war отношения никакого уже не имеет и на этом сервере он обычно не находится. Если ресурсы нужны только при сборке и тестировании, то путь к корню для одномодульного maven проекта можно задать как `${project.basedir}`, но для многомодульного проекта (а все реальные проекты многомодульные) это путь к корню своего модуля.
2. В "реальном приложении" делается совершенно по разному:
   - нести с собой в classpath, но ресурсы нельзя будет динамически (без передеплоя) обновлять
   - класть в war (не в classpath) и обновлять в развернутом TOMCAT_HOME/webapps/[appname]/...
   - класть в зафиксированное определенное место (нарпимер в home: `~` или в путь от корня `/app/config`). Можно задавать фиксированный пусть в пропертях профиля maven и фильтровать ресурсы (maven resources), чтобы они попали в проперти проекта.
   - делать через переменную окружения, как у нас
   - задавать в параметрах запуска JVM как системную переменную через -D..
   - располагать в преференсах (для unix это home, для windows- registry): <a href="http://java-course.ru/articles/preferences-api/">использование Preferences API</a>
   - держать настройки в DB

   Часто в одном приложении используют несколько способов для разных видов конфигураций.

> Не происходит ли дублирования при кэшировании пользователей чрез Hibernate и `@Cacheable` ?

`@Cacheable` кэширует результат запроса `getAll()`, те список юзеров. Hibernate кэширует юзеров по отдельности, те, грубо мапа, id->User. Те можно назвать это дублированием. Нужно ли будет такое в реальном приложении - все смотрится из логики запросов и их частоты, вполне вероятно что нет. Как то мы писали приложение для Дойчебанка (аналог skype на GWT, те на экране небольшое окошко)- там было 5!! уровней кэширования, первый вообще в базе.

> У меня стоит томкат 7 версии, в помнике у нас 8 прописан, но всё работает. Почему?

В `pom.xml` мы подключаем servlet-api версии 3.1 со `scope=provided`, что означает что он используется только для компиляции и не идет в war. Тк мы не используем никаких фич 3.1, то наш код совместим с Tomcat 7.x. При запуске через `cargo-maven2-plugin` Tomcat 8 загружается из maven репозитория.

> Откуда `@Transactional` вытягивает класс для работы с транзакцией, в составе какого бина он идет?

1. Если в контексте Spring есть `<tx:annotation-driven/>`, то подключается `BeanPostProcessors`, который проксирует классы (и методы), помеченные `@Transactional`.
2. По умолчанию для TransactionManager используется бин с `id=transactionManager`

---------------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW06
- 1.1 Починить тесты `InMemoryAdminRestControllerSpringTest/InMemoryAdminRestControllerTest`  (в новой версии Spring классы `spring-mvc` требуют `WebApplicationContext`, поэтому поправьте `mock.xml`)
- 1.2 Починить Jdbc тесты (валидацию исключить)
  - <a href="http://iliachemodanov.ru/ru/blog-ru/12-tools/57-junit-ignore-test-by-condition-ru">org.junit.Assume</a>
  - <a href="http://www.ekiras.com/2015/09/spring-how-to-get-current-profiles-in-spring-application.html">How to get Current Profiles in Spring Application</a>
- 1.3 Починить работу meals: перенести функциональность `MealServlet` в контроллеры (сервлеты удалить)
  - 1.3.1 разнести запросы на update/delete/.. по разным методам (попробуйте вообще без `action=`). Можно по аналогии с `RootController#setUser` принимать `HttpServletRequest request` (аннотации на параметры и адаптеры для `LocalDate\Time` мы введем позже). 
  - 1.3.2 в одном контроллере нельзя использовать другой. Чтобы не дублировать код можно сделать наследование.
  - 1.3.3 добавить локализацию и `jsp:include` в `mealForm.jsp / meals.jsp`

#### Optional
- 2.1 Добавить транзакционность (`DataSourceTransactionManager`) в Jdbc реализации  
- 2.2 Добавить еще одну роль к ADMIN (будет 2 роли: `ROLE_USER, ROLE_ADMIN`)
- 2.3 Добавить проверку ролей в UserTestData.MATCHER
- 2.4 Починить тесты в `JdbcUserRepositoryImpl` (добавить роли). 
  - 2.4.1 В реализации `getAll` НЕ делать запрос ролей для каждого юзера (N+1 select)
  - 2.4.2 При save посмотрите на <a href="https://www.mkyong.com/spring/spring-jdbctemplate-batchupdate-example/">batchUpdate()</a>

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
- 2: Если не поднимается контекст Spring, смотрим верх самого нижнего эксепшена - там написано какого бина у вас нет в контексте и нужно включить в контекст.
- 3: Если неправильно формируется url относительно контекста приложения (например `/topjava/meals/meals`), посмотрите на
  -  <a href="http://stackoverflow.com/questions/4764405/how-to-use-relative-paths-without-including-the-context-root-name">Relative paths in JSP</a>
  -  <a href="http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix">Spring redirect: prefix</a>
- 4: При проблемах с запуском томкат проверьте запущенные `java` процессы, нет ли в `TOMCAT_HOME\webapps` приложения каталога `topjava`, логи tomcat - нет ли проблем с доступом к каталогам или контекстом Spring.
- 5: Если создаете List с одним значением или Map с одним ключом-значением, пользуйтесь `Collections.singleton..`
- 6: В MealController общую часть `@RequestMapping(value = "/meals")` лучше вынести на уровень класса
- 7: Не забывайте при реализации `JdbcUserRepositoryImpl` про `Map.computeIfAbsent` и `EnumSet`
- 8: Проверьте `@Transactional(readOnly = true)` сверху `Jdbc..RepositoryImpl`
- 9: Проверьте, что `config\messages\app_ru.properties` у вас в кодировке UTF-8 (в любом редакторе/вьюере или при отключенном [Transparent native-to-ascii conversion](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%B4%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D1%83-utf-8) в IDEA). ASCII коды нужны были только для JSP.
