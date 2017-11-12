# Онлайн проекта <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

### Обновил <a href="http://topjava.herokuapp.com/">Демо</a> приложение нашей версией

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSzlObk8tbHdtcXc">Материалы занятия</a>
#### Некоторые темы урока совсем новые, поэтому видео есть не везде. Все материалы проекта (в том числе и будущие обновления) останутся доступны в Google Drive.

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правка и рефакторинг

#### Apply 11_0_0_fix.patch
> - Небольшие правки стиля кода
> - Заменил depricated `$.parseJSON` на `JSON.parse`
 
#### Apply 11_0_1_dateTime_refactoring
> Вернул форматирование ISO-8601 для `dateTime` по Ajax и добавил в `mealDatatables.js`  [jQuery converters](http://api.jquery.com/jQuery.ajax/#using-converters) для преобразования в UI формат

#### Apply 11_0_2_validation_refactoring
> Сделал `View$Persist` группу для валидации [при сохранении через JPA](https://stackoverflow.com/a/16930663/548473). Т.к. отнаследовал ее от `javax.validation.groups.Default`, при персисте учитываются все непомеченные аннотации валидации (`Default`) + помеченные `Persist`.

#### Apply 11_0_3_test_refactoring
> - Сделал метод `JsonUtil.writeWithExtraProps` и `UserTestData.jsonWithPassword` для сериализации в json юзера с паролем
> - Использую `jsonWithPassword` в `AdminRestControllerTest.testUpdate` и `testCreate`. В `testUpdate` валидации при сохранении не происходит из-за `@Transactional`. См. [решение проблемы с транзакционными тестами](https://stackoverflow.com/a/46415060/548473)
> - Сделал и использую утильный метод  `TestUtil.contentMatcher`

#### Apply 11_0_4_error_refactoring
> Добавил в `ErrorInfo` тип ошибки `ErrorType`

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW10

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFX2V5eHRsa09IWHc">HW10</a>
#### Apply 11_01_HW10_fix_encoding.patch
#### Apply 11_02_HW10_validation.patch
> - В [соответствии со спицификацией](http://stackoverflow.com/a/22358422/548473) для поменял `HTTP 400` (ошибка в структуре сообщения) на `HTTP 422` (ошибка в содержании)
> - Сделал тесты и проверку типа ошибки [через `jsonPath`](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/)

#### Apply 11_03_HW10_duplicate_email.patch
> - сделал код(ключ) i18n константой (`EXCEPTION_DUPLICATE_EMAIL`)

#### Apply 11_04_HW10_duplicate_datetime.patch
> - Реализавать обработку дублирования `user.email` и `meal.dateTime` можно по разному
>   - через [поиск в сообщении `DataIntegrityViolationException` имени DB constrains](https://stackoverflow.com/a/42422568/548473)
>   - через [Controller Based Exception Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling)
>   - через [Custom Validator](https://howtodoinjava.com/spring/spring-mvc/spring-mvc-custom-validator-example/), с еще одним запросом в базу. Первый самый простой и расширяемый (хотя зависить от базы), выбрал его. Для работы с HSQLDB сделал `toLowerCase`. Самый корректный и трудоемкий с собственными валидаторами: он позволяет валидировать в конроллерах, а не при сохранении.

> - Сделал утильный класс `MessageUtil` для работы с i18n.
> - Добавил тесты на дублирование. Отключил транзакционность в тестах на дублирование через `@Transactional(propagation = Propagation.NEVER)`.
>   - [Решение проблемы с транзакционными тестами](https://stackoverflow.com/a/46415060/548473)

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYms4YUxEMHdxZHM">HW10 Optional: change locale</a>
#### Apply 11_05_HW10_change_locale.patch

> - Добавил локализацию календаря `$.datetimepicker.setLocale(localeCode)`
> - Вместо смену локали в `lang.jsp` через javascript сделал `href=${requestScope['javax.servlet.forward.request_uri']}?lang=..`

<a href="http://pro-cod.ru/navbar-uroki-bootstrap-3.html">Bootstrap Navbar</a>

## Заключительное 11-е занятие

### Локализация:
#### Apply 11_06_i18n.patch
 - Вынес общие части опций таблиц в `datatablesUtil.js` используя <a href="https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN">jQuery.extend()</a>
 - Добавил <a href="http://stackoverflow.com/questions/29929022/change-datatable-search-label/38133762#38133762">локализацию Search в datatable</a>
 - Сделал локализацию ошибок валидации:
   - Все коды для локализации конкретной ошибки валидации можно посмотреть в дебаге: `FieldError.getCodes()`
   - Коды для ее аргументов: `FieldError.getArguments()` (аргумент складывается из имени класса и поля).
 - Добавил локализацию `ErrorInfo.type` (код локализации в `ErrorType` и поле `ErrorInfo.typeMessage`)

### Защита от XSS (Cross Site Scripting)
> **Попробуйте до и после патча ввести в любое текстовое поле редактирования `<script>alert('XSS')</script>` и сохранить.**
#### Apply 11_07_XSS.patch
> - `password` проверять не надо, т.к. он не выводится в html, а [email надо](https://stackoverflow.com/questions/17480809)
> - Сделал общий интерфейс валидации `View.ValidatedRestUI` и `@Validated(View.ValidatedRestUI.class)` вместо `@Valid` для проверки содержимого только на входе Ajax/REST. При сохранении проверка на безопасный html контент не делается.

- <a href="https://forum.antichat.ru/threads/20140/">XSS для новичков</a>
- <a href="https://habrahabr.ru/post/66057/">XSS глазами злоумышленника</a>
- <a href="http://stackoverflow.com/a/40644276/548473">Prevent people from doing XSS in Spring MVC</a>

### [Обработка ошибок 404 (NotFound)](https://stackoverflow.com/questions/18322279/spring-mvc-spring-security-and-error-handling)
#### Apply 11_08_404.patch

### Доступ к AuthorizedUser
#### Apply 11_09_auth_user.patch
- [Автоподстановка в контроллерах](http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#mvc-authentication-principal)
  - не стал делать автоподстановку по всем контроллерам (в абстрактных контроллерах проще работать с `AuthorizedUser`, чем получать его через `@AuthenticationPrincipal` и передавать параметром)
- [В JSP: the authentication Tag](http://docs.spring.io/spring-security/site/docs/current/reference/html/taglibs.html#the-authentication-tag)
  - авторизованный пользователь доступен в JSP через tag `authentication`, интерсептор становится не нужным

### Ограничение модификации пользователей
#### Apply 11_10_restrict_modification.patch
 - В `AbstractUserController` добавилась защита от удаления-модификации `Admin/User` для профиля `HEROKU` (в `AbstractUserController` заинжектил `Environment` и сделал проверку `HEROKU`)
 - Чтобы тесты были рабочими, ввел профиль `HEROKU`, работающий так же, как и `POSTGRES`.
 - Добавил `ApplicationException` для работы с ошибками с поддержкой i18n в приложении (от него отнаследовал `NotFoundException`)

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZkpVM19QWFBOQ2c">3. Деплой приложения в Heroku.</a>
#### Apply 11_11_heroku.patch
> - Добавил зависимости `postgres` в профиль мавена `heroku`
> - [Поменял настройки `dataSource` для профиля `heroku`](http://stackoverflow.com/questions/10684244/dbcp-validationquery-for-different-databases). 
При опускании/поднятии приложения в heroku.com портятся коннекты в пуле и необходимо их валидировать. 

**Приложение деплоится в ROOT: [http://localhost:8080](http://localhost:8080)**

- <a href="http://habrahabr.ru/post/265591/">Деплой Java Spring приложения в PaaS-платформу Heroku</a>
```
Config Vars
  ERROR_PAGE_URL=...
  TOPJAVA_ROOT=/app

Datasources advanced
    ssl=true
    sslfactory=org.postgresql.ssl.NonValidatingFactory
```    

-  Ресурсы:
   -  <a href="https://www.heroku.com/">PaaS-платформа Heroku</a></h3>
   -  Конфигурирование приложения для запуска через <a href="https://devcenter.heroku.com/articles/java-webapp-runner">Tomcat-based Java Web</a>
   -  Конфигурирование <a href="https://devcenter.heroku.com/articles/connecting-to-relational-databases-on-heroku-with-java#using-the-database_url-in-spring-with-xml-configuration">DataSource profile для Heroku</a>
   -  <a href="http://www.jetbrains.com/idea/webhelp/run-debug-configuration-heroku-deployment.html">Интерграция с IDEA</a>
   -  <a href="http://www.paasify.it/filter">Find your Platform as a Service</a>
   -  <a href="https://devcenter.heroku.com/articles/getting-started-with-java#set-up">Getting Started with Java on Heroku</a>
   -  <a href="https://devcenter.heroku.com/articles/keys">Managing Your SSH Keys</a>
   -  <a href="https://devcenter.heroku.com/articles/getting-started-with-spring-mvc-hibernate#deploy-your-application-to-heroku">Deploy your application to Heroku</a>
   -  <a href="http://www.ibm.com/developerworks/ru/library/j-javadev2-21/">Развертывание приложений Java с помощью PaaS от Heroku</a>
   -  <a href="http://www.infoq.com/articles/paas_comparison">A Java Developer’s Guide to PaaS</a>
   -  <a href="https://dzone.com/articles/simple-paas-comparison-guide">A Simple PaaS Comparison Guide (With the Java Dev in Mind)</a>
   -  <a href="http://www.ibm.com/developerworks/library/j-paasshootout/">Java PaaS shootout</a>


###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQVc2WUdCR0xvLWM">4. Собеседование. Разработка ПО</a>
- [Темы/ресурсы тестового собеседования](http://javaops.ru/interview/test.html)
- [Составление резюме, подготовка к интервью, поиск работы](https://github.com/JavaOPs/topjava/blob/master/cv.md)
- [Слайды](https://docs.google.com/presentation/d/18o__IGRqYadi4jx2wX2rX6AChHh-KrxktD8xI7bS33k), [Книги](http://javawebinar.ru/books.html)
- [Jenkins/Hudson: что такое и для чего он нужен](https://habrahabr.ru/post/334730/)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png)  Домашнее задание по проекту:
- **Задеплоить свое приложение в Heroku** 
- Сделать / обновить резюме (отдать на ревью в канал #hw11 группы slack)
  - [Загрузка сайта на GitHub. Бесплатный хостинг и домен.](https://vk.com/video-58538268_456239051?list=661b165047264e7952)
  - [CSS theme for hosting your personal site, blog, or portfolio](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/)
- Дать ссылку на него [в своем профиле Java Online Projects](http://javaops.ru/view/login) и заполнить информацию по поиску работы (если конечно актуально): резюме, флаги рассматриваю работу и готов к релокации и информация для HR.
- **Обновления базы соискателей буду рассылать по HR 05.10, постарайтесь успеть**
- Сделать и внести в свое портфолио <a href="https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md#graduation-project">Graduation project</a>
  - Для участников с проверкой выпускной проект принимаю на ревью до 19.10 (участникам <a href="http://javaops.ru/reg/masterjava">Masterjava</a> без ограничения).
- **Пройти важную тему МНОГОПОТОЧНОСТЬ по <a href="https://github.com/JavaOPs/masterjava">первому уроку Masterjava</a>**. 
   
## Возможные доработки приложения:
-  Для редактирования паролей сделать отдельный интерфейс с запросом старого пароля и кнопку сброса пароля для администратора.
-  Добавление и удаление ролей для пользователей в админке.
-  Перевести UI на <a href="http://angular.ru/">angular.js</a> / <a href="https://vaadin.com/elements">Vaadin elements</a> /GWT /GXT /Vaadin / ZK/ [Ваш любимый фреймворк]..
-  Перевести проект на <a href="http://projects.spring.io/spring-boot/">Spring Boot</a>
-  Перевести шаблоны с JSP на <a href="http://www.thymeleaf.org/doc/articles/petclinic.html">Thymeleaf</a>
-  Сделать авторизацию в приложение по OAuth 2.0 (<a href="http://projects.spring.io/spring-security-oauth/">Spring Security OAuth</a>,
<a href="https://vk.com/dev/auth_mobile">VK auth</a>, <a href="https://developer.github.com/v3/oauth/">github oauth</a>, ...)
-  Сделать подтверждение регистрации пользователя по email c возможностью восстановления пароля.
-  Сделать отображение еды постранично, с поиском и сортировкой на стороне сервера.
-  Перевод проекта на https
-  Сделать desktop/mobile приложение, работающее по REST с нашим приложением.
-  <a href="http://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules/">Показ ошибок в модальном окне редактирования таблицы так же, как и в JSP профиля</a>
-  <a href="http://www.mkyong.com/spring-security/spring-security-limit-login-attempts-example">Limit login attempts example</a>
-  Сделать авторизацию REST по <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT</a>

## Доработки участников прошлых выпусков:
- [Авторизация в приложение по OAuth2 через GitHub](http://rblik-topjava.herokuapp.com)
  - [GitHub, ветка oauth](https://github.com/rblik/topjava/tree/oauth)
- [Авторизация в приложение по OAuth2 через GitHub/Facebook/Google](http://tj9.herokuapp.com)
  - [GitHub](https://github.com/jacksn/topjava)
- [Angular 2 UI](https://topjava-angular2.herokuapp.com)
  - [tutorial по доработке](https://github.com/Gwulior/article/blob/master/article.md)
  - [ветка angular2 в гитхабе](https://github.com/12ozCode/topjava08-to-angular2/tree/angular2)
- [Отдельный фронтэнд на Angular 2, который работает по REST с авторизацией по JWT](https://topjava6-frontend.herokuapp.com)
  - [ветка development фронтэнда](https://github.com/evgeniycheban/topjava-frontend/tree/development)
  - [ветка development бэкэнда](https://github.com/evgeniycheban/topjava/tree/development)
  - в <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT токенен</a> приложение topjava передает email, name и роль admin как boolean true/false,
на клиенте он декодируется и из него получается auth-user, с которым уже работает фронтэнд

#### Жду твою доработку из списка!

### Ресурсы по Проекту
-  <a href="http://pro-cod.ru/uroki-bootstrap-3-0">Уроки Bootstrap 3</a>
-  <a herf="http://www.tutorialspoint.com/spring/index.htm">Spring at tutorialspoint</a>
-  <a href="http://www.codejava.net/frameworks/spring">Articles in Spring</a>
-  <a href="http://www.baeldung.com/learn-spring">Learn Spring on Baeldung</a>
-  <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/index.html">Spring Framework
            Reference Documentation</a>
-  <a href="http://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html/">Hibernate Reference Documentation</a>
-  <a href="http://java-course.ru/student/book2/">Java Course (книга 2)</a>
-  <a href="http://design-pattern.ru/">Справочник «Паттерны проектирования»</a>
-  <a href="http://martinfowler.com/eaaCatalog/">Catalog of Patterns of Enterprise Application Architecture</a>

## Пожалуйста <a href="http://goo.gl/forms/PtFcPqEV5n">ответь на 4-6 вопросов по проекту</a>, это важно!

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Замечания по резюме:
- варианты размещения: google doc + linkedin + hh + ... 
- хорошо, если будет в html формате (нарпимер в https://pages.github.com/). [Например так](https://gkislin.github.io/), [на github](https://github.com/gkislin/gkislin.github.io/blob/master/index.html)
-  возраст и день рождения можно не писать
-  все упоминания Junior убрать!
-  линки делай кликабельными (если формат поддерживает)
-  если нет опыта в IT, обязательно вставьте [Topjava](https://github.com/JavaOPs/topjava/blob/master/cv.md#Позиционирование-проекта-topjava). Все не-IT можно кратко.
- всю выгодную для себя информацию (и важную для HR) распологайте вверху. Название секций в резюме и их порядок относительно стандартный и важный
