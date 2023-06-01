# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSzlObk8tbHdtcXc">Материалы занятия</a>

### Все материалы проекта (в том числе и будущие обновления) останутся доступны без органичения по времени в [Google Drive](https://drive.google.com/drive/u/0/folders/0B9Ye2auQ_NsFflp6ZHBLSFI2OGVEZ2NQU0pzZkx4SnFmOWlzX0lzcDFjSi1SRk5OdzBYYkU)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 11_0_fix.patch
Поправил ссылки [на демо](http://javaops-demo.ru/topjava)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW10

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFX2V5eHRsa09IWHc">HW10</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Если для вашей операционной системы по умолчанию не установлена кодировка UTF-8
и при запуске JDK не задан параметр `-Dfile.encoding=UTF8`,
в тестах и в приложении данные, введенные кириллицей, будут отобращаться неверно.  

Это связано с тем, что ранее в `web.xml` к проекту мы подключили CSRF-фильтр.
**Обратите внимание, что этот фильтр в конфигурации объявлен до encoding-фильтра.** 
CSRF фильтр открывает поток для чтения из HTTP запроса заголовков и параметров,
после открытия потока кодировку сменить уже невозможно.  Исправить просто - в `web.xml`
объявим encoding-фильтр до security фильтра, чтобы он обрабатывал запрос первым.  

### Перенос валидации в ExceptionInfoHandler  
- Для валидации в REST-контроллерах, перед `@RequestBody` ставим аннотацию `@Valid`.  
- В UI контроллерах убираем обработку `BindingResult` - при неверных данных из метода бросается исключение `BindException`, 
  обработку которого Spring MVC делегирует в `ExceptionInfoHandler`. 
- В `ExceptionInfoHandler.bindValidationError` из `BindingResult` получаем массив ошибок валидации и в объекте `ErrorInfo` отдаем эти данные клиенту со статусом `HTTP.UNPROCESSABLE_ENTITY`
  (в  `ErrorInfo` вместо `String details` сделал массив ошибок).  
Начиная со Spring 5.3 `MethodArgumentNotValidException` наследует `BindException`, и можно его
отдельно не выделять — оставим в параметрах аннотации метода только `@ExceptionHandler(BindException.class)`.
- Так как для отображения на фронтенд мы передаем уже не строку, а массив с ошибками
валидации, в `topjava.common.js` для функции отображения уведомления об ошибке 
`failNoty` сделаем склейку элементов этого массива через тег перевода строки `<br>`.  
- Чтобы проверить обработку исключений для `ProfileRestControllerTest`, `AdminRestControllerTest` и
`MealRestControllerTest` создадим тесты, в которых попытаемся отправить запросы на создание
и обновление сущности с невалидными полями.  
В тестах будем проверять:
     - статус ответа `HTTP.UNPROCESSABLE_ENTITY`;
     - тип ошибки `ErrorType.VALIDATION_ERROR`.   
`ErrorInfo` в теле ответа приходит в формате JSON, для проверки его полей подключим в `pom.xml` библиотеку `json-path`.
Достаем из json элемент `type` и проверяем его значение на соответствие `ErrorType.VALIDATION_ERROR.name()`.

### Обработка ошибки дублирования email  
- В файлы локализации добавим сообщения об ошибке.  
- В `AbstractUserController` добавим константу `EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail"` - код ошибки в локализации. 
- В методах создания и обновления пользователей отлавливаем и обрабатываем `DataIntegrityViolationException`- нарушение уникальности при сохранении в БД.
- Обработка неверных данных будет отличаться - в `ProfileUIController` у нас данные приходят из формы Spring (`form:form` в `profile.jsp`), в методе можем принять объект `BindingResult`, в котором есть поддержка сообщений об ошибке.
В `AdminUIController` данные приходят из обычной формы (в модальном окне), поддержки обработки ошибок нет,
поэтому используем Spring класс поддержки локализации `MessageSource messageSource`. 
В методе `ProfileUIController.saveRegister` для повторной регистрации восстанавливаем ` model.addAttribute("register", true)`.
  
### Обработка ошибки дублирования dateTime еды
- В файлы локализации добавим сообщения об ошибке, которая будет выводиться в случае дублирования `dateTime` еды.  
- Делаем обработку ошибок в `ExceptionInfoHandler.conflict`: ищем в сообщениях об ошибке название DB constraints 
  и выводим соответствующее ему сообщение об ошибке (в мапе `CONSTRAINTS_I18N_MAP` ключами являются имена DB constraints, значениями - код в локализации,
`toLowerCase()` для сообщения из DB нужен для HSQLDB).
- Делаем тесты на дублирование `email` и `dateTime`. Обратите внимание, что 
для корректной работы этих тестов нам надо отключить rollback (`@Transactional` в `AbstractControllerTest`): ставим над тестами аннотацию `@Transactional(propagation = Propagation.NEVER)`.  
- Общие методы проверки содержимого `ErrorInfo` выносим в `AbstractControllerTest`

### Валидация email в собственном валидаторе
- Чтобы проверка email происходила при валидации с остальными полями, а не при попытке записи в БД, создадим Spring `@Component` - собственный валидатор `UniqueMailValidator`. 
Он должен имплементировать интерфейс `org.springframework.validation.Validator`: 
   - `#boolean supports(Class<?> clazz)` - поддерживает ли валидатор обработку экземпляров класса;  
   - `#validate(Object target, Errors errors)` - экземпляр объекта для проверки и класс для рапорта об ошибке 
   
- Для универсальной обработки `User` и `UserTo` создадим над ними интерфейс `HasIdAndEmail extends HasId` - валидатор будет обрабатывать все реализующие его классы.  
- В `AbstractUserController.initBinder` добавим наш `UniqueMailValidator` - теперь он будет вызываться Spring MVС наряду со всеми остальными валидаторами. 
- Поправим тесты (при дублирующемся email будет возвращаться статус `HTTP.UNPROCESSABLE_ENTITY`).
- Для корректной работы `UniqueMailValidator` требуется `id` в объекте - добавим скрытое поле в `profile.jsp`
</details>

#### Apply 11_01_HW10_fix_encoding.patch
> - Выставлять кодировку `-Dfile.encoding=UTF-8` лучше в _Help menu -> Edit Custom VM Options_
> - Советы по дополнительным настройкам: 
>   - [Customize IntelliJ IDEA Memory](http://tomaszdziurko.com/2015/11/1-and-the-only-one-to-customize-intellij-idea-memory-settings)
>   - [Increase IDE memory limit in IntelliJ IDEA](https://stackoverflow.com/questions/13578062/how-to-increase-ide-memory-limit-in-intellij-idea-on-mac)
>   - [Slow startup time on Windows](https://youtrack.jetbrains.com/issue/IDEA-211178#focus=streamItem-27-3412218.0-0)

#### Apply 11_02_HW10_validation.patch
> - В [соответствии со спецификацией](http://stackoverflow.com/a/22358422/548473) для поменял `HTTP 400` (ошибка в структуре сообщения) на `HTTP 422` (ошибка в содержании)
> - Сделал тесты и проверку типа ошибки [через jsonPath](https://www.petrikainulainen.net/programming/spring-framework/integration-testing-of-spring-mvc-applications-write-clean-assertions-with-jsonpath/)

#### Apply 11_03_HW10_duplicate_email.patch
> - сделал код(ключ) i18n константой (`EXCEPTION_DUPLICATE_EMAIL`)
> - в `GlobalExceptionHandler` добавил логирование и общий код вынес в `ValidationUtil`

#### Apply 11_04_HW10_duplicate_datetime.patch
> - Реализовать обработку дублирования `user.email` и `meal.dateTime` можно по разному
>   - через [поиск в сообщении `DataIntegrityViolationException` имени DB constrains](https://stackoverflow.com/a/42422568/548473)
>   - через [Controller Based Exception Handling](https://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling)

Первый самый простой и расширяемый (хотя зависить от базы), выбрал его. Для работы с HSQLDB сделал `toLowerCase`.

#### Apply [11_04_2_fix.patch](https://drive.google.com/file/d/1OvUAJON7RWFR_y7iNcQYGyJVxt3i9edB) - можно накатывать после любого патча. Имя Constraint привел в соответствие с именем таблички

> - Для работы с i18n использую `MessageSourceAccessor`.
>   - [get error text from BindingResult](https://stackoverflow.com/questions/2751603/548473)
> - Добавил тесты на дублирование. Отключил транзакционность в тестах на дублирование через `@Transactional(propagation = Propagation.NEVER)`.
>   - [Решение проблемы с транзакционными тестами](https://stackoverflow.com/a/46415060/548473)

#### Apply 11_05_HW10_binder_validation.patch
> - Добавил корректный способ проверки email своим валидатором: проверка делается в контроллерах, а не при сохранении.
>   - [Spring MVC: валидатор и @InitBinder](https://coderlessons.com/articles/java/spring-mvc-validator-i-initbinder)

> Проблема: при REST update нам могут приходить `User/UserTo` без `id` (он корректируется уже после валидации через `ValidationUtil.assureIdConsistent`)
> Для UI мы в профиль добавили скрытый `id`, для REST проблема осталась.
> Для проверки сообщений в тестах в `ExceptionInfoHandler#bindValidationError` сделал `messageSourceAccessor::getMessage` и из сообщений ошибок ушли поля. Починим в патче `11_08_i18n`

#### Apply 11_06_HW10_fix_update_duplicate.patch
Когда в REST запросе приходит `User` без `id`, `UniqueMailValidator` не справляется - `ValidationUtil.assureIdConsistent` вызывается уже после валидации
(добавил в тесты условия, при которых это происходит). Вариантов решения проблемы несколько: 
например выполнять валидацию самостоятельно вручную (при этом приходится переделывать код контроллеров).
Я выбрал вариант без переделки контроллеров - workaround, когда мы в `UniqueMailValidator` дополнительно проверяем путь запроса, 
который можно получить из внедряемого `HttpServletRequest`.

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYms4YUxEMHdxZHM">HW10 Optional: change locale</a>

<details>
  <summary><b>Краткое содержание</b></summary>

- Возможность выбрать локаль должна быть доступна на всех страницах нашего приложения,
сделаем ее выбор в `bodyHeader.jsp`, который включается везде:
в  `navbar` добавим выпадающий список `nav-item dropdown`. Текущую локаль получаем через `${pageContext.response.locale}`.
- Настроим `spring-mvc.xml`: информацию о выбранной локали будет храниться в Cookie - `localeResolver` и 
переключать локаль запросом с параметром `lang` - `localeChangeInterceptor`.
Чтобы при переключении локали оставаться на той же самой странице, используем ссылку `${requestScope['javax.servlet.forward.request_uri']}?lang=..`  
- Сделал js переменную `localeCode`, которая хранит текущую локаль и использую ее для локализации `datetimepicker`
</details>

#### Apply 11_07_HW10_change_locale.patch
- Добавил локализацию календаря `$.datetimepicker.setLocale(localeCode)`
- Вместо смены локали в `lang.jsp` через javascript сделал `href=${requestScope['javax.servlet.forward.request_uri']}?lang=..`
- Добавил [Collapsing The Navigation Bar](https://www.w3schools.com/bootstrap4/bootstrap_navbar.asp)

## Заключительное 11-е занятие

### Локализация:
#### Apply 11_08_i18n.patch
 - Добавил [локализацию Search в datatable](https://datatables.net/reference/option/language)
 - Сделал локализацию ошибок валидации:
    - [MessageSourceAccessor](https://stackoverflow.com/a/20550627/548473)
 - Добавил локализацию `ErrorInfo.type` (код локализации в `ErrorType` и поле `ErrorInfo.typeMessage`)
 - В выводе AJAX ошибки вывожу `errorInfo.typeMessage`
 - [Увеличил ширину высплывающего noty](https://stackoverflow.com/a/53855189/548473) 
 
### [Обработка ошибок 404 (NotFound)](https://stackoverflow.com/questions/18322279/spring-mvc-spring-security-and-error-handling)

Сейчас, при отправке неправильного запроса, сервер отвечает стандартным HTTP.404. 
Настроим `mvc-dispatcher`, чтобы вместо этого генерировалось исключение:
```xml
<init-param>
    <param-name>throwExceptionIfNoHandlerFound</param-name>
    <param-value>true</param-value>
</init-param>
```
Теперь при неправильном запросе сервер будет выбрасывать `NoHandlerFoundException`, который
мы можем обработать в `GlobalExceptionHandler`.  Добавим новый тип `ErrorType.WRONG_REQUEST` и сообщение в файлы локализации.

#### Apply 11_09_404.patch

### Доступ к AuthorizedUser
Сейчас авторизованного пользователя в контроллерах мы получаем из `SecurityContext` с помощью утильных методов `SecurityUtil` 
и отображаем его в `bodyHeader.jsp` из модели, которая добавляется в `ModelInterceptor`-е.
Spring позволяет получить доступ к авторизованному пользователю не обращаясь напрямую к `SecurityContext`:
для его передачи в метод контроллера есть специальная аннотация `@AuthenticationPrincipal`. 
Чтобы Spring обрабатывал эту аннотацию, в `spring-mvc.xml` добавляем специальный резолвер: `AuthenticationPrincipalArgumentResolver`.
Сделал контроллеры профиля юзера через `@AuthenticationPrincipal`.   

Также Spring позволяет доставать авторизированного пользователя в JSP, в `bodyHeader.jsp` будем использовать `sec:authentication property="principal"`.
`ModelInterceptor` больше не нужен, удаляем.

#### Apply 11_10_auth_user.patch
- [@AuthenticationPrincipal](https://docs.spring.io/spring-security/reference/servlet/integrations/mvc.html#mvc-authentication-principal)
  - не стал делать автоподстановку по всем контроллерам (в абстрактных контроллерах проще работать с `SecurityUtil`, чем получать его через `@AuthenticationPrincipal` и передавать параметром)
- [В JSP: the authentication Tag](https://docs.spring.io/spring-security/reference/servlet/integrations/jsp-taglibs.html#_the_authentication_tag)
  - авторизованный пользователь доступен в JSP через tag `authentication`, интерсептор становится не нужным
  
### Кастомизация статуса ошибки
Кастомизируем статус ответа сервера в зависимости от ошибки -  добавим `ErrorType.status` и будем использовать его для 
статуса ответа в `GlobalExceptionHandler`: `ModelAndView.setStatus` и в `ExceptionInfoHandler`: `ResponseEntity.status`.

#### Apply 11_11_error_status.patch

### Защита от XSS (Cross Site Scripting)
> **Попробуйте ввести в любое текстовое поле редактирования `<script>alert('XSS')</script>` и сохранить.**

- <a href="https://forum.antichat.ru/threads/20140/">XSS для новичков</a>
- <a href="https://habrahabr.ru/post/66057/">XSS глазами злоумышленника</a>

Раньше я [реализовывал XSS защиту через `@SafeHtml`](https://stackoverflow.com/a/40644276/548473), пока его не [удалили из hibernate validator](https://hibernate.org/validator/documentation/migration-guide/).
Пришлось сделать собственную аннотацию `@NoHtml` на основе [Sanitizing User Input](https://thoughtfulsoftware.wordpress.com/2013/05/26/sanitizing-user-input-part-ii-validation-with-spring-rest/)
  и [jsoup - Sanitize HTML](https://www.tutorialspoint.com/jsoup/jsoup_sanitize_html.htm)
Все классы, относящиеся к валидации перенес в пакет `ru.javawebinar.topjava.util.validation`
- `password` проверять не надо, т.к. он не выводится в html, а [email надо](https://stackoverflow.com/questions/17480809)
- Сделать общий интерфейс валидации `View.Web` и `@Validated(View.Web.class)` вместо `@Valid` для проверки содержимого только на входе UI/REST. 
При сохранении в базу проверка на безопасный html контент (XSS) повторно не делается.
- [Validation groups in Spring MVC](https://blog.codeleak.pl/2014/08/validation-groups-in-spring-mvc.html)

#### Apply 11_12_XSS.patch

### Swagger2

Swagger это фреймворк для автоматического создания REST-API документации по аннотациям контроллеров Spring MVC. 
Подключим зависимость `springfox-swagger2` и `springfox-swagger-ui` в `pom.xml`.
Сразу же в проект подключается Swagger UI интерфейс, который позволяет отправлять запросы к эндпоинтам REST-API и просматривать документацию.  

Настройка swagger производится в конфигурации `spring-mvc.xml` подключением бина `Swagger2DocumentationConfiguration`.
Чтобы смотреть REST-API документацию мог любой пользователь, в `spring-security.xml` доступ к эндпоинтам Swagger UI открываем всем: 
```xml
<intercept-url pattern="/swagger-ui.html" access="permitAll()"/>
<intercept-url pattern="/swagger-resources/**" access="permitAll()"/>
<intercept-url pattern="/v2/api-docs/**" access="permitAll()"/>
```
`AuthorizedUser authUser` не является реальным параметром методов контроллера, который передается клиентом.
Это авторизированный пользователь, который резолвится Spring Security через `@AuthenticationPrincipal`.
Убираем его из документации запросов через `@ApiIgnore`: Swagger будет игнорировать такие параметры при генерировании документации.
UI контроллеры также исключаем из REST-API, пометив их `@ApiIgnore` на уровне класса.  

Создадим на `login.jsp` кнопку "Swagger REST Api Documentation".

**Внимание: Swagger подключается в проект ОЧЕНЬ просто, а пользу от него для ревью трудно переоценить. Вместо примеров `curl` в выпускных проектах 
предлагаю вам подключить Swagger и в `readme.md` дать ссылку на сгенерированную REST API документацию.** 

- [Setting Up Swagger 2 with a Spring REST API](https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api)
- [Swagger 2 Configuration With Spring (XML)](https://medium.com/@andreymamontov/swagger-2-configuration-with-spring-xml-3cd643a12425)
- [Hiding Endpoints From Swagger Documentation](https://www.baeldung.com/spring-swagger-hiding-endpoints)
> В версиях выше 2.10 и 3.0 появились проблемы с маппингом. Вариант документации c OpenAPI 3.0 смотрите в [Spring Boot курсе](https://javaops.ru/view/bootjava)

#### Apply 11_13_swagger2.patch

---------------------
### ДЗ Optional 2

Обратите внимание в Swagger UI на `Example Value` при
- `POST /rest/admin/users (createWithLocation)` - здесь не должно быть поля `meals`. Пользователь создается без еды, еда управляется своими запросами.
- `POST /rest/profile/meals (createWithLocation)` - здесь не должно быть поля `user`.
Нужно поправит `Example Value`. При этом учтите, что в API у нас есть метод  
`GET /rest/admin/users/{id}/with-meals (getWithMeals)` - вернуть пользователя с едой.

[Hide a Request Field in Swagger API](https://www.baeldung.com/spring-swagger-hide-field)

-----------------------

Платформа Heroku стала платной, вместо нее мы предлагаем зарегистрировать и задеплоиться на собственный выделенный хостинг.  
Тарифы хостингов сейчас самые демократичные: собственный Ubuntu сервер с доступом по ssh: от 130 руб./месяц.  

### Ограничение модификации пользователей
Наше [демо-приложение](http://javaops-demo.ru/topjava) доступно любому и нам нужно защитить стандартные учетные записи User и Admin от попыток их
модификации. Сделаем новый профиль `VDS` и в `UserService` введем флаг `modificationRestriction` - нужна ли нам такая проверка.
Через `Environment` проверяем активный профиль и для профиля `VDS` устанавливаем флаг в *true*.
В методах, изменяющих пользователя, проверяем этот флаг и `id` изменяемой сущности, и, попытке несанкционированных изменений, бросаем `UpdateRestrictionException`.
Отнаследовал это исключение от `ApplicationException` - универсального исключения нашего приложения, в котором можно задавать тип и код локализации ошибки.
В `GlobalExceptionHandler` и `ExceptionInfoHandler` создаем обработчики `ApplicationException`.
Для тестирования исключения при попытке изменение пользователя и админа для профиля `VDS` делаем `VdsRestControllerTest`: 
задаем профиль запуска `@ActiveProfiles(VDS)`, делаем модификацию и проверяем исключение.

#### Apply 11_14_vds_restrict_modification.patch

 - В `UserService` добавил защиту от изменения `Admin/User` для профиля `VDS` (в `UserService` заинжектил `Environment` и сделал проверку на наличие профиля `VDS`)
 - **В выпускном проекте, если только не выставляете его в облако для показа, это НЕ требуется**.   
 - Чтобы тесты были рабочими, ввел профиль `VDS`, работающий так же, как и `POSTGRES`.
 - Добавил универсальный `ApplicationException` для работы с ошибками с поддержкой i18n в приложении (от него отнаследовал `UpdateRestrictionException`)

> Для тестирования с профилем vds добавьте в VM options: `-Dspring.profiles.active=datajpa,vds`

### Деплой приложения на Выделенный сервер

Наше [демо-приложение](http://javaops-demo.ru/topjava) теперь хостится на <a href="https://firstvds.ru/?from=1119260">FirstVDS</a> (SSD диск вместо NVMe)   
Первое открытое занятие [курса Startup](https://javaops.ru/view/startup) обучает регистрации хостинга, установке ПО и деплою приложения

### [Открытое занятие курса Startup](https://github.com/JavaOPs/startup)

#### Apply 11_15_vds.patch
Файл пропертей с паролями не принято держать в репозитории (даже если репозиторий _private_).  
Поэтому вам необязательно комитить его, просто скопируйте на сервер по инструкции в разделе урока "_Деплой war на сервер_"  

#### Apply [11_16_HW_fix_swagger.patch](https://drive.google.com/file/d/1A76XXvZdZCKxeKnVjZ2VkrWAHEQ1iof2)
- Скрываем необязательные поле `id` при POST и PUT запросах через `@ApiModelProperty(hidden = true)` в примерах запроса Swagger. При этом передавать поле в запросе можно.
- `Meal.user` отсутствует в REST API, можно игнорировать: `@JsonIgnore`
- `User.meals` можно было сделать `JsonProperty.Access.READ_ONLY`, но при этом не пройдут тесты `getWithMeals` (maels не будет сериализоваться из ответа сервера для сравнения). Скрыл также через `@ApiModelProperty(hidden = true)`
- Также можно было скрыть нулевое поле `User.meals` при выводе через `@JsonInclude(JsonInclude.Include.NON_EMPTY)`. Но при этом поле исчезнет при запросе `getWithMeals` пользователя с пустым списком еды (например для Guest). Все зависит от бизнес-требований приложения (например насколько API публично и должно быть красивым). Можете попробовать самостоятельно скрыть это поле из вывода для запросов без еды через `View` (или отдельный TO).
----------------------

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQVc2WUdCR0xvLWM">Собеседование. Разработка ПО</a>
- [Темы/ресурсы тестового собеседования](http://javaops.ru/interview/test.html)
- [Составление резюме, подготовка к интервью, поиск работы](https://github.com/JavaOPs/topjava/blob/master/cv.md)
- [Слайды](https://docs.google.com/presentation/d/18o__IGRqYadi4jx2wX2rX6AChHh-KrxktD8xI7bS33k), [Книги](http://javaops.ru/view/books)
- [Jenkins/Hudson: что такое и для чего он нужен](https://habrahabr.ru/post/334730/)

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  [Вебинар: Составление резюме и поиск работы в IT](https://www.facebook.com/watch/live/?v=2789025168007756)
###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="https://drive.google.com/open?id=1QtHfavgIeLEnKA2Yt58XzKOouiLhg6qX">Разбор типовых собеседований (необработанный вебинар)</a>
###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  <a href="http://javaops.ru/view/resources/fromStudyToJob">Вебинар выпускников</a>

-----------------------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png)  Домашнее Задание:
### **Опционально: [зарегистрировать хостинг и задеплоить в него свое приложение](https://github.com/JavaOPs/startup)** 
   
### **Пройдите основы Spring Boot по курсу [BootJava](https://javaops.ru/view/bootjava)**
- **Занятие по миграция на BootJava будет в начале следующей недели**

### **[Выполнить выпускной проект](https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md)**
   - Сроки сдачи указан в выпускном.
   - Если есть проверка или Диплом, после выполнения выпускного [заполни форму проверки](https://docs.google.com/forms/d/1G8cSGBfXIy9bNECo6L-tkxWQYWeVhfzR7te4b-Jwn-Q) 
   - Если проверки или Диплома нет, заполнять не нужно. 
   -  **Возможно [доплатить за ревью выпускного отдельно из JavaOPs профиля](https://javaops.ru/auth/payonline?payId=OG), как за тестовое собеседование: 3450р**

### **Сделать / обновить резюме (отдать на ревью в канал #lesson11 группы slack)**
- **Вставь ссылку на свой сертификат [из личного профиля](http://javaops.ru/auth/profile#finished), немного досрочно:)**
   - [Загрузка сайта на GitHub. Бесплатный хостинг и домен.](https://vk.com/video-58538268_456239051?list=661b165047264e7952)
   - [CSS theme for hosting your personal site, blog, or portfolio](https://mademistakes.com/work/minimal-mistakes-jekyll-theme/)

#### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Замечания по резюме:
   -  **если нет опыта в IT, обязательно вставь [участие в стажировке Topjava](https://github.com/JavaOPs/topjava/blob/master/cv.md#Позиционирование-проекта-topjava). Весь не-IT опыт можно кратко.**
   -  варианты размещения: Pdf в любом облаке, [Google Doc](https://docs.google.com/), LinkedIn, HH,  [еще варианты и рекомендации](https://github.com/JavaOPs/topjava/blob/master/cv.md#составление-резюме)  
Хорошо, если будет в html или pdf формате (например в https://pages.github.com/). [Например так](https://gkislin.github.io/), [на github](https://github.com/gkislin/gkislin.github.io/blob/master/index.html). Возраст и день рождения писать не обязательно
   -  [все упоминания Junior убрать!!](https://vk.com/javawebinar?w=wall-58538268_1589)
   -  линки делай кликабельными (если формат поддерживает)
   -  всю выгодную для себя информацию (и важную для HR) распологайте вверху. Название секций в резюме и их порядок относительно стандартный и важный
   - **Резюме на hh или других ресурсах ДОЛЖНО БЫТЬ ОТКРЫТО ДЛЯ ПРОСМОТРА и иметь телефон для связи**
   - Заполните контакты `skype/telegram/whatsapp`, HR ими пользуется! Почта как контакт очень медленная, телефон может быть не всегда удобен. Вообще `skype/telegram` для программиста - **Must have**.
   - **Добавьте в резюме ссылки на свои проекты в `GitHub` и на задеплоенные на ваш сервер. Не забудьте про выпускной!**.
   - Диплом РФ от Виакадемии о [профессиональной переподготовке](https://ru.wikipedia.org/wiki/Профессиональная_переподготовка) приравнивается ко второму высшему образованию.  В резюме, полагаю, можно указать в высшем образовании
   - Заполнить в [своем профиле Java Online Projects](http://javaops.ru/auth/profileER) ссылку на резюме и информацию по поиску работы (если конечно актуально): резюме, флаги рассматриваю работу, готов к релокации и информация для HR.
   - **Рассылку обновления базы соискателей по HR буду делать в конце мая, можно не спешить**

### **После ревью резюме - опубликовать на ресурсах IT вакансий**
 - [Основные сайты поиска работы](https://github.com/JavaOPs/topjava/blob/master/cv.md#основные-сайты-поиска-работы)

### **Получить первое открытое занятие МНОГОПОТОЧНОСТЬ и пройти эту важную тему в [проекте Masterjava](http://javaops.ru/view/masterjava)**
   - Обучение на MasterJava идет в индивидуальном режиме без проверки ДЗ: старт в любой момент, время прохождение ничем не ограничено
   - Проект, патчи, группа Slack, занятия и видео, разбор ДЗ аналогичны проекту Topjava. 
   - **До 19.05 на курс MasterJava для выпускников действует специальная цена** 

#### Возможные доработки приложения:
-  Разделить `Meal.dateTime` на `date` и `time` и выполнять запрос целиком в SQL
-  Для редактирования паролей сделать отдельный интерфейс с запросом старого пароля и кнопку сброса пароля для администратора.
-  Добавление и удаление ролей для пользователей в админке.
-  Перевести UI на Angular / <a href="https://vaadin.com/elements">Vaadin elements</a> /GWT /GXT /Vaadin / ZK/ [Ваш любимый фреймворк]..
-  Перевести шаблоны с JSP на <a href="http://www.thymeleaf.org/doc/articles/petclinic.html">Thymeleaf</a>
-  Сделать авторизацию в приложение по OAuth 2.0 (<a href="http://projects.spring.io/spring-security-oauth/">Spring Security OAuth</a>,
<a href="https://vk.com/dev/auth_mobile">VK auth</a>, <a href="https://developer.github.com/v3/oauth/">github oauth</a>, ...)
-  Сделать отображение еды постранично, с поиском и сортировкой на стороне сервера.
-  Перевод проекта на https
-  Сделать desktop/mobile приложение, работающее по REST с нашим приложением.
-  <a href="http://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules/">Показ ошибок в модальном окне редактирования таблицы так же, как и в JSP профиля</a>
-  <a href="http://www.mkyong.com/spring-security/spring-security-limit-login-attempts-example">Limit login attempts example</a>
-  Сделать авторизацию REST по <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT</a>

#### Доработки участников прошлых выпусков:
- [Авторизация в приложение по OAuth2 через GitHub, ветка oauth](https://github.com/rblik/topjava/tree/oauth)
- [Авторизация в приложение по OAuth2 через GitHub/Facebook/Google](https://github.com/jacksn/topjava)
- Angular 2 UI
  - [tutorial по доработке](https://github.com/Gwulior/article/blob/master/article.md)
  - [ветка angular2 в гитхабе](https://github.com/12ozCode/topjava08-to-angular2/tree/angular2)
- Отдельный фронтэнд на Angular 2, который работает по REST с авторизацией по JWT
  - [ветка development фронтэнда](https://github.com/evgeniycheban/topjava-frontend/tree/development)
  - [ветка development бэкэнда](https://github.com/evgeniycheban/topjava/tree/development)
  - в <a href="https://en.wikipedia.org/wiki/JSON_Web_Token">JWT токенен</a> приложение topjava передает email, name и роль admin как boolean true/false,
на клиенте он декодируется и из него получается auth-user, с которым уже работает фронтэнд

#### Жду твою доработку из списка!

### Ресурсы по Проекту
-  <a href="https://webformyself.com/urok-1-frejmvork-bootstrap-4-chto-takoe-bootstrap-otlichiya-bootstrap-3-ot-bootstrap-4/">Уроки Bootstrap 4</a>
-  <a herf="http://www.tutorialspoint.com/spring/index.htm">Spring at tutorialspoint</a>
-  <a href="http://www.codejava.net/frameworks/spring">Articles in Spring</a>
-  <a href="http://www.baeldung.com/learn-spring">Learn Spring on Baeldung</a>
-  <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/index.html">Spring Framework
            Reference Documentation</a>
-  <a href="http://hibernate.org/orm/documentation">Hibernate Documentation</a>
-  <a href="http://java-course.ru/student/book2/">Java Course (книга 2)</a>
-  <a href="http://design-pattern.ru/">Справочник «Паттерны проектирования»</a>
-  <a href="http://martinfowler.com/eaaCatalog/">Catalog of Patterns of Enterprise Application Architecture</a>
