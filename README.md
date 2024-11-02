# SP_SpringBoot_AWS_Desafio_02

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



