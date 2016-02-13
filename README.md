Java Enterprise Online Project 
===============================
Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

    Когда вы слышите что-то, вы забываете это.
    Когда вы видите что-то, вы запоминаете это.
    Но только когда вы начинаете делать это,
    вы начинаете понимать это

    Старинная китайская поговорка

## <a href="description.md">Описание и план проекта</a>
### <a href="http://topjava.herokuapp.com/" target=_blank>Демо разрабатываемого приложения</a>
### <a href="https://github.com/JavaOPs/topjava/wiki">Требования к участникам, Wiki</a>

Вводное занятие
===============
## ![video](http://s.ytimg.com/yts/img/favicon-vflz7uhzw.ico) <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFSUNrdVc0bDZuX2s">Системы управления версиями. Git.

-  <a href="http://ru.wikipedia.org/wiki/Система_управления_версиями">Система управления версиями</a>. <a
                    href="http://ru.wikipedia.org/wiki/%D0%A1%D0%B8%D1%81%D1%82%D0%B5%D0%BC%D0%B0_%D1%83%D0%BF%D1%80%D0%B0%D0%B2%D0%BB%D0%B5%D0%BD%D0%B8%D1%8F_%D0%B2%D0%B5%D1%80%D1%81%D0%B8%D1%8F%D0%BC%D0%B8#.D0.A0.D0.B0.D1.81.D0.BF.D1.80.D0.B5.D0.B4.D0.B5.D0.BB.D1.91.D0.BD.D0.BD.D1.8B.D0.B5_.D1.81.D0.B8.D1.81.D1.82.D0.B5.D0.BC.D1.8B_.D1.83.D0.BF.D1.80.D0.B0.D0.B2.D0.BB.D0.B5.D0.BD.D0.B8.D1.8F_.D0.B2.D0.B5.D1.80.D1.81.D0.B8.D1.8F.D0.BC.D0.B8">VCS/DVSC</a>.
-  Ресурсы:            
    -  <a href="https://try.github.io/levels/1/challenges/1">Интерактивная Git обучалка</a>
    -  <a href="http://githowto.com/ru">Основы Git</a>
    -  <a href="https://illustrated-git.readthedocs.org/en/latest/#working-with-remote-repositories">Working with remote repositories</a>
    -  <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5B3sufBJ_KFTpKkMEvaTQR">Видео по обучению Git</a>
    -  <a href="http://habrahabr.ru/post/125799/">Как начать работать с GitHub: быстрый старт</a>
    -  <a href="http://ndpsoftware.com/git-cheatsheet.html">Справочник в графическом виде</a>
    -  <a href="https://blog.interlinked.org/tutorials/git.html">Git Overview</a>
    -  <a href="http://geekbrains.ru/gitstart">Видеокурс по Git</a>

##  ![video](http://s.ytimg.com/yts/img/favicon-vflz7uhzw.ico) <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZDdaaU5fZEo4X3c">Работа с проектом (выполнять инструкции)</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFNHk5dVJ4N0xJTWc">Prepare_ to_ HW0.patch (скачать и положить в каталог вашего проекта)</a>**
-  <a href="http://www.youtube.com/watch?v=_PDIVhEs6TM">Доступно о Java 8 Lambda</a>

