Java Enterprise Online Project 
===============================
Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей с использованием наиболее популярных инструментов и технологий Java: Maven, Spring MVC, Security, JPA(Hibernate), REST(Jackson), Bootstrap (css,js), DataTables, jQuery + plugins, Java 8 Stream and Time API и хранением в базах данных Postgresql и HSQLDB.

![topjava_structure](https://javaops.ru/static/images/projects/top-scheme.jpg)

    Когда вы слышите что-то, вы забываете это.
    Когда вы видите что-то, вы запоминаете это.
    Но только когда вы начинаете делать это,
    вы начинаете понимать это

    Старинная китайская поговорка

## <a href="http://javaops-demo.ru/topjava" target=_blank>Демо разрабатываемого приложения</a>

Обновленное вводное занятие (обязательно смотреть все видео)
===============
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [Вступление, история, команда, источники](entrance/video1.md)

## Обзор наиболее востребованных технологий, которые будут изучаться на курсе TopJava
## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2.1. [Часть 1: инфраструктура](entrance/video2.1.md)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2.2. [Часть 2: frameworks Spring, ORM](entrance/video2.2.md)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2.3. [Часть 3: тренды](entrance/video2.3.md)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2.3. [Часть 4: обзор разрабатываемого приложения](entrance/video2.4.md)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [Рекомендуемые подходы к обучению на курсе](entrance/video3.md)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. [Структура приложения (многоуровневая архитектура)](entrance/video4.md)
### [Демо приложения](http://javaops-demo.ru/topjava)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. [Системы управления версиями. Git](entrance/video5-vcs-git.md)</a>

##  ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 6. <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFZDdaaU5fZEo4X3c/view?resourcekey=0-DvpzWIlMHZ7KX_v1SMuiAw">Работа с проектом (выполнять инструкции)</a>
- Поправка: JetBrains больше не выдает ключей, вот [варианты после trial](https://github.com/JavaOPs/topjava/wiki/IDEA#licence) 
- **ВНИМАНИЕ: выбирайте для проекта простой пусть без пробелов и русских букв, например, `c:\projects\topjava\` (Windows). Иначе впоследствии будут проблемы**
- **Плагин Git Intergation уже не требуется, а вкладку `Version control` в IDEA переименовали в `Git`**

Для переключения режима отображения изменений из вкладки `Commit` в `Git: Local Changes` нужно переключить `Settings/Preferences | Version Control | Commit | Use non-modal commit interface` или в контекстном меню вкладки `Commit`:

![image](https://user-images.githubusercontent.com/13649199/105491518-72d8f300-5cc7-11eb-8b79-c46382562deb.png)  ![image](https://user-images.githubusercontent.com/13649199/105488663-05c35e80-5cc3-11eb-962e-30f403d623e8.png)

### Патч [prepare_to_HW0.patch](https://drive.google.com/file/d/1LNPpu9OkuCpfpD8ZJHO-o0vwu49p2i5M) (скачать и положить в каталог вашего проекта)

> Проект постоянно улучшается, поэтому видео иногда отличается от кода проекта. Изменения указываю после видео: 
> - переименовал класс `UserMealWithExceed` и его поле `exceed` в `UserMealWithExcess.excess`
> - в `UserMeals/UserMealWithExcess` поля изменились на `private`
> - обновил данные `UserMealsUtil.meals` и переименовал некоторые переменные, поля и методы
> - добавил `UserMealWithExcess.toString()` и метод для выполнения _Optional домашнего задания_
> - метод фильтрации в `TimeUtil` переименовал в `isBetweenHalfOpen` (также изменилась логика сравнения: `startTime` включается в интервал, а `endTime` - не включается) 

### GitHub поменял политику: теперь пушить нужно через токен. IDEA предложит его сгенерировать при пуше, или можно [создать токен в настройках](https://www.jetbrains.com/help/idea/github.html#register-account)
- [Способы авторизации в GitHub](https://topjava.ru/blog/vvedeniye-v-git-github-ustanovka-i-nastroyka#6)

##  Инструкция по шагам (из видео):</h3>
-  <a href="http://javaops.ru/view/soft">Установить ПО (Git, JDK8, IntelliJ IDEA, Maven)</a>
-  Создать аккаунт на <a href="https://github.com">GitHub</a>
-  Сделать Fork **ЭТОГО** проекта (https://github.com/JavaOPs/topjava) </a>
-  Сделать локальный репозиторий проекта:
            <pre>git clone https://github.com/[Ваш аккаунт]/topjava.git</pre>

> Вместо Fork можно сделать [клонирование проекта](https://github.com/JavaOPs/topjava/wiki/Git#user-content-Клонирование-проекта): он не будет привязан к исходному https://github.com/JavaOPs/topjava и у него не будет истории.

-  Открыть и настроить проект в IDEA
   - <a href="http://stackoverflow.com/questions/29695918/intellij-idea-console-issue#33035499">Выставить кодировку UTF-8 в консоли</a>
   - <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D1%81%D1%82%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%BA%D0%BE%D0%B4%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D1%83-utf-8">Поставить кодировку UTF-8</a>
   - Опционально: <a href="https://github.com/JavaOPs/topjava/wiki/IDEA#%D0%9F%D0%BE%D0%BC%D0%B5%D0%BD%D1%8F%D1%82%D1%8C-%D1%84%D0%BE%D0%BD%D1%82-%D0%BF%D0%BE-%D1%83%D0%BC%D0%BE%D0%BB%D1%87%D0%B0%D0%BD%D0%B8%D1%8E-dejavu">поменять шрифт по умолчанию на DejaVu</a> или на **новый [JetBrains Mono](https://habr.com/ru/company/jugru/news/t/484134/)**
-  По ходу видео сделать `Apply Patch...` скачанного патча  [Prepare_ to_ HW0.patch](https://drive.google.com/file/d/1LNPpu9OkuCpfpD8ZJHO-o0vwu49p2i5M)
-  Закоммитить и запушить изменения (`commit` + `push`)
-  Сделать ветку домашнего задания
-  Выполнить задание и залить на GitHub (`commit` + `push`)
-  Переключиться в основную ветку проекта `master`.

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. [Maven](https://drive.google.com/file/d/1qEJTwv9FNUQjx-y9MSydH01xaAne0-hu)
- [Руководство по Maven](https://topjava.ru/blog/apache-maven-osnovy-1)
- Wiki: [Apache Maven](https://ru.wikipedia.org/wiki/Apache_Maven)
- [The Central Repository](http://search.maven.org)
- Дополнительно:
    - [Мой Wiki по Maven](https://github.com/JavaOPs/topjava/wiki/Maven)
    - [Основы Maven](https://www.youtube.com/watch?v=0uwMKktzixU)
    - JavaRush: [Основы Maven](https://javarush.ru/groups/posts/2523-chastjh-4osnovih-maven)
    - Инструмент сборки проектов [Maven](https://www.examclouds.com/ru/java/java-core-russian/lesson20)
    - [Maven Getting Started Guide](https://maven.apache.org/guides/getting-started/index.html)
    - [Видео: Maven vs Gradle vs SBT (Архипов, Борисов, Садогурский)](https://www.youtube.com/watch?v=21qdRgFsTy0)
    - [Build Lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
    - [Dependency Mechanism](http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html)

## ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. [Как правильно относиться к техзаданию (ТЗ). Полуоткрытый интервал.](https://drive.google.com/file/d/1BpTzjNFjS0TSekCyt_xvt6YoLvuw5KTZ)
- [Типы промежутков](https://ru.wikipedia.org/wiki/Промежуток_(математика))

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW0

### ВНИМАНИЕ: НЕ НАДО в репозиторий делать Pull Request со своими решениями! См. видео выше ("Работа с проектом")
Реализовать метод `UserMealsUtil.filteredByCycles` через циклы (`forEach`):
-  должны возвращаться только записи между `startTime` и `endTime`
-  поле `UserMealWithExcess.excess` должно показывать, превышает ли сумма калорий за весь день значение `caloriesPerDay`
        
Т. е. `UserMealWithExcess` - это запись одной еды, но поле `excess` будет одинаково для всех записей за этот день.
    
> - Проверьте результат выполнения ДЗ (можно проверить логику в [http://javaops-demo.ru/topjava](http://javaops-demo.ru/topjava), список еды)
> - Оцените Time complexity алгоритма. Если она больше O(N), например O(N*N) или N*log(N), сделайте O(N).  
> **Внимание: внимательно прочитайте про O(N). O - это любой коэффициент, 2*N это тоже O(N).**

-  <a href="http://www.mscharhag.com/2014/02/java-8-datetime-api.html">Java 8 Date and Time API</a>
-  <a href="http://web.archive.org/web/20201128101944/https://tproger.ru/translations/algorithms-and-data-structures/">Алгоритмы и структуры данных для начинающих: сложность алгоритмов</a>
-  [Сложность алгоритмов и Big O Notation](https://threadreaderapp.com/thread/1470666237286010881)
-  [Головач: сложность алгоритмов в теме коллекций](https://www.youtube.com/watch?v=Ek9ijOiplNE&feature=youtu.be&t=778)
-  <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFNEJWRFJkVDA3TkU/view?usp=sharing&resourcekey=0-MPCuoLVdSLiSc7hlE2jefQ">Time complexity</a>
-  <a href="https://ru.wikipedia.org/wiki/Временная_сложность_алгоритма">Временная сложность алгоритма</a>
-  <a href="https://ru.wikipedia.org/wiki/Вычислительная_сложность">Вычислительная сложность</a>

#### ВНИМАНИЕ: варианты Optional делайте в одной ветке в разных методах `UserMealsUtil`. Проще делать, проще проверять

### Optional (Java 8 Stream API)
```
Реализовать метод `UserMealsUtil.filteredByStreams` через Java 8 Stream API.
```
-  <a href="http://www.youtube.com/watch?v=_PDIVhEs6TM">Видео: Доступно о Java 8 Lambda</a>
-  <a href="https://devcolibri.com/java-8-killer-features-%D1%87%D0%B0%D1%81%D1%82%D1%8C-1/">Java 8: Lambda выражения</a>
-  <a href="https://devcolibri.com/java-8-killer-features-%D1%87%D0%B0%D1%81%D1%82%D1%8C-2/">Java 8: Потоки</a>
-  <a href="https://javadevblog.com/polnoe-rukovodstvo-po-java-8-stream.html">Pуководство по Java 8 Stream</a>
-  [Полное руководство по Java 8 Stream API в картинках и примерах](https://annimon.com/article/2778)    
-  [7 способов использовать groupingBy в Stream API](https://habrahabr.ru/post/348536)
-  <a href="http://habrahabr.ru/post/224593/">Лямбда-выражения в Java 8</a>
-  <a href="https://github.com/winterbe/java8-tutorial">A Guide to Java 8</a>
-  <a href="http://habrahabr.ru/company/luxoft/blog/270383/">Шпаргалка Java Stream API</a>
-  <a href="https://www.youtube.com/watch?v=hEyCK4ueBlc">Алексей Владыкин: Элементы функционального программирования в Java</a>
-  <a href="https://www.youtube.com/watch?v=iD8H7cmxw_w">Yakov Fain о новом в Java 8</a>
-  <a href="http://stackoverflow.com/questions/28319064/java-8-best-way-to-transform-a-list-map-or-foreach">stream.map vs forEach</a
-  [Руководство по Java Stream в Java 8](https://javarush.com.ua/groups/posts/3974-kofe-breyk-177-podrobnoe-rukovodstvo-po-java-stream-v-java-8)
-  Дополнительно
   - [Сергей Куксенко — Stream API, часть 1](https://www.youtube.com/watch?v=O8oN4KSZEXE)
   - [Сергей Куксенко — Stream API, часть 2](https://www.youtube.com/watch?v=i0Jr2l3jrDA)

### Optional 2 (+5 бонусов, только после выполнения базового и Optional задания!)
- циклом за 1 проход по `List<UserMeal>`
  - без циклов по другим коллекциям/массивам (к ним также относим методы коллекций `addAll()/removeAll()`)
- через Stream API за 1 проход по исходному списку `meals.stream()`
  - нельзя использовать внешние коллекции, не являющиеся частью коллектора
  - возможно дополнительные проходы по частям списка, при этом превышение должно считаться один раз для всего подсписка. Те например нельзя разбить список на на 2 подсписка с четными и нечетными датами и затем их объединить, с подсчетом превышения для каждого элемента.
 
Временная сложность реализации должна быть O(N) (обратите внимание на п. 13 замечаний)  
Решение должно быть рабочим в общем случае (должно работать в приложении с многими пользователями, не только при запуске `main`)  
Нельзя 2 раза проходить по исходному списку (в том числе по его отфильтрованной или преобразованной копии)

Ресурсы:
- [Baeldung: Custom Collectors](https://www.baeldung.com/java-8-collectors#Custom)
- [Руководство по Java 8 Stream API: Collector](https://annimon.com/article/2778#collector)
- [Хватит писать циклы! Топ-10 лучших методов для работы с коллекциями из Java 8](https://javarush.ru/groups/posts/524-khvatit-pisatjh-ciklih-top-10-luchshikh-metodov-dlja-rabotih-s-kollekcijami-iz-java8)
- [Понять Java Stream API](https://vc.ru/u/604567-yerlan-akzhanov/194409-ponyat-java-stream-api)    

### Замечания по использованию Stream API:
- Когда встречаешь что-то непривычное, приходится перестраивать мозги. Например, переход с процедурного на ООП-программирование дается непросто. Те, кто не знает шаблонов (и не хотят учить), также их встречают плохо. Хорошая новость в том, что если это принять и начать использовать, то начинаешь получать от этого удовольствие. И тут главное не впасть в другую крайность:
  - [Используйте Stream API проще (или не используйте вообще)](https://habrahabr.ru/post/337350/)
- Если вас беспокоит производительность стримов, обязательно прочитайте про оптимизацию 
    - ["Что? Где? Когда?"](http://optimization.guide/intro.html)
    - [Перформанс: что в имени тебе моём?](https://habrahabr.ru/company/jugru/blog/338732/)
    - [Performance это праздник](https://habrahabr.ru/post/326242/)
    
При использовании Stream API производительность улучшится только на больших задачах, где возможно распараллеливание.
Еще: просто так запустить и померить скорость JVM нельзя (как минимум надо дать прогреться и запустить очень большое число раз). Лучше использовать какие-нибудь бенчмарки, например [JMH](http://tutorials.jenkov.com/java-performance/jmh.html), который мы используем на другом проекте (Mastejava).
  
## ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Замечания к HW0
- 1: Код проекта менять можно! Одна из распространенных ошибок как в тестовых заданиях на собеседовании, так и при работе на проекте, что ничего нельзя менять. Конечно, при правках в рабочем проекте обязательно нужно проконсультироваться/проревьюироваться у авторов кода (находятся по истории VCS)
- 2: Наследовать `UserMealWithExcess` от `UserMeal` нельзя, т. к. это разные сущности: Transfer Object и Entity. Мы будем их проходить на 2-м уроке. Это относится и к их зависимости друг от друга.
- 3: Правильная реализация должна быть простой и красивой, можно сделать 2-мя способами: через стримы и через циклы. Сложность должна быть O(N), т.е. без вложенных стримов и циклов.
- 4: При реализации через циклы посмотрите в `Map` на методы `getOrDefault` или `merge`
- 5: **При реализации через `Stream` заменяйте `forEach` оператором `stream.map(..)`**
- 6: Объявляйте переменные непосредственно перед использованием (если возможно - сразу с инициализацией). При объявлении коллекций в качестве типа переменной используйте интерфейс (Map, List, ..)
- 7: Если IDEA предлагает оптимизацию (желтым подчеркивает), например, заменить лямбду на ссылку на метод (method reference), соглашайтесь (Alt+Enter)
- 8: Пользуйтесь форматированием кода в IDEA: `Alt+Ctrl+L`
- 9: Перед check-in (отправкой изменений на GitHub) просматривайте внесенные изменения (Git -> [Log](https://www.jetbrains.com/help/idea/log-tab.html) -> курсор на файл и Ctrl+D): не оставляйте в коде ничего лишнего (закомментированный код, TODO и пр.). Если файл не меняется (например только пробелы или переводы строк), не надо его чекинить, делайте ему `revert` (Git -> Revert / `Ctrl+Alt+Z`).
- 10: `System.out.println` нельзя использовать нигде, кроме как в `main`. Позже введем логирование.
- 11: Результаты, возвращаемые `UserMealsUtil.filteredByStreams`, мы будем использовать [в нашем приложении](http://javaops-demo.ru/topjava) для фильтрации по времени и отображения еды правильным цветом.
- 12: Обращайте внимание на комментарии к вашим коммитам в Git. Они должны быть короткие и информативные (лучше на english)
- 13: Не полагайтесь в решении на то, что список еды будет подаваться отсортированным. Такого условия нет.
-----

>  - ДЗ первого урока будет связано с созданием небольшого [CRUD](https://ru.wikipedia.org/wiki/CRUD)-приложения (в памяти, без базы данных) на JSP и сервлетах
>  - основы JavaScript необходимы для понимания проекта, начиная с 8-го занятия

### Полезные ресурсы
#### HTML
- [Основы HTML от mozilla](https://developer.mozilla.org/ru/docs/Learn/Getting_started_with_the_web/HTML_basics)
- [Основы HTML от webref](https://webref.ru/course/html-basics)
- [Учебник HTML для начинающих](https://msiter.ru/tutorials/html-nachalnogo-urovnya)
- [HTML для чайников](https://site-do.ru/html/)
- [Основы HTML (видео от учеников JavaRush)](https://www.youtube.com/watch?v=BdsA7VCLRc8)
    
#### Web, JavaScript, CSS 
- [Знакомство с HTML и CSS](https://htmlacademy.ru/courses/basic-html-css)
- [Справочник по WEB](https://developer.mozilla.org/ru/)
- [Видео по WEB-технологиям](https://www.youtube.com/user/WebMagistersRu/playlists)
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
- [Сборник видео "Изучаем Java"](https://www.youtube.com/playlist?list=PLyxk-1FCKqockmP-fXZmHQ7UlYP3qvZRa)
- <a href="https://github.com/JavaOPs/masterjava#Первое-занятие-многопоточность">1-й урок MasterJava: Многопоточность</a>
- [Основы Java garbage collection](http://web.archive.org/web/20180831013112/https://ggenikus.github.io/blog/2014/05/04/gc)
- <a href="https://habrahabr.ru/post/134102/">Размер Java объектов</a>
- <a href="http://www.quizful.net/post/java-reflection-api">Введение в Java Reflection API</a>
- <a href="https://habrahabr.ru/users/tarzan82/topics/">Структуры данных в картинках</a>
- <a href="https://habrahabr.ru/company/luxoft/blog/157273/">Обзор java.util.concurrent.*</a>
- <a href="http://web.archive.org/web/20200808064416/http://www.skipy.ru/technics/synchronization.html">Синхронизация потоков</a>
- <a href="http://java67.blogspot.ru/2014/08/difference-between-string-literal-and-new-String-object-Java.html">String literal pool</a>
- <a href="https://habrahabr.ru/post/132241/">Маленькие хитрости Java</a>
-  <a href="https://github.com/winterbe/java8-tutorial">A Guide to Java 8</a>

### Туториалы, разное
- [Открытый курс: Spring Boot + HATEOAS](https://javaops.ru/view/bootjava)
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
