# About
This repository contains my code for practicing and learning unit testing with Spring Boot, based on the Udemy course [Unit Testing in Spring Boot](https://www.udemy.com/course/spring-boot-unit-testing). 

The purpose of this repository is purely educational, allowing me to follow along with the course material and enhance my understanding of unit testing in a Spring Boot environment.

Topics include: JUnit 5, Mockito, MockMvc, TDD, JsonPath, Hamcrest, H2 Embedded DB, MySQL

# How to run 3.00-starting-project
## Project Setup Instructions
To clone the repository, use the following git command:

```bash
git clone https://github.com/kayulu/spring-boot-unit-testing.git
```

## Prerequisites

Before starting the application, ensure that a MySQL server is up and running. If you prefer to use a Docker container to host the MySQL server, you can use the provided `docker-compose.yml` file to start the MySQL server.

After cloning the repository, navigate to the project root folder where the `docker-compose.yml` and `pom.xml` files are located:

```bash
cd spring-boot-unit-testing\3.00-starting-project
```

To start the MySQL server using Docker Compose, run the following command from inside the project root folder:

```bash
docker-compose up -d
```

This command will start the MySQL server in detached mode.

### Starting the Application

Once the MySQL server is running, you can start the Spring Boot application. This can be done using the Maven command from the project root folder:

```bash
mvn spring-boot:run
```

Alternatively, you can start the application from within an IDE of your choice.

Once the application is running, it should be accessible at:

```
http://localhost:1500/
```
