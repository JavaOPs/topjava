Java Enterprise Online Project 
===============================
Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), datatables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных Postgresql и HSQLDB.

![topjava_structure](https://user-images.githubusercontent.com/13649199/27433714-8294e6fe-575e-11e7-9c41-7f6e16c5ebe5.jpg)

    Когда вы слышите что-то, вы забываете это.
    Когда вы видите что-то, вы запоминаете это.
    Но только когда вы начинаете делать это,
    вы начинаете понимать это

    Старинная китайская поговорка

## <a href="description.md">Описание и план проекта</a>
### <a href="http://topjava.herokuapp.com/" target=_blank>Демо разрабатываемого приложения</a>
### [Изменения проекта (Release Notes)](ReleaseNotes.md)
### <a href="https://github.com/JavaOPs/topjava/wiki">Требования к участникам, Wiki</a>
### <a href="cv.md">Составление резюме, подготовка к интервью, поиск работы</a>

Вводное занятие (обязательно смотреть все видео)
===============
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFY1ZDNXRCd1NCTG8">Осваиваем Java Enterprise. Трудоустройство. Ответы на вопросы.</a>
- <a href="https://goo.gl/XNVOj4">Слайды презентации</a>
- <a href="http://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-2016/">Java Tools and Technologies Landscape Report 2016</a>
- [Java in 2017 Survey](http://www.baeldung.com/java-in-2017)
- <a href="https://habrahabr.ru/post/308104/">Из юниоров в разработчики: получаем первую работу</a>

#### Spring Pet-Clinic
- <a href="https://github.com/spring-projects/spring-petclinic">Spring PetClinic Sample Application </a>
- <a href="https://speakerdeck.com/michaelisvy/spring-petclinic-sample-application">Presentation</a>

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFSUNrdVc0bDZuX2s">Системы управления версиями. Git.</a>
-  **<a href="https://github.com/JavaOPs/topjava/wiki/Git">Wiki по ведению проекта в Git</a>**
-  <a href="http://ru.wikipedia.org/wiki/Система_управления_версиями">Система управления версиями</a>. <a href="http://ru.wikipedia.org/wiki/%D0%A1%D0%B8%D1%81%D1%82%D0%B5%D0%BC%D0%B0_%D1%83%D0%BF%D1%80%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F_%D0%B2%D0%B5%D1%80%D1%81%D0%B8%D1%8F%D0%BC%D0%B8#.D0.A0.D0.B0.D1.81.D0.BF.D1.80.D0.B5.D0.B4.D0.B5.D0.BB.D1.91.D0.BD.D0.BD.D1.8B.D0.B5_.D1.81.D0.B8.D1.81.D1.82.D0.B5.D0.BC.D1.8B_.D1.83.D0.BF.D1.80.D0.B0.D0.B2.D0.BB.D0.B5.D0.BD.D0.B8.D1.8F_.D0.B2.D0.B5.D1.80.D1.81.D0.B8.D1.8F.D0.BC.D0.B8">VCS/DVSC</a>.
-  Ресурсы:            
    -  <a href="https://try.github.io/levels/1/challenges/1">Интерактивная Git обучалка</a>
    -  <a href="http://learngitbranching.js.org/">Еще одна интерактивная обучалка, по-русски</a>    
    -  <a href="https://git-scm.com/book/ru/v2">Книга Git</a>
    -  <a href="https://illustrated-git.readthedocs.org/en/latest/#working-with-remote-repositories">Working with remote repositories</a>
    -  <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5B3sufBJ_KFTpKkMEvaTQR">Видео по обучению Git</a>
    -  <a href="https://blog.interlinked.org/tutorials/git.html">Git Overview</a>
    -  [Основы Git за 20 минут](https://www.youtube.com/watch?v=TMeZGvtQnT8)
    -  [Git - для новичков](https://www.youtube.com/watch?list=PLY4rE9dstrJyTdVJpv7FibSaXB4BHPInb&v=PEKN8NtBDQ0)

##  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZDdaaU5fZEo4X3c">Работа с проектом (выполнять инструкции)</a>
**ВНИМАНИЕ: выбирайте для проекта простой пусть без пробелов и русских букв, например (Windows) `c:\projects\topjava\`. Иначе впоследствии будут проблемы**
### Патч [prepare_to_HW0.patch](https://drive.google.com/file/d/1LNPpu9OkuCpfpD8ZJHO-o0vwu49p2i5M) (скачать и положить в каталог вашего проекта)

> Проект постоянно улучшается, поэтому видео иногда отличается от кода проекта. Изменения указываю после видео: 
> - переименовал класс `UserMealWithExceed` и его поле `exceed` в `UserMealWithExcess.excess`
> - в `UserMeals/UserMealWithExcess` поля изменились на `private`
> - обновил данные `UserMealsUtil.meals` и переименовал некоторые пременные, поля и методы
> - добавил `UserMealWithExcess.toString()` и метод для выполнения _Optional домашнего задания_


##  Инструкция по шагам (из видео):</h3>
-  <a href="http://javaops.ru/view/soft">Установить ПО (git, JDK8, IntelliJ IDEA, Maven)</a>
-  Создать аккаунт на <a href="https://github.com">GitHub</a>
-  Сделать Fork **ЭТОГО** проекта (https://github.com/JavaOPs/topjava) </a>
-  Сделать локальный репозиторий проекта:
            <pre>git clone https://github.com/[Ваш аккаунт]/topjava.git</pre>
-  Открыть и настроить проект в IDEA
   - <a href="http://stackoverflow.com/questions/29695918/intellij-idea-console-issue#33035499">Выставить кодировку UTF-8 в консоли</a>
   - <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%B4%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D1%83-utf-8">Поставить кодировку UTF-8</a>
   - Опционально: <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D0%BC%D0%B5%D0%BD%D1%8F%D1%82%D1%8C-%D1%84%D0%BE%D0%BD%D1%82-%D0%BF%D0%BE-%D1%83%D0%BC%D0%BE%D0%BB%D1%87%D0%B0%D0%BD%D0%B8%D1%8E-dejavu">Поменять фонт по умолчанию (DejaVu)</a> или на **новый [JetBrains Mono](https://habr.com/ru/company/jugru/news/t/484134/)**
-  По ходу видео сделать Apply Patch... скаченного патча Prepare_ to_ HW0.patch
-  Закоммитить и запушить изменения (commit + push)
-  Сделать ветку домашнего задания
-  Выполнить задание и залить на GitHub (commit + push)
-  Переключиться в основную ветку проекта master.

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. [Тех.задание: библия или допускаются изменения. Полуоткрытый интервал.](https://drive.google.com/file/d/123XyBYVeKLC3ZcRr_dUkwyvO9NC6WLkY/view?usp=sharing)
- [Типы промежутков](https://ru.wikipedia.org/wiki/Промежуток_(математика))

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW0
```
Реализовать метод `UserMealsUtil.filteredByCycles` через циклы (`forEach`):
-  должны возвращаться только записи между `startTime` и `endTime`
-  поле `UserMealWithExcess.excess` должно показывать, 
                                     превышает ли сумма калорий за весь день значение `caloriesPerDay`
        
Т.е `UserMealWithExcess` - это запись одной еды, но поле `excess` будет одинаково для всех записей за этот день.
    
- Проверьте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
- Оцените Time complexity алгоритма. Если она больше O(N), например O(N*N) или N*log(N), сделайте O(N).
```
-  <a href="http://www.mscharhag.com/2014/02/java-8-datetime-api.html">Java 8 Date and Time API</a>
-  <a href="https://tproger.ru/translations/algorithms-and-data-structures">Алгоритмы и структуры данных для начинающих: сложность алгоритмов</a>
-  [Головач: сложность алгоритмов в теме коллекций](https://www.youtube.com/watch?v=Ek9ijOiplNE&feature=youtu.be&t=778)
-  <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFNEJWRFJkVDA3TkU/view">Time complexity</a>
-  <a href="https://ru.wikipedia.org/wiki/Временная_сложность_алгоритма">Временная сложность алгоритма</a>
-  <a href="https://ru.wikipedia.org/wiki/Вычислительная_сложность">Вычислительная сложность</a>

#### ВНИМАНИЕ: варианты Optional делайте в `UserMealsUtil` в одной ветке в разных методах. Проще делать, проще проверять

### Optional (Java 8 Stream API)
```
Реализовать метод `UserMealsUtil.filteredByStreams` через Java 8 Stream API.
```
-  <a href="http://www.youtube.com/watch?v=_PDIVhEs6TM">Видео: Доступно о Java 8 Lambda</a>
-  <a href="https://devcolibri.com/java-8-killer-features-%D1%87%D0%B0%D1%81%D1%82%D1%8C-1/">Java 8: Lambda выражения</a>
-  <a href="https://devcolibri.com/java-8-killer-features-%D1%87%D0%B0%D1%81%D1%82%D1%8C-2/">Java 8: Потоки</a>
-  <a href="https://javadevblog.com/polnoe-rukovodstvo-po-java-8-stream.html">Pуководство по Java 8 Stream</a>
-  <a href="https://annimon.com/article/2778">Java 8 Stream API в картинках и примерах</a>
-  [7 способов использовать groupingBy в Stream API](https://habrahabr.ru/post/348536)
-  <a href="http://habrahabr.ru/post/224593/">Лямбда-выражения в Java 8</a>
-  <a href="https://github.com/winterbe/java8-tutorial">A Guide to Java 8</a>
-  <a href="http://habrahabr.ru/company/luxoft/blog/270383/">Шпаргалка Java Stream API</a>
-  <a href="https://www.youtube.com/watch?v=hEyCK4ueBlc">Алексея Владыкин: Элементы функционального программирования в Java</a>
-  <a href="https://www.youtube.com/watch?v=iD8H7cmxw_w">Yakov Fain о новом в Java 8</a>
-  <a href="http://stackoverflow.com/questions/28319064/java-8-best-way-to-transform-a-list-map-or-foreach">stream.map vs forEach</a>
-  Дополнительно
   - [Сергей Куксенко — Stream API, часть 1](https://www.youtube.com/watch?v=O8oN4KSZEXE)
   - [Сергей Куксенко — Stream API, часть 2](https://www.youtube.com/watch?v=i0Jr2l3jrDA)

### Optional 2 (+5 бонусов)
Сделать реализацию со сложностью O(N) (обратите внимание на п.13 замечаний):
- циклом за 1 проход по `List<UserMeal>`
  - без циклов по другим коллекциям
  - решение должно быть рабочим в общем случае (не только при запуске main)
- через Stream API за 1 проход по исходному списку `meals.streem()`
  - нельзя использовать внешние коллекции, не являющиеся частью коллектора или 2 раза проходить по исходному списку (в том числе модифицированному, например отфильтрованному).
    Т.е. в решении не должно быть 2 раза `meal.stream()` (в том числе неявно, в составных коллекторах)
  - возможно дополнительные проходы по частям списка

### Замечания по использованию Stream API:
- Когда встречаешь что-то непривычное, приходится перестраивать мозги. Например, переход с процедурного на ООП программирование дается непросто. Те, кто не знает шаблонов (и не хотят учить) также их встречают плохо. Хорошая новость в том, что если это принять и начать использовать, то начинаешь получать от этого удовольствие. И тут главное не впасть в другую крайность:
  - [Используйте Stream API проще (или не используйте вообще)](https://habrahabr.ru/post/337350/)
- Если вас беспокоить производительность стримов, обязательно прочитайте про оптимизацию 
    - ["Что? Где? Когда?"](http://optimization.guide/intro.html)
    - [Перформанс: что в имени тебе моём?](https://habrahabr.ru/company/jugru/blog/338732/)
    - [Performance это праздник](https://habrahabr.ru/post/326242/)
    
При использовании Stream API производительность улучшиться только на больших задачах, где возможно распараллеливание.
Еще - просто так запустить и померять скорость JVM нельзя (как минимум дать прогреться и запустить очень большое число раз). Лучше использовать какие-нибудь бенчмарки, например [JMH](http://tutorials.jenkov.com/java-performance/jmh.html), который мы юзаем на другом проекте (Mastejava).
  
## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Замечания к HW0
- 1: Код проекта менять можно! Одна из распространенных ошибок как в тестовых заданиях на собеседовании, так и при работе на проекте, что ничего нельзя менять. Конечно при правках в рабочем проекте обязательно нужно проконсультироваться/проревьюироваться у авторов кода (находится по истории VCS)
- 2: Наследовать `UserMealWithExcess` от `UserMeal` я не буду, т.к. это разные сущности: Transfer Object и Entity. Мы будет их проходить на 2м уроке.
- 3: Правильная реализация должна быть простой и красивой, можно сделать 2-мя способами: через стримы и через циклы. Сложность должна быть O(N), т.е. без вложенных стримов и циклов.
- 4: При реализации через циклы посмотрите в `Map` на методы `getOrDefault` или `merge`
- 5: **При реализации через `Stream` заменяйте `forEach` оператором `stream.map(..)`**
- 6: Объявляйте переменные непосредственно перед использованием (если возможно - сразу с инициализацией). При объявлении коллекций используйте тип переменной - интерфейс (Map, List, ..)
- 7: Если IDEA предлагает оптимизацию (желтым подчеркивает), например заменить лямбду на метод-референс, соглашайтесь (Alt+Enter)
- 8: Пользуйтесь форматированием кода в IDEA: `Alt+Ctrl+L`
- 9: Перед check-in проверяйте чендж-лист (курсор на файл и Ctrl+D): не оставляйте в коде ничего лишнего (закомментированный код, TODO и пр.). Если файл не меняется (например только пробелы или переводы строк), не надо его чекинить, делайте ему `revert` (Git -> Revert / `Ctrl+Alt+Z`).
- 10: `System.out.println` нельзя делать нигде, кроме как в `main`. Позже введем логирование.
- 11: Результаты, возвращаемые `UserMealsUtil.filteredByStreams` мы будем использовать [в нашем приложении](http://topjava.herokuapp.com/) для фильтрации по времени и отображения еды правильным цветом.
- 12: Обращайте внимание на комментарии к вашим коммитам в git. Они должны быть короткие и информативные (лучше на english)
- 13: Не полагайтесь в решении на то, что список будет подаваться отсортированным. Такого условия нет.
-----
## [Пример 7-го занятия онлайн стажировки, несколько видео открыто](https://github.com/JavaOPs/topjava/blob/master/doc/lesson07.md)

### Полезные ресурсы
> ВНИМАНИЕ:
>  - **ДЗ первого урока будет связано с [созданием небольшого CRUD приложения (в памяти, без DB) на JSP и сервлетах](https://danielniko.wordpress.com/2012/04/17/simple-crud-using-jsp-servlet-and-mysql/)**. Введение будет, но предварительное знакомство не помешает.
>  - основы JavaSсript необходимы для понимания проекта, начиная с 8-го занятия!

Все остальное - опционально.

#### HTML, JavaScript, CSS 
- [Basic HTML and HTML5](https://learn.freecodecamp.org/responsive-web-design/basic-html-and-html5/say-hello-to-html-elements/)
- [Справочник по WEB](https://developer.mozilla.org/ru/)
- [Видео по WEB технологиям](https://www.youtube.com/user/WebMagistersRu/playlists)
- [Изучение JavaScript в одном видео уроке за час](https://www.youtube.com/watch?v=QBWWplFkdzw)
- <a href="http://www.w3schools.com/default.asp">HTML, CSS, JAVASCRIPT, SQL, JQUERY, BOOTSTRAP</a>
- <a href="https://www.youtube.com/watch?v=j0ycGQKqMT4">Введение в программирование на JavaScript</a>
- <a href="http://anton.shevchuk.name/javascript/html-css-javascript-standarts/">Стандарты кодирования для HTML, CSS и JavaScript’a</a>
- <a href="http://www.intuit.ru/studies/courses/1102/134/info">Основы работы с HTML/CSS/JavaScript</a>
- <a href="http://itchief.ru/lessons/javascript/94-javascript-introduction">JavaScript - Основы</a>
- <a href="http://learn.javascript.ru/first-steps">Основы JavaScript</a>
- <a href="http://itchief.ru/lessons/bootstrap-3/19-introduction-to-twitter-bootstrap-3">Bootstrap 3 - Основы</a>
- <a href="http://anton.shevchuk.name/jquery/">jQuery для начинающих</a>

#### Java (базовые вещи)
- <a href="http://www.intuit.ru/studies/courses/16/16/info">Интуит. Программирование на Java</a>
- <a href="https://github.com/JavaOPs/masterjava#Первое-занятие-многопоточность">1й урок MasterJava: Многопоточность</a>
- [Основы Java garbage collection](http://web.archive.org/web/20180831013112/https://ggenikus.github.io/blog/2014/05/04/gc)
- <a href="https://habrahabr.ru/post/134102/">Размер Java объектов</a>
- <a href="http://www.quizful.net/post/java-reflection-api">Введение в Java Reflection API</a>
- <a href="https://habrahabr.ru/users/tarzan82/topics/">Структуры данных в картинках</a>
- <a href="https://habrahabr.ru/company/luxoft/blog/157273/">Обзор java.util.concurrent.*</a>
- <a href="http://www.skipy.ru/technics/synchronization.html">Синхронизация потоков</a>
- <a href="http://java67.blogspot.ru/2014/08/difference-between-string-literal-and-new-String-object-Java.html">String literal pool</a>
- <a href="https://habrahabr.ru/post/132241/">Маленькие хитрости Java</a>
-  <a href="https://github.com/winterbe/java8-tutorial">A Guide to Java 8</a>

### Туториалы, разное
- [Что нужно знать о бэкенде новичку в веб-разработке](https://tproger.ru/translations/backend-web-development)
- [Туториалы: Spring Framework, Hibernate, Java Core, JDBC](http://proselyte.net/tutorials/)

#### Сервлеты
-  <a href="https://devcolibri.com/как-создать-servlet-полное-руководство/">Как создать Servlet? Полное руководство.</a>
-  [Сервлеты](https://metanit.com/java/javaee/4.1.php)

#### JDBC, SQL
-  <a href="https://habrahabr.ru/post/123636/">Основы SQL на примере задачи</a>
-  <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5qdMQLXiIwGLTLyUHkTqi2">Уроки по JDBC</a>
-  <a href="https://www.codecademy.com/learn/learn-sql">Learn SQL</a>
-  <a href="http://www.intuit.ru/studies/courses/5/5/info">Интуит. Основы SQL</a>
-  <a href="http://campus.codeschool.com/courses/try-sql/contents">Try SQL</a>
-  <a href="https://stepic.org/course/Введение-в-базы-данных-551">Курс "Введение в базы данных"</a>

#### Разное
-  <a href="http://javaops.ru/view/test">Вопросы по собеседованию, ресурсы для подготовки</a>
-  <a href="http://jeeconf.com/materials/intellij-idea/">Эффективная работа с кодом в IntelliJ IDEA</a>
-  <a href="http://www.quizful.net/test">Quizful- тесты онлайн</a>
-  <a href="https://stepic.org/course/Введение-в-Linux-73">Введение в Linux</a>

#### Книги
-  <a href="http://www.ozon.ru/context/detail/id/24828676/">Джошуа Блох: Java. Эффективное программирование. Второе издание</a>
-  <a href="http://www.labirint.ru/books/87603/">Гамма, Хелм, Джонсон: Приемы объектно-ориентированного проектирования. Паттерны проектирования</a>
-  <a href="http://www.bookvoed.ru/book?id=639284">Редмонд Э.: Семь баз данных за семь недель. Введение в современные базы данных и идеологию NoSQL</a>
-  <a href="http://www.ozon.ru/context/detail/id/3174887/">Brian Goetz: Java Concurrency in Practice</a>
-  <a href="http://bookvoed.ru/book?id=2593572">G.L. McDowell: Cracking the Coding Interview</a>
