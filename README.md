[![Build Status](https://travis-ci.org/ZubovVP/job4j_grabber.svg?branch=master)](https://travis-ci.org/ZubovVP/job4j_grabber)

# Агрегатор вакансий.
Основная задача агрегатора - это периодически получать информацию с сайта sql.ru, полученная информация сверяется с существующей в базе данных, уникальная информация добавляется в базу. Вывод всей информации из базы данных осуществляется через браузер.

#### Инструменты:
В данном проекте использовались следующие инструменты:    
1) IntelliJ Idea    
2) Maven    
3) jUnit      
4) Liquibase    
5) PostgreSQL    
6) Hibernate.    

#### Настройка:
Для настройки частоты запуска приложения, необходимо в файле [app.properties](src/main/resources/app.properties), в строке "interval" указать необходимую частоту запуска (частота запуска указывается в секундах).     
В строке "port" указывается номер порта для вывода информации в браузер (по умолчанию установлен 9000).    
В строке "link" указывается стартовая страница для осуществления сбора информации (Приложение собирает информацию с первых пяти страниц сайта).

#### Запуск:
Для запуска приложения необходимо запустить main метод класса [Grabber](src/main/java/ru/job4j/Grabber.java).
После получения информации с сайта, пользователь может осуществить переход в браузере по URL http://localhost:9000, для ознакомления с полученной информацией, если в ходе настройки приложения поменялся номер порта, необходимо будет использовать в URL соответствующий номер порта.
