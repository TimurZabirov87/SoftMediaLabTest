# SoftMediaLabTest
* Тестовое задание для SoftMediaLab. Описание в TASKS.md
* Перед запуском приложения запустить docker-compose-db.yml
```shell
docker-compose -f docker-compose-db.yml up -d
```
* Коллекция запросов в postman находится в postman_collection.json. Запросы выполнять последовательно, данные в теле запроса и переменных пути подставить свои.
* Для импорта коллекции:
1. Скопируйте содержимое файла
2. Перейдите в Postman
3. Нажмите Import
4. Вставьте скопированный текст


* Пути к документации Swagger указаны в application.properties

* При запуске тестов запускается PostgreSQL в Docker с помощью testcontainers. Поэтому в этот момент должен быть включен любой Docker Daemon.
