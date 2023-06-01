# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFfkpMd2UyWjBsc2JsSE4tRDFkU3BvMktFQkhUN1J6VExxSUUzOHlSR0RhNm8">Материалы занятия</a>

- **Браузер кэширует javascript и css. Если изменения не работают, обновите приложение в браузере (в хроме `Ctrl+F5`)**
- **При удалении файлов не забывайте делать clean: `mvn clean`**
 
### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Правки в проекте

#### Apply 8_0_fix.patch

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW7

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW7](https://drive.google.com/file/d/1h6wg2V9yZoNX7fA7mNA7w7Kxp8IACsIJ)

  <details>
  <summary><b>Краткое содержание</b></summary>

#### Тесты ResourceController
Прежде всего в настройках логирования для класса `ExceptionHandlerExceptionResolver`
установим уровень "debug". Теперь в логах мы сможем увидеть запросы, у которых проблемы с маппингом.  
Чтобы протестировать доступ к ресурсам, создадим `ResourceControllerTest` с единственным тестовым методом.
Класс `MediaType` позволяет указать требуемый тип с помощью фабричного метода `valueOf`.  
Начиная с [Spring 4.3 ожидаемый тип ответа нужно сравнивать с помощью `contentTypeCompatibleWith`](https://github.com/spring-projects/spring-framework/issues/19041), а не `contentType`
(в этом случае кодировка UTF-8 в типе ответа не учитывается в сравнении).

#### Тесты для RootController на еду
Для `RootController` тесты на еду делаем точно так же, как и на `User`, с небольшим отличием.  
Так как `MealTo` - это транспортный объект, который не является Entity и не находится под управлением
JPA, у него нет ограничений по методам `equals / hashCode`, и мы можем
добавить свои (сгенерировать с помощью IDEA). Теперь в тестах объекты `MealTo` мы можем сравнивать
через `equals()`.  
Чтобы убедиться что два списка `MealTo` - ожидаемый, и полученный от контроллера, сравниваются поэлементно
через `equals`, мы можем установить в сравнении брекпоинт и запустить тест в режиме дебага.

#### Реализовать MealRestController
`MealRestController` реализуем аналогично контроллерам пользователей.
В метод `MealRestController#getBetween` с параметрами запроса нужно передать
время и дату начала и конца диапазона, для которого будет найдена еда. Это можно сделать с помощью аннотации `@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)`.
Spring автоматически конвертирует параметры запроса в объекты типа `LocalDateTime`.

В `MealRestControllerTest` нужно обратить внимание на тесты
для методов `get` и `getBetween` контроллера, так как они возвращают список `MealTo`, а не `Meal`.  
Поэтому для сравнения списков еды создадим отдельный `TO_MATCHER` с помощью статического фабричного метода `usingEqualsComparator(MealTo.class)`:
```
public static MatcherFactory.Matcher<MealTo> TO_MATCHER = MatcherFactory.usingEqualsComparator(MealTo.class)
```  
Он будет сравнивать `MealTo` уже не рекурсивно, а с помощью `MealTo#equals()` &mdash; сравнения в методах `assertMatch` переделал с использованием реализаций интерфейса `BiConsumer`:
*assertion* и *iterableAssertion*.  Получается очень гибко (привет, паттерн "стратегия"): для создания матчера мы можем использовать любые собственные реализации сравнений.

Для того чтобы для тестов создать объекты `MealTo`, используем утилитный метод `MealsUtil#createTo`, изменив у него модификатор доступа на *public*.

Для некоторых методов с переменным количеством аргументов IDEA сообщает о небезопасности типов. Чтобы подавить эти
предупреждения, над методами у нас стоят аннотации `@SafeVarargs` (для использования этой аннотации метод должен быть `final`).

Чтобы Jackson мог сериализовать/десериализовать объекты `MealTo`, нам нужно сделать для этого класса сеттеры, или создать конструктор, помеченный специальной аннотацией `@ConstructorProperties`,
в параметры которой передаем поля объекта json, соответствующие аргументам конструктора.

</details>



#### Apply 8_01_HW07_controller_test.patch

- [Persistent classes implementing equals and hashcode](https://docs.jboss.org/hibernate/orm/4.3/manual/en-US/html_single/#persistent-classes-equalshashcode): переопределять `equals()/hashCode()`
  необходимо, если
    - использовать Entity в `Set` (рекомендовано для many-ассоциаций) либо как ключи в `HashMap`
    - использовать _reattachment of detached instances_ (т.е. манипулировать одним Entity в нескольких транзакциях/сессиях).
- Оптимально использовать уникальные неизменяемые бизнес-поля, но обычно таких нет, и чаще всего используется суррогатный PK с ограничением, что он может быть `null` у новых объектов и нельзя объекты сравнивать
  через `equals` в бизнес-логике (например, в тестах).
- [Equals() and hashcode() when using JPA and Hibernate](https://stackoverflow.com/questions/1638723)

------------------------

#### Apply 8_02_HW07_rest_controller.patch
> - В `MealTo` вместо изменяемых полей и конструктора без параметров сделал [`@ConstructorProperties`](https://www.logicbig.com/tutorials/misc/jackson/constructor-properties.html). `Immutable` классы
    всегда предпочтительнее для данных.
- [Паттерн стратегия](https://refactoring.guru/ru/design-patterns/strategy).

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLXZ3OHdac18yZlk">HW7 Optional</a>

  <details>
  <summary><b>Краткое содержание</b></summary>

#### Собственный Spring-конвертер (форматтер) для даты и времени
Spring фраймворк с помощью встроенных конвертеров (реализующих интерфейс `org.springframework.core.convert.converter.Converter`) и форматтеров (интерфейс `org.springframework.format.Formatter`) делает автоматическое преобразование параметров запроса из одного типа в другой.  
В нашем случае параметры фильтрации еды - дата и время - по REST приходят в виде строки, и мы можем добавить свой конвертер или форматтер, чтобы он автоматически приводил их к нужному нам типу.
> - Конвертер Spring преобразует объект одного типа в объект другого типа
> - Форматер преобразует объект типа String в объект нужного типа (при этом может поддерживать локаль)

Сделаем собственные форматтеры для преобразования строки в дату и время `DateTimeFormatters`, добавим в `spring-mvc.xml` бин `conversionService` с перечнем наших форматтеров и сделаем на него ссылку:
```
<mvc:annotation-driven conversion-service="conversionService">
```  
`LocalTimeFormatter` и `LocalDateFormatter` - наши кастомные форматтеры, которые будут парсить строку параметра. Для этого они должны реализовывать
интерфейс `Formatter<Целевой тип>` и переопределять его методы `#parse` и `#print`. Теперь мы можем убрать аннотации `@DateTimeFormat` из аргументов `MealRestController#getBetween`. `conversionService` будет
искать среди форматеров или конвертеров те, которые смогли бы преобразовать параметр-строку в объект соответствующего типа, объявленный в методе контроллера, и в результате будут использованы наши кастомные форматеры.  
Для новой реализации метода `getBetween` теперь создадим несколько тестов - с различным набором параметров (в том числе и с пустыми параметрами).

#### Протестировать сервисы с помощью SoapUI
Помимо SoapUI, для тестирования REST можно использовать команду *curl* через *Git Bash* (этот способ имеет свои недостатки - не поддерживается UTF8).
Для запросов требуется указывать Content-Type, иначе контроллер не сможет обработать запрос.  
Также популярными средствами тестирования REST являются *Postman* и в IDEA: *Tools->HTTP Client*.
> Для тестирования REST у вас должен быть запущен Tomcat с вашим приложением!

</details>


#### Apply 8_03_HW07_formatters.patch

> - Перенес форматтеры в подпакет `web`, т.к. они используются Spring MVC
> - Заменил `@RequestParam(required = false)` на `@RequestParam @Nullable`

#### Apply 8_04_HW07_soapui_curl.patch

> Добавил примеры запросов curl в `config/curl.md`

- <a href="http://rus-linux.net/lib.php?name=/MyLDP/internet/curlrus.html">Написание HTTP-запросов с помощью Curl</a> (для Windows можно использовать Git Bash)
- В IDEA появился отличный инструмент тестирования запросов. Для конвертации
  в [Tools->HTTP Client->Test RESTful Web Service](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html) скопируйте curl без флага `-s`

### Внимание! curl команды, требуемые в ТЗ к выпускному проекту, сделайте в `readme.md`, НЕ НАДО делать в выпускном проекте отдельный `curl.md`.

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [HW7 Optional: getWithMeals + тесты](https://drive.google.com/file/d/13cjenXzWDr52HTTzleomOd-yjPAEAbOA)

  <details>
  <summary><b>Краткое содержание</b></summary>

В нашем приложении у `Meal` есть ссылка на `User`, а в `User` есть ссылка на коллекцию `Meal`.
Таким образом, мы имеем дело с *BiDirectional* циклической зависимостью. При сериализации через Jackson у нас возникнут проблемы, так как он перейдет в
бесконечный цикл при переходе по ссылкам сущностей друг на друга.  
Возможно следующее разрешение циклических зависимостей:

- над полем `Meal.user` добавить аннотацию `@JsonBackReference`, теперь для еды это поле не будет сериализоваться в json;
- над коллекцией `User.meals` добавить аннотацию `@JsonManagedReference`, поле будет сериализоваться.

Теперь для получения пользователя с едой в методах контроллера можно просто вызвать соответствующий метод сервиса.

Для новой функциональности создадим дополнительные тесты. В тестовых данных для пользователей заполним поля *meals*.  
Чтобы сразу проверять пользователя вместе с его едой, создадим дополнительный `UserTestData.WITH_MEALS_MATCHER`, который будет сравнивать сущности с помощью переданных ему интерфейсов сравнения.
Коллекции пользователей с едой мы не реализуем, поэтому `iterableAssertion` также делать не нужно, бросаем `UnsupportedOperationException`.

Так как метод получения пользователя с едой у нас реализован только в профиле datajpa, в тестах перед выполнением метода нужно проверить, что текущий профиль Spring  - `dataJpa`, тесты будут пропускаться для других профилей.
Такую функциональность мы ранее уже реализовывали - внедряем в тестовый класс `Environment` и проверяем активный профиль с помощью `Assumptions#assumeTrue`.

</details>

#### Apply 8_05_HW07_with_meals.patch
#### Apply 8_06_HW07_test_with_meals.patch
> Изменения в AssertJ: `ignoringAllOverriddenEquals` для рекурсивных сравнений не нужен. См. [overridden equals used before 3.17.0](https://assertj.github.io/doc/#assertj-core-recursive-comparison-ignoring-equals)

## Занятие 8:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUmVsM3V6djMzYmc">WebJars. jQuery and JavaScript frameworks</a>

  <details>
  <summary><b>Краткое содержание</b></summary>

**WebJars** — библиотеки на стороне клиента (JavaScript библиотека и/или CSS модуль), упакованные в JAR.

Добавим в наш проект в `pom.xml` дополнительные зависимости - библиотеки JavaScript и css:
- *jQuery* - самая распространенная утилитная JavaScript-библиотека;
- *Bootstrap* - фреймворк CSS-стилей;
- *Datatables* - плагин для отрисовки таблиц;
- *datetimepicker* - плагин для работы с датой и временем;
- *noty* - для работы с уведомлениями;

</details>

#### Apply 8_07_webjars.patch

> - Обновил jQuery до 3.x, Bootstrap до 4.x
    >   - <a href="https://tproger.ru/translations/new-features-of-jquery-3/">Новое в jQuery 3</a>
> - УБРАЛ из проекта <a href="http://dandelion.github.io">Dandelion обертку к Datatables</a>
    >   - не встречал нигде, кроме Spring Pet Clinic;
    >   - поддержка работы с Datatables через Dandelion оказалось гораздо более трудоемкой, чем работа с плагином напрямую.
> - Исключил ненужные зависимости

- Подключение веб-ресурсов. <a href="http://www.webjars.org/">WebJars</a>.
- <a href="http://www.jamesward.com/2012/04/25/introducing-webjars-web-libraries-as-managed-dependencies">Introducing WebJars</a>
- <a href="https://ru.wikipedia.org/wiki/Document_Object_Model">Document Object Model (DOM)</a>
- <a href="https://css-tricks.com/dom/">What is the DOM?</a>
- <a href="https://ru.wikipedia.org/wiki/JQuery">jQuery</a>
- <a href="http://stackoverflow.com/questions/7062775/is-jquery-a-javascript-library-or-framework">Is jQuery a javascript library or framework</a>
- <a href="https://www.datatables.net/">DataTables</a>
- <a href="http://www.sitepoint.com/working-jquery-datatables/">Working with jQuery DataTables</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Bootstrap](https://drive.google.com/file/d/1RHtzw8OQt6guCu6xe3apT7F9EfiX96tr)

  <details>
  <summary><b>Краткое содержание</b></summary>

Front-end нашего приложения будет строиться на основе фреймворка Bootstrap.
> В новой версии Bootstrap 5 из зависимостей исключена библиотека jQuery, и весь необходимый функционал Bootstrap делается на простом JavaScript. Однако JQuery нам нужна для *Datatables* и плагинов, поэтому не стал переходить на 5-ю версию.

По ссылке [Bootstrap Examples](https://getbootstrap.com/docs/4.6/examples/) приведены примеры сайтов на Bootstrap. Из перечня уже готовых шаблонов можно выбрать
подходящий шаблон, скопировать из его исходного кода стили, форматирование и использовать в своем проекте.
- В `spring-mvc.xml` мы должны явно указать маппинг на *WebJars*-ресурсы, с которыми будет работать приложение:
````xml
    <mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/"/>
````  
- В `headTag.jsp`, который у нас сейчас добавляется через `jsp:include` в начало каждой JSP страницы, подтягиваем из *WebJars* нужные нам *css*-ресурсы и иконку для нашего приложения.
- Для отрисовывания стандартных иконок подключается ресурс `<link rel="stylesheet" href="webjars/noty/3.1.4/demo/font-awesome/css/font-awesome.min.css">`.  
  В класс иконок `.fa` добавим `cursor: pointer` - это курсор-рука, который обычно используется для кнопок.
- В стили добавим sticky-footer - это footer, который будет включаться в конце JSP-страниц и приклеиваться к нижней части экрана.
- JSP-страницу со списком пользователей оформим с использованием элементов Bootstrap и добавим иконки на кнопки.
- на странице `index.jsp` форму выбора пользователя поместим в класс Bootstrap *jumbotron* - крупный выносной элемент с большим текстом и большими отступами
- таблицей пользователей в `users.jsp` поместим в аналогичный элемент *jumbotron*
</details>

#### Apply 8_08_bootstrap4.patch

> - [WIKI Bootstrap](https://ru.wikipedia.org/wiki/Bootstrap_(фреймворк))
> - Добавил <a href="https://www.w3schools.com/icons/fontawesome_icons_intro.asp">Font Awesome</a>
    >   - [Map glyphicon icons to font-awesome](https://gist.github.com/blowsie/15f8fe303383e361958bd53ecb7294f9)
> - В `headTag.jsp` в ссылку на `style.css` добавил `?v=2`. Стили изменились, изменяя версию в параметре мы заставляем браузер не брать их из кэша, а загружать заново.

- [Bootstrap](https://getbootstrap.com/)
    - [Navbar](https://getbootstrap.com/docs/4.1/components/navbar/)
    - [Spacing](https://getbootstrap.com/docs/4.1/utilities/spacing/)
    - [Forms](https://getbootstrap.com/docs/4.1/components/forms/)
    - [Sticky footer](https://getbootstrap.com/docs/4.1/examples/sticky-footer/)
- [Документация Bootstrap на русском](https://bootstrap-4.ru/)
- Дополнительно
    - <a href="http://www.tutorialrepublic.com/twitter-bootstrap-tutorial/">Twitter Bootstrap Tutorial</a>
    - <a href="https://www.youtube.com/playlist?list=PLVfMKQXDAhGUxJ4prQSC2K13-YlYj8LgB">Видеоуроки Bootstrap 4</a>
    - [Bootstrap верстка современного сайта за 45 минут](https://www.youtube.com/watch?v=46q2eB7xvXA)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> А где реально этот путь "classpath:/META-INF/resources/webjars"?

Внутри подключаемых webjars ресурсы лежат по пути `/META-INF/resources/webjars/...` Не поленитесь посмотреть на них через `Ctrl+Shift+N`. Все подключаемые jar попадают в classpath, и ресурсы доступны
по этому пути.

> У меня webjars-зависимость лежит внутри ".m2\repository\org\webjars\". С чем это может быть связано?

Maven скачивает все зависимости в local repository, который по умолчанию находится в `~/.m2`. Каталог по умолчанию можно переопределить в `APACHE-MAVEN-HOME\conf\settings.xml`,
элемент `localRepository`.

> WEBJARS лежат вообще в другом месте WEB-INF\lib\. Биндим mapping="/webjars/*" на реальное положение jar в war-e, откуда Spring знает, где искать наш jQuery?

В war в `WEB-INF/lib/*` лежат все jar, которые попадают к classpath. Spring при обращении по url `/webjars/` ищет по пути
биндинга `<mvc:resources mapping="/webjars/ " location="classpath:/META-INF/resources/webjars/"/>`
по всему classpath (то же самое, как распаковать все jar в один каталог) в `META-INF/resources/webjars/`. В этом месте во всех jar, которые мы подключили из webjars, лежат наши ресурсы.

> Оптимально ли делать доступ к статическим ресурсам (css, js, html) через webjars ?

На продакшене под нагрузкой статические ресурсы лучше всего держать не в war, а снаружи. Доступ к ним делается либо
через <a href="https://www.techsupper.com/2017/05/serve-static-resources-from-external-folder-outside-webapps-tomcat.html">конфигурирование Tomcat</a>.  
Но чаще всего для доступа к статике ставят прокси, например <a href="https://nginx.org/ru/">Nginx</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFcGs4b1IyWWF2S2c">AJAX. Datatables. jQuery</a>

<details>
  <summary><b>Краткое содержание</b></summary>

**AJAX** (асинхронный JavaScript и XML) — подход к построению интерактивных пользовательских интерфейсов веб-приложений, заключающийся в "фоновом" обмене данными браузера с веб-сервером.

#### AdminUIController
У нас будут отдельные от REST UI-контроллеры, так как в них будут отличаться обработка исключений, некоторая логика и авторизация.  
В `AdminUIController` метод `#create` будет использоваться как для создания, так и для обновления пользователя в зависимости от значения `id`.

#### Список пользователей
Оформляем таблицу пользователей с помощью js/css библиотеки `Datatables`. Таблица должна иметь id (в нашем случае "datatable"), чтобы к ней можно было обращаться.  
Также на страницу добавляем форму, с помощью которой будем редактировать и добавлять пользователей.
Форма имеет скрытое поле `id`, которое будет использоваться в наших js-скриптах.

#### topjava.users.js
> Код по сравнению с видео изменился! Про изменения я говорю в конце видео и перечислил их после *Краткого содержания*

Для работы AJAX объявляем переменные:
- *ajaxUrl* - адрес нужного endpoint контроллера
- *datatableApi* - объект таблицы `datatable`

Страница html имеет определенный жизненный цикл, в процессе которого с ней совершаются какие-то действия.  
Одно из таких действий - загрузка, после которого мы можем производить какие-то манипуляции на странице.  
С помощью jQuery мы определяем коллбэк-метод, который будет вызываться после загрузки страницы:
```
$(function () {
   ...
```
Строчка
```
datatableApi = $("#datatable").DataTable(
``` 
преобразует HTML-элемент c *id=datatable* в javaScript-объект с помощью метода `DataTable` библиотеки *Datatables*.
Параметр этого метода - объект-конфигурация, который задает опции отображения таблицы и в "columns" задает соответствие колонок таблицы полям приходящего с сервера JSON-объекта пользователей.
Внизу конфигурации добавляется сортировка таблицы по первому столбцу.
После этого вызывается метод `makeEditable()` (он находится в `topjava.common.js`).

#### topjava.common.js

- В `makeEditable` к событию *click* всех объектов HTML c классом *delete* привязываем вызов метода `deleteRow`. Параметром берем аттрибут `id` текущего элемента `$(this)`.

- Метод `add` вызывается из `users.jsp` по нажатию на кнопку "Добавить":  `onclick="add()"`. В нем
    - обнуляются все поля `input` формы `detailsForm`:  `$("#detailsForm").find(":input").val("")`
    - вызывается входящий в Bootstrap метод `modal()`, который преобразует HTML-элемент `id=editRow` в модальное окно. [Botstrap4 Modal](https://getbootstrap.com/docs/4.6/components/modal)

- В методе `deleteRow` делаем AJAX-запросы к серверу и по после их успешного выполнения вызываем обновление таблицы.

- В `updateTable` по AJAX запрашиваем с сервера массив пользователей, в случае успеха очищаем таблицу и заполняем ее данными, полученными с сервера.

- В `save` средствами jQuery сериализуем форму `id=detailsForm` в JSON-объект и методом POST отдаем эти данные. После успешного выполнения запроса закрываем модальное окно и обновляем таблицу.

Intellij IDEA предоставляет нам возможность дебага кода JavaScript. См. видео для примера.

#### Загрузка HTML
По умолчанию при стандартной загрузке страницы с js-скриптами браузер будет:
- Парсить нужную HTML-страницу;
- Как только браузер сталкивается с тегом `<script>`, он останавливает отрисовку страницы и подгружает нужные скрипты. Далее продолжает загружать страницу, останавливаясь на загрузку скриптов, которые
  ему будут встречаться. Пока все скрипты не будут отработаны, браузер всю страницу не отрисует.

Поэтому определение css-стилей обычно размещают в самом начале HTML документа, а JavaScript - в конце документа, чтобы пользователь увидел отрисованную страницу, не дожидаясь загрузки скриптов.
Но более современный подход - загружать JavaScript асинхронно, в фоне. Чтобы скрипты загружались асинхронно, используется специальный атрибут тега *async* -
тогда скрипты и HTML-страница будут загружаться одновременно. Если необходимо соблюдать порядок загрузки скриптов, который определен на странице, вместо
атрибута *async* используется атрибут *defer*. У нас загрузка скриптов сделана через *defer* и вынесена во фрагмент `headTag` - скрипты будут загружаться в
фоне, и это не будет тормозить загрузку страницы.
Для корректного отображения Datatables в Bootstrap добавлены дополнительные `dataTables.bootstrap4`-стили и js-скрипты.

Для удобства дебага js-скрипты вынесены в отдельные файлы и сделан небольшой рефакторинг.
</details>


JSP полезны, если надо с сервера отдать статический html с серверной логикой (условия, циклы), сформированный на основе модели. Для динамической отрисовки таблицы мы будем использовать AJAX запрос в 9-м уроке (работа с Datatables через AJAX).

По дебагу JavaScript из IDEA проверьте:

- в IDEA плагин `JavaScript Debugger`
- [Chrome extension is not required for debugging since 2017.3](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360010507240-where-is-JETBRAINS-IDE-SUPPORT-chrome-extension-it-cant-be-found-anywhere-now-on-the-internet)
- при проблемах с портами [удалите в настройках IDEA файлы `~\AppData\Roaming\JetBrains\IntelliJIdea2020.x\options\web-browsers.xml` и `other.xml`](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360009567459-Webstorm-2020-2-1-Remote-Debugging-do-not-work)

#### Apply 8_09_ajax_datatables.patch

> - Переименовал js-скрипты по [javascript filename naming convention](https://stackoverflow.com/questions/7273316/what-is-the-javascript-filename-naming-convention)
> - `reset()` не чистит скрытые (hidden) поля формы. Сделал очистку полей через `form.find(":input").val("")`
> - Поменял форматирование модального окна: [Botstrap4 Modal](https://getbootstrap.com/docs/4.6/components/modal/)
> - URL с  `/ajax/admin/users` поменял на `/admin/users`

JavaScript

- <a href="https://ru.wikipedia.org/wiki/AJAX">AJAX</a>
- <a href="https://learn.javascript.ru/introduction-browser-events">Введение в браузерные события</a>
- [Скрипты: async, defer](https://learn.javascript.ru/script-async-defer)
- [With defer, in the head](https://flaviocopes.com/javascript-async-defer/#with-defer-in-the-head)
- <a href="http://stackoverflow.com/questions/436411/where-should-i-put-script-tags-in-html-markup/24070373#24070373">JavaScript loading modern approach</a>

jQuery

- <a href="http://ruseller.com/jquery.php?id=124">Событие $(document).ready</a>.
- <a href="http://anton.shevchuk.name/jquery/">jQuery для всех</a>.
- <a href="http://anton.shevchuk.name/javascript/jquery-for-beginners-ajax/">jQuery для начинающих. AJAX</a>.
- <a href="http://anton.shevchuk.name/javascript/jquery-for-beginners-selectors/">jQuery для начинающих. Селекторы</a>.
- [jQuery task from freecodecamp](https://www.freecodecamp.org/map-aside#nested-collapsejQuery)
- <a href="http://api.jquery.com/">jQuery API</a>

DataTables/Bootstrap

- <a href="http://datatables.net/reference/api/">DataTables API</a>
- <a href="http://bootstrap-ru.com/203/javascript.php">Javascript плагины для Bootstrap</a>
- <a href="https://datatables.net/examples/styling/bootstrap4">DataTables/Bootstrap 4 integration</a>
- <a href="https://datatables.net/upgrade/1.10-convert">Converting parameter names for 1.10</a>
- <a href="http://stackoverflow.com/questions/25207147/datatable-vs-datatable-why-is-there-a-difference-and-how-do-i-make-them-w">dataTable() vs. DataTable()</a>

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Что делает код?

```
$('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });
```

На все элементы DOM с классом `delete` вешается обработчик события `click` который вызывает функцию `deleteRow`. См. [селекторы в jQuery](http://anton.shevchuk.name/javascript/jquery-for-beginners-selectors/)

> Как в таблицу `<table id="datatable">` из JSP вставляются дополнительные `div`, поле для поиска, стрелочки для сортировки и т.д. (видны в браузере через `Inspect (Ctrl+Shift+I)` в хроме) ?

JSP отдает html на клиента, в браузере исполняется скрипт `$("#datatable").DataTable(..)`, который модифицирует элемент таблицы и вставляет туда (в элементы DOM html документа) все табличные плюшки.

> Как выполняется сценарий по запросу `http://localhost:8080/topjava/users` ?

**Обязательно смотрите в браузере вкладку Networks**

1. `RootController` принимает GET-запрос, достает из репозитория юзеров и отдает их `users.jsp` для формирования таблицы. На следующем занятии мы поменяем сценарий - данные для таблицы для
   первоначальной отрисовки будут доставаться по AJAX.
2. Сформированный `users.jsp` отдается в браузер клиента.
3. Страница и js-скрипты, подключенные в `users.jsp`, загружаются в браузер.
4. После загрузки страницы по событию `onload` вызывается `$(function ()` из `topjava.users.js`. В ней компонент `#datatable` делается табличкой `DataTable` и вызывается `makeEditable()`
   из `topjava.common.js`.
5. В `makeEditable()` вешается `click` на элементы с классом `.delete`

Все. Приложение ждет дальнейших запросов от клиента.

> Тянет ли Bootstrap за собой jQuery?

Да, до версии 5 Bootstrap зависит от jQuery: http://stackoverflow.com/questions/14608681/can-i-use-twitter-bootstrap-without-jquery#answer-14608772  
В новой Bootstrap 5 он реализует функционал (например модальное окно) без jQuery, собственным JavaScript. Но нам jQuery нужен для таблицы и плагинов.

#### Apply 8_10_refactor_js.patch
>  - Сделал [объект `ctx` (контекст) для глобальных переменных](https://stackoverflow.com/a/5064235/548473)
>  - `datatableApi` присваиваю в функции `makeEditable`, куда ее передаю параметром
>  - Вынес переменную `form = $('#detailsForm')` (инициализирую только один раз)
>  - При удалении строки добавил [подтверждение confirm](https://stackoverflow.com/questions/10462839/how-to-display-a-confirmation-dialog-when-clicking-an-a-link)
>  - [В `jquery.ajax` заменил depricated `success` на `done()`](http://api.jquery.com/jquery.ajax/#jqXHR)
>  - В HTML атрибут id у каждого элемента документа должен быть уникален, а у нас в `users.jsp` дублировались `id`. Переместил атрибут `id` в тэг `<tr>` и в `makeEditable` достаю его через селектор jQuery `closest('tr')`.

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFMTVWaXdWRUZsUEE"> Notifications</a>

  <details>
  <summary><b>Краткое содержание</b></summary>

#### Отрисовка всплывающих подсказок
В `topjava.common.js` создадим всплывающие подсказки. При успешном событии вызывается `successNoty`, при ошибке - `failNoty`.
Ошибку не будем закрывать автоматически, как успешные события, а будем хранить в переменной `failNote`.
Она закрывается по клику на сообщение и при любом успешном событии через вызов `closeNote`.  
В функциях по удалению и сохранению пользователей добавили вызов успешного уведомления.

На случай, если AJAX-запрос завершится неуспешно (например, произойдет исключение на сервере), помимо действия *succes/done* в AJAX-запросе мы можем определить и действие *fail*, которое выполнится при статусе ответа, отличном от 2xx.
Исключения для всех AJAX запросов мы будем обрабатывать одинаково, поэтому, чтобы не дублировать код в каждой функции, определим общую функцию `fail`, которая будет срабатывать для
всех неуспешных AJAX ответов:
```
$(document).ajaxError(function (event, jqXHR, options, jsExc) {
    failNoty(jqXHR);
})
```
</details>


#### Apply 8_11_notification.patch

> - Сделал [защиту от кэширование AJAX запросов в IE](https://stackoverflow.com/a/4303862/548473)
> - Обновил API Noty (3.x), добавил в сообщения font-awesome
> - [Tomcat 8.5.x перестал отдавать в заголовке `statusText`](http://tomcat.apache.org/tomcat-8.5-doc/changelog.html). Отображаем просто `status`
    >   - RFC 7230 states that clients should ignore reason phrases in HTTP/1.1 response messages. Since the reason phrase is optional, Tomcat no longer sends it (statusText).

- <a href="http://ruseller.com/jquery.php?id=2">Обработка ajaxError</a>.
- <a href="http://ned.im/noty/">jQuery notification</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFRVkzcFMwc0hrYmM">Добавление Spring Security</a>

  <details>
  <summary><b>Краткое содержание</b></summary>

Прежде всего в файл `pom.xml` нужно подключить зависимости `spring-security-web` и `spring-security-config`.  
Чтобы Spring Security смог перехватывать и проверять все запросы к серверу, в `web.xml` нужно добавить дополнительный фильтр - `springSecurityFilterChain`.  
Spring Security может работать не только на уровне web, но и на уровне всего приложения, поэтому в `spring-app.xml` импортируем конфигурацию `spring-security.xml`, в которой
описаны настройки безопасности и сконфигурированы бины Spring Security.

В конфигурации `spring-security.xml`:
```xml
<http pattern="/resources/**" security="none"/> <!--отключаем security для статических ресурсов..-->
<http pattern="/webjars/**" security="none"/> <!-- и библиотек webjars -->

        <!--Для всех запросов, которые будут соответствовать паттерну "/rest/**" (запросы к REST контроллерам)-->
        <!--включаем авторизацию, включаем возможность использования expressionLanguage для проверки доступа -->
        <!--и отключаем создание сессии (сессия будет использоваться для UI контроллеров, будем смотреть на нее позже) -->
    <http pattern="/rest/**" use-expressions="true" name="restSecurityFilterChain" create-session="stateless">
        <http-basic/> <!--Будет использоваться базовая авторизация, логин и пароль будут передаваться в заголовке запроса, закодированные в Base64 (открыто) -->

        <!--Чтобы обратиться к endpoint с паттерном "/rest/admin/**", пользователь должен иметь роль ADMIN-->
        <intercept-url pattern="/rest/admin/**" access="hasRole('ADMIN')"/>

        <!--Ко всем остальным endpoint будут иметь доступ только аутентифицированные пользователи-->
        <intercept-url pattern="/**" access="isAuthenticated()"/>

        <!--Выключаем защиту от межсайтовой подделки запросов, будем смотреть на нее и включим позже-->
        <csrf disabled="true"/>
    </http>

        <!-- Конфигурируем хранение пароля в открытом виде (сделаем шифрацию позднее) -->
    <beans:bean name="noopEncoder" class="org.springframework.security.crypto.password.NoOpPasswordEncoder"/>

        <!--Настройка аутентификации пользователей -->
    <authentication-manager>
        <authentication-provider>
            
            <!-- Кодировщик паролей -->
            <password-encoder ref="noopEncoder"/>
            
            <!-- Явно задаем данные пользователей в конфигурации (изменим на следующем уроке на реальные, из базы) -->
            <user-service>
                <user name="user@yandex.ru" password="password" authorities="ROLE_USER"/>
                <user name="admin@gmail.com" password="admin" authorities="ROLE_ADMIN"/>
            </user-service>
        </authentication-provider>
    </authentication-manager>
</beans:beans>
```  
Теперь обратиться к REST-контроллерам приложения можно только с логином и паролем пользователей, которых мы прописали в конфигурации.
Чтобы отправить запрос к REST-контроллеру из любого клиента, нужно к запросу добавить заголовок `Authorization` c логином и паролем в формате login:password, закодированными в формате Base64
или же в `curl` передавать их через параметр *--user*.

</details>

Картинка
https://bs-uploads.toptal.io/blackfish-uploads/uploaded_file/file/412345/image-1602672495860.085-952930c83f53503d7e84d1371bec3775.png

> - Правка к видео: путь в `intercept-url` должен быть полный: `pattern="/rest/admin/**"`
> - В Spring Security 4.x по умолчанию включен CSRF (защита от <a href="https://ru.wikipedia.org/wiki/Межсайтовая_подделка_запроса">межсайтовой подделки запроса</a>). Выключил, включим на 10-м занятии.
> - В Spring Security 5.x по умолчанию пароль кодируется. Выключил, включим на 10-м занятии.

- [Adding a Password Encoder](https://docs.spring.io/spring-security/reference/servlet/appendix/namespace/authentication-manager.html#nsa-password-encoder)

#### Apply 8_12_add_security.patch

> Обновил версии Spring и Spring Data JPA, не забудьте кнопку *Reload All Maven Projects* и `mvn clean`   
> Недавно вышла новая версия Spring 6.0, Spring Data JPA 3.0. Они все используют пакет _jakarta_ и включены в недавний релиз Spring Boot 3.0.
> В конце курса мы мигрируем наше приложение на последний Spring Boot 3.0   

- <a href="http://projects.spring.io/spring-security/">Spring Security</a>
- <a href="https://ru.wikipedia.org/wiki/Протокол_AAA">Протокол AAA</a>
- <a href="https://ru.wikipedia.org/wiki/Аутентификация_в_Интернете">Методы аутентификации</a>.
- <a href="https://en.wikipedia.org/wiki/Basic_access_authentication">Basic access authentication</a>
- <a href="http://articles.javatalks.ru/articles/17">Использование ThreadLocal</a>
- <a href="http://www.baeldung.com/security-spring">Security with Spring</a>
- [Decode/Encode Base64 online](http://decodebase64.com/)

#### Вместо

`curl -v -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' http://localhost:8080/topjava/rest/profile/meals`

#### в выпускных проектах используйте эквивалентный

`curl -v --user user@yandex.ru:password http://localhost:8080/topjava/rest/profile/meals`

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Как можно в браузере сбросить введенный пароль базовой авторизации?

Проще всего делать новый запрос в новой анонимной вкладке (`Ctrl+Shift+N` в Chrome)

> Как по REST определяется залогиненный юзер? Аутентификация происходит при каждом запросе?

<a href="http://stackoverflow.com/questions/319530/restful-authentication">Способы RESTful Authentication</a>. Мы будем использовать 2:
- Basic Authentication для REST контроллеров с аутентификацией при каждом запросе
- cookie + http session для UI-контроллеров на следующем уроке

> Почему при выполнении тестов `AdminRestControllerTest` не задействуется Spring Security?

Для этого в `MockMvc` надо явно добавлять security filter. Будем делать на следующем уроке.

> Почему `@RequestParam` не работает в запросах PUT и DELETE?

По спецификации Servlet API параметры в теле для методов PUT, DELETE, TRACE не обрабатываются (только в url). Можно:
- использовать POST
- передавать параметры в url
- использовать `HttpPutFormContentFilter` фильтр
- настроить Tomcat в обход спецификации.
  См. <a href="http://stackoverflow.com/a/14568899/548473">Handle request parameters for an HTTP PUT method</a>

> Данные между браузером и AJAX гоняются в виде json? Почему в `AdminUIController`  у методов `delete` и `create` нет в аннотациях параметра `consumes = MediaType.APPLICATION_JSON_VALUE` ?

Посмотреть на данные между приложением и браузером можно (**и нужно!**) в браузере (вкладка Network в Инструментах разработчика, F12 в Хроме). Формат зависит от того,
как они отправляются из браузера и из приложения. Данные формы обычно передаются просто параметрами. `APPLICATION_JSON_VALUE` в контроллере нужно, только если параметры отдаются/принимаются в формате JSON.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW08

- 1: Перевести `meals` на `Datatables` (изменить `meals.jsp`, вместо `JspMealController` будет `MealUIController`).
    - 1.1 Реализовать добавление записи еды через модальное окно Bootstrap и удаление еды по AJAX (БЕЗ редактирования).
    - 1.2 При вставке данных по AJAX пропадает все JSP-форматирование, чинить перерисовку НЕ надо. Следующий урок - будем делать Datatables по AJAX и форматирование на стороне клиента.

### Optional
- 2: Перевести работу фильтра на AJAX.
    - [Руководство по выбору между GET и POST](https://handynotes.ru/2009/08/get-versus-pos.html)
- 3: Сделать кнопку сброса фильтра.
- 4: Реализовать `enable/disable` юзера
    - 4.1 Через checkbox в `users.jsp` с сохранением в DB. Неактивных пользователей выделить css стилем. Проверьте, как у вас первоначально (или по F5) отображаются неактивные пользователи (если
      меняете css при `enable/disable`)
    - 4.2 Добавить метод `enable` в `AdminRestController` и протестировать его в `AdminRestControllerTest` и в сервисах

### Optional 2
- 5: Попробуйте после модификации таблицы (например, после добавления записи) обновлять ее также с учетом фильтра (как в [демо](http://javaops-demo.ru/topjava)).
---------------------

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Подсказки по HW08

- 1: `enable/disable` можно реализовать как на уровне репозитория, так и на уровне сервиса через несколько SQL, которые должны быть в одной транзакции (`@Transactional`)
- 2: в `topjava.common.js` следует вынести только общие скрипты (cкрипты еды размещайте в  `topjava.meals.js`, пользователей - в `topjava.users.js`)
- 3: если в контроллер приходит `null`, проверьте в `Network`-вкладке DevTools браузера, в каком формате приходят данные и в каком формате в контроллере вы их принимаете (`consumes`)
- 4: при реализации `enable/disable` лучше явно указывать нужное состояние, чем переключать на противоположное. Если параллельно кто-то изменит состояние, то будет несоответствие UI и DB
 - 5: Ошибка в браузере _DataTables warning: ... Requested unknown parameter ..._ может иметь несколько причин:
   - названия столбца в конфигурации таблицы и поля в json ответе от REST контроллера не совпадают. Проверьте имя в конфигурации DataTable `"columns" [{"data": "..."}, ...` и JSON ответ контроллера 
   - у вас неверный маппинг. При запросе по ajax данных для отрисовки `DataTables` должны приходить JSON данные таблицы, а у вас вместо JSON приходит ,нарпимер, HTML страничка с UI контроллера
   - неверный формат даты - должен быть `DateTimeFormat.ISO.DATE_TIME` с "T" разделителем  
**Смотрим ответ сервера во вкладке _Network_ браузера (F12->Network в Хроме)**  
