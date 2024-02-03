# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>
 
## [Материалы занятия](https://drive.google.com/drive/u/0/folders/0B9Ye2auQ_NsFT1NxdTFOQ1dvVnM)  (скачать все патчи можно через Download папки patch)
> **ВНИМАНИЕ! При удалении класса из исходников, его скомпилированная версия все еще будет находиться в target (и classpath). В этом случае (или в любом другом, когда проект начинает глючить) сделайте `mvn clean`.**

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Разбор домашнего задания HW02
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 1. [Реализация репозиториев](https://drive.google.com/file/d/1hZay5jV-mVEByMnDveZ36jUAY0I3WChT)

#### Apply 3_01_HW2_repositories.patch

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 2. [Фильтрация в репозитории](https://drive.google.com/file/d/1s1tAsopU60gRvMaL53TP9EjS1gap99s3)
Метод `MealRepository.getBetweenHalfOpen` мы используем в следующем патче для фильтрации по целым дням.  
Используем `LocalDateTime` вместо `LocalDate` с прицелом на то, что в DB будем делать тип даты - `timestamp`.

#### Apply 3_02_HW2_repo_filters.patch
> - Исправил багу в `usersMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new)` - в этом случае создается  `new ConcurrentHashMap(userId)`, что неверно
 - [Spring `@Nullable` аннотации](https://www.jetbrains.com/help/idea/nullable-and-notnull-annotations.html)
 
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 3. [Meals Layers](https://drive.google.com/file/d/1jwd4Yhdy434fUAQyjpsZOO24XT-4lfp8)
> - В `DateTimeUtil` переименовал методы. Название должно описывать, "что метод делает", а не "зачем его вызывают". 
> - Переименовал `getBetween` для дат. Для времени у нас полуоткрытый диапазон, для дат - закрытый. 

#### Apply 3_03_HW2_meal_layers.patch
- [Should services always return DTOs, or can they also return domain models?](http://stackoverflow.com/questions/21554977/548473)
- [Mapping Entity->DTO goes in which application layer: Controller or Service?](https://stackoverflow.com/a/35798539/548473)

### Рефакторинг InMemory репозиториев
#### Apply 3_04_refactor_repository.patch
- сделал базовый `InMemoryBaseRepository` 
- наследую от него `InMemoryUserRepository`
- использую его в `InMemoryMealRepository` вместо `Map<Integer, Meal>`

Обратите внимание на `InMemoryBaseRepository.counter` - счетчик один, общий для всех хранимых объектов

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 4. [HW2 Optional](https://drive.google.com/file/d/1yzNvGBgjgtuKXDFo983OqtTNoHDbyn1z)
#### Apply 3_05_HW2_optional_MealServlet.patch
> - Заменил `@Depricated StringUtils.isEmpty` на `hasLength` (условие приходится инвертировать)
> - Убрал логирование (уже есть в контроллере)
> - `assureIdConsistent` позволяет в контроллере обновлять еду с `id=null`

#### Apply 3_06_HW2_optional_filter.patch
- [JSP Implicit Objects](https://stackoverflow.com/a/1890462/548473)
- [Использование data-* атрибутов](https://developer.mozilla.org/ru/docs/Web/Guide/HTML/Using_data_attributes), имена сделал [low-case через дефисы](https://stackoverflow.com/questions/36176474/548473)
 
#### Apply 3_07_HW2_optional_select_user.patch

### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопросы по HW2

> Что делает `repository.computeIfAbsent / computeIfPresent` ?

Всегда пробуйте ответить на вопрос сами. Достаточно просто зайти по Ctrl+мышка в реализацию и посмотреть javadoc и **их дефолтную реализацию**

> Почему выбрана реализация `Map<userId, Map<mealId,Meal>>` а не `Meal.userId + Map<mealId,Meal>` ?

В данном случае двойная мапа (мультимапа) - способ хранения, который не требует итерирования (перебора всех значений) и он проще. С другой стороны затраты по памяти в этом решении больше.

## Занятие 3:
### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 5. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFOU8wWlpPVE05STA">Коротко о жизненном цикле Spring контекста.</a>
#### Apply 3_08_bean_life_cycle.patch
> ** Добавил в pom `javax.annotation-api` для JDK больше 8. Этот пакет [убрали из JDK начиная с версии 9](https://stackoverflow.com/a/46502132/548473). 

-  <a href="http://habrahabr.ru/post/222579/">Spring изнутри. Этапы инициализации контекста.</a>
-  Ресурсы:
   -  <a href="https://www.youtube.com/watch?v=BmBr5diz8WA">Евгений Борисов. Spring, часть 1</a>
   -  <a href="https://www.youtube.com/watch?v=cou_qomYLNU">Евгений Борисов. Spring, часть 2</a>

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png)  6. [Тестирование через JUnit](https://www.youtube.com/watch?v=wSVg6_iK2Aw)
### ВНИМАНИЕ!! Перед накаткой патча создайте каталог test (из корня проекта путь `\src\test` или `\src\test\java`), иначе часть файлов попадет в `src\main`.

> - "Deeply mutable" константы `UserTestData.user/admin` [сделал lowercase](https://google.github.io/styleguide/javaguide.html#s5.2.4-constant-names). 
> - Добавил для тестирования в данные `User guest` без ролей и без еды.

- все классы, которые не нужны при работе приложения переносятся в test (и не включаются в сборку)
- в тестах очень частая ошибка - менять местами `expected` (ожидаемое) и `actual` (фактическое) значения.
- [Регрессионное тестирование](https://ru.wikipedia.org/wiki/Регрессионное_тестирование)
- [Разработка через тестирование](https://ru.wikipedia.org/wiki/Разработка_через_тестирование)
- [Unit тестирование с JUnit](https://devcolibri.com/unit-тестирование-с-junit/)
- [maven-surefire-plugin](https://maven.apache.org/surefire/maven-surefire-plugin/usage.html)
- Дополнительно:
  - [JUnit 4](http://junit.org/junit4)
  - [Тестирование в Java. JUnit](http://habrahabr.ru/post/120101/)
  - [Юнит-тестирование для чайников](https://habr.com/ru/post/169381/)
  - [Тестирование кода Java с помощью фреймворка JUnit](https://www.youtube.com/watch?v=z9jEVLCF5_w) (youtube)

#### Apply 3_09_add_junit.patch

### После патча сделайте `clean` и [обновите зависимости Maven](https://github.com/JavaOPs/topjava/wiki/IDEA#maven_update), чтобы IDEA определила сорсы тестов
#### ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Вопрос: почему проект упадет при попытке открыть страничку еды? (в логе смотреть самый верх самого нижнего исключения). Чинить приложение будем в HW03.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 7. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFai1veG9qaFZlZ2s">Spring Test</a>
> - поменял `@RunWith`: `SpringRunner` is an alias for the `SpringJUnit4ClassRunner`
#### Apply 3_10_add_spring_test.patch
-  [Spring Testing](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 8. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVlNYczhnSU9JdXc">Базы данных. Обзор NoSQL и Java persistence solution без ORM.</a>
### Postgres можно установить локально и/или через Docker - пригодиться!

--------------
### Установка локально:

> **Рекомендуется PostgreSQL 13**
-  <a href="https://ru.wikipedia.org/wiki/PostgreSQL">PostgreSQL</a>.
-  [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc)
-  <a href="http://java-course.ru/begin/postgresql/">Установка PostgreSQL</a>.
-  Чтобы избежать проблем с правами и именами каталогов, [**рекомендуют установить postgres в простой каталог, например `C:\Postgresql`**. И при проблемах создать каталог data на другом диске](https://stackoverflow.com/questions/43432713/548473). Если Unix, [проверить права доступа к папке (0700)](http://www.sql.ru/forum/765555/permissions-should-be-u-rwx-0700). Название ПК и имя пользователя должны быть латиницей (или можно устанавливать сервер [отсюда](https://postgrespro.ru/windows))
    - [Установка PostgreSQL в UBUNTU](https://github.com/JavaOPs/startup/blob/main/postgres.md)
    
> Создать в pgAdmin новую базу `topjava` и новую роль `user`, пароль `password`

![image](https://cloud.githubusercontent.com/assets/13649199/18809406/118f9c48-8283-11e6-8f10-d8291517a497.png)

>  Проверьте, что у user в Privileges есть возможность авторизации (особенно для pgAdmin4)

или в UNIX командной строке:
```
sudo -u postgres psql
CREATE DATABASE topjava;
CREATE USER "user" WITH password 'password';
GRANT ALL PRIVILEGES ON DATABASE topjava TO "user";
```

--------------
### Установка через Docker (если у вас Windows, рекомендую этот вариант уже после выполнения ДЗ - с ним можно долго провозиться)
Для работы современного ПО часто требуется большая настроенная инфраструктура: RDBMS, NoSQL, Kafka, RabbitMQ и др. Кроме того, концепция микросервисов подразумевает
запуск каждого сервиса в изолированной среде, их быстрое поднятие и масштабирование. 
Инструмент [Docker](https://ru.wikipedia.org/wiki/Docker) позволяет «упаковать» приложение со всем его окружением и зависимостями в контейнер, который может быть развёрнут на любой Linux-системе, что делает его незаменимым инструментом разработчика.  
Аналогично GitHub, для контейнеров есть репозиторий [DockerHub](https://hub.docker.com/), куда можно публиковать свои контейнеры и откуда можно брать готовые контейнеры со стандартным ПО.
Таким образом Docker служит для:
- Ускорения процесса настройки окружения. Нет нужды в установке сторонних программ вроде PostgreSQL, Redis, Elasticsearch. Они могут быть запущены в контейнерах.
- Удобной инкапсуляции приложений, которые можно представить как единый контейнер, а не набор файлов и инструкций развертывания.
- Одинакового поведения приложений на локальном компьютере и тестовом/прод-сервере.
- Простого и понятного мониторинга.
- Легкого масштабирования. Если вы сделали свое приложение правильно, то оно будет готово к масштабированию не только в Docker.

[**Основы и руководство по Docker**](https://tproger.ru/translations/how-to-start-using-docker/)   
[Docker - понятный туториал](https://badtry.net/docker-tutorial-dlia-novichkov-rassmatrivaiem-docker-tak-iesli-by-on-byl-ighrovoi-pristavkoi/)

#### Для разных ОС установка Docker отличается.  Для Windows часто это достаточно заморочно, возможно лучший вариант - [собственный UNIX хостинг](https://github.com/JavaOPs/startup). Он также пригодиться для практики с терминалом Linux и для деплоя приложения в конце стажировки.
- для [Windows без поддержки Hyper-V](https://docs.microsoft.com/en-us/virtualization/hyper-v-on-windows/reference/hyper-v-requirements) требуется Docker Toolbox,
для Windows 10 Pro and Enterprise - Docker Desktop. [Инструкция по проверке виртуализации и установке](https://devconnected.com/how-to-install-docker-on-windows-7-8-10-home-and-pro/)
- На Windows Home можно поставить Docker Desktop, предварительно [установив WSL2 (Windows Subsystem for Linux)](https://docs.microsoft.com/ru-ru/windows/wsl/install)
  - Убедитесь, что wsl версии 2: возможно понадобится включить в BIOS виртуализацию (гуглится по материнской плате) и [отключить в Windows гипервизор](https://sysadmintips.ru/kak-vykljuchit-virtualizaciju-hyper-v-windows-10.html#Otklucenie_Hyper-V_v_Windows_10_cerez_Programmy_i_komponenty)
  - [Upgrade version from WSL 1 to WSL 2](https://docs.microsoft.com/en-us/windows/wsl/install#upgrade-version-from-wsl-1-to-wsl-2)
  - [Обновление WSL до версии 2](https://docs.microsoft.com/en-us/windows/wsl/install-manual)
  - Команды из cmd/PoswrShell:
```
wsl -l -v : проверить версию
wsl --set-version Ubuntu-20.04 2  : поставить версию 2
wsl : запустить Ubuntu 

sudo service docker status <- проверка статуса
sudo service docker start  <-старт
sudo service docker restart <-рестарт
```   
- [Установка для Mac и Linux](https://docs.docker.com/get-started/)
- Установка под Ubuntu: ` sudo apt install docker.io`
  - [How can I use docker without sudo?](https://askubuntu.com/a/477554/1357134)
  
#### Работе Docker могут мешать: DrWeb, firewall, анитивирусы

После установки у меня не запускался `Docker Quickstart Terminal`, я запустил Docker Toolbox из `Git Bash`:
- Запустил Git Bash
- Перешел в каталог С:\...\DockerToolbox: `cd /C/../DockerToolbox`
- Запустил `start.sh` 

#### Разворачиваем Postgres:
- загружаем [контейнер с последним postgres](https://hub.docker.com/_/postgres): `docker pull postgres`
- если на локальной машине уже запущен сервис postgres, надо его остановить: для Windows я запустил `C:\Windows\System32\cmd.exe as Admin` и остановил командой `net stop postgresql-x64-10`
- запускаем контейнер с postgres: `docker run -p 5432:5432 -d --name topjava_db -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=topjava postgres`   
  -p: преобразование портов HOST PORT:CONTAINER PORT (или -P: все порты)  
  -d: флаг запускает контейнер в фоновом режиме (как демон)  
  -e: задание переменной окружения   
  --name [имя]: устанавливает имя демона для нового контейнера  

[Основные команды Docker](https://tproger.ru/translations/top-10-docker-commands):
```
docker ps -a : посмотреть все контейнеры
docker stop topjava_db : остановить наш контейнер
docker start topjava_db : запустить его
docker rm topjava_db : удалить
docker help : справка по командам
```

Если Docker установился через Docker Toolbox, он запускается в VirtualBox, необходимо настроить [проброс порта 5432 из VirtualBox на локальную машину](https://losst.ru/probros-portov-virtualbox). Порты можно делать одинаковые: Host Post: 5432, Guets Port: 5432
Проверить postgres порт 5432 можно через
- Unix: `netstat -an |grep 'LISTENING'`
- Windows:  `netstat -an |find /i "listening"`  

Если порт 5432 слушается, можно приконнектиться к нему из IDEA, следующее видео.

------------

- <a href="http://habrahabr.ru/post/77909/">Обзор NoSQL систем</a>. <a href="http://blog.nahurst.com/visual-guide-to-nosql-systems">CAP</a>
- <a href="http://db-engines.com/en/ranking">DB-Engines Ranking</a>
- <a href="http://ru.wikipedia.org/wiki/Java_Database_Connectivity">JDBC</a>
- Обзор Java persistence solution без ORM: <a href="http://commons.apache.org/proper/commons-dbutils/">commons-dbutils</a>,
            <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc">Spring JdbcTemplate</a>,
            <a href="http://en.wikipedia.org/wiki/MyBatis">MyBatis</a>, <a href="http://www.jdbi.org/">JDBI</a>, <a href="http://www.jooq.org/">jOOQ</a>
- Основы:
  - <a href="https://ru.wikipedia.org/wiki/Реляционная_СУБД">Реляционная СУБД</a>
  - <a href="http://habrahabr.ru/post/103021/">Реляционные базы</a>
  - [Руководство по проектированию реляционных баз данных](https://habr.com/ru/post/193136/)
  - <a href="https://www.youtube.com/playlist?list=PLIU76b8Cjem5qdMQLXiIwGLTLyUHkTqi2">Уроки по JDBC</a>
  - <a href="http://postgresguide.com/">Postgres Guide</a>
  - <a href="http://www.postgresqltutorial.com">PostgreSQL Tutorial</a>
  - <a href="http://java-course.ru/begin/database01/">Базы данных на Java</a>
  - <a href="http://java-course.ru/begin/database02/">Возможности JDBC — второй этап</a>
- Дополнительно:
  - [Документация к PostgreSQL](https://postgrespro.ru/docs/postgrespro)
  - [Книги по PostgreSQL](https://postgrespro.ru/education/books)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 9. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFQWtHYU1qTDlMWVE">Настройка Database в IDEA.</a>
Heroku стал платным, вместо него предлагаю зарегистрировать [**собственный выделенный хостинг**](https://github.com/JavaOPs/startup).     
По цене от 150р./мес имеем: собственный сайт, практика с Linux, деплой своих приложений, развертывание Docker.  

#### Apply 3_11_add_postgresql.patch
-  <a href="http://habrahabr.ru/company/JetBrains/blog/204064/">Настройка Database в IDEA</a> и запуск SQL.

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 10. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFMGNWUXhaVzdlU0k">Скрипты инициализации базы. Spring Jdbc Template.</a>
#### Apply 3_12_db_implementation.patch
> - в `JdbcUserRepository` 
>   - в `getByEmail()` заменил `queryForObject()` на `query()`. Загляните в код: `queryForObject` бросает `EmptyResultDataAccessException` вместо нужного нам `null`.
>   - в `save()` добавил проверку на несуществующей в базе `User.id`
> - в классе `JdbcTemplate` есть настройки (`queryTimeout/ skipResultsProcessing/ skipUndeclaredResults`) уровня приложения (если они будут меняться, то, скорее всего, везде в приложении).
  Мы можем дополнительно сконфигурировать его в `spring-db.xml` и использовать в конструкторах `NamedParameterJdbcTemplate` и в `SimpleJdbcInsert` вместо `dataSource`.

Для запуска скриптов кликнуть правой мышкой на табе скрипта, выбрать Run и в диалоге добавть чз "+" базу

![image](https://user-images.githubusercontent.com/11200258/155393771-3331289b-64bf-4c33-b804-b25d55ffb298.png)
![image](https://user-images.githubusercontent.com/11200258/155394005-6702bd7b-7fa5-411d-93a3-b933c9605211.png)

- Дополнительно:
  - [alishev: Spring Framework. Урок 27: JdbcTemplate](https://www.youtube.com/watch?v=YozbZQ7PxtQ&list=PLAma_mKffTOR5o0WNHnY0mTjKxnCgSXrZ&index=27&ab_channel=alishevalishev)

![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) **Вопрос: почему отображение еды все еще не работает? (чинить приложение будете в HW03)**

-  Подключение <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/data-access.html#jdbc">Spring Jdbc</a>.
-  Конфигурирование DataSource. <a href="http://www.mkyong.com/spring/spring-propertyplaceholderconfigurer-example/">Property Placeholder</a>
-  Интеграция `JdbcUserRepository` с DB: [Добавить поддержку DB в JDBC](https://github.com/JavaOPs/topjava/wiki/IDEA#user-content-Добавить-поддержку-db-в-jdbc)

>  Проверьте, что в контекст Spring проекта включены оба файла конфигурации

![image](https://cloud.githubusercontent.com/assets/13649199/24730713/eb21456a-1a6d-11e7-997c-fb4ad728ba45.png)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 11. <a href="https://drive.google.com/open?id=1SPMkWMYPvpk9i0TA7ioa-9Sn1EGBtClD">Тестирование UserService через AssertJ.</a>
#### Apply 3_13_test_UserService.patch
> - В тестах `delete` и `create` проверяю результат напрямую (не через `getAll`)
> - В `UserTestData` добавил вспомогательные `getNew()` и `getUpdated()`
> - При выполнении функционала `create/update` объекты могут измениться, и мы не можем считать их эталонными. Поэтому при сравнении мы создаем эталон еще раз.
> - Поставил `@Ignore` в inmomory тестах

- [Tutorial: testing with AssertJ](http://www.vogella.com/tutorials/AssertJ/article.html)
- [Spring Testing Annotations](https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/htmlsingle/#integration-testing-annotations-spring)
- [The JPA hashCode() / equals() dilemma](https://stackoverflow.com/questions/5031614/548473)
- [Hibernate: implementing equals() and hashCode()](https://docs.jboss.org/hibernate/orm/current/userguide/html_single/Hibernate_User_Guide.html#mapping-model-pojo-equalshashcode)
- [Junit Matcher for comparators](https://stackoverflow.com/questions/17949752/548473)
- [AssertJ custom comparison strategy](http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#custom-comparison-strategy). [AssertJ field by field comparisons](http://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#field-by-field-comparison)

### ![video](https://cloud.githubusercontent.com/assets/13649199/13672715/06dbc6ce-e6e7-11e5-81a9-04fbddb9e488.png) 12. <a href="https://drive.google.com/open?id=0B9Ye2auQ_NsFVmZaSm9UMktXUnc">Логирование тестов.</a>
#### Apply 3_14_test_logging.patch
> - Новый PostgreSQL JDBC Driver [логирует через java.util.logging](https://github.com/pgjdbc/pgjdbc#changelog).  [Направил логирование в SLF4J](http://stackoverflow.com/a/43242620/548473)
> - Поменял формат вывода. См. [Logback Layouts](https://logback.qos.ch/manual/layouts.html)

- Ресурсы, которые кладутся в classpath, maven при сборке берет из определенных каталогов `resources` ([Introduction to the Standard Directory Layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)). Их можно настраивать через [maven-resources-plugin](https://maven.apache.org/plugins/maven-resources-plugin/examples/resource-directory.html), меняем в проекте Masterjava.

#### Apply 3_15_fix_servlet.patch
**И снова вопрос: почему отображение еды все еще не работает? (чинить приложение будете в HW03)**

## ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Ваши вопросы
> Что такое REST?

Коротко посмотрите тут: [понимание REST](http://spring-projects.ru/understanding/rest/). Более подробно мы будем разбирать этот архитектурный стиль API на 7-ом занятии.
 
> Какая разница между @BeforeClass and @Before? 

`@BeforeClass` выполняется один раз после загрузки класса (поэтому метод может быть только статический), `@Before` перед каждым тестом.
Также: для чистоты тестов экземпляр тестового класса [пересоздается перед каждым тестом](http://stackoverflow.com/questions/6094081/548473)

> Тесты в классе в каком-то определенном порядке выполняются ("сверху вниз" например)?

Порядок по умолчанию неопределен, каждый тест должен быть автономен и не зависеть от других. [См. также "How to run test methods in specific order in JUnit4?"](http://stackoverflow.com/questions/3693626/548473) 

> Обязательно ли разворачивать postgreSQL?

Желательно: хорошая и надежная ДБ:) Если совсем не хочется - можно работать со своей любимой RDBMS (поправить `initDB.sql`) или работать c postgresql в heroku (креденшелы к нему есть сверху в `postgres.properties`). На следующем уроке добавим HSQLDB, она не требует установки.

> Зачем начали индексацию с 100000?

Так удобно вставлять в базу (если будет потребность) записи вручную не мешая счетчику. Но это не общий принцип, часто нумерация идет с 1.

> Из 5-го видео - "Логика в базе - большое зло". Можно чуть поподробней об этом?

- Есть успешные проекты с логикой в базе. Те все относительно.
- Логика в базе - это процедуры и триггеры. Нет никакого ООП, переиспользовать код достаточно сложно, никагого рефакторинга, поиска по коду и других плюшек IDE. Нельзя делать всякие вещи типа кэширования, хранения в сессии - это все для логики на стороне java. Например json можно напрямую отдать в процедуру и там парсить и вставлять в таблицы или наоборот - собирать из таблиц и возвращать.
А затем потребуется некоторая логика на стороне приложения и все равно придется этот json дополнительно парсить в java.
Я на таком проекте делал специальную миграцию, чтобы процедуры мигрировать не как sql-скрипты, а каждую процедуру хранить как класс с историей изменений. Если логика: триггеры и простые процедуры записи-чтения, которые не требуют переиспользования кода или
проект небольшой - это допустимо, иначе проект становится трудно поддерживать. Также иногда используют [View](http://postgresql.men/gruber/ch20.html) для разграничения доступа. Например, для финансовых систем, таблицы проводок доступны только  для админ учеток, а View просто не дадут увидеть (тем более изменить) данные обычному оператору на уровне СУБД.

> У JUnit есть ассерты и у спринга тоже. Можно ли обойтись без JUnit?

Предусловия Spring `Assert` и JUnit-тесты совершенно разные вещи, один другого не заменит. У нас будут предусловия в следующем уроке.

> Я так понял, что при работе с небольшими записями, VARCHAR быстрее, чем TEXT. Наши записи будут небольшими (255). Почему вы приняли решение перейти на TEXT?

В отличие от MySql, в Postgres  [VARCHAR и TEXT - тоже самое](https://stackoverflow.com/questions/4848964/548473) 

> Зачем при создании таблицы мы создаем `CREATE UNIQUE INDEX` и `CREATE INDEX`. При каких запросах он будет использоваться?

UNIQUE индекс нужен для обеспечения уникальности, DB не даст сделать одинаковый индекс. Индексы используется для скорости выполнения запросов. Обычно они используются, когда в запросе есть условия, на которые сделан индекс. Узнать по конкретному запросу можно  запросив план запроса: см. <a href="https://habrahabr.ru/post/203320">Оптимизация запросов. Основы EXPLAIN в PostgreSQL</a>. На измерение производительности с индексами посмотрим в следующем уроке.

> А это нормально, что у нас в базе у meals есть userId, а в классе - нет?

Ненормально, когда в приложении есть "лишний" код, который не используется. Для ORM он нам понадобится - добавим `Meal.user`.

> Почему мы делаем один sequence на разные таблицы?

Мы будем использовать Hibernate. По умолчанию он делает глобальный sequence на все таблицы. В этом подходе есть [как плюсы, так и минусы](http://stackoverflow.com/questions/1536479/548473).

> Каким образом попадают в тесты классы, расположенные в каталоге `test`, если в конфигурации спринга нет указание на ее сканирование?

Сканируются не папки, а пакеты. Обычно тесты классов располагают в том же самом пакете каталога `test`. Таким образом тесты могут видеть поля классов `main` с видимостью по умолчанию (внутри пакета). При этом классы `test` видят `main`, а наоборот нет. Когда приложение деплоится, в коде тестов быть не должно!

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Домашнее задание HW03
- 1 Понять, почему перестали работать `SpringMain, InMemoryAdminRestControllerTest, InMemoryAdminRestControllerSpringTest`. Чиним в Optional.
- 2 Дополнить скрипты создания и инициализации базы таблицей `meals`. Запустить скрипты на вашу базу (через Run). Порядок таблиц при DROP и DELETE важен, если они связаны внешними ключами (foreign key, fk). Проверьте, что ваши скрипты работают.
   - 2.1 Сделать индексы к таблице `meals`: запретить создавать у одного и того-же юзера еду с одинаковой dateTime.
Индекс на pk (id) postgres создает автоматически: [Postgres and Indexes on Foreign Keys and Primary Keys](http://stackoverflow.com/questions/970562/548473)
  - [Индекс_базы_данных](https://ru.wikipedia.org/wiki/Индекс_(базы_данных))
  - [PostgreSQL: индексы](https://postgrespro.ru/docs/postgresql/10/indexes-intro)
  - [Postgres Guide: Indexes](http://postgresguide.com/performance/indexes.html)
  - [Оптимизация запросов. Основы EXPLAIN в PostgreSQL](https://habrahabr.ru/post/203320/)
  - [Оптимизация запросов. Часть 2](https://habrahabr.ru/post/203386/)
  - [Оптимизация запросов. Часть 3](https://habrahabr.ru/post/203484/)

> ![question](https://cloud.githubusercontent.com/assets/13649199/13672858/9cd58692-e6e7-11e5-905d-c295d2a456f1.png) Как правильно придумать индекс для базы? Указать в нем все поля, комбинация которых создает по смыслу уникальную запись, или какие-то еще есть условия?

Индекс нужно делать по тем полям, по которым будут искаться записи (участвуют в WHERE, ORDER BY). Уникальность - совсем необязательное условие. Индексы ускоряют поиск по определенным полям таблицы. Они не бесплатные (хранятся в памяти, замедляется вставка), поэтому на всякий случай их делать не надо. Также не строят индексы на колонки с малым процентом уникальности (например поле "М/Ж"). Поля индекса НЕ КОММУТАТИВНЫ и порядок полей в описании индекса НЕОБХОДИМО соблюдать (в силу использования B-деревьев и их производных как поисковый механизм индекса). При построении плана запроса EXPLAIN учитывается количество записей в базе, поэтому вместо индексного поиска (Index Scan) база может выбрать последовательный (Seq Scan). Проверить, работают ли индексы можно <a href="http://stackoverflow.com/questions/14554302/548473">отключив Seq Scan</a>. Также см. <a href="https://dba.stackexchange.com/a/27493/3684">Queries on the first field of composite index</a>

- 3 Реализовать через Spring JDBC Template `JdbcMealRepository`
  - 3.1. сделать каждый метод за один SQL запрос
  - 3.2. `userId` в класс `Meal` вставлять НЕ надо, делаем _foreign key_ только в базе (для UI и REST это лишние данные, userId это id залогиненного пользователя)
  - 3.3. `JbdcTemplate` работает через сеттеры. Вместе с конструктором по умолчанию их нужно добавить в `Meal` 
  - 3.4. Список еды должен быть отсортирован (тогда мы его сможем сравнивать с тестовыми данными). Кроме того это требуется для UI и API: последняя еда наверху.
- 4 Проверить работу MealServlet, запустив приложение

### Optional
- 5 Сделать `MealServiceTest` из `MealService` и реализовать тесты для `JdbcMealRepository`.
> По `Ctrl+Shift+T` (выбрать JUnit4) можно создать тест для конкретного класса, выбрав для него нужные методы. Тестовый класс создастся в папке `test` в том же пакете, что и тестируемый. 
  - 5.1 Сделать тестовые данные `MealTestData` (точно такие же, как вставляем в `populateDB.sql`).
  - 5.2 Сделать тесты на чужую еду (delete, get, update) с тем, чтобы получить `NotFoundException` и `duplicateDateTimeCreate`, аналогичный `duplicateMailCreate`.
- 6 Починить `SpringMain, InMemory*Test`. **InMemory тесты должны использовать реализацию в памяти**

----------------------------
### Итоги занятия после выполнения ДЗ:
Наше приложение стало хранить данные в PostgreSQL.   
Добавили в приложение JUnit тесты.  
Разделили Spring контекст для работы с памятью и с БД. 

----------------------------

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Решение проблем

> Из каталога `main` не видятся классы/ресурсы в `test`

Все что находится в `test` используется только для тестов и недоступно в основном коде.  

> Из `IDEA` не видятся ресурсы в каталоге `test`

- Сделайте Reimport All в Maven окне

![image](https://cloud.githubusercontent.com/assets/13649199/18831806/7e43bedc-83f0-11e6-97db-67d4e1a7599f.png)

> В UserService и MealService подчеркнуты красным repository, ошибка: Could not autowire. There is more than one bean of 'MealRepository' type.

- Spring test контекст не надо включать в Spring Facets проекта, там должны быть только `spring-app.xml` и `spring-db.xml`. Для тестовых контекстов поставьте чекбокс `Check test files` в Inspections. 

![image](https://cloud.githubusercontent.com/assets/13649199/18831817/8a858f22-83f0-11e6-837e-bf5317b33b3a.png)

### ![error](https://cloud.githubusercontent.com/assets/13649199/13672935/ef09ec1e-e6e7-11e5-9f79-d1641c05cbe6.png) Типичные ошибки и подсказки по реализации

- 1: В `MealTestData` еду делайте константами. Не надо `Map` конструкций!
- 2: SQL case-insensitive, **не надо писать в стиле Camel**. В POSTGRES возможны case-sensitive значения, их надо в кавычки заключать (обычно не делают).
- 3: ЕЩЕ РАЗ: `InMemory` тесты должны идти на `InMemory` репозитории
- 4: **Проверьте, что возвращает `JdbcMealRepository` при обновлении чужой еды**
- 5: В реализации `JdbcMealRepository` одним SQL запросом используйте возвращаемое `update` значение `the number of rows affected`
- 6: При тестировании не портите эталонные тестовые объекты из `MealTestData`
- 7: Проверьте, что все, что относится к тестам, находится в каталоге `test` (не попадает в сборку проекта)
- 8: **Еще раз: в тестах нельзя сравнивать объекты через `JUnit Assert` и `assertThat().isEqualTo`, потому что в этом случае сравнение будет происходить через `AbstractBaseEntity.equals`, который сравнивает объекты только по `id`. Мы не можем переопределять `equals` для объектов модели, тк будем использовать JPA (см. [The JPA hashCode() / equals() dilemma](https://stackoverflow.com/questions/5031614/548473))**
- 9: НЕ делайте склейку SQL запросов вручную из параметров, только через `jdbcTemplate` параметры! См. [Внедрение_SQL-кода](https://ru.wikipedia.org/wiki/Внедрение_SQL-кода)
- 10: Напомню: `BeanPropertyRowMapper` работает через отражение. Ему нужны геттеры/сеттеры и имена полей должны "совпадать" с колонками `ResultSet` (Column values are mapped based on matching the column name as obtained from result set metadata to public setters for the corresponding properties. The names are matched either directly or by transforming a name separating the parts with underscores to the same name using "camel" case).
- 11: Для разделения тестов InMemory и Jdbc можно использовать разные Spring context. В том числе переопределять контест для тестов в `src\test\resources`
- 12: Обязательно протестируйте в `MealServiceTest` нормальное поведение методов (по аналогии с `UserServiceTest`)
