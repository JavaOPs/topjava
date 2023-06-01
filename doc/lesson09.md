# Стажировка <a href="https://github.com/JavaWebinar/topjava">TopJava</a>

## <a href="https://drive.google.com/drive/folders/0B9Ye2auQ_NsFVWRGbEw1RjJrMjg">Материалы занятия</a>

- **[Запускать браузер с чистым кэшем в режиме ингогнито](https://github.com/JavaOPs/topjava/wiki/IDEA#cache)**
- **При удалении файлов не забывайте делать clean: `mvn clean package`**

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW8

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [HW8 + Optional 2,3](https://drive.google.com/file/d/1ZxmXrhz3K4V-mLkOOrH-JVtl5x0KSpIj)

<details>
  <summary><b>Краткое содержание</b></summary>

#### Перевод таблицы еды на Datatables

- Для удаления и обновления еды мы будем использовать иконки - теперь мы можем удалить `delete` и `update` из файлов локализации.
- Создавать/редактировать еду будем в модальном окне - удаляем форму `mealForm.jsp`
- Для полей фильтрации будем использовать форму Bootstrap "Grid System", поэтому css стили для формы фильтра (`dl, dd, dt`) также можем удалить.

Вместо того, чтобы в `makeEditable` вешать обработчики событий на все элементы страницы с классом `delete`, сделаем обработчик события прямо в JSP: `onclick="deleteRow(${user.id})"`. Функция будет
вызываться при нажатии на кнопку и в нее автоматически будет передаваться `id` пользователя или еды.

> Возможно тут мой выбор расходится с распространенным, где положено отделять html от JavaScript. Я опять склоняюсь в сторону KISS.

Для таблицы еды, в отличие от таблицы пользователей, требуется обновление с учетом параметров фильтрации (*Optional2*), поэтому мы используем различные стратегии обновления для этих таблиц. Функции
обновления таблицы инициализируются в контексте `ctx.updateTable` и вызываться в `topjava.common.js`. Из `updateTable` будем вызывать функцию `updateTableByData(data)`, которая обновляет таблицу
переданными ей данными.  
В `topjava.users.js` код

```
    updateTable: function () {
        $.get(userAjaxUrl, updateTableByData);
    }
```

через jQuery делает AJAX GET запрос и полученные данные автоматически передает в `updateTableByData`. Для еды (в`topjava.meals.js`) `updateTable` по `id=filter` получает форму фильтрации, с помощью jQuery `serialize()`
сериализует ее поля и отправляет запросом GET в `MealUIController#getBetween`. Отфильтрованную еду в коллбэке `done` передаем в `updateTableByData`. Функцию `ctx.updateTable()` вешаем на `onclick`
кнопки фильтрации в `meals.jsp`. И она же будет вызываться из `topjava.common.js` при любом обновлении таблицы.

Вместо `MealJspController` используем `MealUiController`, он маппиться по URL `/profile/meals`, так как еда принадлежит конкретному пользователю (находится в его профиле).

> **Внимание! Не делайте в выпускном проекте путь `/profile/...` к ресурсам, которые НЕ принадлежат пользователю.**

`MealUiController` будет реализован так же, как и `MealRestController`, за некоторыми исключениями:

- для создания или обновления еды будет использоваться метод `#createOrUpdate`, который принимает информацию о еде в параметрах запроса `@RequestParameter`, приходящих из формы
- авторизации у этих контроллеров будут отличаться (будет ниже в этом занятии)

`meals.jsp` изменяем по аналогии с `users.jsp`. Отличие этих страниц - для еды будет использоваться форма фильтрации таблицы, которую создадим с помощью Bootstrap Grid System

> [Bootstrap Grid System](https://getbootstrap.com/docs/4.6/layout/grid/) - экран разбивается на 12 колонок и для каждого элемента страницы мы можем задать сколько колонок он может занимать. Колонки можно настраивать (отступы и т.д...)

По 3 колонки на `startDate` и `endDate` (`col-3`), затем будет 2 колонки отступа (`offset-2`), и далее по 2 колонки на `startTime` и `endTime` (`col-2`).

### Кнопка сброса фильтра

В форму фильтрации добавим кнопку очистки формы. При нажатии на нее будет вызываться функция `clearFilter()`.  
В `$('#filter')[0].reset()` берем массив всех элементов с указанным `id=filter` (нам вернется массив из одного элемента - нашей формы) и сбрасываем все ее поля через `reset()`. После этого обновляем
таблицу еды без учета фильтрации.

</details>

#### Apply 9_01_HW8.patch

- [Grid system](https://getbootstrap.com/docs/4.1/layout/grid/)
- [Difference among col-* in Bootstrap](https://stackoverflow.com/a/19865627/548473)
- [Bootstrap forms](https://getbootstrap.com/docs/4.1/components/forms/)

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:

> Можно ли было удаление делать без перезагрузки таблицы (удалением строки) и для редактирования брать данные со страницы, а не с сервера?

В многопользовательском приложении принято при изменении данных подтягивать все изменения с базы, иначе может быть несогласованность базы и UI (например когда пользователей редактируют несколько
администраторов одновременно). Для еды доставать из базы данные при редактировании нет необходимости, но лучше делать все универсально. В таблице часто представлены не все данные, которые можно
редактировать. Дополнительная нагрузка на базу тут совсем небольшая. Для еды нам при каждом добавлении-удалении-редактировании еще необходимо пересчитывать превышение `excess`.

#### Apply 9_02_HW8_clear_filter.patch

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [HW8 Optional: enable/disable](https://drive.google.com/file/d/1-2ekRtwd60Cbqq4LPEQ_MOsqWcR7d04a)

<details>
  <summary><b>Краткое содержание</b></summary>

Сделаем в `UserService` метод `enable`, который принимает `boolean` (вкл./выкл. пользователя). В методе загружаем из базы нужного пользователя, устанавливаем ему значение `enabled` и записываем
обновленного пользователя обратно в базу. `repository.save(user)` нужен только для JDBC реализации, в JPA изменения сущностей в `@Transactional` методах попадают в базу автоматически. Метод помечен
аннотацией `@Transactional`, чтобы все действия в методе выполнялись в одной транзакции.

> Внимание! Не забываем в выпускных проектах ставить `@Transactional` над методами сервиса, где есть несколько обращений к базе.

Теперь можно вызвать этот метод из контроллеров. В отличие от UI, в REST контроллере используем `@PatchMapping` - сущность изменяется не полностью, а частично.

На странице `users.jsp` для строки таблицы пользователей добавляем атрибут `data-userEnabled`. Для случая, когда этот атрибут будет `false`, в css добавим еще один стиль - теперь строки для неактивных
пользователей будут становиться полупрозрачными.  
На событие `onlick` на чекбокс вешаем функцию `enable($(this), ${user.id})`. В эту функцию передается `this` элемент - чекбокс и `id` пользователя. В функции получаем галочку флага `:checked`, и
передаем ее в POST запросе в контроллер. После успешного выполнения запроса меняем для текущей строки таблицы атрибут `data-userEnabled`, чтобы изменился стиль ее отображения и выводим уведомление.

> Добавил коллбэк `fail` - если обновить базу не удалось, возвращаем флаг в прежнее положение.

### Тесты для REST контроллера и сервиса

Создадим тест `AbstractUserServiceTest#enable`: в нем сначала деактивируем пользователя, получаем его из базы и проверяем что он действительно не активен. Затем активируем этого же пользователя и
снова проверяем - теперь он должен быть активным.

В тесте `AdminRestControllerTest#enable` делаем PATCH запрос деактивации, проверяем статус ответа и отсутствие контента. После чего получаем этого пользователя из базы и проверяем, что он
действительно деактивирован.

</details>

#### Apply 9_03_HW8_enable_disable.patch

>  В тестах сервисов `AbstractServiceTest` базу восстанавливаем после теста (при старте приложения она популируется, если последний тест в сервисах ее меняет, тесты контроллеров могут не пройти)

Примечание: [в публичном API выполнять PATCH с параметрами нельзя](https://stackoverflow.com/questions/64390768/can-i-use-query-parameters-with-http-patch-method). But in a situation where your API is
only used by front ends that you control (for example, only called via your java script client downloaded from your web servers), and if you don't need to use any intermediate components (like a web
cache) in the middle, then you might get away with it (данные у нас не кешируются).

## Занятие 9:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYlRkc2NGRGVydk0">Spring Binding</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Spring Data Binding - функциональность Spring преобразовывать данные в параметрах или теле запроса в экземпляры класса. Формат данных может быть как `application/x-www-form-urlencoded` (из html формы), так и
JSON.  
Для удобства обмена данными между frontend и сервером применяется объект (и паттерн) Transfer Objects. Объект TO содержит только те поля, которые нужны UI и в процессе работы приложения происходит
конвертация entity в TO и обратно.  
Создадим объект `UserTo` только с теми полями, которые может редактировать администратор и пользователь
(ввод ролей админом у нас не будет реализован, по окончанию стажировки можете доработать наше приложение самостоятельно). В `AdminUIController#create` вместо набора параметров будем принимать `UserTo`

- Spring автоматически извлечет из запроса нужные данные и, используя отражение, сделает из них объект. Для этого **объект должен иметь конструктор без параметров и сеттеры**.

</details>

#### Apply 9_04_binding.patch

> Перенес `ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY` в `ru.javawebinar.topjava.util.UsersUtil`

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFd2ZvcS1pSjdMQlU">Реализация update</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Записи в таблице будут обновляться с помощью js функции `updateRow`. В этой функции:

- Запрашиваем у сервера данные о редактируемой сущности (на случай если к этому моменту данные уже были кем-то изменены).
- Функцией `.each` проходимся по всем полям принятых JSON данных, ищем в форме модального окна (`form = $('#detailsForm')`) соответствующие `input` элементы:
  `form.find("input[name='" + key + "']")`
- Присваиваем полям значения `.val(value)`. Таким образом мы заполняем форму актуальными данными пользователя.
- Открываем модальное окно с нашей формой

Переименовываем `AdminUIController#create` в `createOrUpdate`. Если в пришедшем объекте `id = null`, в базе создается новая сущность, иначе обновляем существующую с пришедшим `id`.  
Дополнительно создаем `UserUtil#updateFromTo`, который обновляет сущность данными TO.

В `topjava.common.js` методе `save` после обновления или добавления пользователя в базу в коллбэке `done` повторно запрашиваем с сервера список всех пользователей и обновляем таблицу.

</details>

#### Apply 9_05_update.patch

> - Сделал интерфейс `HasId` от которого наследуются `BaseTo` и `AbstractBaseEntity`
> - Сделал проверку `id` в `ValidationUtil` на основе `HasId`
> - Сделал в `ProfileRestController` обновление своего профиля через `UserTo` (нельзя изменять себе роли) и поправил тест

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFLXp5MTFDMEY5WFE">Spring Validation</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Проверка на корректность данных задается с помощью аннотаций валидации над полями объекта. Для большинства таких аннотаций в скобках можно указать дополнительные параметры, по которым будет
осуществляться проверка. Также можно переопределить стандартное уведомление, которое будет сообщать о неверных данных. Эти аннотации нельзя использовать непосредственно при вводе данных, так как формы
ввода данных находится на стороне клиента, а проверка происходит на сервере.  
В `AdminUIController#createOrUpdate` перед `UserTo` укажем аннотацию `@Valid` (запустить функционал валидации) и добавим параметр `BindingResult` - результат валидации. Если в результате есть ошибки,
склеим их в строку и отдадим клиенту со статусом `UNPROCESSABLE_ENTITY`. На стороне клиента в `failedNote` будет выведено сообщение об ошибке (обработчик всех ошибок по AJAX задаем
в `$(document).ajaxError`), к тексту уведомления добавим ответ сервера.

</details>

#### Apply 9_06_validation.patch

> - `responseJSON` не выводится в случае его отсутствия (например при попытке добавить пользователя с дублирующимся email)

- <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#validation-beanvalidation">Spring Validation.</a>
- <a href="http://beanvalidation.org/">Bean Validation</a>
- <a href="https://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules">Валидация формы по AJAX.</a>
- <a href="http://stackoverflow.com/questions/14730329/jpa-2-0-exception-to-use-javax-validation-package-in-jpa-2-0#answer-17142416">JSR-303, 349</a>
- <a href="https://dzone.com/articles/spring-31-valid-requestbody">@Valid @RequestBody + Error handling</a>
- [Java Bean Validation Basics](https://www.baeldung.com/javax-validation)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFcW1qeTVFdS1BdHM">Перевод DataTables на Ajax</a>

<details>
  <summary><b>Краткое содержание</b></summary>

В методе `DataTables` есть параметр конфигурации `ajax.url`. Если он присутствует, по этому URL выполнится AJAX запрос и таблица будет инициализирована полученными данными.

- При этом JSP больше не требуется данные для таблицы (модели в контроллерах)
- Отрисовывать таблицу в JSP тоже больше не нужно, она строится автоматически, используя конфигурацию `DataTables`.
- В конфигурацию `DataTables` добавляем  `ajax.url` - ендпойнт, по которому запрашиваются данные
- В `columns` добавляем метод `render` - функция отображения содержимого ячейки таблицы. Так как с сервера дата `registered` приходит в формате ISO, при отображении содержимого ячейки нужно
  предварительно произвести ее конвертацию.
- Функции отрисовки кнопок удаления `renderDeleteBtn` и редактирования `renderEditBtn` будут общими для страниц пользователей и еды.
- В конфигурации `DataTable` можно настроить функцию отображения всей строки `createdRow`. Если пользователь в этой строке неактивен, задаем ей соответствующий css стиль.

</details>

#### Apply 9_07_datatable_via_ajax.patch

> - Перешли на [параметры Datatables в формате 1.10](https://datatables.net/upgrade/1.10-convert)
> - В `makeEditable()` больше нет манипуляций c DOM, которые требуются делать ПОСЛЕ отработки плагина `datatables`, поэтому нам не обязательно вызывать ее в коллбэке `initComplete`. Отображения строки меняем в параметре конфигурации `createdRow`

- [DataTables Ajax](https://datatables.net/manual/ajax)

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос:

> Что за дополнительный параметр (который каждый раз инкрементируется) появляется при запросе datatables данных по ajax (например `http://localhost:8080/topjava/ajax/admin/users/?_=1496156621129`) ?

Это защита `datatables` от кэширования запроса браузером. При изменении js, css и других статический ресурсов, также полезно добавлять в запрос версию, чтобы данные не брались из кэша (особенно когда
приложение уже вышло в продакшен).

#### Apply 9_08_js_i18n.patch

> - Добавил [простую интернационализацию в JavaScript](https://stackoverflow.com/questions/6218970/resolving-springmessages-in-javascript-for-i18n-internationalization).

- на стороне сервера формируется `i18n` JavaScript массив с значениями, который затем используется для интернационализации в браузере

> - в модальном окне заголовок подменяется через `$('#modalTitle').html(..title)`

> Для тестирования локали
> - [можно поменять `Accept-Language`](https://stackoverflow.com/questions/7769061/how-to-add-custom-accept-languages-to-chrome-for-pseudolocalization-testing). Для хрома в `chrome://settings/languages` перетащить нужную локаль наверх.
> - можно поставить [Locale Switcher](https://chrome.google.com/webstore/detail/locale-switcher/kngfjpghaokedippaapkfihdlmmlafcc) хром плагин

- <a href="http://stackoverflow.com/a/6242840/548473">JavaScript internationalization</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUmhUTms1WnhTeHc">Форма логина / логаут.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

Добавляем в `spring-security.xml` еще одну security-конфигурацию `<http>`. URL `/admin/**` будет доступен только с ролью `ADMIN`, все остальные URL будут доступны только аутентифицированным
пользователям. В конфигурации указываем, что аутентификация будет проходить через Spring стандартные login и logout формы.  
`RootControllerTest` перестал работать - Spring Security при каждом запросе к контроллеру будет делать перенаправление на страницу `login` - вместо ожидаемого в тестах статуса ответа HTTP.200 с
сервера будет возвращаться ответ со статусом HTTP.302 - redirect. Исправим тесты, указав ожидаемый `forwardedUrl`.

#### Своя страница login

Страница логина должна быть доступна для любого не аутентифицированного пользователя. Для этого в `spring-security.xml` добавляем путь `"/login"` с доступом для всех: `permitAll`.  
И настраиваем `form-login`:

- указываем ссылку на страницу логина;
- стандартные страницы, на которые будет осуществляться переход после успешного или неуспешного логина
- `login-processing-url` - это путь, по которому Spring будет обрабатывать запросы на сервер от формы логина.

Cоздадим собственную страницу логина по Bootstrap шаблону - `login.jsp`. На ней расположена форма логина, в `action` которой указываем `login-processing-url` - путь к обработке Spring Security POST
запроса.  
На странице сделаем элемент для отображения информации об ошибке в случае неправильных аутентификационных данных. Spring Security кладет в HTTP сессию сообщения об ошибке и при неуспешном
логине (`authentication-failure-url="/login?error=true"`) оно отображается на странице.  
В `RootController#root` перенаправим запросы пользователей к руту ("/") на страницу еды: `redirect:meals`. Чтобы такие запросы обрабатывались корректно и при обращении к корню происходил редирект,
нужно удалить или переименовать `index.html/index.jsp`.  
И еще добавим пример обработки статических ресурсов - `test.html`. Чтобы обратиться к нему из браузера, в `spring-mvc.xml` добавим `<mvc:default-servlet-handler/>`, который мапит запросы к статическим html страницам.   
В `RootController` добавляем метод, который будет обрабатывать запросы по url-паттерну "/login" и перенаправлять их на страницу `login.jsp`. Информацию о неуспешной аутентификации или сообщения вместо
атрибутов передаем в параметрах запроса (`param.error/message`).

</details>

#### Apply 9_09_min_form_login.patch

> Добавил функциональность logout

- [Минимальный form-login](https://docs.spring.io/spring-security/reference/servlet/configuration/xml-namespace.html#ns-minimal)
- <a href="https://docs.spring.io/spring-security/site/migrate/current/3-to-4/html5/migrate-3-to-4-xml.html#m3to4-xmlnamespace-form-login">Migrating &lt;form-login&gt;</a>

#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Мои вопросы:

- Почему при логине как admin еда отдаются для user?
- Почему при логине как user не отображается список пользователей?
- Почему еда не редактируется?

> Подсказка: поглядите вкладку Network в браузере.

#### Apply 9_10_jsp_form_login.patch

> Рефакторинг
> - В `login.jsp` вместо атрибутов достаю параметры запроса (`param.error/message`).
> - Сделал i18n описания приложения
> - При нажатии кнопок `Зайти как ...` сделал вход в приложение

- [Собственный form-login](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html#servlet-authentication-form-custom)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFYTA4aVN4bWxzbEU">Реализация собственного провайдера авторизации.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

#### Реализация собственного провайдера авторизации.

Сейчас пользователи приложения и их аутентификационные данные жестко прописаны в конфигурации `spring-security.xml`, приемлимо только для тестового использования.  
Чтобы получать данные креденшелов из базы вместо простого `user-service` настроим `jdbc-user-service`.  
Для этого прямо в конфигурации указываем SQL запросы, которые Spring будет выполнять к базе для получения креденшелов и данных аутентифицированного пользователя.  
Если аутентификация прошла успешно, Spring Security в `ThreadLocal` (стратегия хранения по умолчанию) сохраняет для текущего потока объект `Authentication`. Данные аутентифицированного пользователя
можно достать из `ThreadLocal` с помощью `SecurityContextHolder.getContext().getAuthentication()`.  
Раньше в проекте для получения этих данных использовался утильный класс `LoggedUser`, теперь он переименован в `SecurityUtil`. В этом классе определены методы доступа к залогированному пользователю:

- `safeGet()` - возвращается или `AuthorizedUser` или `null`, если аутентифицированного пользователя нет.

Заменим `jdbc_user_service` и SQL в конфигурации `spring-security.xml` кодом Java: в `<authentication-provider user-service-ref="userService">`
задаем бин, который реализует интерфейс Spring Security `UserDetailsService` и реализуем его метод `#loadUserByUserName`. В этот метод передается значение `username` из формы логина - в нашем
приложении это `email`. Если через `UserRepository#getByEmail` пользователь не найдется в базе, выбросим стандартное Spring Security исключение `UsernameNotFoundException`. Метод `#loadUserByUserName`
должен возвратить класс - данные аутентифицированного пользователя - который имплементирует Spring Security интерфейс `UserDetails`. Вместо самостоятельной реализации всех методов
интерфейса `UserDetails` проще всего сделать класс (`AuthorizedUser`), отнаследовав его от стандартной Spring Security имплементации этого
интерфейса `org.springframework.security.core.userdetails.User`
и в конструкторе передав ему все необходимые данные.   
Роли он принимает как `Collection<? extends GrantedAuthority> authorities`, поэтому `enum Role` отнаследуем от `GrantedAuthority` и реализуем его метод `getAuthority()`:
[права на основе ролей принято задавать с префиксом "ROLE_"](https://stackoverflow.com/a/19542316/548473). 
Класс `AuthorizedUser` задает в нашем приложении аутентифицированного пользователя и мы будем хранить в нем `UserTo` - данные, которых нет в
стандартном `org.springframework.security.core.userdetails.User`, в частности `id` и `caloriesPerDay`.  
Есть еще много разных способов реализации `UserDetails`, которые можно найти в интернете. На мой взгляд наше текущее решение самое простое.

Еще - объект `AuthorizedUser` будет хранится в сессии (про нее видео ниже) и для этого ему требуется сериализация средствами Java. Это наследование его и всех классов-полей от маркерного
интерфейса `Serializable` и необязательный, но желательный `serialVersionUID`.

> **Будьте внимательны в выпускных проектах с `Serializable`. Им нужно помечать ТОЛЬКО объекты, которые будут храниться в сессии**

</details>

#### Apply 9_11_auth_via_user_service.patch

> - В `UserService` добавил `@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)`, т.к. без этой аннотации для кэширования создается прокси над интерфейсом `UserDetailsService` (см. следующее видео по типам проксирования Spring). Можете проверить, что без этой аннотации приложение не поднимется.
> - `GrantedAuthority` это "разрешение" или "право". Если оно дается на основе роли, в Spring Security принято использовать префикс `ROLE_`. При этом сама роль не должна иметь префикс.
>    - [Role and GrantedAuthority](https://stackoverflow.com/a/19542316/548473)

- [UserDetailsService](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html)
- [serialVersionUID value](https://stackoverflow.com/a/605832/548473)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9.  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFT2Qya2V4N0kzWWM">Принцип работы Spring Security. Проксирование.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

### Принцип работы Spring Security. Проксирование.

Одна из основных функциональностей Spring Core, кроме IOC контейнера и связываний, это проксирование. Чаще всего оно задается аннотациями: при поднятии приложения и создании контекста на основе
пре-процессоров Spring анализирует аннотации бинов и, находя указание к проксированию, создает прокси (обертку) над исходным объектом. В контекст Spring попадает уже не исходный инстанс класса, а его
прокси. В Spring используется две стратегии проксирования:

- на основе JDK 4 [Dynamic Proxy API](https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html) - прокси-объект создаются как обертка ко всем интерфейсам, которые имплементирует
  сервис.
- на основе CGLib - когда нет интерфейсов, прокси объект создается на уровне модификации байт-кода класса.

По умолчанию, если класс имплементирует интерфейсы, проксирование происходит по стратегии Dynamic Proxy и в прокси мы имеем только методы интерфейсов. Стратегию проксирования можно поменять на CGLib, задав явно в
конфигурациях параметра `proxy-target-class` или, как сделали мы, аннотацию `@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)`. В результате прокси нашего `UserService` сделано через CGLib ив нем доступны все его методы.
Второй путь - создать и реализовать интерфейс, в котором есть всем методы класса.

Работа Spring Security основывается на цепочке Security-фильтров. HTTP запрос, перед тем как поступить в Dispatcher Servlet проходит цепочку фильтров (стандартная функциональность Servlet API). Spring
предоставляет собственную цепочку стандартных фильтров и возможность отключать/заменять любые фильтры из этой цепочки или внедрять в нее собственные фильтры.

</details>

- <a href="https://ru.wikibooks.org/wiki/Spring_Security/Технический_обзор_Spring_Security">Технический обзор Spring Security</a>
- <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-proxying">Типы проксирования</a>
- <a href="http://samolisov.blogspot.ru/2010/04/proxy-java.html">Dynamic Proxy API</a>
- [Security фильтры](https://docs.spring.io/spring-security/reference/servlet/configuration/xml-namespace.html#filter-stack)
- [Основы работы с Spring Security от Eugene Suleimanov](https://www.youtube.com/watch?v=7uxROJ1nduk)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFU3hMR0o4eGNoUmc">Spring Security Test</a>

<details>
  <summary><b>Краткое содержание</b></summary>

### Spring Security Test

Для тестирования контроллеров, к запросам которого требуется аутентификация, будем использовать библиотеку `spring-security-test`. Для этого в `pom.xml` подключим эту зависимость и в `MockMvc`
добавить аналог цепочки security-фильтров: `.apply(springSecurity())`. Если сейчас запустить тесты, то они упадут, потому что в `mockMvc` происходит аутентификация, а в запросах, которые тесты
посылают серверу креденшелов пользователя нет.  
Чтобы пройти аутентификацию в REST контроллерах, в каждом запросе укажем креденшелы пользователя через `...with(userHttpBasic(ADMIN))`.
`TestUtil#userHttpBasic` - наш утильный метод, который добавляет к запросу базовую аутентификацию (заголовок `Authorization` с данными *логина:пароля*).

</details>

#### Apply 9_12_spring_security_test.patch

> - Cделал "честную" аутентификацию для `RootControllerTest` (через `TestUtil#userAuth`)
> - Cделал `mockAuthorize` для `SpringMain`, в который не попадают фильтры

- [Spring Security Testing](https://docs.spring.io/spring-security/reference/servlet/test/index.html)
- [Setting Up MockMvc and Spring Security](https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/setup.html)
- [HttpBasic авторизация](https://docs.spring.io/spring-security/reference/servlet/test/mockmvc/http-basic.html)
- [Тестирование контроллеров в Spring Boot](https://javaops.ru/view/bootjava/lesson06#test)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 11. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFUzNFanF6MGZGNHc">Cookie. Session.</a>

<details>
  <summary><b>Краткое содержание</b></summary>

### Cookie. Session.

Если в браузере с помощью инструментов разработчика внимательно посмотреть на запросы, которые уходят от клиента на сервер - можно увидеть что к каждому запросу прикрепляется Cookie `JSESSIONID`. 
Эта Cookie - ключ к мапе, которая хранится в сессии и содержит аутентификационные данные. При аутентификации клиента в приложении создается объект `Authentication`, генерируется ключ, по нему объект кладется в сессию (мультимапа) и
этот ключ возвращается клиенту в ответе как значение cookie `JSESSIONID`. 
Браузер хранит cookie на основе домена сайта и прикрепляет их ко всех запросам к этому домену. По значению cookie `JSESSIONID` Spring Security
хранит в сессии `Authentication`, из котрого мы уже можем достать нашего `AuthorizedUser`.  

Для REST контроллеров в конфигурации мы указали `create-session="stateless"` - при обращении к ним приложение
не будет создаваться HTTP сессии и сookie. В каждом запросе клиента к REST контроллеру вместо cookie есть заголовок `Authorization` с данными *логина:пароля* клиента. Каждый запрос проходит цепочку
Security фильтров и для базовой аутентификации при каждом запросе будет происходить обращение к БД для получения пользователя по email и проверка его креденшелов из заголовок `Authorization`.  

При некоторых условиях Tomcat сохраняет данные сессии и ему требуется возможность их сериализации, поэтому объекты в сеcсии (и объекты, которые в них содержатся) обязательно должны имплементировать
интерфейс `Serializable` (в нашем случае `AuthorizedUser` и `UserTo`).

</details>

- <a href="https://ru.wikipedia.org/wiki/HTTP_cookie">HTTP cookie</a></h3>
- <a href="http://stackoverflow.com/questions/595872/under-what-conditions-is-a-jsessionid-created">Under what conditions is a JSESSIONID created?</a>
- <a href="http://halyph.blogspot.ru/2014/08/how-to-disable-tomcat-session.html">Tomcat Session Serialization</a>

### Дополнительно: ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 12. [Новое в Spring 5. Миграция проекта](https://javaops.ru/view/resources/spring5)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> В куки попадает обычная строка JSESSIONID. Куда сериализуется объект User?

Для хранения состояния сессии (например корзины покупателя) в Servlet API есть механизм хранения объектов сессии (грубо - мультимапмапа, которая достается из хранилища по ключу). При создании сессии
на стороне сервера (через `request.getSession`) создается кука `JSESSIONID`, которая передается между клиентом и сервером в каждом запросе и является ключом в хранилище объектов сессий.
См. <a href="http://javatutor.net/books/tiej/servlets#_Toc39472970">обработка сессий с помощью сервлетов</a>

> В `login.jsp` есть форма `<form:form action="spring_security_check" ..>` Где такой url используется?

Он задается в `login-processing-url` конфигурации `spring-security.xml` и определяет URL к Spring Security, который принимает данные авторизационной формы (`username` и `password`).

> Если не пользовать js, а писать UI на JSP, сообщения между ui и сервером будут в формате json? Это же будет JSON API?

Есть данные, которые передаются между клиентом и сервером в формате json или get/post с параметрами, есть стили взаимодействия клиента и сервера (<a href="https://ru.wikipedia.org/wiki/REST">REST</a>
, <a href="http://jsonapi.org/">JSON API</a>, <a href="https://ru.wikipedia.org/wiki/JSON-RPC">JSON-RPC</a>) и есть средства генерации HTML: JSP, Javascript фреймворк, Thymleaf и пр. Не надо эти вещи
путать между собой.

> По умолчанию спринг работает с `UserDetailsService#loadUserByUsername`, который должен возвращать `UserDetails`. Но мы не хотим стандартные, мы хотим свои, поэтому просто наследуем наши `UserService` и `AuthorizedUser` от соответствующих интерфейсов и реализуем недостающие методы, которые spring security и будет использовать?

В прошлых выпусках я сам реализовывал интерфейс `UserDetails`. Сейчас я считаю проще отнаследовать `AuthorizedUser` от `org.springframework.security.core.userdetails.User`, который уже имеет реализацию.
А в `UserService` мы реализуем `UserDetailsService#loadUserByUsername` и указываем этот сервис в `spring-security.xml` `<authentication-provider user-service-ref="userService">`.
Также есть его стандартные реализации, которые использовались до нашей кастомной `UserService`, например `jdbc-user-service` использует реализацию `JdbcUserDetailsManager`

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW9

- 1: Реализовать для meal Binding/ Update/ Validation. Проверить работу при пустом значении `calories`.
- 2: Перевести `meals.jsp` на работу по ajax. Стиль строки таблицы сделать в зависимости от `excess`, время отображать без `T`. Добавить i18n.
- 3: Починить meals тесты, добавить тест на неавторизованный доступ.

### Optional

- 4: Подключить datetime-picker к фильтрам и модальному окну добавления/редактирования еды
    - <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>
    - [jQuery: конверторы](https://jquery-docs.ru/jQuery.ajax/#using-converters)

- Попробуйте при запросах по REST оставить стандартный ISO формат (с разделителем `T`). То есть:
    - Отображение и редактирование еды на UI происходит без `T` (формат значений на UI можно увидеть во вкладке браузера Network)
    - Когда мы работаем по REST, в json и запросах формат даты ISO (с разделителем `T`)
    - Напомню, что параметры методов контроллера (в том числе собранные в объекты через Binding) парсятся конверторами спринга (`@DateTimeFormat`), а объекты json парсится Jackson и они никак не
      влияют друг на друга.

## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Проверка в HW09

- 1: Проверьте, что при добавлении и редактировании пользователя и еды у вас корректно отображаются заголовки модального окна:
  "Добавить/Редактировать еду пользователя"
- 2: Не дублируйте

```
<c:forEach var='key' ...
i18n['${key}'] = ...
```

- 3: Для подключения css и js datetimepicker-а посмотрите в его jar (или поищите в проекте по Ctrl+Shift+N: `datetimepicker`). При ошибке в datetimepicker javascript, обратите внимание на **имя
  javascript файла** в [DateTimePicker](https://xdsoft.net/jqplugins/datetimepicker/) "How do I use it?"
- 4: datetimepicker работает корректно в Хроме, если убрать в `type` в `<input type="date/time/datetime-local" ..`
- 5: Если появляются проблемы с JS типа `... is not defined` - обратите внимание на порядок загрузки скриптов и атрибут `defer`. Скрипты должны идти в нужном порядке. Если определяете скрипт прямо в
  jsp, он выполняется до `defer` скриптов.
- 6: Не дублируйте обработку ошибок в `BindingResult` в ajax контроллерах
- 7: Проверьте редактирование еды: открыть на редактирование и сохранить не должно приводить к ошибкам с форматом времени.
- 8: Проверьте в `RootController.meals()`, его нужно тоже поправить
- 9: При решении с jQuery конвертором: он создает поле `responseJSON`, если внутри не вылетает по эксепшену. См.
  также [hasOwnProperty()](https://developer.mozilla.org/ru/docs/Web/JavaScript/Reference/Global_Objects/Object/hasOwnProperty)  
