<h1>job4j_dreamjob</h1>
Проект «Dream Job» — это учебный проект, который представляет собой сервис по поиску вакансий и кандидатов. Проект создан с использованием технологии Spring Boot.
По запросу любой страницы происходит фильтр текущей сессии через java.servlet.Filter и переадресация пользователя на страницу авторизации. Только авторизованный пользователь может создавать/ редактировать вакансии и кандидатов. Все записи сохраняются в базу данных.  

Используемые технологии в проекте:
- Spring boot 2.5.2
- Thymeleaf,
- Bootstrap,
- JDBC(PostgreSql).
- Junit 4.13.2
- AssertJ 3.23.1
- log4j 1.2.17
- JCIP Annotations 1.0
- DBCP 2 2.7.0
- h2database 1.4.200
- Mockito 3.5.13
- checkstyle-plugin 3.1.2
- puppycrawl 9.0
- Liquibase 3.6.2

Необходимое окружение:
- Java 15+
- Maven 3.8
- PostgreSQL 14

Для запуска приложения необходимо:
Создать базу данных с именем dreamjob и запустить команду mvn spring-boot:run

По всем вопросам по данному проекту вы можете написать мне на email kuptsovns@gmail.com


<h1>job4j_dreamjob</h1>
Dream Job is a training project, representing a job and candidate search service. The project was developed using the Spring Boot technology.
Upon request for any page, the current session is filtered through java.servlet.Filter and the user is redirected to the authorization page. Only the authorized user can create/edit jobs and candidates. All entries are saved in the database.  

Technologies used in the project: 
- Spring boot 2.5.2
- Thymeleaf, 
- Bootstrap, 
- JDBC(PostgreSql). 
- Junit 4.13.2
- AssertJ 3.23.1
- log4j 1.2.17
- JCIP Annotations 1.0
- DBCP 2 2.7.0
- h2database 1.4.200
- Mockito 3.5.13
- checkstyle-plugin 3.1.2
- puppycrawl 9.0
- Liquibase 3.6.2

Required environment:
- Java 15+
- Maven 3.8
- PostgreSQL 14

To run the application you need to:
Create a database under the name dreamjob and run the command mvn spring-boot:run

If you have any questions about this project you can email me at kuptsovns@gmail.com