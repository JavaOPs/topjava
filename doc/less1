## Servlet API. Apache Tomcat. JSP

Большинство приложений, написанных на Java это [веб-приложения](https://ru.wikipedia.org/wiki/Веб-приложение): серверное приложение, в котором клиент взаимодействует с веб-сервером при помощи браузера.
Браузер является универсальным клиентом, который уже есть практически у каждого пользователя. Общение между браузером и приложением идет по [протоколу HTTP/HTTPS](https://developer.mozilla.org/ru/docs/Web/HTTP).
Открываем в браузере [демо нашего веб-приложения](http://javaops-demo.ru/topjava), затем _Delevoper Tools_ (в Chrome: _F12_ или _Ctrl+Shift+I_).
Во вкладке _Network_ мы видим весь обмен данными между сервером и браузером по протоколу HTTP: в основном страницы передаются в формате [HTML](https://ru.wikipedia.org/wiki/HTML), JavaScript, CSS, изображения и просто данные в формате [JSON](https://ru.wikipedia.org/wiki/JSON).
Вверху вкладки _Network_ можно поставить фильтр по типу данных или url, а также есть полезные галочки _Preserve log_ для сохранения истории запросов после редиректа и _Disable chache_ для предотвращения кэширования (запросы GET могут кэшироваться браузером, также _Ctrl+F5_ позволяет перегрузить страницу целиком).

![http](https://user-images.githubusercontent.com/11200258/216206893-433e9528-08b0-44dc-8ded-37b25e4c50e8.png)

Для работы по HTTP в Java есть специальный фреймворк [Servlet API](https://ru.wikipedia.org/wiki/Сервлет_(Java)). Он не входит в Java SE(JDK), а является частью [Jakarta EE](https://wiki5.ru/wiki/Jakarta_EE) (бывшая Java Enterprise Edition/Java EE, об этом немного ниже).
На Servlet API основаны подавляющее большинство Java веб-фреймворков, в том числе Spring MVC, который мы будем изучать позднее. Исключением являются фреймворки для асинхронной, неблокирующей работы по HTTP, например Spring WebFlux.
Асинхронная разработка требует значительно больших усилий и неблокирующих взаимодействий на всей цепочке (например для работы с БД нужен асинхронный драйвер), поэтому оно в основном применяется при больших нагрузках. 
Стоит отметить, что сервлеты с версии 3.0 также поддерживают асинхронность (мы смотрим на них в 11 уроке курса [MasterJava](https://javaops.ru/view/masterjava#program)), но современные асинхронные фреймворки обычно его не используют (например Spring WebFlux по умолчанию использует [Netty](https://netty.io/),
хотя его также можно [настроить на работу с Servlet non-blocking I/O](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-server-choice)).

Реализуют Servlet API специальные Java приложения: серверы приложений Java (Application Server/AS) или их урезанный вариант - контейнеры сервлетов (Web Server), см [Web vs. Application Server](https://www.baeldung.com/java-servers).
Мы будем работать на самом распространенном контейнере сервлетов/веб-сервере [Apache Tomcat](https://ru.wikipedia.org/wiki/Apache_Tomcat).   

При [передаче разработки](https://www.opennet.ru/opennews/art.shtml?num=51477) Java EE от Oracle проекту Eclipse она стала называться Jakarta и пакеты с реализацией спецификаций _javax_ были переименованы в _jakarta_.
Поэтому при работе с сервлетами (равно как и с другими EE спецификациями) важно выбирать совместимые версии фреймворков. Мы начнем работать с реализацией Java EE (пакетом _javax_), это 
[9.x версия Tomcat](https://tomcat.apache.org/download-90.cgi), Spring 5.x, Hibernate 5.6.x, Hibernate Validator 6.x. При миграции в конце стажировки на Spring Boot 3.0 автоматически подтянутся новые зависимости Jakarta EE:
встроенный Tomcat 10.x, Spring 6.x и [Hibernate Validator 8.x](https://hibernate.org/validator/releases/).   

Дополнительно: 
- [Категория Java Enterprise Edition](https://ru.wikipedia.org/wiki/Категория:Java_Enterprise_Edition)

### Устанавливаем [Tomcat 9.x](https://tomcat.apache.org/download-90.cgi)
Варианты: [как сервис в Windows](https://javarush.com/quests/lectures/questservlets.level11.lecture01), [вручную](https://javarush.com/quests/lectures/questservlets.level11.lecture02) или [как сервис в Unix](https://github.com/JavaOPs/startup/blob/main/tomcat.md)  
На мой взгляд самый простой способ - вручную: скачиванием Binary Distributions xxx.zip (например apache-tomcat-9.0.71.zip), разархивируем его и настраиваем [tomcat-users.xml, 3.3 Настройка Tomcat](https://javarush.com/quests/lectures/questservlets.level11.lecture02)  
**Установите Tomcat в каталог без пробелов и русских букв (пример С:\java\apache-tomcat-9.0.71), иначе в будущем возможны проблемы (например когда начнем работать с кэшем) на стандартный порт Tomcat 8080**  
Запускаем Tomcat и [смотрим стандартный список веб-приложений](https://javarush.com/quests/lectures/questservlets.level11.lecture03)
- [Проверка, кто занял порт](https://stackoverflow.com/a/38953356/548473) в случае проблем с запуском на порту 8080

-----------------------------
### ВНИМАНИЕ! далее патчи идут в ветку `master` после `1_1_HW0_streams`

#### Apply 1_2_war.patch (при изменении pom.xml на забывайте сделать [Reload All Maven Projects](https://github.com/JavaOPs/topjava/wiki/IDEA#maven_update) или сделайте авторелоад)
Применяем патч и смотрим изменения в проекте (можно через _Ctrl+D_ в _Local Changes_):

- Добавляем статический (не зависящий от состояния приложения) _index.html_
- Добавляем пустой _web.xml_
- Меняем _pom packaging_ на [WAR(Web archive)](https://ru.wikipedia.org/wiki/WAR_(тип_файла))
- По умолчанию (_defaultGoal_) делаем сборку проекта _package_ (если запустить просто _mvn_)
- Конфигурируем заранее WAR package через _maven-war-plugin_. Если у вас JDK выше 14, [сбока без этой правки не пройдет](https://stackoverflow.com/questions/66920567/548473)  

### Собираем и деплоим приложение в Tomcat
- Собираем проект (_package_ в вкладке Maven) и смотрим в папке проекта на _target\topjava.war_ (это обычный архив, _Total Commander_ заходит в него по Enter или [еще варианты](https://filext.com/ru/rasshirenie-faila/WAR)) и его структуру
  - [Устройство war-файла](https://javarush.com/quests/lectures/questservlets.level11.lecture05) 
- Деплоим наш _topjava.war_ в Tomcat ([4.2 Деплой тестового веб-приложения](https://javarush.com/quests/lectures/questservlets.level11.lecture03))
- [Добавляем Tomcat в IDEA Ultimate, 5.1](https://javarush.com/quests/lectures/questservlets.level11.lecture04). После кнопки _Fix_ у меня IDEA предлагает такой выбор: 


![war](https://user-images.githubusercontent.com/11200258/216206984-4a2042a6-66ae-48bd-af19-9ebbde4ef8f3.png)

Выбираем _war exploded_: то есть мы задеплоим его распакованным архивом. Это позволит нам его дебажить и менять в рантайме (без передеплоя).
А в _Application Context_ пишем просто topjava:

![war2](https://user-images.githubusercontent.com/11200258/216207025-2ce11809-3364-4a4b-8212-81fb721edae4.png)

Если все сделали правильно, после запуска (Run или Debug) в браузере мы увидим отображение нашего _index.html_ (по умолчанию Tomcat открывает _index.html_ или _index.jsp_).

`Application Context` должен быть тот же, что и url приложения, деплоить надо `war exploded`**

![image](https://cloud.githubusercontent.com/assets/975870/11599106/057932c4-9ad6-11e5-9e9e-fe9fd389532e.png)

### Динамическая перезагрузка
Задеплойте приложение в IDEA через Debug и включите кнопку Update Resource On Frame Deactivation

![image](https://user-images.githubusercontent.com/13649199/216185863-ed535918-79f4-4738-9f82-8c0c075abf4b.png)

Попробуйте поменять _index.html_ (например поменяйте текст в _h3_) и проверьте, что по _F5_ в браузере также меняется его содержимое без передеплоя.

### Маршрутизацию запросов, JSP шаблоны
Все запросы по нашему _Application Context (topjava)_ Tomcat будет сверять с _web.xml_ нашего приложения.
Файл web.xml [хранит информацию о конфигурации приложения]((https://javarush.com/quests/lectures/questservlets.level11.lecture06)) и, в частности, задает маппинг - какой сервлет какие запросы к нашему приложению будет обрабатывать. 
[Сервлет](https://javarush.com/quests/lectures/questservlets.level12.lecture00) принимает входящие запросы _HttpServletRequest_ и обрабатывает их, возвращая результат в _HttpServletResponse_, который затем отображается в браузере.

Выводит ответ в сервлете через 
```
    PrintWriter out = response.getWriter();
    out.print("<html> ");
    ....
```
неудобно для больших страниц, поэтому придумали технологию шаблонов, которые задают структуру страницы и места, куда нужно вставить данные приложения. 
В Tomcat по умолчанию включен [движок шаблонов JSP](https://javarush.com/quests/lectures/questservlets.level13.lecture00), в Spring Boot по умолчанию используются [шаблоны Thymeleaf](https://alexkosarev.name/2017/08/08/thymeleaf-template-engine/).
Все они основаны на [паттерне MVC](https://javarush.com/quests/lectures/questcollections.level06.lecture01) - из приложения (Controller) мы отдаем шаблону (View) на отрисовку мапу с данными (Model). 
В нашем случае мы просто будем перенаправлять запрос (`request.getRequestDispatcher("/users.jsp").forward(request, response)`) на наш шаблон, в котором нет данных приложения, соответственно нет модели.

#### Apply 1_3_servlet_api.patch
> **Все зависимости в Maven проект подтягиваются ТОЛЬКО через Maven!** (см. [обновить зависимости в maven проекте](https://github.com/JavaOPs/topjava/wiki/IDEA#maven_update))

- В _pom.xml_  добавляем зависимость на сервлеты (_javax.servlet-api_). [Scope _provided_](https://java-online.ru/maven-faq.xhtml#scope) означает, что зависимость используется при компиляции, но не пакуется в WAR (можете проверить). Эти классы уже есть в Tomcat и в WAR они будут мешать его правильной работе. 
- В _index.html_ мы делаем ссылку на url _users_ (она формируется от _Application Context_, который у нас задали _topjava_)
- В _web.xml_ добавляем маппинг: запрос браузера _topjava/users_ Тоmcat направит на сервлет _UserServlet_
- Сервлет _UserServlet_ принимает _HttpServletRequest_ запрос и перенаправляет его на _users.jsp_

Перезапустите Tomcat и проверьте новый функционал приложения.   
Необходимо понимать [жизненный цикл сервлета](https://metanit.com/java/javaee/4.8.php): наш _UserServlet_ создается сразу при деплое приложения (параметр _load-on-startup_ в _web.xml_). При создании у него вызывается метод _init()_, а при выгрузке war метод _destroy()_. Внутри Tomcat находится пул HTTP запросов (_maxThreads_, 200 по умолчанию), его можно конфигурировать.
Если нагрузка большая и мы не успели обработать 200 запросов за время, когда пришел 201-й, он встанет в очередь и будет ждать время timeout (60 секунд по умолчанию). Все _/topjava/user_ запросы будет обрабатывать один и тот же экземпляр _UserServlet_, то есть **поле сервлета будет общим для всех запросов** и это следует учитывать в логике.   

- Если проблема с Tomcat debug и работает Dr.Web- нужно его отключить, либо добавить в исключения путь к  `.IntelliJIdeaXXX/`
- Наше приложение: [http://localhost:8080/topjava](http://localhost:8080/topjava)
- Наш сервлет:     [http://localhost:8080/topjava/users](http://localhost:8080/topjava/users)

#### Apply 1_4_forward_to_redirect.patch

- Поменяем `Forward` на `Redirect`. Обратите внимание на изменение приложения во вкладке _Network_ браузера. Теперь при клике на _Users_ приложение возвращает браузеру ответ 302 и в заголовках (Headers) ответа _Location users.jsp_.
  
![redirect](https://user-images.githubusercontent.com/11200258/216207137-8a3580e2-1ac2-41d9-b047-38d769192519.png)

Браузер по этому перенаправлению меняет свой url и выполняет новых запрос _/topjava/users.jsp_.  

### Попробуйте подебажить приложение: поставить брекпойнт в [IDEA в коде сервлета](http://info.javarush.ru/idea_help/2014/01/22/Руководство-пользователя-IntelliJ-IDEA-Отладчик-.html) и в JPS:

![debug_jsp](https://user-images.githubusercontent.com/11200258/216207167-92936706-7c41-4a36-8e29-6fb4e846def9.png)

> Alt+F8 - просмотр переменной в дебаге

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Не устарели ли сервлеты, зачем их учить? Используются ли сервлеты на реальных проектах сегодня?

1. Вопрос подобен подобным: "нужно ли знать JDBC, если работаешь исключительно с JPA/Hibernate" или "работу Spring MVC, если работаешь со Spring Boot".
   Мой ответ - как минимум основы вы должны знать (совсем не обязательно учить их полную спецификацию и все методы). При написании учебных приложений часто можно обойтись без них,
   но когда приходится решать проблемы (основная работа разработчика) или на большом коммерческом проекте, очень часто без этих основ вам будет очень тяжело справиться с задачей.
2. Бывают легаси проекты, бывают современные, где не подтягивается сторонний web фреймворк. При этом, даже работая с фреймворком, приходится иметь дело с Servlet API (часто с `HttpServletRequest/HttpServletResponse`) - обработка ошибок, валидаторы, фильтры, пре/пост обработка зарпосов, получение ip, работа с сессией и пр.

>  Используются ли еще где-то в реальной разработке JSP, или это уже устаревшая технология? Заменит ли ее JSF [(форум)](https://web.archive.org/web/20170609064143/http://javatalks.ru/topics/38037)

JSF и JSP- разные ниши и задачи.
JSP- шаблонизатор, JSF - МVС фреймворк. Из моего опыта- с JSP сталкивался в 60% проектов. Его прямая замена: http://www.thymeleaf.org (в Spring-Boot по умолчанию), но в уже запущенных проектах встречается достаточно редко. JSP не умирает, потому что просто и дешево. Кроме того включается в большинство веб-контейнеров (в Tomcat его реализация Jasper)

JSF- sun-овский еще фреймворк, с которым я ни разу не сталкивался и особого желания нет. Вот он как раз, по моему мнению, активно замещается хотя бы javascript фреймворками (Angular, React, Vue.js).

> Какой метод сервлета вызвается из HTML/JSP: doGet/doDelete/doPut..?

Методы можно посмотреть в вкладке браузера `Network`. По [сcылке](http://htmlbook.ru/html/a/href) вызывается `GET/doGet()`. Из [формы можно делать GET и POST](http://htmlbook.ru/html/form/method), обычно данные формы передаются через POST. **Для других методов нужен JavaScript, пока их не используем**

### Краткие итоги
- Мы посмотрели, как на Java работать с HTTP протоколом через Servlet API и JSP (части теперь уже Jakarta EE). 
- Установили самый популярный веб-сервер Tomcat и задеплоили в него наше приложение
- Посмотрели на динамическую перезагрузку и поробовали IDEA дебаг

### Дополнительно:
- [Yakov Fain: Intro to Java EE. Glassfish. Servlets (по-русски)](https://www.youtube.com/watch?v=tN8K1y-HSws&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=14)
- [Yakov Fain: HTTP Sessions, Cookies, WAR deployments, JSP (по-русски)](https://www.youtube.com/watch?v=Vumy_fbo-Qs&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=15)
- [Golovach Courses: Junior.February2014.Servlets](https://www.youtube.com/playlist?list=PLoij6udfBncjHaO5s7Ln4w4BLj5FVc49P)
- [Java объекты, доступные в JSP](http://stackoverflow.com/questions/1890438/how-to-get-parameters-from-the-url-with-jsp#1890462)
