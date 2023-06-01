# Стажировка <a href="https://github.com/JavaWebinar/topjava">TopJava</a>

## [Материалы занятия](https://drive.google.com/open?id=0B9Ye2auQ_NsFfk43cG91Yk9pM3JxUHVhNFVVdHlxSlJtZm5oY3A4YXRtNk1KWEZxRlFNeW8)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 10_0_fix.patch
- Поправил [объект javascript](https://learn.javascript.ru/object)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW9

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZnQ2dDZsT0dvYjQ">HW9</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Создаем `MealUIController`, `MealTo` у нас используется только в списке еды для выбора цвета строки в зависимости от `MealTo.excess`. 
Для полей `Meal` добавляем аннотацию форматирования даты `@DateTimeFormat`.
Валидация в приложении происходит после биндинга - сначала из параметров запроса через сеттеры собирается объект, затем делается проверка на поля объекта.
Чтобы валидация калорий на null работала, меняем тип `calories` на `Integer` и не забываем менять тип в `setCalories` -
тогда при `calories=null` объект соберется без `NullPointerException (NPE)`.  

Для работы по Ajax в `MealUIController` добавим GET метод получения еды по ее `id`. Метод 
`#createOrUpdate` реализован так же, как и в `AdminUIController` - метод
принимает `@Valid Meal` и результат валидации `BindingResult`.  

Так как в `RootController` при запросе в методах теперь просто происходит
перенаправление на нужную JSP-страницу, а `Datatables` сама получает необходимые
данные - удалим из параметров и тела `getMeals` ненужные больше `Model` и операции с ней.  

Конфигурация отрисовки таблицы еды в `topjava.meals.js` аналогична `topjava.users.js`.
Так как с сервера `dateTime` в списке еды приходит в формате ISO, 
для отображения ее в таблице определим функцию, которая будет заменять буквау `Т` в 
строке с датой на пробел и отрезать секунды.
В функции отрисовки строки `createdRow` добавим к каждой строке атрибут `data-meal-excess` со значением `excess` - по нему в `style.css` определяется цвет.  

Теперь в `meals.jsp` можно удалить таблицу, она будет отрисовываться с помощью `dataTables`.  
В форме создания и редактирования еды нужно обратить внимание на то, что атрибут `name`
поля формы и название полей `Meal` должны строго
совпадать, иначе биндинг для этих полей происходить не будет.

В js я сделал рефакторинг - вынес общую часть конфигурации и создание `DataTable` в `topjava.common.js`. Теперь в `makeEditable` я передаю не объект `DataTable`, а часть ее конфигурации,
которая склеивается с общей частью через [`jQuery $.extend`](https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN)

### Тесты
В `RootControllerTest.getMeals` добавляем авторизацию и убираем проверку модели.
Во всех тестовых методах `MealRestControllerTest` к запросам добавляем авторизацию 
(по аналогии с тестами пользователей). Также добавляем тест на неавторизованный доступ - 
в нем проверяем, что запрос без авторизации вернет статус `status().isUnauthorized`.  

### Datetime-picker  
Из примера по ссылке к домашнему заданию возьмем пример реализации `datetime-picker`. 
Адаптируем его к нашей странице - отдельно вынесем `start/end` переменные и добавим параметр `formatDate`.  
Чтобы в форме создания и редактирования еды дата отображалась не в формате ISO (без буквы "T"),
в `topjava.common#updateRow` добавим проверку и JSON поле данных `dateTime` форматируем через `formatDate` (вынесли в отдельную функцию).  
Дата из формы будет приходить в параметрах POST в `MealUIController#createOrUpdate` также не в ISO. Поменяем формат в `@DateTimeFormat` на "yyyy-MM-dd HH:mm" 
</details>

#### Apply 10_01_HW9_binding_ajax.patch

Datatables перевели на ajax (`"ajax": {"url": ajaxUrl, ..`), те при отрисовке таблица сама по этому url запрашивает данные. Поэтому в методе `RootController.meals()` нам нужно только возвратить view "meals" (`meals.jsp`) которому уже не нужны данные в атрибутах.

> - JavaScript `i18n[]` локализацию перенес в `i18n.jsp` и передаю туда `page` как параметр
>   - [JSP include action with parameter example](https://beginnersbook.com/2013/12/jsp-include-with-parameter-example)
> - Вынес общий код в `ValidationUtil.getErrorResponse()` и сделал обработку через `stream()`
> - Вместо контекста передаю в `makeEditable` опции `DataTable`.
> - Вынес создание `DataTable` в `topjava.common.js`. В параметр конфигурации добавляю общие опции используя [jQuery.extend()](https://api.jquery.com/jquery.extend/#jQuery-extend-deep-target-object1-objectN)

#### Apply 10_02_HW9_test.patch

#### Apply 10_03_HW9_datetimepicker.patch
> - Вынес форматирование даты в `topjava.common.formatDate()` 
> - Изменил формат ввода dateTime в форме без 'T': при биндинге значений к полям формы в `topjava.common#updateRow()` для поля `dateTime` вызываю `formatDate()`.  
    REST интерфейс по прежнему работает в стандарте ISO-8601
> - В новой версии `datetimepicker` работает ограничение выбора времени `startTime/endTime`
> - Добавил `autocomplete="off"` для выключения автоподстановки (у некоторых участников мешает вводу, у меня не воспроизводится)

- <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>
- <a href="https://github.com/xdan/datetimepicker/issues/216">Datetimepicker and ISO-8601</a>

## Занятие 10:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [Дополнительно: кастомизация JSON (@JsonView) и валидации (groups)](https://drive.google.com/file/d/0B9Ye2auQ_NsFRTFsTjVHR2dXczA)

<details>
  <summary><b>Краткое содержание</b></summary>

В `MealRestController` мы принимаем Meal с датой в формате ISO, а на страницах пользовательского интерфейса нам приходится вручную заменять букву "Т" в дате на пробел, а при биндинге
использовать не-ISO форматтер.  
Решим эту проблему через [JSON Views](http://www.baeldung.com/jackson-json-view-annotation) - с помощью view-аннотаций в контроллерах будет возвращаться даты в разных форматах: поле `dateTime` в формате ISO будет
использоваться для REST, а `dateTimeUI` в формате "yyyy-MM-dd HH:mm" для UI.

Создадим два маркерных интерфейса (у них нет методов, служат лишь для маркировки объектов):
- `View.JsonREST` - для REST контроллера;
- `View.JsonUI` - для UI контроллера;

В `Meal` и `MealTo` сделаем геттеры `getDateTimeUI` с аннотациями `@JsonView(View.JsonUI.class)` и `@JsonFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)`. В сериализованном с `View.JsonUI` объекте не
будет поля `dateTime`, а будет `dateTimeUI`. И наоборот, при `View.JsonREST` будет только поле `dateTime` в формате ISO. Тк. в `Meal` мы принимаем
данные с UI, сделаем сеттер - также поле `dateTimeUI` в формате  "yyyy-MM-dd HH:mm".   
В `MealUIController` в методах, где мы отдаем или принимаем JSON объект укажем `View.JsonUI` - сериализация/десериализация объектов будет делаться через `View.JsonUI`.

По умолчанию в `JsonObjectMapper` включим REST view: `#setConfig(getSerializationConfig().withView(View.JsonREST.class))`. Оно будет применяться везде в контроллерах, где view не указано явно.  

Jackson будет:
- сериализовывать все поля, не помеченные аннотациями;
- поля с аннотациями сериализовывать в зависимости от наличия совпадающих аннотаций в контроллерах;
- если в контроллере view аннотаций нет - использовать сконфигурированный по умолчанию `View.JsonREST`;

Теперь в страницах для еды мы можем удалить функции изменения даты (где "Т" заменялась на пробел).  
В форме создания и редактирования еды атрибуты name поля должны строго соответствовать полям объекта в json - для даты нужно поставить `name="dateTimeUI"` и в `DataTable columns` также поменять
`dateTime` на `dateTimeUI`.

Для кастомных мапперов добавим дополнительные тесты:

- в `JsonUtil` добавим метод, который будет сериализовывать объект с помощью `ObjectWriter`;
- в `JsonUtilTest` добавим тест, который получает `ObjectWriter` для `View.JsonUI.class`, сериализует `adminMeal1` и проверяет наличие в JSON `"datetimeUi"`.

### Кастомизация валидации

Из формы `meals.jsp` данные приходят без поля `user`, и нам пришлось убрать на это поле валидацию `@NotNull`. 
В Spring с версии 3.0 появилась возможность создавать группы валидации - проводить проверку в зависимости от заданной группы. 
Для этого в контроллерах вместо `@Valid` нужно использовать `@Validated` с параметром группы валидации - маркерного интерфейса `ValidateUI.class`.
В `Meal` для всех полей, кроме `user`, в аннотациях валидации укажем эту группу. Эти же аннотации используются при сохранении в базу, поэтому они должны выполняться по умолчанию - 
добавим в группу аннотаций через запятую `Default.class`. Для аннотации `@NotNull` над полем `user` будет применятся только валидация по умолчанию.

</details>

### ВНИМАНИЕ! Патчи `10_04_opt` и `10_05_opt` - не обязательные! Если будете делать- то в отдельной ветке (у меня `opt_view_group`). Это варианты решений, которые не идут в `master`

**Можно не делать в своих выпускных проектах вью и группы валидации. Это усложняет код, который в тестовом задании должен быть простым и понятным для ревью**

- [Что возвращать: Entity или DTOs](https://stackoverflow.com/a/21569720/548473)

#### Apply 10_04_opt_json_view.patch

- [Jackson Annotation ](http://www.baeldung.com/jackson-annotations)
    - [Jackson JSON Views](http://www.baeldung.com/jackson-json-view-annotation)
- [@JsonView: фильтруем JSON](https://habrahabr.ru/post/307392/)
- [Jackson set default view](https://stackoverflow.com/questions/22875642/548473)

#### Apply 10_05_opt_validated_groups.patch

- [Validation Group in SpringMVC](https://narmo7.wordpress.com/2014/04/26/how-to-set-up-validation-group-in-springmvc/)
- [@Validated and Default group](http://web.archive.org/web/20170826153901/http://forum.spring.io/forum/spring-projects/web/117289-validated-s-given-groups-should-consider-default-group-or-not)

> **Починил тесты JDBC, добавив при проверке группы валидации.**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [Рефакторинг: jQuery конверторы и группы валидации по умолчанию](https://drive.google.com/file/d/1tOMOdmaP5OQ7iynwC77bdXSs-13Ommax)

<details>
  <summary><b>Краткое содержание</b></summary>

Чтобы отображать дату и время на страницах в UI формате "yyyy-MM-dd HH:mm", сделаем ее форматирование на стороне клиента с помощью jQuery AJAX конвертера. 
Объекты с сервера приходят в тела запроса (текст), jQuery конвертирует их в JSON объект и добавляет поле `responseJSON` в объект AJAX ответа. 
Стандартный конвертер `JSON.parse(stringData)` мы можем заменить на свой, добавим его в `topjava.meal.js`. 
В нем парсим тело ответа, и, если это объект, для каждого составного объекта в нем (список в json выглядит как массив, нам надо выполнить операцию для каждого элемента массива) через jQuery функцию `each` 
проверяем на наличие в объекте поля `dateTime`. Если поле есть - форматируем его в формат UI. Этот конвертер будет исполняться для всех AJAX запросов "text->json" в первую очередь и поле `dateTime` везде будет попадать в javascript в UI формате.  

### Валидация для сохранения в БД через JPA

Для JPA можно задать группу валидацию, которая будет управлять валидацией перед сохранением в БД. Создадим маркерный интерфейс `View.Persist extends Default` и 
в `spring-db.xml` сконфигурируем `entityManagerFactory`, задав ему эту группу для `pre-persist` и `pre-update`.
Теперь, перед любым сохранением в ДБ, Hibernate будет валидировать все дефолтные поля (тк мы отнаследовались от `Default`), и, дополнительно к ним, поля, помеченные нашим маркерным `View.Persist`,
а в контроллерах будут валидироваться только дефолтные поля. Поле `Meal#user` пометим `@NotNull(View.Persist.class)` - теперь оно будет валидироваться только при сохранении в БД.

</details>

### ВНИМАНИЕ! далее патчи идут после `10_03_HW9_datetimepicker` в ветке `master`

#### Apply 10_04_jquery_converters.patch

- [jQuery: типы данных](https://jquery-docs.ru/jQuery.ajax/#data-types)
- [jQuery: конверторы](https://jquery-docs.ru/jQuery.ajax/#using-converters)

> В конвертере в `JSON.parse()` передаю вторым параметром [функцию парсинга](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/JSON/parse). Код становится сильно проще.    

#### Apply 10_05_persist_validate_group.patch

- [Default Hibernate validation](https://stackoverflow.com/a/16930663/548473). Т.к.  `Persist` наследуется от `javax.validation.groups.Default`, при сохранении учитываются все непомеченные аннотации
  валидации (`Default`) + помеченные `Persist`.

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png)
При `NotNull(groups = View.Persist.class)` валидация происходит непосредственно перед созданием или редактированием на уровне репозиториев? А если бы мы установили без группы то проверка была сразу после получения с UI?

Если `View` никаких нет (что равнозначно `javax.validation.groups.Default`), то бины, в которых есть аннотации валидации, проверяются Spring в контроллерах (если перед параметром стоит `@Valid`) и
Hibernate перед сохранением и обновлением. Через `View` мы можем управлять валидацией. В нашем случае мы включили в `spring-db.xml` указание Hibernate валидировать поля с `View.Persist`. Так
как `View.Persist` наследуется от `Default`, то Hibernate будет также валидировать и поля, на которых нет View.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFTVZyQnBlYUtkNms">Spring Security Taglib. Method Security Expressions.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Модуль Spring Security Taglib содержит набор тегов для jsp-страниц, добавляем в `pom.xml` зависимость `spring-security-taglibs`.
Теперь в jsp появилась возможность через security tag управлять отображением контента в зависимости от авторизированного пользователя.  
- В `bodyHeader.jsp`
  - оставляем доступ к управлению юзерами только для админов - `hasRole('ADMIN')` 
  - делаем `logout` доступным только для авторизированных пользователей
  - переносим сюда из `login.jsp` заголовок для неавторизированных пользователей
- В `login.jsp` кнопки логина "User" и "Admin" отображаем только для неавторизированных пользователей (защита от Back в истории браузера после логина)

Чтобы не-администратор не имел прав к методу `RootController#getUsers` (страница управления пользователями), над соответствующим методом поставим аннотацию `@Secured("ROLE_ADMIN")`.
Вместо нее можно использовать аннотацию `@PreAuthorize("HasRole('ROLE_ADMIN')")`, которая предоставляет более широкие возможности настройки доступа с помощью expression language.  
Попробуйте теперь войти в приложение как пользователь и дернуть страничку [http://localhost:8080/topjava/users](http://localhost:8080/topjava/users).
Чтобы эти аннотации учитывались Spring при поднятии контекста, нужно в `spring-mvc.xml` настроить препроцессор:   
```xml
<global-method-security secured-annotations="enabled" pre-post-annotations="enabled"/>
```
Если мы настроим препроцессор в `spring-security.xml`, который импортируется в `spring-app.xml`, то он не будет работать для классов контроллера. 
Препроцессоры для контроллеров следует задавать в `spring-mvc.xml`.

</details>

#### Apply 10_06_secure_tag_annotation.patch

> - Сделал общий `bodyHeader.jsp` с разделением `isAnonymous/isAuthenticated`
> - В `login.jsp` кнопки входа отображаю только для `isAnonymous`

- [Spring Security Taglib](https://docs.spring.io/spring-security/reference/servlet/integrations/jsp-taglibs.html)
- [Introduction to Spring Method Security](https://www.baeldung.com/spring-security-method-security)

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:

> У нас в `sprng-mvc` подключается `spring-app`, в который подключается `spring-security`. Т.о. `spring-mvc`  в себе содержит код `spring-app` и `spring-security`. Почему тогда аннотация `<security:global-method-security...` из `spring-security` не видна для контроллера из `spring-mvc`?

1. Подключаем один контекст внутрь другого: это про import а не про наследование (тоже самое что скопипастить сюда xml из подключаемого import файла). Наследование означает, что поиск бина, если он не
   найден, происходит в родителе.
2. При создании контекста аннотация `<security:global-method-security...` говорит про то, что все бины текущего контекста, у которых есть аннотации авторизации (`@PreAuthorize/ @Secured/ ..`) будут
   проксироваться с проверкой авторизации. Контексты родителей и детей создаются сами по себе и препроцессоры у них собственные.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFc1JMTE4xVG0zN0U">Interceptors. Редактирование профиля. JSP tag files.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

В заголовке каждой страницы будем отображать данные текущего пользователя. Чтобы не добавлять эти данные в каждом методе контроллера, сделаем interceptor: 
в пакете `web` создадим подпакет `interceptor` с классом `ModelInterceptor`. Если в ответе от Spring MVC есть непустое view и пользователь авторизован, он добавляется в модель.
Interceptor конфигурируем в `spring-mvc.xml`, а в `bodyHeader.jsp` добавляем кнопку профиля пользователя.   

В `profile.jsp` будет форма для редактирования данных пользователя, `userTo` попадает в модель jsp через `ModelInterceptor`. 
Для валидации полей пользователя и отображения ошибок биндинга на jsp странице сделаем [JSP custom tags](http://www.techrepublic.com/article/an-introduction-to-jsp-20s-tag-files/) `inputField.tag`.
Его импорт происходит через `<%@ taglib prefix="topjava" tagdir="/WEB-INF/tags" %>`     
Создаем `ProfileUIController`, который возвращает view `profile` по кнопке из `bodyHeader.jsp` и сохраняет пользователя в методе `updateProfile`. 
Если проверка не прошла, мы возвращаемся обратно в профиль.  
Магия красивого отображение ошибок в форме работает через [Spring MVC Form Validation](http://www.codejava.net/frameworks/spring/spring-mvc-form-validation-example-with-bean-validation-api) - тэги `form:form` в `profile.jsp` и `spring:bind / form:input` в `inputField.tag`. 

В профиле мы можем менять `caloriesPerDay`: добавляем на него сеттер и поле в конструктор и данные пользователя. 
</details>

#### Apply 10_07_interceptor.patch

- <a href="http://www.mkyong.com/spring-mvc/spring-mvc-handler-interceptors-example/">Spring interceptors</a>.

#### Apply 10_08_profile_jsptag.patch

**Глюк Хрома - у меня поле `email` у User показывается неверно (как для admin). В другом браузере, анонимном окне и коде страницы (Ctrl+U) все ок. Я решил локально обновлением Хрома. Еще решения:**

- [В своем браузере](https://www.howtogeek.com/425270/how-to-disable-form-autofill-in-google-chrome)
- [Chrome ignores autocomplete=“off”](https://stackoverflow.com/questions/12374442/548473)
- [Disabling Chrome Autofill](https://stackoverflow.com/questions/15738259/548473)

> - Создал отдельный `ProfileUIController` для операций с профилем (вместо `RootController`)
> - Упростил: в `inputField.tag`: передаю для label код локализации и убрал ветвление
> - В профиль добавил кнопку "Cancel"

- [Bootstrap 4 form validation example](http://nicesnippets.com/snippet/bootstrap-4-form-validation-with-form-all-input-example)
- <a href="http://www.techrepublic.com/article/an-introduction-to-jsp-20s-tag-files/">Делаем jsp tag для ввода поля формы</a>.
- <a href="http://www.codejava.net/frameworks/spring/spring-mvc-form-validation-example-with-bean-validation-api">Spring MVC Form Validation</a>
- <a href="http://www.mkyong.com/spring-mvc/spring-mvc-form-check-if-a-field-has-an-error/">Spring MVC Form: check if a field has an error</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNWpUNktMeGJURmM">Форма регистрации.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

На странице `login.jsp` добавим кнопку регистрации, а в `ProfileUIController` добавим метод `register`, который будет возвращать форму регистрации. 
В `spring-security` этот url делаем доступным для всех пользователей (не перепутайте настройки авторизации REST и UI). 
Для регистрации будем использовать существующую форму `profile.jsp`, куда будем передавать пустой `userTo` и аттрибут `"register"=true`. 
В форму регистрации добавим условие - если флаг установлен, то введенные данные POST запросом уходят на `profile/register` и обрабатываются в `ProfileUIController#saveRegister`.
Если данные успешно прошли валидацию, новый пользователь сохраняется в БД и идет перенаправление на страничку логина с сообщением `app.registered`.

Добавим также регистрацию в `ProfileRestController` и тест на нее: `ProfileRestControllerTest#register`. Обратите внимание, что регистрация делается неавторизированным пользователем:
в настройках REST авторизации в `spring-security.xml` добавляем:
```xml
<intercept-url pattern="/rest/profile" method="POST" access="isAnonymous()"/>
```
</details>

#### Apply 10_09_registration.patch

> - Добавил локализацию
> - При регистрации передаю `username` в `login.jsp` как параметр и делаю `setCredentials` (пароль вводится скрыто, поэтому его не передаю из соображений безопасности). Т.к `setCredentials` требует jQuery, убрал для него `defer`
> - Поменял путь регистрации с `/registered` на `/profile/registered` (в `ProfileUIController` вместо `RootController`)
> - Добавил регистрацию пользователя по REST: `ProfileRestController.register`, тест и пример `curl`. Обратите внимание на хедер `Location`, он отличается от `AdminRestController.createWithLocation`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFZ19lU29VVDRfNXM">Обработка исключений в Spring.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Spring MVC предоставляет возможность стандартной обработки исключений.  
Для нашего исключения `NotFoundException` добавим аннотацию:

```java
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "No data found")
```  

Теперь, при возникновении этого исключения, Spring MVC будет генерировать ответ с указанным в аннотации статусом.  
Добавим тесты, где проверяем статус возврата ответов с `NotFoundException`.

Для обработки исключений в контроллерах создадим `GlobalExceptionHandler`, помеченный аннотацией `@ControllerAdvice` - глобальный обработчик исключений.
В методе `defaultErrorHandler` будем обрабатывать все исключения приложения - помечаем его аннотацией `@ExceptionHandler(Exception.class)`.
В методе мы делаем логирование и передаем на страничку `exception.jsp` модель c причиной (`rootCause`), сообщением и статусом.  
Стек-трейс исключения отобразим в блоке комментариев `<!-- ... -->` - такие комментарии не будут видны пользователю, но их можно будет увидеть в исходном коде страницы
(решение скорее для тестового приложения, тк на продакшене обычно скрываются детали реализации).

### Обработка исключений REST и UI контроллеров

Для REST запросов в случае возникновения исключения будем возвращать информацию о нем в формате json. Для этого создадим класс `ErrorInfo` - он будет представлять информацию об исключении в ответе клиенту.  
Для обработки исключений REST и UI контроллеров, помеченных аннотацией  `@RestController` создадим `ExceptionInfoHandler`.
Аннотация `@RestControllerAdvice` - это комбинация `@ControllerAdvice` and `@ResponseBody` (будут возвращаться не view, а тело ответа). Параметр `annotations = RestController.class` означает, что будут обрабатываться только эксепшены от контроллеров `@RestController`.
Создадим методы для обработки различных типов исключений, которые указываем параметрами аннотации`@ExceptionHandler` над методами.
В методах получаем объект запроса и исключение, логируем исключение и возвращаем созданный `ErrorInfo`. 
Теперь для исключений, возникших по ajax-запросу клиенту будет возвращаться информация в виде json, и ее нужно корректно отобразить в сообщениях UI:
в `failedNoty` принимаем объект `errorInfo` и выводим на экран детали исключения.

</details>

#### Apply 10_10_not_found_422.patch

> [Поменял код 404 (URL not found) на 422 (Unprocessable Entity)](http://stackoverflow.com/a/22358422/548473)

- [Обработка исключений в контроллерах Spring](https://habr.com/ru/post/528116/)
- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Using HTTP Status Codes</a>

#### Apply 10_11_global_exception.patch

> - Перед отображением exception предварительно делаю `ValidationUtil.getRootCause`
> - Добавил локализацию
> - Добавил общий статус `500` в ответ `GlobalExceptionHandler` (на следующем уроке будем его менять, в зависимости от типа ошибки)

- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Global Exception Handling</a>

#### Apply 10_12_controller_advice_exception.patch

**Отображение валидации для формы еды и юзера пропало (`TODO` в коде для HW10). Можно посмотреть сериализацию в `ErrorInfo` при попытке добавить юзера с дублирующимся email.**

> - Добавил в `ErrorInfo` тип ошибки `ErrorType`
> - Добавил обработку неверного запроса (`IllegalRequestDataException`)
> - `@ResponseBody` над методами `ExceptionInfoHandler` заменил на `@RestControllerAdvice`
> - В `ExceptionInfoHandler` удалил `@Order` над методами и добавил над классом:

```
  Methods are matched by closest exception in
  *  @see  org.springframework.web.method.annotation.ExceptionHandlerMethodResolver#getMappedMethod
  *  164: Collections.sort(matches, new ExceptionDepthComparator(exceptionType))
```

> - Добавил в `curl.md` пример с возвращением `ErrorInfo`. Локализация ошибок будет на последнем занятии

- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#errors-and-rest">Errors and REST</a>
- <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc#controller-based-exception-handling">Controller Based Exception Handling</a>
- <a href="https://www.javacodegeeks.com/2013/11/controlleradvice-improvements-in-spring-4.html">@ControllerAdvice improvements in Spring 4</a>
- <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/file/d/1XZXvOThinzPw4EhigAUdo8-MWT_g8wOt">Encoding password. Json READ/WRITE access</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Encoding password

Все кодировщики паролей, которые может использовать спринг, должны реализовывать интерфейс `PasswordEncoder`. Этот интерфейс объявляет два метода - `#encode` - для кодировки исходного пароля, и `#matches` - чтобы сравнить исходный пароль с закодированным (хранящимся в БД).

В последних версиях Spring рекомендуется использовать `DelegatingPasswordEncoder`, который по префиксу в начале пароля определяет, 
по какому алгоритму зашифрован хранящийся в базе пароль и делегирует его обработку соответсвующему PasswordEncoder-у. В `spring-security.xml` меняем `NoOpPasswordEncoder` на `DelegatingPasswordEncoder`. 

> Для тестовых данных в паролях пользователей мы можем указать пароль в незашифрованном виде с добавлением префикса `{noop}` - шифрование не используется

При обновлении или сохранении пользователя нам нужно шифровать пароль и сохранять его в базу в зашифрованном виде: делаем `UserUtil#prepareToSave`, в котором:
- шифруем пароль
- переводить `email` в lowerCase, чтобы он сохранялись в БД в нижнем регистре  

`UserUtils` не является бином Spring, а в методе ему нужен спринговый `PasswordEncoder`, передаем его в параметрах из `UserService`.

> Убрал проверку на пустой пароль - `password` валидируется через `@NotBlank` и не может быть пустым 

В тестах пользователей исключим пароли из сравнения, т.к. один и тот же пароль каждый раз может кодироваться в различные последовательности символов при одном и том же алгоритме шифрования.

#### Json READ/WRITE access

У сущности `User` есть поля:
- `password` - его не нужно отдавать в ответах, но нужно получать из json объекта при создании или обновлении пользователя
- `registered` - его нужно отдавать в ответах, но не нужно считывать из json объекта при создании или обновлении пользователя.

Для этих целей будем использовать специальные аннотации Jackson.  
Над полем `password` ставим `@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)`, а над полем `registered` - `@JsonProperty(access = JsonProperty.Access.READ_ONLY)`.

Тест на создание пользователя не пройдет, так как в пароль теперь не сериализуется (его не будет в `newUser`), а он валидируется в контроллере через `@NotBlank`. 
Специально для тестов создадим утильный метод `UserTestData#jsonWithPassword`, который в `JsonUtil#writeAdditionProps` добавляет пароль в сериализованный json.  
Добавим `JsonUtilTest#writeOnlyAccess` тест на `WRITE_ONLY` пароль, в котором проверяем отсутствие пароля в json при сериализации через `JsonUtil#writeValue` и присутствие его через `UserTestData#jsonWithPassword`.

</details>

#### Apply 10_13_password_encoding.patch

> - Добавил утильный метод `UserService.prepareAndSave`
> - Для InMemory тестов добавил `passwordEncoder` в  `inmemory.xml`

- [Password Encoding](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/password-encoder.html)

#### Apply 10_14_read_write_access.patch

> - Добавил методы сериализации в json `JsonUtil.writeAdditionProps` с дополнительными полями и `UserTestData.jsonWithPassword` для того, чтобы передавать в контроллер пользователя с паролем (который теперь не сериализуется)

### В реальном приложении для управления паролем необходим отдельный UI интерфейс с подтверждением старого пароля - одна из ваших доработок проекта по окончанию стажировки

- [@JsonProperty READ_ONLY / WRITE_ONLY](https://stackoverflow.com/a/12505165/548473)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNDlPZGdUNThzNUU">Межсайтовая подделка запроса (CSRF).</a>

<details>
  <summary><b>Краткое содержание</b></summary>

В конфигурации `spring-security.xml` ранее мы принудительно отключили защиту от CSRF.
Удалим или закомментируем эту строчку. Теперь Spring Security добавит дополнительный `CsrfFilter` в свою цепочку. 
Этот фильтр для каждого не-(GET, HEAD, TRACE, OPTIONS) запроса будет проверять наличие специального заголовка или скрытого поля. 
Чтобы видеть, как обрабатываются запросы, в настройках логирования для класса `CsrfFilter` установим уровень *debug*.  
В `bodyHeader.jsp` для `logout` заменим тип запроса с GET на POST, чтобы для него действовала защита от CSRF (невозможно злонамеренно разлогинить). 
Для POST запросов из JSP форм, вместо добавления в форму поля CSRF токена, мы можем использовать тег Spring `form:form` - Spring Security добавит это поле сам.

Для добавления CSRF токена в AJAX запросы в `headTag.jsp` объявим тэги `meta` с `name="csrf"` и `name= "csrf_header"`, в аттрибуты `content` поместим значения, которые нам предоставляет Spring Security.
Чтобы к каждому AJAX запросу не добавлять CSRF header, в `topjava.common.js`  настроим через jQuery сразу все AJAX запросы. 
Заголовок и токен получим из тэгов `meta` которые мы определили в `headTag.jsp`.

Запросы, которые отправляются на сервер через POST с типом `MediaType.APPLICATION_JSON_VALUE` также защищены в браузере правилом *same origin policy*: 
невозможно сделать из браузера POST запрос с типом "application/json" на сайт с другим доменным именем без специального разрешения. См. также вопрос ниже.

</details>

#### Apply 10_15_csrf.patch

> Убрал `form:form` из ajax запросов: там CSRF работает через header. Проверьте во вкладке браузера `Network`.

**Поломалась UTF-8 кодировка в редактировании профиля и регистрациию (если по умолчанию не UTF-8). В Optional HW10 нужно будет починить.**

- <a class="anchor" id="csrf"></a><a href="https://ru.wikipedia.org/wiki/Межсайтовая_подделка_запроса">Межсайтовая подделка запроса (CSRF)</a>
- [Using Spring Security CSRF Protection](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#servlet-csrf-using)
- [Ajax and JSON Requests](https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#servlet-csrf-include-ajax)
- <a href="http://blog.jdriven.com/2014/10/stateless-spring-security-part-1-stateless-csrf-protection/">Stateless CSRF protection</a>
- Ресурсы:
    - <a href="http://habrahabr.ru/post/264641/">Spring Security 4 + CSRF</a>
    - <a href="http://stackoverflow.com/questions/11008469/are-json-web-services-vulnerable-to-csrf-attacks">Are JSON web services vulnerable to CSRF attacks</a>
    - <a href="https://ru.wikipedia.org/wiki/Правило_ограничения_домена">Правило ограничения домена (SOP)</a>
    - <a href="https://ru.wikipedia.org/wiki/Cross-origin_resource_sharing">Cross-origin resource sharing (CORS)</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> В чем отличие между аннотоацией `@PreAuthorize("hasRole('ADMIN')")` и конфигурацией в jsp: `<sec:authorize access="isAuthenticated()">`, `<sec:authorize access="hasRole('ADMIN')">` ?

Анотация `@PreAuthorize` обрабатывается Spring анологично `@Transactional`, `@Cacheable` - класс проксируется и до-после вызова метода добавляется функциональность. В данном случае перед вызовом
метода проверяются роль залогиненного юзера. JSTL тэг `authorize` выполняет проверку условия в залогиненном юзере внутри jsp.

> Еще раз: почему не нужен csrf для REST и нельзя подделать JSON запрос с вредоносного сайта?

Попробуйте выполнить AJAX запрос из вашего js скрипта на url, домен которого отличается от вашего (например 'http://javaops-demo.ru/topjava/rest/admin/users/{id}'). В консоли браузера
будет `XMLHttpRequest cannot load`... - <a href="https://developer.chrome.com/extensions/xhr">нарушение same origin policy</a>. Формам же разрешается делать submit (через `action=..`) на другой домен,
но невозможно cделать `Content-Type`, отличный от <a href="http://htmlbook.ru/html/form/enctype">стандартных enctype</a> и методов <a href="http://htmlbook.ru/html/form/method">кроме get и post</a>.
Таким образом `consumes = MediaType.APPLICATION_JSON_VALUE` для POST защищает приложение от CSRF.

> Почему использован `BCryptPasswordEncoder`а не `hash(password+salt)`?

[`BCryptPasswordEncoder` automatically generates a salt and concatenates it with the hash value in a single String](http://stackoverflow.com/a/8528804/548473).

> Когда запускается в `GlobalExceptionHandler` метод `defaultErrorHandler`? Когда как в него исключение попадает? Как выбирается, кто обрабатывает исключения: `ExceptionInfoHandler` или `GlobalExceptionHandler`?

`GlobalExceptionHandler` попадает в контекст спринг (через `@ControllerAdvice` его находят в пакете `web`). Далее спринг перехватывает исключения и отправляет в подходящий по исключению
метод `GlobalExceptionHandler`. `ExceptionInfoHandler` помечен `@RestControllerAdvice(annotations = RestController.class)`, он обрабатывает только ошибки из всех контроллеров с
аннотацией `RestController`.

> Откуда берутся в валидации сообщения на русском "должно быть между 10 и 10000"?

Локализация встроена в Hibernate Validation. Смотрите `Ctrl+Shift+N` и `ValidationMessages_ru.properties`.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW10

- 1: Сделать валидацию в `AdminUIController/MealUIController` через `ExceptionInfoHandler`. Вернуть клиенту `ErrorInfo` и статус `HttpStatus.UNPROCESSABLE_ENTITY` (тип методов контроллеров
  сделать `void`). Ошибки валидации отобразить на клиенте красиво (так, как это сделано в [demo](http://javaops-demo.ru/topjava), без локализации полей)
- 2: Сделать валидацию принимаемых json объектов в REST контроллерах через `ExceptionInfoHandler`. Добавить для Rest контроллеров тесты для невалидных данных.
    - <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>
- 3: Сделать обработку ошибки при дублирования email (вывод сообщения "User with this email already exists") для:
    - 3.1 регистрации / редактирования профиля пользователя
    - 3.2 добавления / редактирования пользователя в таблице
    - 3.3 REST контроллеров  
      Варианты выполнения:
        - через `catch DataIntegrityViolationException`
        - обработку ошибок в  `@ExceptionHandler`(https://stackoverflow.com/a/42422568/548473)
        - более сложная реализация - [собственный валидатор](https://coderlessons.com/articles/java/spring-mvc-validator-i-initbinder)
    - Опционально - [сделать локализацию выводимой ошибки](https://www.logicbig.com/tutorials/spring-framework/spring-core/message-sources.html)

### Optional

- 4: Сделать обработку ошибки при дублирования dateTime еды. Сделать тесты на дублирование email и dateTime.
    - [Тесты на DB exception c @Transactional](http://stackoverflow.com/questions/37406714/548473)
    - [Сheck String in response body with mockMvc](https://stackoverflow.com/questions/18336277/548473)
- 5: Сделать в приложении выбор локали (см. http://javaops-demo.ru/topjava)
    - [Internationalization](https://terasolunaorg.github.io/guideline/5.0.x/en/ArchitectureInDetail/Internationalization.html)
    - <a href="http://www.mkyong.com/spring-mvc/spring-mvc-internationalization-example">Spring MVC internationalization sample</a>
    - <a href="https://www.concretepage.com/spring-4/spring-mvc-internationalization-localization">Spring 4 MVC Internationalization</a>
- 6: Починить UTF-8 в редактировании профиля и регистрации, если кодировка по умолчанию у вас не UTF-8 - в форме регистрации введи юзера с русским именем и посмотри на него.
-------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: `ErrorInfo` просто бин для передачи информации на клиента. Кода возврата и ответ настраиваются в `ExceptionInfoHandler`.
- 2: Не дублируйте обработку ошибок `BindingResult`: `result.getFieldErrors()..`
- 3: Можно не создавать собственные эксепшены, а в `ExceptionInfoHandler` ловить стандартные
- 4: `MethodArgumentNotValidException` наследуется от `BindException`. Обратите внимание на импорт класса: `javax.validation.BindException`, не `java.net.BindException`
- 5: Не дублируйте код переключения локали на странице логина и в приложении
- 6: При проблемах с валидацией `Meals` в `MealRestController`, посмотрите на валидацию в `MealUIController.updateOrCreate`
