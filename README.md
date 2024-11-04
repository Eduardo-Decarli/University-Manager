# SP_SpringBoot_AWS_Desafio_02

This is a project developed by the members of group-2 to carry out the challenge on SpringBoot and ApiResfull, it was developed in a group, with the appropriate and professional use of git, containing a good relationship between branches and maintaining commit standards.

## The project

The project is a university management system, which has students, teachers, coordinators, courses, disciplines and enrollments. Coordinators can create courses and teachers in the system, as well as courses and subjects for the college, in addition to being able to register student enrollments for courses present at the university.

## Data Flow

- Coordinator

    - Has full access to the system and manages all entities

- Professor 

    - He can manage your data and view student data and enrollments

-  Student

    - Can view and manage your data

- Registration

    - The coordinator can register students, thus being able to associate the student with a course

- Course

    - Represents a complete course with a maximum of 5 subjects that the student can take

- Discipline

    - represents one of the subjects associated with a course

## Technologies used

- Java 21
- Spring Boot 🍃
- JWT and Spring Security 🔒
- MySQL
- Maven
- Swagger
- Git
- GitHub
- IntelliJ IDEA
- Postman
- Docker 🐋
- JPA
- Spring Web
- Scrum
- Trello

## How to use docker in the project

1. Open a command terminal in the folder containing the docker-compose file (Project root)

2. Run the command `docker-compose up -d` to start the containers -> 
This command starts and creates the containers specified in the docker-compose.yml file.

    - If it results in an "error response from deamon", port 3306 will probably already be in use by a SQL service, it is necessary to close and run the command again

3. Within docker desktop you can see if the container with the project name is running... If not, just press start container

4. Now with the container running, just open mySQL from the terminal using the command `docker exec -it mysql_university mysql -u root -p` and enter the password "123456" registered in the docker-compose file. Or enter mySQL workbench and add a new connection to the server, the connection data will be:

- Connection Name: university
- Hostname: 127.0.0.1
- Port: 3306
- Username: root
- password(store in vault): 123456

5. Run the projet to create the tables with JPA.

