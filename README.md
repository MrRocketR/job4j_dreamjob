<h1>job4j_dreamjob</h1>
Проект «Dream Job» — это учебный проект, который представляет собой сервис по поиску вакансий и кандидатов. Проект создан с использованием технологии Spring Boot.

По запросу любой страницы происходит фильтр текущей сессии через java.servlet.Filter и переадресация пользователя на страницу авторизации. Только авторизованный пользователь может создавать/ редактировать вакансии и кандидатов. Все записи сохраняются в базу данных.  

Используемые технологии в проекте: Spring boot 2.5.2, Thymeleaf, Bootstrap, JDBC(PostgreSql). Тестирование: Junit 4 + AssertJ, Mockito, Liquibase(H2DB).

Для запуска проекта необходимы Java 15+, Maven 3.8, PostgreSQL 14;

Перед запуском необходимо:
1) Установить все зависимости из файла pom.xml
2) Создать базу данных dreamjob и основные таблицы, воспользовавшись скриптом job4j_dreamjob\db\scripts\init_schema.sql 

По всем вопросам по данному проекту вы можете написать мне на email kuptsovns@gmail.com






























Dream Job is a training project, which is a job and candidate search service. The project is created using the Spring Boot technology.
On request of any page, the current session is filtered through java.servlet.Filter and the user is redirected to the authorization page. Only the authorized user can create/edit jobs and candidates. All entries are saved in the database.  

Technologies used in the project: Spring boot 2.5.2, Thymeleaf, Bootstrap, JDBC(PostgreSql). Testing: Junit 4 + AssertJ, Mockito, Liquibase(H2DB).

Java 15+, Maven 3.8, PostgreSQL 14 are required to run the project;

Before launching, it is necessary to:
1) Install all dependencies from the pom.xml file
2) Create a dreamjob database and main tables using the job4j_dreamjob\db\scripts\init_schema.sql script
 
If you have any questions about this project you can email me at kuptsovns@gmail.com

