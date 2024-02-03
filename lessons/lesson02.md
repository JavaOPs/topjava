# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## [Материалы занятия](https://drive.google.com/drive/folders/0B9Ye2auQ_NsFfkpsWE1uX19zV19IVHd0bTlDclc5QmhMMm4xa0Npek9DT18tdkwyLTBZdXM) (скачать все патчи можно через Download папки patch)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW1:

- **Перед сборкой проекта (или запуском Tomcat) откройте вкладку Maven Projects и сделайте `clean`**
- **Если страничка в браузере работает неверно, очистите кэш (`Ctrl+F5` в хроме)**

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFXzByNVF3VV9zM1k">Отображения списка еды в JSP</a>
#### Apply 2_1_HW1.patch

> - Переименовал `TimeUtil` в `DateTimeUtil`
> - Переименовал `mealList.jsp` в `meals.jsp`
> - Изменения в `MealsUtil`:
>    - Сделал константу `List<Meal> meals`. [Правило именования констант, которые не "deeply immutable"](https://google.github.io/styleguide/javaguide.html#s5.2.4-constant-names)
>    - Для фильтрации по времени и без нее в метод `filterByPredicate` передаю реализацию `Predicate`, см. паттерн [Стратегия](https://refactoring.guru/ru/design-patterns/strategy) и, если непонятно, [картинку](https://user-images.githubusercontent.com/13649199/95467365-093a1080-0986-11eb-8177-0985456d857a.png)
> - Форматирование даты сделал на основе <a href="http://stackoverflow.com/questions/35606551/jstl-localdatetime-format#35607225"> Custom EL function</a>
>    - [Create a custom Function for JSTL через tag library descriptor (TLD)](http://findnerd.com/list/view/How-to-create-a-custom-Function-for-JSTL/2869/)
> - Добавил еще один способ вывести `dateTime` через стандартную JSTL функцию `replace`  (префикс `fn` в шапке также надо поменять)

- [jsp:useBean](http://java-online.ru/jsp-actions.xhtml#useBean)
- [MVC - Model View Controller](http://design-pattern.ru/patterns/mvc.html)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQndBeWFOa3phRTg">Optional: реализация CRUD</a>
#### Apply 2_2_HW1_optional.patch
Про использование паттерна Repository будет подробно рассказано в видео "Слои приложения"
> - Согласно ответам на [Java Interfaces/Implementation naming convention](https://stackoverflow.com/questions/2814805/java-interfaces-implementation-naming-convention) 
убрал `Impl` в `InMemory` (и всех последующих) реализациях репозиториев. Они не нужны.
> - Поправил `InMemoryMealRepository.save()`. Если обновляется еда, которой нет в хранилище (c несуществующим id), вставка не происходит.
> - В `MealServlet.doGet()` сделал выбор через `switch`
> - В местах, где требуется `int`, заменил `Integer.valueOf()` на `Integer.parseInt()`
> - В `mealForm.jsp` использую <a href="http://stackoverflow.com/questions/1890438/how-to-get-parameters-from-the-url-with-jsp#1890462">параметр запроса `param.action`</a>, который не кладется в атрибуты.
> - Переименовал `mealEdit.jsp` в `mealForm.jsp`. Поля ввода формы добавил `required`
> - Пофиксил багу c `history.back()` в `mealForm.jsp` для **FireFox** (коммит формы при Cancel, сделал `type="button"`).

Дополнительно:
  - <a href="http://stackoverflow.com/questions/246859/http-1-0-vs-1-1">HTTP 1.0 vs 1.1</a>

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы по HW1

> Зачем в `InMemoryMealRepository` наполнять map с помощью нестатического блока инициализации, а не в конструкторе?

В общем случае так делать не надо. Сделал, чтобы напомнить вам про эту конструкцию, см. [Малоизвестные особенности Java](https://habrahabr.ru/post/133237/)

> Почему `InMemoryMealRepository` не singleton?

Начиная с Servlet API 2.3 пул сервлетов не создается, [создается только один инстанс сервлетов](https://stackoverflow.com/questions/6298309). Те. `InMemoryMealRepository` в нашем случае создается тоже только один раз. Далее все наши классы слоев приложения будут создаваться через Spring, бины которого по умолчанию являются синглтонами (в его контексте).  

> `Objects.requireNonNull` в `MealServlet.getId(request)` если у нас нет `id` в запросе бросает NPE (`NullPointerException`). Но оно вылетит и без этого метода. Зачем он нужен и почему мы его не обрабатываем?

`Objects.requireNonNull` - это проверка предусловия (будет подробно на 4-м занятии). Означает что в метод пришел неверный аргумент (должен быть не null) и приложение сообщает об ошибке сразу на входе (а не "может быть где-то потом"). См. [What is the purpose of Objects#requireNonNull](https://stackoverflow.com/a/27511204/548473). Если ее проглатывать или замазывать, то приложение возможно где-то работает неверно (приходят неверные аргументы), а мы об этом не узнаем. Красиво обрабатывать ошибки будем на последних занятиях (Spring Exception Handling).

## Занятие 2:
   
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=1Ve2vLlZAHwOKBfpuOL9X_jbSpQMGsLVP">Многоуровневая(многослойная) архитектура</a>
![Слои приложения](https://javaops.ru/static/images/projects/top-scheme.jpg)
-  <a href="https://metanit.com/sharp/mvc5/23.5.php">Многоуровневая(многослойная) архитектура</a>
-  <a href="https://ru.wikipedia.org/wiki/Data_Access_Object">Data Access Object</a>
-  <a href="http://martinfowler.com/eaaCatalog/dataTransferObject.html">Паттерн DTO</a>
-  <a href="http://stackoverflow.com/questions/21554977/should-services-always-return-dtos-or-can-they-also-return-domain-models">Should services always return DTOs, or can they also return domain models?</a>
- [Mapping Entity->DTO goes in which application layer: Controller or Service?](http://stackoverflow.com/questions/31644131/spring-dto-dao-resource-entity-mapping-goes-in-which-application-layer-cont/35798539#35798539)
-  Дополнительно:
   -  <a href="http://stackoverflow.com/questions/1612334/difference-between-dto-vo-pojo-javabeans">Value Object и Data Transfer Object</a>
   - <a href="http://stackoverflow.com/questions/6640784/difference-between-active-record-and-dao">Difference between Active Record and DAO</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=1pWsm_fNfxqPB8yygWJsMUpvWMVyRxIqt">Создание каркаса приложения для пользователей</a>

#### Apply 2_3_app_layers.patch
> - Убрал интерфейсы к сервисам. Я всегда предпочитаю писать меньше кода и в случае с одной реализацией можно обходится без них. 
    По поводу инкапсуляции и отделения API от реализации - интерфейсы к сервисам это внутренняя часть приложения с одной реализацией. Меньше кода, проще поддерживать.
> - Переименовал `ExceptionUtil` в `ValidationUtil`
> - Поменял `LoggedUser` на `SecurityUtil`. Это класс, из которого приложение будет получать данные залогиненного пользователя (пока [аутентификации](https://ru.wikipedia.org/wiki/Аутентификация) нет, он реализован как заглушка). Находится в пакете `web`, т.к. аутентификация/[авторизация](https://ru.wikipedia.org/wiki/Авторизация) происходит на слое контроллеров и остальные слои приложения про нее знать не должны.
> - Добавил проверку id пользователя, пришедшего в контроллер ([treat IDs in REST body](https://stackoverflow.com/a/32728226/548473), "If it is a public API you should be conservative when you reply, but accept liberally"). Считаю это важной частью проверки входных данных в контроллере, не забывайте это делать в ваших выпускных проектах.
> - Удалил в `User` лишнюю инициализацию. Было немножко наперед, добавим при введении конструктора по умолчанию.


## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Почему у `User.registered` тип `Date`, а `Meal.dateTime` `LocalDateTime `?

По логике приложения время регистрации - абсолютное (конкретный момент), а время еды по бизнес логике относительно (те не зависит от часового пояса, завтрак и в Африке должен быть завтраком)

>  Какова цель деления приложения на слои?

Управляемость проекта (особенно большого) повышается на порядок:
- Обеспечивается меньшая связываемость. Допустим если мы меняем что-то в контроллере, то сервис эти изменения не задевают.
- Облегчается тестирование (мы будем тестировать слои сервисов и контроллеров отдельно)
- Четко разделяется функционал - где писать, куда смотреть. Не создаются [God objects](https://ru.wikipedia.org/wiki/Божественный_объект)

> DTO используются когда есть избыточность запросов, которую мы уменьшаем, собрав данные из разных бинов в один? Когда DTO необходимо использовать?

(D)TO может быть как частью одного entity  (набор полей) так и набором нескольких entities.
В нашем проекте для данных, которые надо отдавать наружу и отличающихся от Entiy (хранимый бин), мы будем делать (Data) Transfer Object и класть в отдельный пакет to. Например `MealsTo` мы отдаем наружу и он является Transfer Object, его пернесем в пакет `to`.
На многих проектах (и собеседованиях) практикуют разделение на уровне maven модулей entity слоя от логики и соответствующей конвертацией ВСЕХ Entity в TO, даже если у них те же самые поля.
Хороший ответ когда TO обязательны есть на <a href="http://stackoverflow.com/questions/21554977/should-services-always-return-dtos-or-can-they-also-return-domain-models#21569720">stackoverflow: When to Use</a>.

> Почему контроллеры положили в папку web, а не в controllers?

То же самое что `domain/model` - просто разные названия, которые устоялись в Java. Не придумывайте своих!

> Зачем мы наследуем `NotFoundException` от `RuntimeException`?

Так с ним удобнее работать. И у нас нет никаких действий по восстановлению состояния приложения (no recoverable conditions): <a href="http://stackoverflow.com/questions/6115896/java-checked-vs-unchecked-exception-explanation">checked vs unchecked exception</a>. По последним данным checked exception вообще depricated: <a href="http://blog.takipi.com/ignore-checked-exceptions-all-the-cool-devs-are-doing-it-based-on-600000-java-projects">Ignore Checked Exceptions</a>

> Что такое `ProfileRestController`?

Контроллер, где залогиненный пользователь будет работать со своими данными

> Зачем в `AdminRestController` переопределяются методы родителя с вызовом тех же родительских?

Сделано на будущее, мы будем менять этот код.

> Что лучше возвращать из API: `Collection` или `List`

Вообще, как правило, возвращают `List`, если не просится по коду более общий случай (например возможный `Set` или `Collection`, возвращаемый `Map.values()`). Если возвращается отсортированный список, то `List` будет адекватнее.

> **Вопрос вам (очень важный):** можно ли в `MealRestController` контроллере сделать член класса `private int userId = SecurityUtil.authUserId()` и использовать его в методах контроллера?

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Что такое Spring Framework](https://www.youtube.com/watch?v=megjriLG35I). 
- [Wiki: Spring Framework](https://ru.wikipedia.org/wiki/Spring_Framework)
- [JVM Ecosystem Report 2020: Spring](https://snyk.io/blog/spring-dominates-the-java-ecosystem-with-60-using-it-for-their-main-applications/)
- [2020 Java Technology Report](https://www.jrebel.com/blog/2020-java-technology-report)
- [Spring Framework Documentation](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/index.html)
- [Что такое Spring Framework? Внедрение зависимостей](https://habr.com/ru/post/490586/)
- [Евгений Борисов — Spring-построитель](https://www.youtube.com/watch?v=rd6wxPzXQvo)
- [Инверсия управления] (https://ru.wikipedia.org/wiki/Инверсия_управления)

#### Apply 2_4_add_spring.patch
> Сделал рефакторинг конструктора User, чтобы была возможность создавать пользователя без ролей

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. [Запуск Spring Application Context](https://drive.google.com/file/d/1y-3ok-6CzhjnR4Rmv3-z4EV4VsElIDn6)
- [Container Overview](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#beans-basics)
#### Apply 2_5_add_spring_context.patch

###  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Dependency Injection, Annotation_processing](https://drive.google.com/file/d/1Z9cgULTrXxgeaqqnsh7rJtIaD2LSdzHT)
#### Apply 2_6_dependency_injection.patch
- [IoC, DI, IoC-контейнер. Просто о простом](http://habrahabr.ru/post/131993/)
- [Что такое Spring Framework? Внедрение зависимостей](https://habr.com/ru/post/490586/)
- [Перевод "Field Dependency Injection Considered Harmful"](https://habrahabr.ru/post/334636/)
- [Field vs Constructor vs Setter DI](http://stackoverflow.com/questions/39890849/what-exactly-is-field-injection-and-how-to-avoid-it)

#### Apply 2_7_annotation_processing.patch
-  [Spring Auto Scanning Components](http://www.mkyong.com/spring/spring-auto-scanning-components)
-  [Difference between @Component, @Repository & @Service annotations in Spring](http://stackoverflow.com/questions/6827752/whats-the-difference-between-component-repository-service-annotations-in)
-  [Использование аннотации @Autowired](http://www.seostella.com/ru/article/2012/02/12/ispolzovanie-annotacii-autowired-v-spring-3.html)
-  Дополнительное:
   - [Подготовка к Spring Professional Certification. Контейнер, IoC, бины](https://habr.com/ru/post/470305/)
   - [Spring на GitHub](https://github.com/spring-projects)
   - [Spring Annotations](https://dzone.com/refcardz/spring-annotations)

#### Дополнительно видео по Spring
   - [Юрий Ткач: Spring Framework - The Basics](https://www.youtube.com/playlist?list=PL6jg6AGdCNaWF-sUH2QDudBRXo54zuN1t)
   - [Java Brains: Spring Framework](https://www.youtube.com/playlist?list=PLC97BDEFDCDD169D7)
   - [Тимур Батыршинов: Spring Core - основы фреймворка, ядро](https://www.youtube.com/watch?v=CfHDr-19WWY&list=PL8X2nqRlWfaYYP1-qXjdPKE7bXYkl6aL4)
   - [alishev: Spring Framework](https://www.youtube.com/playlist?list=PLAma_mKffTOR5o0WNHnY0mTjKxnCgSXrZ)

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> Что такое схема в spring-app.xml xsi:schemaLocation и зачем она нужна

<a href="https://ru.wikipedia.org/wiki/XML_Schema">XML схема</a> нужна для валидации xml, IDEA делает по ней автозаполнение.

> Что означает для Spring

     <bean class="ru.javawebinar.topjava.service.UserService">
         <property name="repository" ref="inmemoryUserRepository"/>
     </bean> ?

Можно сказать так: создай и занеси в свой контекст экземпляр класса (бин) `UserService` и присвой его проперти `repository` бин `inmemoryUserRepository`, который возьми из своего контекста.

> `SecurityUtil.authUserId()`  и `user.id` это одно и то или это разные вещи?

`User.id` это уникальный идентификатор юзера, которых в приложении много.
`SecurityUtil.authUserId()` это идентификатор `id` залогиненного юзера. Мы можем, например, получить самого залогиненного юзера, выполнив запрос с `User.id==SecurityUtil.authUserId()`
Когда вы логинитесь в свое почтовое приложение, оно отдает вам именно ваши письма на основе вашего `id`, который она определяет и запоминает во время аутентификации (логина).  
У нас пока этого нет и `id` задается константой (хардкодится). Но когда мы сделаем настоящую аутентификацию, все будет работать для любого залогиненного пользователя.

> Как биндинг происходит для `@Autowired`? Как поступать, если у нас больше одной реализации `UserRepository`?

`@Autowired`  инжектит по типу (т.е. ижектит класс который реализует `UserRepository`). Обычно он один. Если у нас несколько реализаций, Spring не поднимится и поругается - `No unique bean`.
 В этом случае <a href="http://www.mkyong.com/spring/spring-autowiring-qualifier-example/">можно уточнить имя бина через @Qualifier</a>. `@Qualifier` обычно добавляют только в случае нескольких реализаций.
См. [Inject 2 beans of same type](https://stackoverflow.com/a/2153680/548473)

> Почему нельзя сервлет помещать в Spring контекст?

Сервлеты- это исключительно классы `servlet-api` (веб контейнера), они инстанциируются Tomcat. Те технически можно (без `init/destroy`), но идеологически - неверно. Cоздастся два сервлета: один настоящий, Tomcat-ом, и второй - нерабочий, Spring-ом. НЕ надо включать сервлет в контекст Spring.

--------------------
- **Еще раз смотрим на [демо приложение](http://javaops-demo.ru/topjava) и вникаем, что такое пользователь и его еда и что он может с ней сделать. 
Когда пользователь логинится в приложении, его id и норма калорий "чудесным образом" попадают в  `SecurityUtil.authUserId() / SecurityUtil.authUserCaloriesPerDay()`. Как они реально туда попадут будет в уроке  9 (Spring Security, сессия и куки)**
- **Перед началом выполнения ДЗ (если есть хоть какие-то сомнения) прочитайте ВСЕ ДЗ. Если вопросы остаются - то ВСЕ подсказки**. Особенно этот пункт важный, когда будете делать реальное рабочее ТЗ.

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW02
- 1: Имплементировать `InMemoryUserRepository` по аналогии с `InMemoryMealRepository` (список пользователей возвращать отсортированным по имени)
- 2: сделать `Meal extends AbstractBaseEntity`, `MealTo` перенести в пакет `ru.javawebinar.topjava.to` (transfer objects)
- 3: Изменить `MealRepository` и `InMemoryMealRepository` таким образом, чтобы вся еда всех пользователей находилась в одном общем хранилище, но при этом каждый конкретный аутентифицированный пользователь мог видеть и редактировать только свою еду.
  - 3.1: реализовать хранение еды для каждого пользователя можно с добавлением поля `userId` в `Meal` ИЛИ без него (как нравится). Напомню, что репозиторий один и приложение может работать одновременно с многими пользователями.
  - 3.2: если по запрошенному id еда отсутствует или чужая, возвращать `null/false` (см. комментарии в `MealRepository`)
  - 3.3: список еды возвращать отсортированный в обратном порядке по датам
  - 3.4: дополнительно: попробуйте сделать реализацию атомарной  (те учесть коллизии при одновременном изменении еды одного пользователя)
- 4: Реализовать слои приложения для функциональности "еда". API контроллера должна удовлетворять все потребности демо приложения и ничего лишнего (см. [демо](http://javaops-demo.ru/topjava)). Поиск и изменение порядка сортировки в таблице демо приложения реализованы на клиенте в браузере (плагин DataTables), сейчас делать не нужно.
  - **Смотрите на реализацию слоя для user и делаете по аналогии! Если там что-то непонятно, не надо исправлять или делать по своему. Задавайте вопросы. Если действительно нужна правка - я сделаю и напишу всем.**
  - 4.1: после авторизации (сделаем позднее), id авторизованного юзера можно получить из `SecurityUtil.authUserId()`. Запрос попадает в контроллер, методы которого будут доступны снаружи по http, т.е. запрос можно будет сделать с ЛЮБЫМ id для еды
  (не принадлежащем авторизированному пользователю). Нельзя позволять модифицировать/смотреть чужую еду.
  - 4.2: `SecurityUtil` может использоваться только на слое web (см. реализацию `ProfileRestController`). `MealService` можно тестировать без подмены логики авторизации, поэтому **в методы сервиса и репозитория мы передаем параметр `userId`: id авторизованного пользователя (предполагаемого владельца еды)**.
  - 4.3: если еда не принадлежит авторизированному пользователю или отсутствует, в `MealService` бросать `NotFoundException`.
  - 4.4: конвертацию в `MealTo` можно делать как в слое web, так и в service ([Mapping Entity->DTO: Controller or Service?](http://stackoverflow.com/questions/31644131))
  - 4.5: в `MealService` постараться сделать в каждом методе только одни запрос к `MealRepository`
  - 4.6 еще раз: не надо в названиях методов повторять названия класса (`Meal`).

![image](https://user-images.githubusercontent.com/13649199/121820224-66ffc480-cc9a-11eb-8abb-d1015ec2cb79.png)

- 5: включить классы еды в контекст Spring (добавить аннотации) и вызвать из `SpringMain` любой метод `MealRestController` (проверить что Spring все корректно заинжектил)

### Optional
- 6: в `MealServlet` сделать инициализацию Spring, достать `MealRestController` из контекста и работать с едой через него (**как в `SpringMain`**). `pom.xml` НЕ менять, работаем со `spring-context`. Сервлет обращается к контролеру, контроллер вызывает сервис, сервис - репозиторий. Когда будем работать через Spring MVC, `MealServlet` удалим, останется только контроллер.

![image](https://user-images.githubusercontent.com/13649199/121820239-71ba5980-cc9a-11eb-8c25-54df6f50f43c.png)

- 7: добавить в `meals.jsp` и `MealServlet` фильтрацию еды по дате и по времени (см. [демо](http://javaops-demo.ru/topjava)). Сброс фильтра делать не надо (реализуем через ajax в HW8). ВНИМАНИЕ: в приложении фильтрация делается не по интервалу дата-время, а отдельно по датам и затем отдельно по времени.
- 8: добавить выбор текущего залогиненного пользователя (имитация аутентификации, сделать [Select](http://htmlbook.ru/html/option) с двумя элементами со значениями 1 и 2 в `index.html` и `SecurityUtil.setAuthUserId(userId)` в `UserServlet`). От выбора user или admin будет зависеть отображение еды: user-а или admin-а.
Настоящая аутентификация будет через Spring Security позже.

----------------------------
### Итоги занятия после выполнения ДЗ: 
Мы создали архитектуру нашего приложения с разделением на слои и внедрили в наш проект фреймворк Spring, который их связывает.  
Далее мы реализовали функционал нашего приложения для работы с едой, как он сделан в [демо приложении](http://javaops-demo.ru/topjava) (но с фиктивной аутентификацией)

---------------------

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: **В реализации `InMemoryUserRepository`**
  - 1.1: `getByEmail` попробуйте сделать через `stream`
  - 1.2: `delete` попробуйте сделать за одно обращение к map (без `containsKey`). При удалении пользователя его еду можно оставить, при реализации в базе будет CASCADE.
  - 1.3: при запросе списка юзеров предусмотрите случай одинаковых `User.name` (порядок должен быть зафиксированным). Поле `User.email`у нас уникально - в базе будет ограничение.
- 2: **В реализации `InMemoryMealRepository`**
  - 2.1: В `Meal`, которая приходит из браузера в контроллер, нет никакой информации о пользователе (еда приходит в контроллер **БЕЗ `user/userId`, она может быть только от авторизированного пользователя**). По id еды и авторизованному пользователю нужно проверить ее принадлежность (его это еда или чужая)
  **Проверьте сценарий: авторизованный пользователь пробует изменить чужую еду (id еды ему не принадлежит).**
  - 2.2: `get\update\delete` - следите, чтобы не было NPE (`NullPointException` может быть, если в хранилище отсутствует юзер или еда).
  - 2.3: Фильтрацию по датам сделать в репозитории т.к. из базы будем брать сразу отфильтрованные по дням записи. Следите чтобы **первый и последний день не были обрезаны, иначе сумма калорий будет неверная**.
  - 2.4: Если запрашивается список и он пустой, не возвращайте NULL! По пустому списку можно легко итерироваться без риска `NullPoinException`.
  - 2.5: Не дублируйте код в `getAll` и метод с фильтрацией
  - 2.6: попробуйте учесть, что следующая реализация (сортировка, фильтрация) будет делаться прямо в базе данных
- 3: Проверьте, что удалили `Meal.id` и связанные с ним методы (он уже есть в базовом `BaseEntity`)
- 4: Проверку `isBetweenHalfOpen` сделать в `DateTimeUtil`. Попробуйте использовать `LocalDateTime` вместо `LocalDate` с прицелом на то, что в DB будет тип даты `timestamp`. Тогда для `LocalTime` и `LocalDateTime` можно использовать один метод проверки полуоткрытого интервала и дженерики (см. [Generics Tutorials](https://docs.oracle.com/javase/tutorial/extra/generics/morefun.html) и [Погружаемся в Java Generics](https://habr.com/ru/company/sberbank/blog/416413/))
- 5: **Реализация 'MealRestController' должен уметь обрабатывать запросы**:
  - 5.1: Отдать свою еду (для отображения в таблице, формат `List<MealTo>`), запрос БЕЗ параметров
  - 5.2: Отдать свою еду, отфильтрованную по startDate, startTime, endDate, endTime
  - 5.3: Отдать/удалить свою еду по id, параметр запроса - id еды. Если еда с этим id чужая или отсутствует - `NotFoundException`
  - 5.4: Сохранить/обновить еду, параметр запроса - Meal. Если обновляемая еда с этим id чужая или отсутствует - `NotFoundException`
  - 5.5: Сервлет мы удалим, а контроллер останется, поэтому возвращать `List<MealTo>` надо из контроллера. И userId принимать в контроллере НЕЛЬЗЯ (иначе - для чего аторизация?). 
  - 5.6: В концепции REST при update дополнительно принято передавать id (см. `AdminRestController.update`)
  - 5.7: Для получения всей своей еды сделайте отдельный `getAll` без применения фильтра
- 6: Проверьте корректную обработку пустых значений date и time (в частности, если все значения пустые, должен выводится весь список)
- 7: `id` авторизированного пользователя получаем так: `SecurityUtil.authUserId()`, cм. `ProfileRestController`
- 8: В `MealServlet`
  - 8.1: Закрывать springContext в сервлете грамотнее всего в `HttpServlet.destroy()`: если где-то в контексте Spring будет ленивая инициализация, метод-фабрика, не синглтон-scope, то контекст понадобится при работе приложения. У нас такого нет, но делать надо все грамотно.
  - 8.2: Не храните параметры фильтра как члены класса сервлета, это не многопоточно! Один экземпляр сервлета используется всеми запросами на сервер, попробуйте дернуть его из 2х браузеров.
  - 8.3: В сервлете нельзя использовать `@Autowired` и `@Component`. См вопрос выше- "Почему нельзя сервлет помещать в Spring контекст?"

#### Если с ДЗ большие сложности, можно получить итоговые Meal интерфейсы для сверки в личке (`@Katherine`, `@Valeria`).
И напоследок история от Татьяны:
> 2.1 По id еды и авторизованному пользователю нужно проверить ее принадлежность.

На примере уточню:
Вася Пупкин нашел неименную банковскую карточку, т.е. номер есть, но имени нет.
Т.к. Вася не очень честный человек, то решил снять деньги с чужой карточки.
Наклеил *стикер со своим именем* на карточку и пришел в банк. Дает свой паспорт и карточку операционисту и просит снять всю наличность.
Варианты:
1. операционист сверяет стикер на карточке и паспорт - все ок, Вася получает наличность
2. операционист не обращает внимания на стикер, *а делает запрос в БД по номеру и сверяет ФИО в БД с паспортом* - ФИО не совпали, Вася в пролете

Кто и так это понимает, тому небольшой спойлер. А кто не понимает, может, переспросят, пообсуждают.
