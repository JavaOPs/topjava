# Стажировка <a href="https://github.com/JavaOps/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfjVnUVhINEg0d09Nb3JsY2ZZZmpsSWp3bzdHMkpKMmtPTlpjckxyVzg0SWc">Материалы занятия</a>

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 7_0_ignore_test.patch

- Добавил `@Ignore` в тесты, которые не проходят

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW6

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=1luHTJOXQW-BWyueqsfzzAeiP8lUTZkje">HW6</a>

#### Apply 7_01_HW6_fix_tests.patch

<details>
  <summary><b>Краткое содержание</b></summary>

#### Починить InMemory и JDBC тесты

InMemory-тесты перестали работать, т.к ранее мы перенесли сканирование каталога `web` из `spring-app.xml` в конфигурацию `spring-mvc.xml`, которой нет в тестах. В результате контроллеры перестали
попадать в спринг-контекст тестов. Для восстановления добавим сканирование каталога `web` в конфигурацию `inmemory.xml`. Теперь в классах, которые работают с InMemory-реализацией, для создания
контекста можно оставить импорт только конфигурации
`spring/inmemory.xml`.

JDBC-тесты перестали работать, т.к в конфигурации `spring-db.xml` мы объявили бин `JpaUtil` только для профилей jpa и dataJpa, для других профилей (jdbc) этот бин создаваться не будет.  
JDBC-тесты мы запускаем с профилем jdbc, но в абстрактном классе AbstractUserServiceTest (общем для всех тестов сервисного слоя User) для всех профилей мы указали необходимость создания переменной
типа `JpaUtil`. Соответственно, для профиля jdbc в контексте спринга будет отсутствовать этот бин, и спринг не сможет запустить приложение из-за неразрешенной зависимости.

Чтобы спринг смог поднять контекст в профиле JDBC, нужно указать над переменной `jpaUtil`
аннотацию `@Autowired(required = false)` - мы указываем спрингу, что эта зависимость не является обязательной и можно ее проигнорировать.

> В новой версии заменил аннотацию на ленивую инициализацию `@Lazy`

И в `@Before` методе тестов используем этот бин только для JPA реализаций.  
Для этого создадим утильный метод `isJpaBased()`, который будет проверять, относится ли текущая реализация к jpa. Чтобы проверить, с какими профилями запущен Spring, нам придется внедрить
в `AbstractServiceTest`
бин класса `Environment`. Это класс спринга, который позволит получить доступ к информации о том, с какими параметрами он был запущен, с помощью
```env.acceptsProfiles(org.springframework.core.env.Profiles.of(Profiles.JPA, Profiles.DATAJPA))```
С помощью этого же утильного метода теперь мы можем проверить, что для `MealServiceTest` тесты на валидацию `validateRootCause()` будут выполняться только для jpa/dataJpa профилей (если этот тест
запустить для профиля jdbc, то он упадет, т.к. пока в JDBC у нас нет валидации).

#### Локализация, jsp:include для meal*.jsp

1. В файлы интернационализации `app.properties` добавляем дополнительные пары ключ-значение для русского и английского языка. В JSP страницах вместо текста, по аналогии со страницами для User,
   указываем ключи, вместо которых спринг должен подставить локализованные сообщения.
2. Для каждой JSP страницы для включения фрагментов указываем теги:

`<jsp:include page="fragments/headTag.jsp"/>` - в нем определены title страницы, ссылка на статические ресурсы и базовая ссылка на корень приложения.

`<jsp:include page="fragments/bodyHeader.jsp"/>` - верхняя часть страниц, в ней определены ссылки для навигации по приложению.  
И в самом низу страниц:  
`<jsp:include page="fragments/footer.jsp"/>`

Так как мы локализуем приложение с помощью Spring, на страницах нужно удалить тег:
`<fmt:setBundle basename="messages.app"/>` - с ним работает только jstl.

