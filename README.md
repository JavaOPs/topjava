[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a873a0628fd24e2fb0130777fbb2dca1)](https://www.codacy.com/manual/PavelSamodurov/topjava?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=PavelSamodurov/topjava&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/PavelSamodurov/topjava.svg?branch=master)](https://travis-ci.com/PavelSamodurov/topjava)

Java Enterprise Online Project
===============================

Наиболее востребованные технологии /инструменты / фреймворки Java Enterprise:
Maven/ Spring/ Security/ JPA(Hibernate)/ REST(Jackson)/ Bootstrap(CSS)/ jQuery + plugins.

- [Вступительное занятие](https://github.com/JavaOPs/topjava)
- [Описание и план проекта](https://github.com/JavaOPs/topjava/blob/master/description.md)
- [Wiki](https://github.com/JavaOPs/topjava/wiki)
- [Wiki Git](https://github.com/JavaOPs/topjava/wiki/Git)
- [Wiki IDEA](https://github.com/JavaOPs/topjava/wiki/IDEA)
- [Демо разрабатываемого приложения](http://topjava.herokuapp.com/)

#### 26.09: Старт проекта
- Начало проверки [вступительного задания](https://github.com/JavaOPs/topjava#-Домашнее-задание-hw0)

#### 03.10: 1-е занятие
- Разбор домашнего задания вступительного занятия (вместе с Optional)
- Обзор используемых в проекте технологий. Интеграция ПО
- Maven
- WAR. Веб-контейнер Tomcat. Сервлеты
- Логирование
- Уровни и зависимости логгирования. JMX
- Домашнее задание 1-го занятия (HW1 + Optional)

#### 10.10: 2-е занятие
- Разбор домашнего задания HW1 + Optional
- Библиотека vs Фреймворк. Стандартные библиотеки Apache Commons, Guava
- Слои приложения. Создание каркаса приложения
- Обзор Spring Framework. Spring Context
- Пояснения к HW2. Обработка Autowired
- Домашнее задание (HW2 + Optional)

#### 17.10: 3-е занятие
- Разбор домашнего задания HW2 + Optional
- Жизненный цикл Spring контекста
- Тестирование через JUnit
- Spring Test
- Базы данных. Обзор NoSQL и Java persistence solution без ORM
- Настройка Database в IDEA
- Скрипты инициализации базы. Spring Jdbc Template
- Тестирование UserService через AssertJ
- Логирование тестов
- Домашнее задание (HW3 + Optional)

#### 24.10: 4-е занятие
- Разбор домашнего задания HW3 + Optional
- Методы улучшения качества кода
- Spring: инициализация и популирование DB
- Подмена контекста при тестировании
- ORM. Hibernate. JPA
- Поддержка HSQLDB
- Домашнее задание (HW4 + Optional)

#### 31.10: 5-е занятие
- Обзор JDK 9/11. Миграция Topjava с 1.8 на 11
- Разбор вопросов
- Разбор домашнего задания HW4 + Optional
- Транзакции
- Профили Maven и Spring
- Пул коннектов
- Spring Data JPA
- Spring кэш
- Домашнее задание (HW5 + Optional)

#### 07.11: 6-е занятие
- Разбор домашнего задания HW5 + Optional
- Кэш Hibernate
- Spring Web
- JPS, JSTL, internationalization
- Динамическое изменение профиля при запуске
- Конфигурирование Tomcat через maven plugin. Jndi-lookup
- Spring Web MVC
- Spring Internationalization
- Домашнее задание (HW6 + Optional)

#### Большое ДЗ + выпускной проект + подтягиваем "хвосты".

#### 21.11: 7-е занятие
- Разбор домашнего задания HW6 + Optional
- Автогенерация DDL по модели
- Тестирование Spring MVC
- Миграция на JUnit 5
- Принципы REST. REST контроллеры
- Тестирование REST контроллеров. Jackson
- jackson-datatype-hibernate. Тестирование через матчеры
- Тестирование через SoapUi. UTF-8
- Домашнее задание (HW7 + Optional)

#### 28.11: 8-е занятие
- Разбор домашнего задания HW7 + Optional
- WebJars. jQuery и JavaScript frameworks
- Bootstrap
- AJAX. Datatables. jQuery
- jQuery notifications plugin
- Добавление Spring Security
- Домашнее задание (HW8 + Optional)

#### 05.12: 9-е занятие
- Разбор домашнего задания HW8 + Optional
- Spring Binding
- Spring Validation
- Перевод DataTables на Ajax
- Форма login / logout
- Реализация собственного провайдера авторицазии
- Принцип работы Spring Security. Проксирование
- Spring Security Test
- Cookie. Session
- Домашнее задание (HW9 + Optional)

#### 12.12: 10-е занятие
- Разбор домашнего задания HW10 + Optional
- Кастомизация JSON (@JsonView) и валидации (groups)
- Рефакторинг: jQuery конверторы и группы валидации по умолчанию
- Spring Security Taglib. Method Security Expressions
- Интерсепторы. Редактирование профиля. JSP tag files
- Форма регистрации
- Обработка исключений в Spring
- Encoding password
- Миграция на Spring 5
- Защита от межсайтовой подделки запросов (CSRF)
- Домашнее задание (HW10)

#### 19.12: 11-е занятие
- Разбор домашнего задания HW10 + Optional
- Локализация datatables, ошибок валидации
- Защита от XSS (Cross Site Scripting)
- Обработка ошибок 404 (NotFound)
- Доступ к AuthorizedUser
- Ограничение модификации пользователей
- Деплой [приложения в Heroku](http://topjava.herokuapp.com)
- Собеседование. Разработка ПО
- Возможные доработки приложения
- Домашнее задание по проекту: составление резюме