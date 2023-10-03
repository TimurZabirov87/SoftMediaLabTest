# Тестовое задание

### Реализовать REST АПИ для клиентского приложения,
####  которое должно отображать страницу со списком студентов с возможностями:
1. Добавить нового студента в список;
2. Удалить существующего студента;
3. Отредактировать существующего студента;
4. Внести изменения названий (поле text) в справочнике успеваемости.
5. Исходные значения:
   [{id 2, text "неуд"}, 
   {id 3, text "уд"},
   {id 4, text "хор"}, 
   {id 5, text "отл"}]

У студента есть поля:
- ФИО
- дата рождения
- успеваемость (опционально, значение из справочника)

Предлагаемый стек:
Java, Spring Framework, PostgreSQL, Spring-data, Liquibase/Flyway, Swagger, Docker

Сопутствующие требования:
1. инструкция по разворачиванию исходной версии СУБД;
2. автогенерация swagger-документации на основании исходного кода;
3. валидация