# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>
## <a href="https://drive.google.com/drive/folders/0B9Ye2auQ_NsFfmctT3oyNW1qaVhDb2p0bGpyTFVlaUJ2VVpOdVgtWF9KTUFBMWFaR2xVYVE">Материалы занятия</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [Обзор JDK 9/11. Миграция Topjava с 1.8 на JDK 17+](http://javaops.ru/view/resources/jdk8+)
### Выбрать можно любую версию 17+. Для JDK 17+ обновите IDEA на последнюю версию и Tomcat на 9.x
> - Проект обновил до JDK 17+. Для запуска Maven или Tomcat переопредели переменную окружения `JAVA_HOME` и переменную `path` на `JAVA_HOME\bin`, чтобы  `java -version` тоже было 17+. Напомню, что IDEA это java процесс. Чтобы новые переменные окружения в ней увиделись, требуется ее перегрузить.

- [API, ради которых наконец-то стоит обновиться с Java 8 (1)](https://habr.com/ru/post/485750)
- [API, ради которых наконец-то стоит обновиться с Java 8 (2)](https://habr.com/ru/post/487636)
- [Руководство по возможностям Java версий 8-19](https://habr.com/ru/post/719744/)

#### Apply 5_1_jdk_17.patch
- Сделал создание коллекций через фабричные методы `List.of`
- Как пример в `InMemoryMealRepository` использовал *local variable type inference* `var`.
  - [26 рекомендаций по использованию типа var в Java](https://habr.com/ru/post/438206/)
- `switch` в `MealServlet` перевел на новый формат (IDEA сама переводит по Alt+Enter). 
  - [Новые switch выражения](https://habr.com/ru/post/443464/)
- В `JdbcUserRepository.save` использовал [Text Blocks](https://www.infoq.com/articles/java-text-blocks/)
- В JDK 17 вместо `.collect(Collectors.toList())` можно использовать `.toList()`. Посмотрите его реализацию.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW4

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [HW4: Meal / JpaMealRepository](https://drive.google.com/file/d/13JJRhLhkn8_C3-xpkyilRxGe_w9_xTF2)
#### Apply 5_2_HW4.patch
 - При сравнении еды тесты падают, т.к. Hibernate делает ленивую обертку к `user`, и если происходит обращение к любому его полю (кроме id) вне транзакции, бросается `LazyInitializationException`.
По логике приложения поле `user` в еде не нужно, и мы не будем его отдавать наружу UI. Более того, включать `user` в запрос будет ошибкой: мы запрашиваем данные, которые приложению не требуются.
В тестах исключаем `user` из сравнения.  
 - [SQL “between” not inclusive](https://stackoverflow.com/questions/16347649/sql-between-not-inclusive/16347680)   

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [Hibernate issue / HW4 Optional](https://drive.google.com/file/d/1kbb2IO15L9ABJ0-2TFJm8XZU_QUXUdni)

#### Apply 5_3_fix_hibernate_issue.patch
 - Из-за [Hibernate bug with proxy initialization when using `AccessType.FIELD`](https://hibernate.atlassian.net/browse/HHH-3718)
в `JpaMealRepository.get()` делался дополнительный запрос в базу для инициализации прокси `User`, и мы делали хак: доступ к полю `AbstractBaseEntity.id` через `AccessType.PROPERTY`.
С версии `5.2.13.Final` загрузка прокси при обращении к `id` управляется флагом `JPA_PROXY_COMPLIANCE` (по умолчанию запрос не делается)
   - [Call to id getter initializes proxy when using AccessType( "field" ): HHH-3718](https://hibernate.atlassian.net/browse/HHH-3718)
   - [According to JPA, a Proxy should be loaded even when accessing the identifier: HHH-12034](https://hibernate.atlassian.net/browse/HHH-12034)
 - <a href="http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access">Which is better, field or property access?</a>
 - Поправил `equals()` с учетом Lazy-проксирования
   - <a href="http://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma">JPA hashCode()/equals() dilemma</a>
   - <a href="https://xebia.com/advanced-hibernate-proxy-pitfalls">Hibernate Proxy Pitfalls</a>

------------------------
> Переопределять `equals()/hashCode()` необходимо, если
> - использовать entity в `Set` (рекомендовано для Many-ассоциаций) либо как ключи в `HashMap`
> - использовать _reattachment of detached instances_ (т.е. манипулировать одним Entity в нескольких транзакциях/сессиях).

> [Implementing equals() and hashCode()](https://docs.jboss.org/hibernate/stable/core.old/reference/en/html/persistent-classes-equalshashcode.html)

> Оптимально использовать уникальные бизнес-поля, но обычно таких нет, и чаще всего используются PK с ограничением, что он может быть `null` у новых объектов, и нельзя объекты сравнивать через `equals() and hashCode()` в бизнес-логике (например, тестах).

> [equals() and hashcode() when using JPA and Hibernate](https://stackoverflow.com/questions/1638723)

------------------------

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Почему над `AbstractBaseEntity` стоит `@Access(AccessType.FIELD)` ? Почему при запросе `user.id` нам не нужно вытаскивать его из базы?

`AccessType.FIELD` делает доступ в `AbstractBaseEntity` и всех классах-наследниках по полям. При загрузке `Meal` Hibernate на основе поля `meal.user_id` делает ленивую прокси к `User`, у которой нет ничего, кроме id.

#### Apply 5_4_HW4_optional.patch
- <a href="http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev#27868954">Stopwatch</a>
- [Logback layouts coloring](https://logback.qos.ch/manual/layouts.html#coloring)
- Дополнительно: [use colored output only when logging to a real terminal](https://stackoverflow.com/questions/31046748)

## Занятие 5:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZENCVEhDMkZiV00">Транзакции</a>
-  <a href="https://ru.wikipedia.org/wiki/Транзакция_(информатика)">wiki Транзакция</a>
-  <a href="https://jira.spring.io/browse/DATAJPA-601">readOnly и Propagation.SUPPORTS</a>
-  [@Transactional In-Depth](https://www.marcobehler.com/guides/spring-transaction-management-transactional-in-depth)
- Ресурсы:
  - [Транзакции в Spring Framework](https://medium.com/@kirill.sereda/%D1%82%D1%80%D0%B0%D0%BD%D0%B7%D0%B0%D0%BA%D1%86%D0%B8%D0%B8-%D0%B2-spring-framework-a7ec509df6d2)
  - <a href="https://dzone.com/articles/how-does-spring-transactional">How does Spring @Transactional Really Work</a>
  - <a href="http://web.archive.org/web/20170314073834/https://www.ibm.com/developerworks/ru/library/j-ts1/index.html">Стратегии работы с транзакциями: распространенные ошибки</a>
  - <a href="http://stackoverflow.com/questions/8490852/spring-transactional-isolation-propagation">Spring @Transactional - isolation, propagation</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNW0yVWhXcGNPU2M">Профили Maven и Spring</a>
#### Apply 5_5_profiles_connection_pool.patch
> - `SLF4JBridgeHandler` перенес в профиль `postgres` (если логировать драйвер не нужно, то и он не нужен) 
> - **Галочка в XML-профиле влияет только на отображение в IDEA и никак не влияет на выполнение кода.**
> - `Profiles.ACTIVE_DB` задает активный профиль базы (postgres/hsqldb)
> - `Profiles.REPOSITORY_IMPLEMENTATION` определяет реализацию репозитория при запуске приложения (для тестов задаются через `@ActiveProfiles`).

> Для переключения на HSQLDB необходимо:
>  - поменять в окне Maven Projects профиль (Profiles) - выключить `postgres`, включить `hsqldb` -  и сделать `Reimport All Maven Projects` (1-я кнопка)
>  - поменять `Profiles.ACTIVE_DB = HSQLDB`
>  - почистить проект `mvn clean` (фаза `clean` не выполняется автоматически, чтобы каждый раз не перекомпилировать весь проект)

Для корректного отображения неактивного профиля в IDEA проверьте флаг _Inactive profile highlighting_ и сделайте проекту clean
 
![image](https://cloud.githubusercontent.com/assets/13649199/25120020/29935958-2425-11e7-8363-1ff027426f64.png)

> Вопрос: почему после этого патча не поднимается Spring при запуске приложения в Tomcat? (будем чинить в ДЗ, п.6)
 
- <a href="https://dzone.com/articles/using-spring-profiles-xml">Using Spring Profiles in XML Config</a>
- <a href="https://www.mkyong.com/spring/spring-profiles-example/">Spring Profiles example</a>

### Автоматический выбор профиля базы: [`ActiveProfilesResolver`](http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver)
#### Apply 5_6_profile_resolver.patch
> Сделал автоматический выбор профиля базы при запуске приложения (тестов) в зависимости от присутствия драйвера базы в classpath (`ActiveDbProfileResolver`)    
> В профили аттрибута `@ActiveProfiles(profiles=..)` добавляем `Profiles.getActiveDbProfile()`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFTWJOdHduOWtNcTA">Пул коннектов</a>
![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) [Александр Колесников - JDBC Pools Battle](https://www.youtube.com/watch?v=J9GzE2qlNuM&feature=youtu.be&t=2895) (ссылка на выводы)

>  [BoneCP to be deprecated ](https://stackoverflow.com/a/1662916/548473)
-  Выбор реализации пула коннектов: <a href="https://commons.apache.org/proper/commons-dbcp/">Commons Database Connection Pooling</a>, <a href="https://github.com/brettwooldridge/HikariCP">HikariCP</a>
-  <a href="https://habrahabr.ru/post/269023/">Самый быстрый пул соединений на java (читаем комменты)</a>
-  <a href="http://blog.ippon.fr/2013/03/13/improving-the-performance-of-the-spring-petclinic-sample-application-part-3-of-5">Tomcat pool</a>


### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYVdyMFYxRUR6bWM">Spring Data JPA</a>

#### Apply 5_7_spring_data_jpa.patch
> - Переименовал классы _Proxy_ на более адекватные _Crud_, убрал _Impl_
> - В `spring-framework-bom` мы уже задали версию Spring. Убрал из остальных зависимостей.
> - В spring-data-jpa 2.x поменялся интерфейс: `T CrudRepository.findOne(ID id)` -> `Optional<T> CrudRepository findById(ID id)`
>   - [Java Optional — Отец холиваров](http://sboychenko.ru/java-optional)
>   - [Optonal от Oracle](https://stuartmarks.files.wordpress.com/2016/09/optionalmotherofallbikesheds3.pdf)
>   - [Java 8 Optional In Depth](https://www.mkyong.com/java8/java-8-optional-in-depth/)
> - Не стал переопределять в `CrudUserRepository` методы `JpaRepository` (для явного указания всех используемых методов). Обычно этого не делают.

### Внимание: при обновлении версий не забудьте обновить зависимости Maven и сделать `clean`.

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
   -  [Spring Data JPA with QueryDSL](https://dontpanic.42.nl/2011/06/spring-data-jpa-with-querydsl.html)
   -  [SpEL support in Spring Data JPA @Query](https://spring.io/blog/2014/07/15/spel-support-in-spring-data-jpa-query-definitions)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> Какой паттерн проектирования применён в классе DataJpaUserRepository (декоратор/адаптер/прокси/другой)?:

Вопрос интересный:) Ближе всего к адаптеру, но по логике композиция с делегированием. Мы просто используем для нашей реализации возможности `data-jpa: CrudUserRepository`.
Делегат интерфейсов не меняет, а прокси похож на делегата, но служит для неявной подмены (часто прямо в рантайм). См. [ПАТТЕРНЫ
ПРОЕКТИРОВАНИЯ](https://refactoring.guru/ru/design-patterns)

> В <a href="https://github.com/spring-projects/spring-petclinic/tree/master/src/main/java/org/springframework/samples/petclinic">spring-petclinic</a> `DataJpa` реализована без дополнительных классов. В таком виде как у них, spring data смотрится, конечно, намного лаконичней других реализаций, но у нас получилось  вдвое больше кода, чем с тем же jpa или jdbc. Плюс только пожалуй в том, что query находятся прямо в репозитории, а  не где-то там в другом пакете. Так что получается, spring data лучше подходит для простейших crud без всяких "фишек"? или в чем его достоинство для больших и сложных проектов?

Достоинства DATA-JPA по сравнению, например, с JPA: есть типизация, готовые реализации типовых методов CRUD, а также paging, data-common. Мы можем переключить реализацию JPA, например, на mongoDb (`PagingAndSortingRepository`, от которого наследуется `JpaRepository`, находится в `spring-data-common`).
Соответственно, его методы будут поддерживаться всеми реализациями `spring-data-common` (JPA - одна из них) и пр. Подробнее о них есть в видео <a href="http://jeeconf.com/archive/jeeconf-2013/materials/spring-data/">Spring Data – новый взгляд на persistence</a>.
Дополнительное проксирование в DATA-JPA - моя "фишка" для устранения минусов этого фреймворка: невозможность дебага, привязка к интерфейсу JpaRepository, перенос логики Repository в слой сервисов.
Для большого приложения выигрыш этого стоит. Для небольших (тестовых) приложений (например выпускного) дополнительных классов делать не нужно.

> Почему мы для InMemory не сделали отдельного профиля? Почему их не удалить вообще?

Реализация InMemory является примером, как в test делать подмену контекста. Для них сделали отдельный `inmemory.xml`, и запускаемый проект ничего не должен о них знать. У нас учебный проект, в котором 4 реализации репозиториев, в реальном такого не будет.

> А как делать транзакционность для реализации jdbc?

Будем делать на следующем уроке.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Spring кэш](https://drive.google.com/file/d/1xpuL2YscL1ounS_qFRb1qzLciEOKn9I4/view?usp=sharing)
#### Apply 5_8_spring_cache.patch
- [Wiki Кэш](https://ru.wikipedia.org/wiki/Кэш)
  - [Spring Cache Abstraction](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache)
  - [ECache](https://www.ehcache.org)
  - [Configuring the Cache Storage](https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache-store-configuration)
- [Spring 3.1 новый механизм кеширования](https://russianblogs.com/article/75981527090/)
- [Spring 4+ with Ehcache 3 – how to](https://imhoratiu.wordpress.com/2017/01/26/spring-4-with-ehcache-3-how-to/)
- [Evict Ehcache elements programmatically, using Spring](https://stackoverflow.com/questions/29557959/evict-ehcache-elements-programmatically-using-spring)
- Дополнительно: [Оптимизация запросов с использованием Spring cache и Bean scope](https://www.youtube.com/watch?v=P7nCQepVk_Y&list=PL6rimDLSyfe947bGsCviqIIYGofeFYQTM)
--------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW05

- 1: Имплементировать `DataJpaMealRepository` и протестировать через `MealServiceTest`. 
#### MealServlet не работает (почему?), чиним в _Optional п.5_
- 2: Разделить реализации Repository по профилям Spring: `jdbc`, `jpa`, `datajpa` (общее в профилях можно объединять, например, `<beans profile="datajpa,jpa">`).
  - 2.1: Профили выбора DB (`postgres/hsqldb`) и реализации репозитория (`jdbc/datajpa/jpa`) независимы друг от друга, и при запуске приложения (тестов) нужно задать тот, и другой.
  - 2.2: Удобно для интеграции с IDEA выставить в `spring-db.xml` справа вверху в `Change Profiles...` профили, например, `datajpa, postgres`. **Это влияет ТОЛЬКО на отображение в IDEA и НИКАК на работу приложения и тестов**
  - 2.3: Общие части для всех в `spring-db.xml` можно оставить как есть без профилей вверху файла **(до первого `<beans profile=` !!!)**.   
Профили делаем в `spring-db.xml` (это не Maven профили, pom.xml не меняем). Здесь мы конфигурируем бины, которые попадут в spring context. Профилями можно переключать, что попадет в context, что нет. Профилями Maven мы меняем в проекте подгружаемые зависимости. Если у вас в `spring-db.xml` что-то красное - значит этих классов нет в зависимостях, это НЕ ошибка. Spring о профилях в Maven НИКАК не догадывается: в IDEA нужно вручную наверху нужные профили задавать, в приложении и тестах - указывать, с какими профилями запускается приложение.
- 3: Сделать тесты всех реализаций (`jdbc, jpa, datajpa`) через наследование (без дублирования).
  -  3.1 **сделать общий базовый класс для `MealServiceTest` и `UserServiceTest`**.
  -  3.2 сводку по времени выполнения тестов также сделать для `user`
- 4: Проверить запуск всех тестов: `mvn test` (в IDEA Maven Lifecycle - `test`, кнопку `skipTest` отжать).
#### `Jdbc` реализация не работает с `hsqldb`. Чиним в _Optional п.6_

### Optional

- 5: Починить `MealServlet` и использовать в `SpringMain` реализацию DB: добавить профили. Попробуйте поднять Spring контекст без использования `spring.profiles.active`.
- 6: Разделить `JdbcMealRepository` для HSQLDB (она не умеет работать с Java8 Time API) и Postgres через `@Profile` (для Postgres оставить `LocalDateTime`). 
  - Цель задания - потренироваться с [паттерном "шаблонный метод"](https://refactoring.guru/ru/design-patterns/template-method) и профилями Spring. 
Какие бины Spring попадут в контекст зависит от выставления активных профилей при запуске (`@ActiveProfiles` в тестах) и конфигурации, где задаются бины для каждого профиля.
Абстрактные классы не создаются и в контекст не попадают. 
  - После выполнения разделения на основе профилей, можно предложить решение проще.
- 7: Сделать и протестировать в сервисах методы (тесты и реализация - только для `DataJpa`):
  - 7.1:  достать по `id` пользователя вместе с его едой
  - 7.2:  достать по `id` и `userId`  еду вместе с пользователем
  - 7.3:  обращения к DB сделать в одной транзакции (можно сделать разные варианты). <a href="https://en.wikibooks.org/wiki/Java_Persistence/OneToMany">Java Persistence/OneToMany</a>

---------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации
- 1: Для того, чтобы не запускались родительские классы тестов, нужно сделать их `abstract`.
- 2: В реализациях `JdbcMealRepository` **код не должен дублироваться**. Если вы возвращаете тип `Object`, посмотрите в сторону <a href="https://web.archive.org/web/20201027090144/http://www.quizful.net/post/java-generics-tutorial">дженериков</a>.
- 3: В `MealServlet/SpringMain` в момент `setActiveProfiles` контекст спринга еще не должен быть инициализирован, иначе выставление профиля уже ни на что не повлияет.
Уметь пользоваться гугл для разработчика, это умение №1. Если застряли- попробуйте например слова: `spring context set profile`
- 4: Если у метода нет реализации, то стандартно бросается `UnsupportedOperationException`. Для уменьшения количества кода при реализации _Optional_ (п. 7, только `DataJpa`) попробуйте сделать `default` метод в интерфейсе.
- 5: В Data-Jpa метод для ссылки на entity (аналог `em.getReference`) - `T getReferenceById(ID id)`  
- 6: Проверьте, что в `DataJpaMealRepository` все обращения к DB для одной операции выполняются в **одной транзакции**.  
(`<logger name="org.springframework.orm.jpa" level="debug"/>` для логирования информации по транзакциям)
- 7: Для 7.1 `достать по id пользователя вместе с его едой` - в `User` добавить `List<Meal> meals`. Учесть, что у юзера может отсутствовать еда. [Ordering a join fetched collection in JPA using JPQL/HQL](http://stackoverflow.com/questions/5903774/ordering-a-join-fetched-collection-in-jpa-using-jpql-hql)
- 8: Проверьте, что все тесты запускаются из Maven (имена классов тестов удовлетворяют соглашению) и итоги тестов класса выводятся корректно (не копятся). По умолчанию [maven-surefire-plugin](http://maven.apache.org/surefire/maven-surefire-plugin/examples/inclusion-exclusion.html) включает в тесты классы, заканчивающиеся на Test.
- 9: В тестах можно вынести общие `@ActiveProfiles` наверх, в разных классах они суммируются
- 10: `<beans profile=` в конфигурации контекста должны находиться **после** всех остальных объявлений.
