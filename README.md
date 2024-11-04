# SP_SpringBoot_AWS_Desafio_02

## Summary

This is a project developed by the members of group-2 to carry out the challenge on SpringBoot and ApiResfull, it was developed in a group, with the appropriate and professional use of git, containing a good relationship between branches and maintaining commit standards.

## The project

The project is a university management system, which has students, teachers, coordinators, courses, disciplines and enrollments. Coordinators can create courses and teachers in the system, as well as courses and subjects for the college, in addition to being able to register student enrollments for courses present at the university.

## Data Flow

- 

## How to use DB on docker

### Install and run mysql on docker

- Check if docker is running on the SO

``` shell

    - sudo systemctl start docker
    - sudo systemctl status docker
    - sudo systemctl stop docker

```

- Download MySQL image for docker

``` shell

    - sudo docker pull mysql:8.0

```

- Create the container with MySQL, map the port and indicate that port 3306 on the machine corresponds to port 3306 on docker, assign a name to the container, use the default MySQL username, set the password and run MySQL in the background

``` shell

    - sudo docker run -p 3306:3306 --name mysql_nomeExemplo  -e MYSQL_ROOT_PASSWORD=senhaExemplo -d mysql:8.0

```

- to list all containers in exec or stoped

``` shell

    - docker ps -a

```

- To start the mysql server, you can use the command sudo docker start mysql_nameExample

``` shell

    - sudo docker start mysql_nomeExemplo

```

- After these steps, just make the connection to mysql workbench



