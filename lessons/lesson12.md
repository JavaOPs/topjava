# Стажировка <a href="https://github.com/JavaWebinar/topjava">Topjava</a>

## [Патчи занятия](https://drive.google.com/drive/u/1/folders/1sizknxR29Yu7XXjaVIBdS88ffXiVuqiB)

## ![hw](https://cloud.githubusercontent.com/assets/13649199/13672719/09593080-e6e7-11e5-81d1-5cb629c438ca.png) Финальные правки:

Один из вариантов сокрытия полей в примерах Swagger - сделать специальный TO класс. Но можно сделать проще через специальные аннотации: [Hide a Request Field in Swagger API](https://www.baeldung.com/spring-swagger-hide-field)
- Скрываем необязательные поле `id` при POST и PUT запросах через `@ApiModelProperty(hidden = true)` в примерах запроса Swagger. При этом передавать поле в запросе можно.
- `Meal.user` отсутствует в REST API, можно игнорировать: `@JsonIgnore`
- `User.meals` можно было сделать `JsonProperty.Access.READ_ONLY`, но при этом не пройдут тесты `getWithMeals` (maels не будет сериализоваться из ответа сервера для сравнения). Скрыл также через `@ApiModelProperty(hidden = true)`
- Также можно было скрыть нулевое поле `User.meals` при выводе через `@JsonInclude(JsonInclude.Include.NON_EMPTY)`. Но при этом поле исчезнет при запросе `getWithMeals` пользователя с пустым списком еды (например для Guest). Все зависит от бизнес-требований приложения (например насколько API публично и должно быть красивым). Можете попробовать самостоятельно скрыть это поле из вывода для запросов без еды через `View` (или отдельный TO).

#### Apply [11_16_HW_fix_swagger.patch](https://drive.google.com/file/d/1A76XXvZdZCKxeKnVjZ2VkrWAHEQ1iof2)

## Миграция на Spring Boot
За основу взят **[финальный код проекта BootJava с миграцией на Spring Boot 3.2, 8-й урок](https://javaops.ru/view/bootjava/lesson08)**  
Вычекайте в отдельную папку (как отдельный проект) ветку `spring_boot` нашего проекта (так удобнее, не придется постоянно переключаться между ветками):
```
git clone --branch spring_boot --single-branch https://github.com/JavaWebinar/topjava.git topjava_boot
```  
Если будете его менять, [настройте `git remote`](https://javaops.ru/view/bootjava/lesson01#project)  
> Если захотите сами накатить патчи, сделайте ветку `spring_boot` от первого `init` и в корне **создайте каталог `src\test`**  

----

#### Apply 12_1_init_boot_java
Оставил как в TopJava название приложения  `Calories Management` и имя базы `topjava`

#### Apply 12_2_add_calories_meals

Добавил из TopJava: 
- Еду, калории
- Таблицы назвал в единственном числе: `user_role, meal` (кроме `users`, _user_ зарезервированное слово)
- Общие вещи (пусть небольшие) вынес в сервис : `MealService`
- Проверку принадлежности еды делаю в `MealRepository.getBelonged`
- Вместо своих конверторов использую `@DateTimeFormat`
- Обратите внимание на `UserRepository.getWithMeals` - он не работает с `@EntityGraph`. Зато работает с обычным `JOIN FETCH` и `DISTINCT` больше не нужен:
  - [forget about DISTINCT](https://vladmihalcea.com/spring-6-migration/#Auto-deduplication)
  - [it is no longer necessary to use distinct in JPQL and HQL](https://docs.jboss.org/hibernate/orm/6.0/migration-guide/migration-guide.html#query-sqm-distinct)
- Мигрировал все тесты контроллеров. В выпускном проекте столько тестов необязательно! Достаточно нескольких, на основные юзкейсы.
- Кэширование в выпускном желательно. 7 раз подумайте, что будете кэшировать! **Максимально просто, самые частые запросы, которые редко изменяются**.
- **Добавьте в свой выпускной OpenApi/Swagger - это будет большим плюсом и избавит от необходимости писать документацию**.

### За основу выпускного предлагаю взять этот код миграции, сделав свой выпускной МАКСИМАЛЬНО в этом стиле.
### Успехов с выпускным проектом и в карьере! 
