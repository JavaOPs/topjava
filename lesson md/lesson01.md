# Онлайн проект <a href="https://github.com/JavaWebinar/topjava">Topjava</a>
- **Не стоит стремится прочитать все ссылки урока, их можно использовать как справочник. Гораздо важнее пройти основной материал урока и сделать Домашнее Задание**
- **Обязательно посмотри <a href="https://github.com/JavaOPs/topjava/wiki/Git#%D0%9F%D1%80%D0%B0%D0%B2%D0%B8%D0%BB%D0%B0-%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B-%D1%81-%D0%BF%D0%B0%D1%82%D1%87%D0%B0%D0%BC%D0%B8-%D0%BD%D0%B0-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B5">правила работы с патчами на проекте</a>**
- **Делать Apply Patch лучше по одному, непосредственно перед видео на эту тему, а при просмотре видео сразу отслеживать все изменения кода проекта по изменению в патче (`Version Control->Local Changes-> Ctrl+D`)**
- **Код проекта обновляется и не всегда совпадает с видео (можно увидеть как развивался проект). Изменения в проекте указываю после соответствующего патча.** 

## <a href="https://drive.google.com/drive/u/0/folders/0B9Ye2auQ_NsFfm5hSHEtbmxmN2kxb0NocVRwWl9KanowWXVCVXRZTlhaM09wQUswZkRidTA">Материалы занятия</a> (скачать все патчи можно через `Download/Скачать` папки patch)
![image](https://cloud.githubusercontent.com/assets/13649199/18330295/5f2ca214-7560-11e6-8e1e-c0494f798c37.png)

### ![correction](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Рефакторинг проекта

#### Apply 1_0_fix.patch
> - переименовал классы `UserMeal*` в более красивые `Meal*`
> - удалил себя из шапки классов

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW0:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFOGU0a3ZUbFo3Skk">Optional: реализация getFilteredMealsWithExceeded через Stream API</a>
#### Apply 1_1_HW0_stream.patch

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFdTJIQUExajZWWkE">Работа с git в IDEA. Реализация через цикл.</a>
#### Apply 1_2_HW0_cycle.patch

> Изменения в проекте: `map.getOrDefault` земенил на `map.merge`, `for` заменены на `forEach`

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы по HW0

> что делает метод `Map.merge` ?

никогда не надо ленится зайти в код `Map.merge` (`Ctrl+мышка` на методе) и почитать javadoc (`Ctrl+Q`). Когда привыкать, если не сейчас?

> что означает `Integer::sum` ?

это ссылка на метод, сокращенная форма лямбды. IDEA иногда выделяет желтым и предлагает замену (соглашаться по `Alt+Enter`). 
Например `m->m.getCalories()` заменяется на `Meal::getCalories` .

> почему не использовать в `TimeUtil` методы `isBefore/isAfter` ?

это строгие (excluded) сравнения, а нам также нужны краевые значения

> В `MealsUtil` у нас где-то есть ключевое слово `final`, где-то нет. В чем разница?

Я участвовал в одном  проекте, где `final` был обязательным (в сеттингах IDEA галочка стояла). Но это скорее исключение, чем правило в проектах java (в Java 8 вообще ввели эффективный final, те по факту). Во всех новомодных языках переменные final по умолчанию, а в java нужно помнить и везде добавлять, утомительно. Но если приучитесь - хуже не будет. Я обычно ставлю там, где важно по смыслу (если не забываю).

> Почему у `User.registered` тип `Date`, а `Meal.dateTime` `LocalDateTime `?

Врямя регистрации - абсолютное (конкретный момент), а время еды по бизнес логике относительно (те независит от часового пояса)

## Занятие 1:

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFRmo0YkVVaDJPTVE">Обзор используемых в проекте технологий. Интеграция ПО.</a>
-  **<a href="http://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-2016/">Обзор популярности инструментов и технологий Java за 2016 г.</a>**
-  <a href="http://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-for-2014/">Обзор популярности инструментов и технологий Java за 2014 г.</a>
-  <a href="http://www.youtube.com/watch?v=rJZHerwi8R0">Видео "Приложение Spring Pet Clinic"</a> 
-  Приложение <a href="https://github.com/spring-projects/spring-petclinic">Spring Pet Clinic</a>. 
-  Demo <a href="http://petclinic.cloudapp.net/">Spring Pet Clinic</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFSlZMTXBJRXJpakU">Maven.</a>
- Среда сборки проектов <a href="http://www.apache-maven.ru/" target="_blank">Maven</a>.
- <a href="http://search.maven.org/#browse">The Central Repository</a>
- Настройка пропертей Maven: кодировка, java version, зависимости, maven-compiler-plugin
- <a href="http://maven.apache.org/guides/mini/guide-multiple-modules.html">The Reactor</a>. Snapshots
- <a href="http://habrahabr.ru/blogs/java/106717/" target="_blank">Недостатки Maven</a>. Другие инструменты сборки.
- Справочник:
  - <a href="https://www.youtube.com/watch?v=21qdRgFsTy0">Видео: Maven vs Gradle vs SBT (Архипов, Борисов, Садогурский)</a>
  - <a href="http://habrahabr.ru/post/77333/">Автоматизация сборки проекта</a>
  - <a href="http://maven.apache.org/">Home Page</a>
  - <a href="http://books.sonatype.com/mvnref-book/reference/index.html">Maven: The Complete Reference</a>
  - <a href="http://study-and-dev.com/blog/build_management_maven_1/">Разработка ПО вместе с maven</a>
  - <a href="http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html">Build Lifecycle</a>
  - <a href="http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html">Dependency Mechanism</a>
  - <a href="http://habrahabr.ru/post/111408/">Создание своих архетипов и каталогов в Maven</a>
  - <a href="http://www.ibm.com/developerworks/ru/library/j-5things13/">Зависимости, профили</a>
  - <a href="http://blog.bintray.com/2014/02/11/bintray-as-pain-free-gateway-to-maven-central/">Bintray: gateway to Maven Central</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFT3pWNkMzWVVybnc">WAR. Веб-контейнер Tomcat. Сервлеты.</a>
> - **Нам нужна 8-я версия Tomcat (например [последняя 8.5.16](http://apache-mirror.rbc.ru/pub/apache/tomcat/tomcat-8/v8.5.16/bin/))** 
> - **Устанавливать Tomcat лучше простым скачиванием архива `apache-tomcat-8.5.16[-windows-xxx].zip` и копированием из него в каталог без пробелов и русских букв (пример `С:\java\apache-tomcat-8.5.16`)**. Не используйте Tomcat 8.5.13 и 9.0.0.M19 IDEA [из за бага Tomcat](https://stackoverflow.com/questions/43354676)

#### Apply 1_3_switch_to_war.patch
> - Обновил сервлеты до <a href="https://www.mkyong.com/web-development/the-web-xml-deployment-descriptor-examples/">версии 3.1</a> 
(Tomcat 8 использует это API, хотя для нас не принципиально, т.к. мы никакие <a href="https://ru.wikipedia.org/wiki/Сервлет_(Java)">фичи 3.x Servlet API</a> не используем)
> - Переименовал `userList.jsp` в `users.jsp`
> - Сервлет добавляется в следующем патче, те **в `web.xml` он будет подчеркиваться красным.**

#### Apply 1-4-add-servlet-api.patch

> - **Если зависимость `servlet-api` не подтянулась, <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9E%D0%B1%D0%BD%D0%BE%D0%B2%D0%B8%D1%82%D1%8C-%D0%B7%D0%B0%D0%B2%D0%B8%D1%81%D0%B8%D0%BC%D0%BE%D1%81%D1%82%D0%B8-%D0%B2-maven-%D0%BF%D1%80%D0%BE%D0%B5%D0%BA%D1%82%D0%B5">обновите maven проект</a>.**
> - [Проверка, кто занял порт](https://stackoverflow.com/a/38953356/548473) (в случае проблем с запуском и дебагом на портах 8080, 8000)</a>
> - [**Деплой war в Tomcat с Application context**](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%94%D0%B5%D0%BF%D0%BB%D0%BE%D0%B9-war-%D0%B2-tomcat-application-context-%D0%B4%D0%BE%D0%BB%D0%B6%D0%B5%D0%BD-%D0%B1%D1%8B%D1%82%D1%8C-%D1%82%D0%BE%D1%82-%D0%B6%D0%B5-%D1%87%D1%82%D0%BE-%D0%B8-url-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-%D0%B4%D0%B5%D0%BF%D0%BB%D0%BE%D0%B8%D1%82%D1%8C-%D0%BD%D0%B0%D0%B4%D0%BE-war-exploded)
> - [**Динамическое обновление без передеплоя**](https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%94%D0%BB%D1%8F-%D0%B4%D0%B8%D0%BD%D0%B0%D0%BC%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%BE%D0%B9-%D0%BF%D0%B5%D1%80%D0%B5%D0%B3%D1%80%D1%83%D0%B7%D0%BA%D0%B8-%D1%80%D0%B5%D1%81%D1%83%D1%80%D1%81%D0%BE%D0%B2-%D0%BA%D0%BD%D0%BE%D0%BF%D0%BA%D0%B0-%D0%BD%D0%B0%D0%B6%D0%BC%D0%B8%D1%82%D0%B5-%D0%BA%D0%BD%D0%BE%D0%BF%D0%BA%D1%83-update-resource-on-frame-deactivation)

#### Apply 1-5-forward-to-redirect.patch

- <a href="http://tomcat.apache.org/">Tomcat Home Page</a>
- <a href="http://java-course.ru/student/book1/servlet/">Сервлеты.</a>
- Томкат менеджер: [http://localhost:8080/manager](http://localhost:8080/manager)
  - в `TOMCAT_HOME\conf\tomcat-users.xml` нужно добавить 
```
<user username="tomcat" password="tomcat" roles="tomcat,manager-gui,admin-gui"/>
```
- Наше приложение: [http://localhost:8080/topjava](http://localhost:8080/topjava)
- Наш сервлет:     [http://localhost:8080/topjava/users](http://localhost:8080/topjava/users)
- Справочник:
  - [HTTP](https://developer.mozilla.org/ru/docs/Web/HTTP)
  - <a href="http://info.javarush.ru/idea_help/2014/01/22/Руководство-пользователя-IntelliJ-IDEA-Отладчик-.html">Отладчик IntelliJ IDEA</a>
  - <a href="https://www.youtube.com/watch?v=tN8K1y-HSws&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=14">Yakov Fain: Intro to Java EE. Glassfish. Servlets (по русски)</a>
  - <a href="https://www.youtube.com/watch?v=Vumy_fbo-Qs&list=PLkKunJj_bZefB1_hhS68092rbF4HFtKjW&index=15">Yakov Fain: HTTP Sessions, Cookies, WAR deployments, JSP (по русски)</a>
  - <a href="https://www.youtube.com/playlist?list=PLoij6udfBncjHaO5s7Ln4w4BLj5FVc49P">Golovach Courses: Junior.February2014.Servlets</a>
  - <a href="http://blog.trifork.com/2014/07/14/how-to-remotely-debug-application-running-on-tomcat-from-within-intellij-idea">Remotely debug on tomcat from IDEA</a>
  - <a href="http://www.java2ee.ru/jsp/">Java Server Page</a>
  - <a href="http://stackoverflow.com/questions/1890438/how-to-get-parameters-from-the-url-with-jsp#1890462">Java объекты, доступные в JSP</a>
  - <a href="http://java-online.ru/jsp.xhtml">Java Server Pages (JSP)</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFaTdYUnpLNFFUeXM">Логирование.</a>
#### Apply 1-6-logging.patch
 
> **Установите переменную окружения на TOPJAVA_ROOT на корень проекта и перезапустите IDEA. Слеши в пути должны быть в стиле unix (/)**

> - изменения в проекте: убрал `LoggerWrapper` и логирую напрямую в логгер SLF4J. При логгировании через вспомогательный класс, в логе теряется имя исходного класса.
> - удалил зависимость `jul-to-slf4j`. Она нам не нужена и, согласно <a href="https://www.youtube.com/watch?v=qzqAUUgB3v8">видео Владимира Красильщика про логирование</a>, она замедляет работу  
> - Не делать конкатенацию строк при логгировании сообщений, если уровень логирования в конфигурации выставлен выше уровня логирования в коде
>   - [slf4j formatting with {}](http://stackoverflow.com/a/10596390/548473)
>   - [What is the fastest way of (not) logging](https://www.slf4j.org/faq.html#logging_performance)

- <a href="http://habrahabr.ru/post/113145/">Java Logging: история кошмара</a>
- <a href="http://skipy.ru/useful/logging.html">Ведение лога приложения</a>
- <a href="http://logging.apache.org/log4j/2.x/index.html">Log4j</a>, <a href="http://logback.qos.ch/">Logback</a>
- <a href="http://www.slf4j.org/legacy.html">Добавление зависимостей логирования</a> в проект.
- <a href="http://logback.qos.ch/manual/configuration.html#variableSubstitution">Logback variable substitution</a>

#### Проверочные вопросы:
- Что нужно изменить в `pom.xml`, чтобы перейти с logback на log4j ?

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы

> Почему `private static final Logger log` а не `LOG` ?

- Так ее называют по умолчанию в проекте lombook. 
- Это [правило именования констант, которые не "deeply immutable"](https://google.github.io/styleguide/javaguide.html#s5.2.4-constant-names), те если их содержимое можно изменить.

>  Используются ли еще где-то в реальной разработке JSP, или это уже устаревшая технология? Заменит ли ее JSF (https://javatalks.ru/topics/38037)?

JSF и JSP- разные ниши и задачи.
JSP- шаблонизатор, JSF- МVС фреймворк. Из моего опыта- с JSP сталкивался в 60% проектов. Его прямая замена: http://www.thymeleaf.org (в Spring-Boot по умолчанию), но в уже запущенных проектах встречется достаточно редко. JSP не умирает, потому что просто и дешево. Кроме того включается в большнство веб-контейнеров (в Tomcat его реализация Jasper)

JSF- sun-овский еще фреймворк, с которым я ни разу не сталкивался и особого желания нет. Вот он как раз, по моему мнению, активно замещается хотя бы javascript фреймворками (angular, ember, react).

> А зачем мы использовали logback? Почему SLF4J нас не устроило? Почему реализация логирования не log4j?

`SLF4J-API` это API. Там есть только пустая реализация `org.slf4j.helpers.NOPLogger` (можно посмотреть в исходниках). Logback для новых проектов стал стандарт. *spring-petclinic* и *spring-boot* используют его по умолчанию.
- http://logback.qos.ch/reasonsToSwitch.html

---------

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW01 (делаем ветку HW01 от последнего патча в master) 

> На проекте мы **учимся мыслить и работать как Java разработчики уже сейчас**, потом это будет гораздо сложнее и стоить дороже.
Вот на мой взгляд [хорошие советы новичкам](http://blog.csssr.ru/2016/09/19/how-to-be-a-beginner-developer). От себя я добавлю:
> - Учись грамотно формулировать проблему. Проблема "у меня не работает" может иметь тысячи причин. В процессе формулирования очень часто приходит ее решение.</li>
> - Учись исследовать проблему. Внимательное чтение логов и [умение дебажить](http://info.javarush.ru/idea_help/2014/01/22/Руководство-пользователя-IntelliJ-IDEA-Отладчик-.html) - основные навыки разработчика. Обычно самый верх самого нижнего эксепшена- причина ошибки, туда нужно ставить брекпойнт.
> - Грамотно уделяй время каждой проблеме. Две крайности - сразу бросаться за помощью и бится над ней часами. Пробуй решить ее сам и в зависимости от проблемы выделяй на это разумное время.

#### 1. Реализовать сервлет с отображением в таблице списка еды (в памяти и БЕЗ учета пользователя)

> Деплоиться в Tomcat лучше как `war exploded`: нет упаковки в war и при нажатой кнопке `Update Resources on Frame Deactivation` можно обновляться css, html, jsp без передеплоя. При изменении `web.xml`, добавлении методов, классов необходим redeploy.

- 1.1 По аналогии с `UserServlet` добавить `MealServlet` и `meals.jsp`
  - Задеплоить приложение (war) в Tomcat c `applicationContext=topjava` (приложение должно быть доступно по <a href="http://localhost:8080/topjava">http://localhost:8080/topjava</a>)
  - Попробовать разные деплои в Tomcat, remote и local debug
- 1.2 Сделать отображения списка еды в JSP, цвет записи в таблице зависит от параметра `exceed` (красный/зеленый).
  - 1.2.1 Список еды захардкодить (те проинициализировать в коде, желательно чтобы в проекте инициализация была только в одном месте)
  - 1.2.2 Время выводить без 'T'
  - 1.2.3 Список выводим БЕЗ фильтрации по `startTime/endTime`
  - 1.2.4 Вариант реализации:
    - из сервлета преобразуете еду в `List<MealWithExceeded>`;
    - кладете список в запрос (`request.setAttribute`);
    - делаете `forward` на jsp для отрисовки таблицы (при `redirect` атрибуты теряются).
    - в `JSP` для цикла можно использовать `JSTL tag forEach`. Для подключения `JSTL` в `pom.xml` и шапку JSP нужно добавить:
```
    <dependency>
       <groupId>javax.servlet</groupId>
       <artifactId>jstl</artifactId>
       <version>1.2</version>
    </dependency>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    ...
```

  - <a href="http://java-course.ru/student/book1/servlet/">Интернет-приложения на JAVA</a>
  - <a href="http://java-course.ru/student/book1/jsp/">JSP</a>
  - <a href="http://devcolibri.com/1250">JSTL для написания JSP страниц</a>
  - <a href="http://javatutor.net/articles/jstl-patterns-for-developing-web-application-1">JSTL: Шаблоны для разработки веб-приложений в java</a>
  - <a href="http://stackoverflow.com/questions/35606551/jstl-localdatetime-format">JSTL LocalDateTime format</a>

### Optional
#### 2. Реализуем в ПАМЯТИ CRUD (create/read/update/delete) для еды
**Пример: <a href="https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/">Simple CRUD using Servlet/JSP</a>**
- 2.1 Хранение в памяти будет одна из наших CRUD реализаций (позже будет JDBC, JPA и DATA-JPA).
- 2.2 Работать с реализацией CRUD через интерфейс, который не должен ничего знать о деталях реализации (Map, DB или что-то еще).
- 2.3 Добавить поле `id` в `Meal/ MealWithExceed` и реализовать генерацию счетчика, УЧЕСТЬ МНОГОПОТОЧНОСТЬ СЕРВЕЛТОВ
    - [обзор java.util.concurrent](https://habrahabr.ru/company/luxoft/blog/157273/)
- 2.4 Сделать форму редактирования в JSP: AJAX/JavaScript использовать НЕ надо, делаем через `<form method="post">` и `doPost()` в сервлете.
- 2.5 Для ввода дат и времени можно использовать <a href="https://webref.ru/html/input/type">html5 типы</a>, хотя они поддерживаются не всеми браузерами (<a href="https://robertnyman.com/html5/forms/input-types.html">протестировать свой браузер</a>). В конце курса мы добавим <a href="http://xdsoft.net/jqplugins/datetimepicker/">DateTimePicker jQuery plugin</a>, который будет работать на всех браузерах.

## После выполнения ДЗ обязательно <a href="lesson01.md#-Типичные-ошибки">проверьте решение на ошибки</a>

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы по HW1

> Не попадаю на страничку/брекпойнт в сервлете.

- внимательно проверь url и applicationContext (Application Context должен быть тот же, что и url приложения: <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%94%D0%B5%D0%BF%D0%BB%D0%BE%D0%B9-war-%D0%B2-tomcat-application-context-%D0%B4%D0%BE%D0%BB%D0%B6%D0%B5%D0%BD-%D0%B1%D1%8B%D1%82%D1%8C-%D1%82%D0%BE%D1%82-%D0%B6%D0%B5-%D1%87%D1%82%D0%BE-%D0%B8-url-%D0%BF%D1%80%D0%B8%D0%BB%D0%BE%D0%B6%D0%B5%D0%BD%D0%B8%D1%8F-%D0%B4%D0%B5%D0%BF%D0%BB%D0%BE%D0%B8%D1%82%D1%8C-%D0%BD%D0%B0%D0%B4%D0%BE-war-exploded">wiki IDEA</a>)
- посомтрите в task manager: возможно запущено несколько JVM и они мешают друг другу. Лишние java приложения убить.

> Приложение не видит TOPJAVA_ROOT.

**После выставления переменной окружения IDEA нужно рестартовать. Слеши в пути должны быть в стиле unix (/)**. Проверить, видит ли java переменную окружения можно так: `System.getenv("TOPJAVA_ROOT")`. Еще вариант: добавить `-DTOPJAVA_ROOT=...` в опции запуска приложения, тогда она доступна из java как `System.getProperty("TOPJAVA_ROOT")`.

> Проблемы с кодировкой в POST (кракозябры). 

Возможное решение - выставьте кодироку ДО первого чтения из request:
```
protected void doPost(HttpServletRequest request, ...) {
    request.setCharacterEncoding("UTF-8");
```

> Если сервлет тыкают несколько пользователей / несколько браузеров, какого должно быть поведение? Нужно ли что-то делать с сессиями?

В Optional нужно делать реализацию хранения  многопоточной. Cессии пока не используем  (начнутся, когда будет прикручивать авторизацию).

> Для чего нам нужна многопоточная реализация коллекции, если каждый пользователь видит только себя?

Реализация хранения в памяти у нас одна на всех. Те коллекция шарится между пользователями, а они в разных потоках ее дергают. Если несколько потоков одновременно будут изменять коллекцию без учета многопоточности (нарпимер один будет удалять, второй вставлять), мы получим `ConcurrentModificationException`

> Предпочтительнее ли создавать новый объект `Meal` при каждом update?

Если при обновлении не создавать копию, то сохраненный в памяти объект может кто-то попортить. Вопрос скорее доверия к коду- если проект большой и людей над ним трудится много, то вероятнее нужно копировать.

> Почему теряются атрибуты при передаче на сервлет: `http://localhost:8080/topjava/meals?action=add&...` и `req.getAttribute("action")` = null ?

См. <a href="http://stackoverflow.com/questions/5243754/difference-between-getattribute-and-getparameter">Difference between getAttribute() and getParameter()</a>. Отсюда также следует, что при редиректе что атрибуты теряются.

> Зачем нужен в jsp `<jsp:useBean id=".." scope="request" type=".."/>` ?

<a href="http://www.java2ee.ru/jsp/useBean.html">jsp:useBean</a> нужен IDEA для автодополнений - она понимает тип переменной, которая уже доступна в JSP (например через setAttribute). И еще эта переменная становится доступной в java вставках. Для вывода в JSP это тэг не обязателен. Если тип переменной JSP не совпадает с тем, что в `jsp:useBean`, будет ошибка.

----------------------------
### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки
- 1 **Если в названии класса есть `Meal`, не нужно использовать слово meal в методах класса.**
- 2 Привыкайте писать коментарии к чекину: одной фразой что вы сделали в нем. Нарпимер: *Meals CRUD implementation*
- 3 Хранение в памяти и операции с ней должны выполняться просто и эффективно
- 4 Хранить нужно `Meal` и конвертировать ее в `MealWithExceed` когда отдаем список на отображение в JSP. Иначе при редактировании любой записи или изменении юзером своей нормы `caloriesPerDay` нужно будет пересчитывать все записи юзера.
- 5 Стили `color` можно применять ко всей строке таблицы `tr`, а не каждой ячейке.
- 6 `DateTimeFormatter` можно сделать один заранее (он потокобезопасный в отличии от `SimpleDateFormatter`), а не создавать новый при каждом запросе.
- 7 Работать с CRUD надо через интерфейс. 
- 8 Реализаций хранения будет несколько, нужно учитывать это в названии класса имплементации работы в памяти.
- 9 В `web.xml` принято группировать сервлет со своим маппингом
- 10 Не размещайте никакую логику (форматирование, счетчики) в бинах, где хранятся только данные (`Meal, MealWithExceed`)
- 11 Еще раз: детали реализации в памяти не должны быть никому видны. Те НЕ НАДО счетчик размещать в `Meal` или `MealServlet` или `MealsUtil`, в базе же он будет по другому генерится. 
- 12 `volatile` при ++ не помогает от многопоточности. Почему? 
- 13 Обратите также внимание на то, чтобы реализация вашей коллекции для хранения еды была также многопоточной.
- 14 Не делайте дублирование кода `MealsUtil`. Возможно вам пригодятся константы `LocalTime.MIN` и `LocalTime.MAX`
- 15 Не дублируйте строки в `jsp`. Посмотрите на <a href="https://steveswinsburg.wordpress.com/2008/09/04/the-ternary-operator-and-jsp/">тернарный оператор</a>.
- 16 после операции `delete` в браузере должен быть url `http:\\localhost:8080\topjava\meals`
- 17 Перед чекином проверяйте свой ченджлист (`Ctrl+D` на файле из `Local Changes` - посмотреть что поменялось). Если там только пробелы/переводы строк, не надо его комитить - делайте файлу `Git->revert`.
- 18 Учтите в названии реализации CRUD, что
  - 18.1 у нас будет несколько реализаций (не только в памяти)
  - 18.2 у нас будет 2 CRUD (для еды и пользователей). А в реальном проекте их намного больше.
- 19 Сессии НЕ использовать! При редиректе все атрибуты (`req.getAttribute()`) теряются (см. вопрос выше). Сценарий редиректа:
  - 1 из сервлета делаем редирект (снова на сервлет, не на JSP!)
  - 2 снава заходим в сервлет
  - 3 кладем нужные атрибуты и делаем forward на jsp
  - 4 если очень хочется передать параметры из 1. в 2. можно сделать их через параметры запроса (например `meals?id=5`) и доставать через `reg.getParameter(id)`. В моей реализации такого не потребовалось.
