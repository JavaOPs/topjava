####   Разработка полнофункционального Spring/JPA Enterprise приложения c авторизацией и правами доступа на основе ролей используя <a href="http://zeroturnaround.com/rebellabs/java-tools-and-technologies-landscape-for-2014/" target="_blank">наиболее популярные инструменты и технологии Java</a>: Maven, Spring, Security, JPA(Hibernate), REST(Jackson), Bootstrap, jQuery + plugins
-  Основное внимание будет уделяться способам решения многочисленных проблем разработки в Spring/JPA, а также структурному (красивому и надежному) java кодированию и архитектуре приложения.
-  Каждая итерация проекта в закрепляется домашним заданием по реализации схожей функциональности. Следующее занятие начинается с разбора домашних заданий.
-  Большое внимание уделяется тестированию кода: в проекте ~ 85 JUnit тестов.
-  Несмотря на относительно небольшой размер, приложение разрабатывается с нуля как большой проект (например мы используем кэш 2-го уровня Hibernate, настраиваем Jenkins для работы с ленивой загрузкой
Hibernate, делаем конверторы для типов LocalDateTime (Java 8 time API), которые еще не поддерживаются ни JPA/Hibernate, ни Jackson/json).
            Разбираются архитектурные паттерны: слои приложения и как правильно разбивать логику по слоям, когда нужно применят Data Transfer Object.
            Т.е на выходе получается не учебный проект, а хорошо маштабируемый шаблон для большого проекта на всех пройденных технологиях.
-   Большое внимание уделяется деталям: популяция базы, использование транзакционности, тесты сервисов и REST
            контроллеров, насторойка EntityManagerFactory,
            выбор реализации пула коннектов. Особое внимание уделяется работе с базой: через Spring JDBC, Spring ORM и
            Spring Data Jpa.
-   Используются самые востребованные на сегодняшний момент фреймворки: Maven, Spring Security 4
            вместе с Spring Security Test, наиболее удобный для работы с базой проект Spring Data Jpa, библиотека логирования logback, реализующая SLF4J, повсеместно используемый Bootstrap и jQuery.

## Архитектура проекта. Персистентность.
-  Системы управления версиями
-  Java 8: Lambda, Stream API
-  Обзор используемых в проекте технологий и инструментов.
-  Maven, другие инструменты сборки.
-  WAR. Веб-контейнер Tomcat. Сервлеты.
-  Логирование.
-  Обзор стандартных библиотек. Apache Commons, Guava
-  Слои приложения. Создание каркаса приложения.
-  Обзор Spring Framework. Spring Context.
-  Тестирование через JUnit.
-  Spring Test
-  Базы данных. PostgreSQL. Обзор NoSQL и Java persistence solution без ORM.
-  Настройка Database в IDEA.
-  Скрипты инициализации базы. Spring Jdbc Template.
-  Spring: инициализация и популирование DB
-  ORM. Hibernate. JPA.
-  Поддержка HSQLDB
-  Транзакции
-  Профили Maven и Spring
-  Пул коннектов
-  Spring Data JPA
-  Spring кэш
-  Кэш Hibernate

## Разработка WEB
-  Spring Web
-  JPS, JSTL, i18n
-  Tomcat maven plugin. JNDI
-  Spring Web MVC
-  Spring Internationalization
-  Тестирование Spring MVC
-  REST контроллеры
-  Тестирование REST контроллеров. Jackson.
-  jackson-datatype-hibernate. Тестирование через матчеры.
-  Тестирование через SoapUi. UTF-8
-  WebJars. Dandelion bundles
-  Bootstrap. Datatables.
-  AJAX. jQuery. Notifications.
-  Spring Security
-  Spring Binding/Validation
-  Работа с Datatables через Ajax.
-  Spring Security Test
-  Encoding password
-  CSRF
-  form-login. Spring Security Taglib
-  Handler interceptor
-  Spring Exception Handling
-  Деплой в Heroku
