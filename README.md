Java Enterprise Online Project 
===============================
Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

**Старт проекта 10.09.2015**

Вводное занятие
===============

## <a href="description.md">Описание проекта</a>

#### <a href="http://topjava.herokuapp.com/" target=_blank>Демо разрабатываемого приложения</a>

## <a href="https://drive.google.com/file/d/0B9Ye2auQ_NsFSUNrdVc0bDZuX2s">Системы управления версиями. Git.

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

##  <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFZDdaaU5fZEo4X3c">Работа с проектом (выполнять инструкции)</a>
- **<a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFWEpvcjUwWGhsTnM">Prepare_ to_ HW0.patch (скачать и положить в каталог вашего проекта)</a>**
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
-  Отослать в skype мне ссылку вашей ветки GitHub (при оплате расширенного участия)
-  Переключиться в основную ветку проекта master.

### Вы готовы к участию в проекте!

-  Ресурсы
    -  <a href="http://devcolibri.com/4137#t2">Java 8: Lambda выражения</a>
    -  <a href="http://www.mscharhag.com/2014/02/java-8-datetime-api.html">Java 8 Date and Time API</a>
    -  <a href="http://devcolibri.com/4274#t7">Java 8: Потоки</a>
    -  <a href="http://prologistic.com.ua/polnoe-rukovodstvo-po-java-8-stream.html">Pуководство по Java 8 Stream</a>
    -  <a href="http://habrahabr.ru/post/224593/">Лямбда-выражения в Java 8</a>

## Домашнее задание HW0
     Реализовать UserMealsUtil.getFilteredMealsWithExceeded:
     -  должны возвращаться только записи между startTime и endTime 
     -  поле UserMealWithExceed.exceed должно показывать, 
        превышает ли сумма калорий за весь день параметра метода caloriesPerDay  
     
Optional

     Сделать реализация через Java 8 Stream API