##  Инструкция по шагам (из видео):</h3>
-  <a href="http://javawebinar.ru/#/soft">Установить ПО (git, JDK8, IntelliJ IDEA, Maven)</a>
-  Создать аккаунт на <a href="https://github.com">GitHub</a>
-  Сделать Fork ЭТОГО проекта (https://github.com/JavaOPs/topjava) </a>
-  Сделать локальный репозиторий проекта:
            <pre>git clone https://github.com/[Ваш аккаунт]/topjava.git</pre>
-  Открыть и настроить проект в IDEA
-  По ходу видео сделать Apply Patch... скаченного патча Prepare_ to_ HW0.patch
-  Закоммитить и запушить изменения (commit + push)
-  Сделать ветку домашнего задания
-  Выполнить задание и залить на GitHub (commit + push)
-  Переключиться в основную ветку проекта master.

## Домашнее задание HW0

-  <a href="http://www.mscharhag.com/2014/02/java-8-datetime-api.html">Java 8 Date and Time API</a>
-  <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFNEJWRFJkVDA3TkU/view">Time complexity</a>
-  <a href="https://ru.wikipedia.org/wiki/Временная_сложность_алгоритма">Временная сложность алгоритма</a>
-  <a href="https://ru.wikipedia.org/wiki/Вычислительная_сложность">Вычислительная сложность</a>
```
Реализовать UserMealsUtil.getFilteredMealsWithExceeded:
-  должны возвращаться только записи между startTime и endTime 
-  поле UserMealWithExceed.exceed должно показывать, 
                                     превышает ли сумма калорий за весь день параметра метода caloriesPerDay  
        
Т.е UserMealWithExceed - это запись одной еды, но поле exceeded будет одинаково для всех записей за этот день.
    
- Проверте результат выполнения ДЗ (можно проверить логику в http://topjava.herokuapp.com , список еды)
- Оцените Time complexity вашего алгоритма (если он O(N*N)- попробуйте сделать O(N).
```
Optional

-  <a href="http://devcolibri.com/4137#t2">Java 8: Lambda выражения</a>
-  <a href="http://devcolibri.com/4274#t7">Java 8: Потоки</a>
-  <a href="http://prologistic.com.ua/polnoe-rukovodstvo-po-java-8-stream.html">Pуководство по Java 8 Stream</a>
-  <a href="http://habrahabr.ru/post/224593/">Лямбда-выражения в Java 8</a>
-  <a href="http://habrahabr.ru/company/luxoft/blog/270383/">Шпаргалка Java Stream API</a>
-  <a href="http://stackoverflow.com/questions/28319064/java-8-best-way-to-transform-a-list-map-or-foreach">stream.map vs forEach</a>

```
Сделать реализация через Java 8 Stream API. Заменяйте forEach оператором stream.map(..)
```
### <a href="cv.md">Составление резюме, подготовка к интервью, поиск работы</a>
### Ресурсы для подготовки к проекту 
#### (желательно иметь представление для лучшего усвоения материала)

#### Java Web (Servlets, JSP)
-  <a href="http://www.intuit.ru/studies/courses/569/425/lecture/9683">Введение в сетевое программирование</a>
-  <a href="http://java-course.ru/student/book1/">Основы Java на реальном примере (Servlets, JSP)</a>
-  <a href="http://devcolibri.com/4284">Как создать Servlet? Полное руководство.</a>
-  <a href="http://www.techinfo.net.ru/docs/web/javawebdev.html">Технологии Java для разработки веб-приложений</a>

#### HTML, JavaScript, CSS 
-  <a href="http://www.intuit.ru/studies/courses/1102/134/info">Основы работы с HTML/CSS/JavaScript</a>
-  <a href="http://anton.shevchuk.name/jquery/">jQuery для начинающих</a>
-  <a href="http://pro-cod.ru/uroki-bootstrap-3-0">Уроки Bootstrap 3</a>

#### Java Core (Reflection API, JUnit)
-  <a href="http://www.quizful.net/post/java-reflection-api">Введение в Java Reflection API</a>
-  <a href="http://javaxblog.ru/article/java-junit-1/">Java JUnit</a>

#### JDBC, SQL
-  <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5qdMQLXiIwGLTLyUHkTqi2">Уроки по JDBC</a>
-  <a href="https://www.codecademy.com/learn/learn-sql">Learn SQL</a>
-  <a href="http://campus.codeschool.com/courses/try-sql/contents">Try SQL</a>

#### Разное
-  <a href="http://www.intuit.ru/studies/courses/16/16/info">Интуит. Программирование на Java</a>
-  <a href="http://spec-zone.ru/RU/Java/Tutorials/java/TOC.html">Oracle Java tutorial на русском.</a>
-  <a href="http://jeeconf.com/materials/intellij-idea/">Эффективная работа с кодом в IntelliJ IDEA</a>