3. Для того, чтобы на страницах получить доступ к корню приложения, используется
   `"${pageRequest.request.contextPath}"` - эту ссылку на root удобнее вынести в `headTag` в виде [`<base href>` элемента](https://stackoverflow.com/a/40228804/548473), чтобы она вместе с этим
   фрагментом добавлялась к каждой странице, и не требовалось бы ее везде дублировать.

4. Чтобы видеть, к каким URL были привязаны контроллеры во время работы приложения, в `logback.xml` настроим уровень логирования для Spring web:  
   `<logger name="org.springframework.web" level="DEBUG"/>`

#### Перенести функциональность из `MealServlet` в контроллеры

Чтобы не дублировать одну и ту же функциональность для REST- и JSP-контроллеров, создадим абстрактный
`AbstractMealController` (от него будут наследоваться остальные Meal-контроллеры), куда перенесем все методы из
`MealRestController`. JSP-контроллер будет работать с jsp-страницами. Каждый метод этого контроллера будет делегировать основную функциональность в родительский абстрактный контроллер.

> **Внимание!**. Не делайте без нужды абстрактных контроллеров в своих выпускных проектах!

Так как каждый метод этого контроллера должен отвечать за единственное действие, разнесем функциональность по разным методам, а доступ к самим методам разделим с помощью
аннотации `@RequestMapping (@GetMapping / @PostMapping)`, в их параметрах укажем путь к endpoint, по которому можно обратиться к методу.

При этом для всего контроллера также зададим `@RequestMapping("/meals")` (`value=` - параметр по умолчанию, можно не указывать). Это префикс запроса для всех методов контроллера.

> Один из признаков "хорошего" контроллера, где не смешивается разная функциональность, - этот общий url. Для каждой функциональности в выпускных создавайте свой собственный контроллер!

Для доступа к определенному методу контроллера нужно будет указать уникальный для нашего приложения "путь + http-метод", который складывается из маппинга к контроллеру, маппинга к нужному методу и
http-метода, например:  
`GET {корень приложения} + "/meals" + "/delete"`
`GET {корень приложения} + "/meals"`
`POST {корень приложения} + "/meals"`
Для `mealList.jsp` теперь не нужно с запросом дополнительно передавать тип действия, которое мы хотим совершить с едой, мы можем просто обратиться к нужному методу по его уникальному пути (endpoint,
url).

Если на этом шаге запустить приложение, то мы столкнемся с проблемой: при выполнении манипуляций и переходе по ссылкам путь портится.

- путь к ресурсу по этой ссылке строится не от корня приложения (application context - topjava), а от текущего контекста сервлета (servlet context), например:  
  `localhost:8080/topjava/meals'+'/meals`
  Также перестали работать стили, так как путь к статическим ресурсам тоже определяется неверно (посмотрите вкладку *Network* браузера).   
  Чтобы это исправить, добавим базовый URL в `headTag`:  
  `base href = "${pageContext.request.contextPath}/"`. **Теперь это станет url, от которой будут строиться все относительные ссылки на страницах**.

Также некоторые методы контроллера в результате работы должны не просто вернуть название view, который Spring MVC должен отобразить, а совершить *redirect*. Для этого при возврате имени view
дополнительно укажем ключевое слово `redirect:`, например, `redirect: /meals`.

Последняя проблема — некорректное отображение текста в кодировке UTF-8. Spring предоставляет для ее решения стандартный фильтр, который будет перехватывать все запросы и ответы сервера и устанавливать
им нужную кодировку: в `web.xml` подключим `encodingFilter`.

</details>

> Инжекцию в `AbstractUserServiceTest.jpaUtil` сделал [`@Lazy`: не иннициализировать бин до первого использования](https://www.logicbig.com/tutorials/spring-framework/spring-core/lazy-at-injection-point.html).

#### Apply 7_02_HW6_meals.patch

> сделал фильтрацию еды через `get`: операция идемпотентная, можно делать в браузере обновление по F5

### Внимание: чиним пути в следующем патче

При переходе на AJAX `JspMealController` удалим за ненадобностью, возвращение всей еды `meals()` останется в `RootController`.

#### Apply 7_03_HW6_fix_relative_url_utf8.patch

- [Relative paths in JSP](http://stackoverflow.com/questions/4764405/548473)
- [Spring redirect: prefix](http://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-redirecting-redirect-prefix)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFaXViWkkwYkF0eW8">HW6 Optional</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Добавление еще одной роли для Admin

1. В файле популирования базы данных `populateDB.sql` добавим для admin дополнительную роль `ROLE_USER`.

2. В тестовых данных для него также добавим аналогичную роль.

> После этого тесты, которые связаны с методом `getAll()`, перестали работать, потому что для получения
> списка всех пользователей с их ролями в именованном запросе мы использовали **LEFT JOIN FETCH**.  
> Происходит объединение таблиц, в результирующей таблице вместо одной записи для админа появляются дублирующие записи для одного и того же пользователя.
> - простой способ решения - исключить из запроса **LEFT JOIN FETCH**. Роли все равно будут загружены, так как они FetchType.EAGER.
> - также можно добавить в запрос ключевое слово **DISTINCT(u)** - теперь в результирующей таблице будут содержаться только уникальные записи.

#### Добавление транзакционности в JDBC реализацию репозитория

Чтобы аннотация `@Transactional` стала работать во всех профилях Spring - в файле `spring-db.xml` вынесем из профиля jpa, dataJpa в общую конфигурацию для всех профилей тег:
```<tx:annotation-driven/>```

Для профиля jdbc настроим DatasourceTransactionManager, который будет управлять транзакциями:  
``
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<property name="dataSource" ref="dataSource" />
</bean>
``
После этого в JDBC-репозитории мы можем расставить аннотации `@Transactional` аналогично jpa репозиториям, и действия станут выполняться транзакционно (
напомню: `<logger name="org.springframework.orm.jpa" level="debug"/>` для логирования информации по транзакциям)

#### Чтобы JDBC репозиторий смог работать с множественными ролями пользователя:

У пользователя добавим сеттер для его ролей. Для JDBC-репозитория создадим вспомогательные методы для записи ролей в базу и их считывания из базы и установления пользователю. Запись ролей в базу будем
производить методом
`JdbcTemplate#batchUpdate`, в таком случае не будет обращения в базу для записи каждой конкретной роли, команды для записи ролей будут накоплены в один пакет и выполнятся за одно обращение к БД. Для
удобства работы с batch Spring предоставляет нам интерфейс `BatchPreparedStatementSetter`, с помощью которого мы определяем как будут устанавливаться параметры для запроса и количество запросов в
одном пакете. Также создадим метод `deleteRoles`, в котором будем удалять роли пользователя из базы (для обновления ролей в базе мы делаем просто: сначала удалим старые из базы и запишем туда новые).

> PS: в JPA с `@ElementCollection` и с параметром *cascade* в `@OneToMany` слияние (merge) изменений в связанных коллекциях происходит автоматически.

Если мы будем получать всех пользователей вместе с их ролями из базы с помощью JOIN, мы столкнемся с проблемой Декартова произведения: для каждого уникального пользователя количество записей в
результирующей таблице будет повторяться столько раз, сколько у него было ролей. Чтобы этого избежать, отдельным запросом получим из базы все роли, и сгруппируем их в `Map` по `userId`, где ключом
будет являться `userId`, а значением — набор ролей пользователя. После чего пройдемся по всем пользователям, загруженным из базы, и установим каждому его роли.
</details>

#### Apply 7_04_HW6_optional_add_role.patch

> - Для доставания ролей у нас дублируется `fetch = EAGER` и `LEFT JOIN FETCH u.roles` (можно делать что-то одно). Запросы выполняются по-разному: проверьте.

- Отключил `JdbcUserServiceTest` - роли не работают. Будем чинить в `7_06_HW6_jdbc_transaction_roles.patch`
- `DataJpaUserServiceTest.getWithMeals` не работает для admin (у админа 2 роли, и еда при JOIN дублируется). Чиним в следующем патче.

#### Apply 7_05_HW6_fix_hint_graph.patch

- `@EntityGraph` в `DataJpaUserServiceTest.getWithMeals()` в последнем Hibernate работает только с `attributePaths = {"meals"}` и `type = EntityGraph.EntityGraphType.LOAD`
- В `JpaUserRepositoryImpl.getByEmail` и `CrudUserRepository.getByEmail` DISTINCT попадает в запрос, хотя он там не нужен. Это просто указание Hibernate не дублировать данные. Для оптимизации можно
  указать Hibernate делать запрос без distinct: [15.16.2. Using DISTINCT with entity queries](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#hql-distinct)
- Бага [HINT_PASS_DISTINCT_THROUGH does not work if 'hibernate.use_sql_comments=true'](https://hibernate.atlassian.net/browse/HHH-13280). При `hibernate.use_sql_comments=false` все работает - в SELECT
  нет DISTINCT.

Еще один вариант решения - в `User` сделать `Set<Meals>`. Интересно, что в ее реализации `PersistentSet`порядок соблюдается и `@OrderBy` работает.

#### Apply 7_06_HW6_jdbc_transaction_roles.patch

> - в `JdbcUserRepositoryImpl.getAll()` собираю роли из `ResultSet` напрямую в `map`
> - в `insertRoles` поменял метод `batchUpdate` и сделал проверку на empty
> - в `setRoles` достаю роли через `queryForList`

Еще интересные JDBC реализации:

- в `getAll()/ get()/ getByEmail()` делать запросы с `LEFT JOIN` и сделать реализацию `ResultSetExtractor`
- подключить зависимость `spring-data-jdbc-core`. Там есть готовый `OneToManyResultSetExtractor`. Можно посмотреть, как он реализован.
- реализация, зависимая от БД: доставать агрегированные роли и делать им `split(",")`. В этой реализации есть ограничение - одно поле из зависимой таблицы.

```
SELECT u.*, string_agg(r.role, ',') AS roles
FROM users u
  JOIN user_roles r ON u.id=r.user_id
GROUP BY u.id
```

### Валидация для `JdbcUserRepository` через Bean Validation API

#### Apply 7_07_HW6_optional_jdbc_validation.patch

- [Валидация данных при помощи Bean Validation API](https://alexkosarev.name/2018/07/30/bean-validation-api/).

На данный момент у нас реализована валидация сущностей только для jpa- и dataJpa-репозиториев. При работе через JDBC-репозиторий может произойти попытка записи в БД некорректных данных, что приведет
к `SQLException` из-за нарушения ограничений, наложенных на столбцы базы данных. Для того, чтобы перехватить невалидные данные еще до обращения в базу, воспользуемся API *javax.validation* (ее
реализация `hibernate-validator` используется для проверки данных в Hibernate и будет использоваться в Spring Validation, которую подключим позже). В `ValidationUtil` создадим один потокобезопасный
валидатор, который можно переиспользовать (см. *javadoc*).  
С его помощью в методах сохранения и обновления сущности в jdbc-репозиториях мы можем производить валидацию этой сущности: `ValidationUtil.validate(object);`
Чтобы проверка не падала, `@NotNull Meal.user` пришлось пока закомментировать. Починим в 10-м занятии через `@JsonView`.

### Отключение кэша в тестах:

Вместо наших приседаний с `JpaUtil` и проверкой профилей мы можем отключить Spring-кэш в тестах, подменив `spring-cache.xml` на тестовый (положив его в ресурсы тестов).   
Отключить кэширование можно через пустую реализацию `NoOpCacheManager` или, как сейчас, не включая `cache:annotation-driven`, который подключает обработку `@Cache`-аннотаций.   
Кэш Hibernate второго уровня отключаем через переопределение свойства `entityManagerFactory.jpaPropertyMap: hibernate.cache.use_second_level_cache=false` (кроме стандартного использования файла
пропертей, можно задать их прямо в конфигурации, через автодополнение в xml можно смотреть все варианты). Подкладываем новый `spring-cache.xml` в ресурсы тестов, он перекроет настройки кэша в
приложении. Остается удалить наши уже ненужные `JpaUtil` и `AbstractServiceTest.isJpaBased()`

#### Apply 7_08_HW06_optional2_disable_tests_cache.patch

- [Example of PropertyOverrideConfigurer](https://www.concretepage.com/spring/example_propertyoverrideconfigurer_spring)
- [Spring util schema](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#xsd-schemas-util)

## Занятие 7:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQXhBN1pqa3FyOUE">Тестирование Spring MVC</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Тестирование Spring MVC

Для более удобного сравнения объектов в тестах мы будем использовать библиотеку *Harmcrest* с Matcher'ами, которая позволяет делать сложные проверки. С *Junit* по умолчанию подтягивается *Harmcrest
core*, но нам потребуется расширенная версия:
в `pom.xml` из зависимости Junit исключим дочернюю `hamcrest-core` и добавим  `hamcrest-all`.

Для тестирования web создадим вспомогательный класс `AbstractControllerTest`, от которого будут наследоваться все тесты контроллеров. Его особенностью будет наличие `MockMvc` - эмуляции Spring MVC для
тестирования web-компонентов. Инициализируем ее в методе, отмеченном `@PostConstruct`:

 ```
mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilter(CHARACTER_ENCODING_FILTER).build();
 ```

Для того, чтобы в тестах контроллеров не популировать базу перед каждым тестом, пометим этот базовый тестовый класс аннотацией `@Transactional`. Теперь каждый тестовый метод будет выполняться в
транзакции, которая будет откатываться после окончания метода и возвращать базу данных в исходное состояние. Однако теперь в работе тестов могут возникнуть нюансы, связанные с пропагацией транзакций:
все транзакции репозиториев станут вложенными во внешнюю транзакцию теста. При этом, например, кэш первого уровня станет работать не так, как ожидается. Т.е при таком подходе нужно быть готовыми к
ошибкам: мы их увидим и поборем в тестах на обработку ошибок на последних занятиях TopJava.

#### UserControllerTest

Создадим тестовый класс для контроллера юзеров, он должен наследоваться от `AbstractControllerTest`. В `MockMvc`
используется [паттерн проектирования Builder](https://refactoring.guru/ru/design-patterns/builder).

 ```
    mockMvc.perform(get("/users"))        // выполнить HTTP метод GET к "/users"
        .andDo(print())                   // распечатать содержимое ответа
        .andExpect(status().isOk())       // от контроллера ожидается ответ со статусом HTTP 200(ok)
        .andExpect(view().name("users"))  // контроллер должен вернуть view с именем "users"
        .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp")) // ожидается, что клиент должен быть перенаправлен на "/WEB-INF/jsp/users.jsp"
        .andExpect(model().attribute("users", hasSize(2))) // в модели должен быть атрибут "users" размером = 2
        .andExpect(model().attribute("users", hasItem(     // внутри которого есть элемент ...
        allOf(                                             
        hasProperty("id", is(START_SEQ)),                  // ...  с аттрибутом id = START_SEQ
        hasProperty("name", is(USER.getName()))            //...   и name = user
    )
   )));
}
 ```

В параметры метода `andExpect()` передается реализация `ResultMatcher`, в которой мы определяем как должен быть обработан ответ контроллера.

</details>

#### Apply 7_09_controller_test.patch

> - в `MockMvc` добавился `CharacterEncodingFilter`
> - поменял реализацию `ActiveDbProfileResolver`: в профили аттрибута `@ActiveProfiles(profiles=..)` он добавляет `Profiles.getActiveDbProfile()`
> - сделал вспомогательный метод `AbstractControllerTest.perform()`

- <a href="https://github.com/hamcrest/hamcrest-junit">Hamcrest</a>
- <a href="http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/">Unit Testing of Spring MVC Controllers</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  4. [Миграция на JUnit 5](https://drive.google.com/open?id=16wi0AJLelso-dPuDj6xaGL7yJPmiO71e)

<details>
  <summary><b>Краткое содержание</b></summary>

Для миграции на 5-ю версию JUnit в файле `pom.xml` поменяем зависимость `junit`
на `junit-jupiter-engine` ([No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)). Актуальную
версию всегда можно посмотреть [в центральном maven репозитории](https://search.maven.org/search?q=junit-jupiter-engine), берем только релизы (..-Mx означают предварительные milestone версии)
Изменять конфигурацию плагина `maven-sureface-plugin` в новых версиях JUnit уже не требуется. Junit5 не содержит в себе зависимости от *Harmcrest* (которую нам приходилось вручную отключать для JUnit4
в предыдущих шагах), поэтому исключение `hamcrest-core` просто удаляем. В итоге у нас останутся зависимости JUnit5 и расширенный Harmcrest.  
Теперь мы можем применить все нововведения пятой версии в наших тестах:

1. Для всех тестов теперь мы можем удалить `public`.
2. Аннотацию `@Before` исправим на `@BeforeEach` - теперь метод, который будет выполняться перед каждым тестом, помечается именно так.
3. В Junit5 работа с исключениями похожа на Junit4 версии 4.13: вместо ожидаемых исключений в параметрах аннотации `@Test(expected = Exception.class)` используется метод `assertThrows()`, в который
   первым аргументом мы передаем ожидаемое исключение, а вторым аргументом — реализацию функционального интерфейса `Executable` (кода теста, в котором ожидается возникновение исключения).
4. Метод `assertThrows()` возвращает исключение, которое было выброшено в переданном ему коде. Теперь мы можем получить это исключение, извлечь из него сообщение с помощью
   `e.getMessage()` и сравнить с ожидаемым.
5. Для теста на валидацию при проверке предусловия, только при выполнении которого будет выполняться следующий участок кода (например, в нашем случае тесты на валидацию выполнялись только в jpa
   профиле), - теперь нужно пользоваться утильным методом `Assumptions` (нам уже не требуется).
6. Проверку Root Cause - причины, из-за которой было выброшено пойманное исключение, мы будем делать позднее, при тестах на ошибки.
7. Из JUnit5 исключена функциональность `@Rule`, вместо них теперь нужно использовать `Extensions`, которые могут встраиваться в любую фазу тестов. Чтобы добавить их в тесты, пометим базовый тестовый
   класс аннотацией `@ExtendWith`.

JUnit предоставляет нам набор коллбэков — интерфейсов, которые будут исполняться в определенный момент тестирования. Создадим класс `TimingExtension`, который будет засекать время выполнения тестовых
методов.  
Этот класс будет имплементировать маркерные интерфейсы — коллбэки JUnit:

- `BeforeTestExecutionCallback` - коллбэк, который будет вызывать методы этого интерфейса перед каждым тестовым методом.
- `AfterTestExecutionCallback` - методы этого интерфейса будут вызываться после каждого тестового метода;
- `BeforeAllCallback` - методы перед выполнением тестового класса;
- `AfterAllCallback` - методы после выполнения тестового класса;

Осталось реализовать соответствующие методы, которые описываются в каждом из этих интерфейсов, они и будут вызываться JUnit в нужный момент:

- в методе `beforeAll` (который будет вызван перед запуском тестового класса) создадим спринговый утильный секундомер `StopWatch` для текущего тестового класса;
- в методе `beforeTestExecution` (будет вызван перед тестовым методом) - запустим секундомер;
- в методе `afterTestExecution` (будет вызван после тестового метода) - остановим секундомер.
- в методе `afterAll` (который будет вызван по окончанию работы тестового класса) - выведем результат работы этого секундомера в консоль;

8. Аннотации `@ContextConfiguration` и `@ExtendWith(SpringExtension.class)` (замена `@RunWith`) мы можем заменить одной `@SpringJUnitConfiguration` (в старых версиях IDEA ее не понимает)

</details>

#### Apply 7_10_JUnit5.patch

- [No need `junit-platform-surefire-provider` dependency in `maven-surefire-plugin`](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-maven)
- [Наконец пофиксили баг с `@SpringJUnitConfig`](https://youtrack.jetbrains.com/issue/IDEA-166549)
- Добавил [`junit-platform-launcher` в pom для запуска JUnit 5 тестов из IDEA](https://youtrack.jetbrains.com/issue/IDEA-231927)
- [JUnit 5 homepage](https://junit.org/junit5)
- [Overview](https://junit.org/junit5/docs/snapshot/user-guide/#overview)
- [Миграция с JUnit4 на JUnit5: важные отличия и преимущества](https://topjava.ru/blog/migratsiya-s-junit4-na-junit5)
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

Чаще всего в REST сервер и клиент общаются посредством обмена JSON-объектами через HTTP-методы GET/POST/PUT/DELETE/PATCH.  
Особенностью REST является отсутствие состояния (контекста) взаимодействий клиента и сервера.

В нашем приложении есть контроллеры для Admin и для User. Чтобы сделать их REST-контроллерами, заменим аннотацию `@Controller` на `@RestController`

> Не поленитесь зайти чз Ctrl+Click в `@RestController`: к аннотации `@Controller` добавлена `@ResponseBody`. Т.е. ответ от нашего приложения будет не имя View, а данные в теле ответа.

В `@RequestMapping`, кроме пути для методов контроллера (`value`) добавляем параметр `produces = MediaType.APPLICATION_JSON_VALUE`. Это означает, что в заголовки ответа будет добавлен
тип `ContentType="application/json"` - в ответе от контроллера будет приходить JSON-объект.

> Чтобы было удобно использовать путь к этому контроллеру в приложении и в тестах,
> выделим путь к нему в константу REST_URL, к которой можно будет обращаться из других классов

1. Метод `AdminRestController.getAll` пометим аннотацией `@GetMapping` - маршрутизация к методу по HTTP GET.

2. Метод `AdminRestController.get` пометим аннотацией `@GetMapping("/{id}")`.  
   В скобках аннотации указано, что к основному URL контроллера будет добавляться `id` пользователя - переменная, которая передается в запросе непосредственно в URL.  
   Соответствующий параметр метода нужно пометить аннотацией `@PathVariable` (если имя в URL и имя аргумента метода не совпадают, в параметрах аннотации дополнительно нужно будет уточнить имя в URL.
   Если они совпадают, [этого не требуется](https://habr.com/ru/post/440214/).

3. Метод создания пользователя `create` отметим аннотацией `@PostMapping` - маршрутизация к методу по HTTP POST. В метод мы передаем объект `User` в теле запроса (аннотация `@RequestBody`) и в формате
   JSON (`consumes = MediaType.APPLICATION_JSON_VALUE`). При создании нового ресурса правила хорошего тона - вернуть в заголовке ответа URL созданного ресурса. Для этого возвращем не `User`,
   а `ResponseEntity<User>`, который мы можем с помощью билдера `ServletUriComponentsBuilder` дополнить заголовком ответа `Location` и вернуть статус `CREATED(201)`
   (если пойти в код `ResponseEntity.created` можно докопаться до сути, очень рекомендую смотреть в исходники кода).

4. Метод `delete` помечаем `@DeleteMapping("/{id}")` - HTTP DELETE. Он ничего не возвращает, поэтому помечаем его аннотацией `@ResponseStatus(HttpStatus.NO_CONTENT)`. Статус ответа будет HTTP.204;

5. Над методом обновления ставим `@PutMapping` (HTTP PUT). В аргументах метод принимает `@RequestBody User user` и `@PathVariable int id`.

6. Метод поиска по `email` также помечаем `@GetMapping`, и, чтобы не было конфликта маршрутизации с методом `get()`, указываем в URL добавку "/by". В этот метод `email` передается как параметр
   запроса, аннотация `@RequestParam`.

> **Все это СТАНДАРТ архитектурного стиля REST. НЕ придумывайте ничего своего в своих выпускных проектах! Это очень большая ошибка - не придерживаться стандартов API.**

7. `ProfileRestController` выполняем аналогичным способом с учетом того, что пользователь имеет доступ только к своим данным.

Если на данном этапе попытаться запустить приложение и обратиться к какому-либо методу контроллера, сервер ответит нам ошибкой со статусом 406, так как Spring не знает, как преобразовать объект User в
JSON...

</details>

#### Apply 7_11_rest_controller.patch

> - Переделал URL поиска по email на `/by-email`

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
    - <a href="https://www.youtube.com/watch?v=Q84xT4Zd7vs&list=PLoij6udfBncivGZAwS2yQaFGWz4O7oH48">И. Головач. RestAPI</a>
    - [value/name в аннотациях @PathVariable и @RequestParam](https://habr.com/ru/post/440214/)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. [Тестирование REST контроллеров. Jackson.](https://drive.google.com/open?id=1aZm2qoMh4yL_-i3HhRoyZFjRAQx-15lO)

<details>
  <summary><b>Краткое содержание</b></summary>

Для работы с JSON добавляем в `pom.xml` зависимость `jackson-databind`.    
Актуальную версию библиотеки можно посмотреть в [центральном maven-репозитории](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-databind).  
Теперь спринг будет автоматически использовать эту библиотеку для сериализации/десериализации объектов в JSON (найдя ее в *classpath*).  
Если сейчас запустить приложение и обратиться к методам REST-контроллера, то оно выбросит `LazyInitializationException`. Оно возникает из-за того, что у наших сущностей есть лениво загружаемые поля,
отмеченные `FetchType.LAZY` - при загрузке сущности из базы, вместо этого поля подставится Proxy, который и должен вернуть реальный экземпляр этого поля при первом же обращении. Jackson при
сериализации в JSON использует все поля сущности, и при обращении к *Lazy* полям возникает исключение, так как сессия работы с БД в этот момент уже закрыта, и нужный объект не может быть
инициализирован. Чтобы Jackson игнорировал эти поля, пометим их аннотацией `@JsonIgnore`.

Теперь при запуске приложения REST-контроллер будет работать. Но при получении JSON объектов мы можем увидеть, что Jackson сериализовал объект через геттеры (например в ответе есть поле `new` от
метода `Persistable.isNew()`). Чтобы учитывались только поля объектов, добавим над `AbstractBaseEntity`:

````java
@JsonAutoDetect(fieldVisibility = ANY, // jackson видит все поля
        getterVisibility = NONE, // ... но не видит геттеров
        isGetterVisibility = NONE, //... не видит геттеров boolean полей
        setterVisibility = NONE) // ... не видит сеттеров
````

Теперь все сущности, унаследованные от базового класса, будут сериализоваться/десериализоваться через поля.

</details>

#### Apply 7_12_rest_test_jackson.patch (внимание: обновил патч 23.03 в 11.00)

- [Jackson databind github](https://github.com/FasterXML/jackson-databind)
- [Jackson Annotation Examples](https://www.baeldung.com/jackson-annotations)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Кастомизация Jackson Object Mapper](https://drive.google.com/open?id=1CM6y1JhKG_yeLQE_iCDONnI7Agi4pBks)

<details>
  <summary><b>Краткое содержание</b></summary>

Сейчас, чтобы не сериализовать *Lazy* поля, мы должны пройтись по каждой сущности и вручную пометить их аннотацией `@JsonIgnore`. Это неудобно, засоряет код и допускает возможные ошибки. К тому же,
при некоторых условиях, нам иногда нужно загрузить и в ответе передать эти *Lazy* поля.  
Чтобы запретить сериализацию Lazy полей для всего проекта, подключим в `pom.xml` библиотеку `jackson-datatype-hibernate`.  
Также изменим сериализацию/десериализацию полей объектов в JSON: не через аннотацию `@JsonAutoDetect`, а в классе `JacksonObjectMapper`, который унаследуем от `ObjectMapper` (стандартный Mapper,
который использует Jackson) и сделаем в нем другие настройки. В конструкторе:

- регистрируем `Hibernate5Module` - модуль `jackson-datatype-hibernate`, который не делает сериализацию ленивых полей.
- модуль для корректной сериализации `LocalDateTime` в поля JSON - `JavaTimeModule` модуль библиотеки `jackson-datatype-jsr310`
- запрещаем доступ ко всем полям и методам класса и потом разрешаем доступ только к полям
- не сериализуем null-поля (`setSerializationInclusion(JsonInclude.Include.NON_NULL)`)

Чтобы подключить наш кастомный `JacksonObjectMapper` в проект, в конфигурации `spring-mvc.xml` к настройке `<mvc:annotation-driven>` добавим `MappingJackson2HttpMessageConverter`, который будет
использовать наш маппер.

</details>

#### Apply 7_13_jackson_object_mapper.patch

- Сериализация hibernate lazy-loading с помощью <a href="https://github.com/FasterXML/jackson-datatype-hibernate">
  jackson-datatype-hibernate</a>
- <a href="https://geowarin.github.io/jsr310-dates-with-jackson.html">Handle Java 8 dates with Jackson</a>
- Дополнительно:
    - <a href="https://www.sghill.net/how-do-i-write-a-jackson-json-serializer-deserializer.html">Jackson JSON Serializer & Deserializer</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Тестирование REST контроллеров через JSONassert и Матчеры](https://drive.google.com/open?id=1oa3e0_tG57E71g6PW7_tfb3B61Qldctl)

<details>
  <summary><b>Краткое содержание</b></summary>

Сейчас в тестах REST-контроллера мы проводим проверку только на статус ответа и тип возвращаемого контента. Добавим проверку содержимого ответа.

#### 7_14_json_assert_tests

Чтобы сравнивать содержимое ответа контроллера в виде JSON и сущность, воспользуемся библиотекой
`jsonassert`, которую подключим в `pom.xml` со scope *test*.

Эта библиотека при сравнении в тестах в качестве ожидаемого значения ожидает от нас объект в виде JSON-строки. Чтобы вручную не преобразовывать объекты в JSON и не хардкодить их в виде строк в наши
тесты, воспользуемся Jackson.  
Для преобразования объектов в JSON и обратно создадим утильный класс `JsonUtil`, в котором с помощью нашего `JacksonObjectMapper` и будет конвертировать объекты.  
И мы сталкиваемся с проблемой: `JsonUtil` - утильный класс и не является бином спринга, а для его работы требуется наш кастомный маппер, который находится под управлением спринга и расположен в
контейнере зависимостей. Поэтому, чтобы была возможность получить наш маппер из других классов - сделаем его синглтоном и сделаем в нем статический метод, который будет возвращать его экземпляр.
Теперь `JsonUtil` сможет его получить.  
И нам нужно указать спрингу, чтобы он не создавал второй экземпляр этого объекта, а клал в свой контекст существующий. Для этого в конфигурации `spring-mvc.xml` определим factory-метод, с помощью
которого спринг должен получить экземпляр (instance) этого класса:

```xml

<bean class="ru.javawebinar.topjava.web.json.JacksonObjectMapper" id="objectMapper" factory-method="getMapper"/>
```  

а в конфигурации `message-converter` вместо создания бина просто сошлемся на сконфигурированный `objectMapper`.

Метод `ContentResultMatchers.json()` из `spring-test` использует библиотеку `jsonassert` для сравнения 2-х JSON строк: одну из ответа контроллера и вторую - JSON-сериализация `admin` без
поля `registered` (это поле инициализируется в момент создания и отличается). В методе `JsonUtil.writeIgnoreProps` мы преобразуем объект `admin` в мапу, удаляем из нее игнорируемые поля и снова
сериализуем в JSON.

Также сделаем тесты для утильного класса `JsonUtil`. В тестах мы записываем объект в JSON-строку, затем конвертируем эту строку обратно в объект и сравниваем с исходным. И то же самое делаем со
списком объектов.

#### 7_15_tests_refactoring

**`RootControllerTest`**

Сделаем рефакторинг `RootControllerTest`. Ранее мы в тесте получали модель, доставали из нее сущности и с помощью `hamcrest-all`
производили по одному параметру их сравнение с ожидаемыми значениями. Метод `ResultActions.andExpect()` позволяет передавать реализацию интерфейса `Matcher`, в котором можно делать любые сравнения.
Функциональность сравнения списка юзеров по ВСЕМ полям у нас уже есть - мы просто делегируем сравнение объектов в `UserTestData.MATCHER`. При этом нам больше не нужен `harmcrest-all`, нам достаточно
только `harmcrest-core`.

**`MatcherFactory`**

Теперь вместо `jsonassert` и сравнения JSON-строк в тестах контроллеров сделаем сравнения JSON-объектов через `MatcherFactory`. Преобразуем ответ контроллера из JSON в объект и сравним с эталоном
через уже имеющийся у нас матчер.  
Вместо сравнения JSON-строк в метод `andExpect()` мы будем передавать реализации интерфейса `ResultMatcher` из `MATCHER.contentJson(..)`.

`MATCHER.contentJson(..)` принимают ожидаемый объект и возвращают для него `ResultMatcher` с реализацией единственного метода `match(MvcResult result)`, в котором делегируем сравнение уже существующим
у нас матчерам. Мы берем JSON-тело ответа (`MatcherFactory.getContent`), десериализуем его в объект (`JsonUtil.readValue/readValues`) и сравниваем через имеющийся `MATCHER.assertMatch`
десериализованный из тела контроллера объект и ожидаемое значение.

> Методы из класса `TestUtil` перенес в `MatcherFactory`, лишние удалил.

**`AdminRestControllerTest`**

- `getByEmail()` - сделан по аналогии с тестом `get()`. Дополнительно нужно дополнить строку URL параметрами запроса.
- `delete()` - выполняем HTTP.DELETE. Проверяем статус ответа 204. Проверяем, что пользователь удален.

> Раньше я получал всех users из базы и проверял, что среди них нет удаленного. При этом тесты становятся чувствительными ко всем users в базе и ломаются при добавлении-удалении новых тестовых данных.

- `update()` - выполняем HTTP.PUT. В тело запроса подаем сериализованный `JsonUtil.writeValue(updated)`. После выполнения проверяем, что объект в базе обновился.
- `create()` - выполняем HTTP.POST аналогично `update()`. Но сравнить результат мы сразу не можем, т.к. при создании объекта ему присваивается `id`.  
  Поэтому мы извлекаем созданного пользователя из ответа (`MATCHER.readFromJson(action)`), получаем его `id`, и уже с этим `id` эталонный объект мы можем сравнить с объектом в ответе контроллера и со
  значением в базе.
- `getAll()` - аналогично get(). Список пользователей из ответа в формате JSON сравниваем с эталонным списком (`MATCHER.contentJson(admin, user)`).

Тесты для `ProfileRestController` выполнены аналогично.

</details>

#### Apply 7_14_json_assert_tests.patch

> - В `JsonUtil.writeIgnoreProps` вместо цикла по мапе сделал `map.keySet().removeAll`

- [JSONassert](https://github.com/skyscreamer/JSONassert)
- [Java Code Examples for ObjectMapper](https://www.programcreek.com/java-api-examples/index.php?api=com.fasterxml.jackson.databind.ObjectMapper)

#### Apply 7_15_tests_refactoring.patch

> - Сделал внутренний класс `MatcherFactory.Matcher`, который возвращается из фабрики матчеров.
> - Методы из класса `TestUtil` перенес в `MatcherFactory`, лишние удалил.
> - Раньше в тестах я для проверок получал всех users из базы и сравнивал с эталонным списком. При этом тесты становятся чувствительными ко всем users в базе и ломаются при добавлении-удалении новых тестовых данных.

- [Java @SafeVarargs Annotation](https://www.baeldung.com/java-safevarargs)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. [Тестирование через SoapUi. UTF-8](https://drive.google.com/open?id=0B9Ye2auQ_NsFVXNmOUdBbUxxWVU)

<details>
  <summary><b>Краткое содержание</b></summary>

SOAP UI - это один из инструментов для тестирования API приложений, которые работают по REST и по SOAP.  
Он позволяет нам по HTTP протоколу дернуть методы нашего API и увидеть ответ контроллеров.

Если в контроллер мы добавим метод, который в теле ответа будет возвращать текст на кириллице, то увидим, что кодировка теряется. Для сохранения кодировки используем `StringHttpMessageConverter`,
который конфигурируем в `spring-mvc.xml`. При этом мы должны явно указать, что конвертор будет работать только с текстом в кодировке *UTF-8*.

</details>

#### Apply 7_16_soapui_utf8_converter.patch

- Инструменты тестирования REST:
    - <a href="https://www.soapui.org/downloads/soapui/">SoapUi</a>
    - [Что такое Curl? Как работает эта команда?](https://highload.today/curl/)
    - <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a>.  
      Для Windows 7 можно использовать Git Bash, с Windows 10 v1803 можно прямо из консоли. Возможны проблемы с UTF-8:
        - [CURL doesn't encode UTF-8](https://stackoverflow.com/a/41384903/548473)
        - [кириллица в теле POST-запроса](https://barbitoff.blogspot.com/2018/11/soapui-post-rest.html)
        - [Нстройка кодировки в Windows](https://support.socialkit.ru/ru/knowledge-bases/4/articles/11110-preduprezhdenie-obnaruzhenyi-problemyi-svyazannyie-s-raspoznavaniem-russkih-simvolov)
    - **[IDEA: Tools->HTTP Client->...](https://www.jetbrains.com/help/idea/rest-client-tool-window.html)**
    - <a href="https://www.getpostman.com/">Postman</a>
    - [Insomnia REST client](https://insomnia.rest/)

**Импортировать проект в SoapUi из `config\Topjava-soapui-project.xml`. Response смотреть в формате JSON.**

> Проверка UTF-8: <a href="http://localhost:8080/topjava/rest/profile/text">http://localhost:8080/topjava/rest/profile/text</a>

[ResponseBody and UTF-8](http://web.archive.org/web/20190102203042/http://forum.spring.io/forum/spring-projects/web/74209-responsebody-and-utf-8)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Зачем у нас и UIController'ы, и RestController'ы? То есть в общем случае backend-разработчику недостаточно предоставить REST-api и RestController?

Часто используются и те и другие. REST обычно используют для отдельного UI например на React или Angular или для интеграции / мобильного приложения.
У нас REST контроллеры используются только для тестирования. UI мы используем для нашего приложения на JSP шаблонах.
Таких сайтов без богатой UI логики тоже немало. Например https://javaops.ru/ :)  
Разница в обработке запросов:

- из UI контроллеров возвращаются как готовые HTML странички, так и данные в формате JSON (будет для AJAX запросов в следующих занятиях)
- для UI мы используем только GET и POST запросы
- при создании-обновлении в UI мы принимаем данные из формы `application/x-www-form-urlencoded` (посмотрите вкладку `Network`, не в формате JSON)
- для REST запросы GET, POST, PUT, DELETE, PATCH и возвращают только данные (обычно JSON)

И в способе авторизации:

- для RESТ у нас будет базовая авторизация
- для UI - через cookies

Также часто бывают смешанные сайты - где есть и отдельное JS приложение и шаблоны.

> При выполнении тестов через MockMvc никаких изменений на базе не видно, почему оно не сохраняет?

`AbstractControllerTest` аннотируется `@Transactional` - это означает, что тесты идут в транзакции, и после каждого теста JUnit делает rollback базы.

> Что получается в результате выполнения запроса `SELECT DISTINCT(u) FROM User u LEFT JOIN FETCH u.roles ORDER BY u.name, u.email`? В чем разница в SQL без `DISTINCT`.

Запросы SQL можно посмотреть в логах. Т.е. `DISTINCT` в `JPQL` влияет на то, как Hibernate обрабатывает дублирующиеся записи (с `DISTINCT` их исключает). Результат можно посмотреть в тестах или
приложении, поставив брекпойнт. По поводу `SQL DISTINCT` не стесняйтесь пользоваться google, например, [оператор SQL DISTINCT](http://2sql.ru/novosti/sql-distinct/)

> В чем заключается расширение функциональности hamcrest в нашем тесте, что нам пришлось его отдельно от JUnit прописывать?

`hamcrest-all` используется в проверках `RootControllerTest`: `org.hamcrest.Matchers.*`. JUnit 4 включает в себя `hamcrest-core`, в JUnit 5 его нужно подключать отдельно.

> Jackson мы просто подключаем в помнике, и Spring будет с ним работать без любых других настроек?

Да, Spring смотрит в classpath и если видит там Jackson, то подключает интеграцию с ним.

> Где-то слышал, что любой ресурс по REST должен однозначно идентифицироваться через url без параметров. Правильно ли задавать URL для фильтрации в виде `http://localhost/topjava/rest/meals/filter/{startDate}/{startTime}/{endDate}/{endTime}` ?

Так делают, только при отношении <a href="https://ru.wikipedia.org/wiki/Диаграмма_классов#.D0.90.D0.B3.D1.80.D0.B5.D0.B3.D0.B0.D1.86.D0.B8.D1.8F">
агрегация</a>, например, если давать админу право смотреть еду любого юзера, URL мог бы быть похож на `http://localhost/topjava/rest/users/{userId}/meals/{mealId}` (не рекомендуется, см ссылку ниже).
В случае критериев поиска или страничных данных они передаются как параметр. Смотри также:

- [15 тривиальных фактов о правильной работе с протоколом HTTP](https://habrahabr.ru/company/yandex/blog/265569/)
- <a href="https://medium.com/@mwaysolutions/10-best-practices-for-better-restful-api-cbe81b06f291">10 Best Practices for Better RESTful
- [REST resource hierarchy (если кратко: не рекомендуется)](https://stackoverflow.com/questions/15259843/how-to-structure-rest-resource-hierarchy)

> Что означает конструкция в `JsonUtil`: `reader.<T>readValues(json)`;

См. <a href="https://docs.oracle.com/javase/tutorial/java/generics/methods.html">Generic Methods</a>. Когда компилятор не может вывести тип, можно его уточнить при вызове generic метода. Неважно,
static или нет.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW07

- 1: Добавить тесты контроллеров:
    - 1.1 `RootControllerTest.getMeals` для `meals.jsp`
    - 1.2 Сделать `ResourceControllerTest` для `style.css` (проверить `status` и `ContentType`)
- 2: Реализовать `MealRestController` и протестировать его через `MealRestControllerTest`
    - 2.1 следите, чтобы url в тестах совпадал с параметрами в методе контроллера. Можно добавить логирование `<logger name="org.springframework.web" level="debug"/>` для проверки маршрутизации.
    - 2.2 в параметрах `getBetween` принимать `LocalDateTime` (конвертировать через <a href="http://blog.codeleak.pl/2014/06/spring-4-datetimeformat-with-java-8.html">@DateTimeFormat with Java 8
      Date-Time API</a>), пока без проверки на `null` (используя `toLocalDate()/toLocalTime()`, см. Optional п.3). В тестах передавать в формате `ISO_LOCAL_DATE_TIME` (
      например `'2011-12-03T10:15:30'`).

### Optional

- 3: Переделать `MealRestController.getBetween` на параметры `LocalDate/LocalTime` c раздельной фильтрацией по времени/дате, работающий при `null` значениях (см. демо и `JspMealController.getBetween`)
  . Заменить `@DateTimeFormat` на свои LocalDate/LocalTime конверторы или форматтеры.
    - [Spring Type Conversion and Field Formatting — пишем первый конвертер или форматтер](https://habr.com/ru/post/703402/)
- 4: Протестировать `MealRestController` (SoapUi, curl, IDEA Test RESTful Web Service, Postman). Запросы `curl` занести в отдельный `md` файл (или `README.md`)
- 5: Добавить в `AdminRestController` и `ProfileRestController` методы получения пользователя вместе с едой (`getWithMeals`, `/with-meals`).
    - [Jackson – Bidirectional Relationships](https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)

### Optional 2

- 6: Сделать тесты на методы контроллеров `getWithMeals()` (п.5)

**На следующем занятии используется JavaScript/jQuery. Если у вас там пробелы, <a href="https://github.com/JavaOPs/topjava#html-javascript-css">пройдите его основы</a>**

---------------------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: Ошибка в тесте _Invalid read array from JSON_ обычно расшифровывается немного ниже: читайте внимательно.
- 2: <a href="https://urvanov.ru/2016/12/03/jackson-и-неизменяемые-объекты/">Jackson и неизменяемые объекты</a> (для сериализации MealTo)
- 3: Если у meal, приходящий в контроллер, поля `null`, проверьте `@RequestBody` перед параметром (данные приходят в формате JSON)
- 4: При проблемах с собственным форматтером убедитесь, что в конфигурации `<mvc:annotation-driven...` не дублируется
- 5: Тесты сервисов у нас идут на все профили работы с БД (JDBC, JPA и DATAJPA), а контроллеров - только на Profiles.REPOSITORY_IMPLEMENTATION.
  Проверьте выполнение тестов контроллера через _maven test_ для каждого из профилей (JDBC, JPA и DATAJPA). Обратите внимание, что `getWithMeals()` реализован только для DATAJPA, для JDBC и JPA тесты  `getWithMeals()` должны игнорироваться. Также, в
  случае проблем, проверьте, что не портите эталонный образец из `MealTestData`
- 6: `@Autowired` в тестах нужно делать в том месте, где класс будет использоваться. Общий принцип: не размазывать код по классам, объявление переменных держать как можно ближе к ее использованию,
  группировать (не смешивать) код с разной функциональностью.
- 7: Попробуйте в `RootControllerTest.getMeals` сделать сравнение через `model().attribute("meals", expectedValue)`. Учтите, что вывод результатов через `toString` к сравнению отношения не имеет. 