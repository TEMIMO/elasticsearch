## Elasticsearch project

Проект, демонстрирующий возможности elasticsearch

Окружение:
* Java 17
* Spring Boot 3.1.3
* Spring Data Elasticsearch 3.1.3
* Elasticsearch-8.9.1

Перед запуском необходимо отключить настройки защищенного соединения в  
elasticsearch-8.9.1\config\elasticsearch.yml

После запуска проверить работоспособность, перейдя по http://localhost:9200
Должен появиться блок с основной информацией

Перейдя по http://localhost:9200/_cat/indices можно увидеть список созданных индексов
и количество сущностей

Пример поиска документа по индексу car:
http://localhost:9200/car/_search?pretty