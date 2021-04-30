[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![CircleCI](https://img.shields.io/circleci/build/github/uppnrise/acme-microservice-template?token=4335d02d7ba72d490a8a10b2d9c455cab6316b61)](https://app.circleci.com/pipelines/github/uppnrise/acme-microservice-template)
[![codecov](https://codecov.io/gh/uppnrise/acme-microservice-template/branch/master/graph/badge.svg?token=TBR1L2U0FK)](https://codecov.io/gh/uppnrise/acme-microservice-template)
# acme-microservices-template

---
This project is a skeleton code for microservice architecture pattern using Spring and Docker.

## Services

### product-composite-service
It calls three core microservices to create an aggregated information.

### product-service
It manages product information and describes each product with the following model:

- Product ID
- Name
- Weight

### review-service
It manages product reviews and stores the following information about each product review:

- Product ID
- Review ID
- Author
- Subject
- Content

### recommendation-service
It manages product recommendations and stores the following information about each recommendation:

- Product ID
- Recommendation ID
- Author
- Rate
- Content

## Common Modules
[WIP]

## Tool Installation

- **Git:** Can be downloaded and installed from [git-scm](https://git-scm.com/downloads)
- **Java11:** Can be downloaded and installed from [Zulu OpenJDK](https://www.azul.com/downloads/zulu-community/?version=java-11-lts&package=jdk)
- **curl:** This command-line tool for testing HTTP-based APIs can be downloaded and installed from [curl-download](https://curl.se/download.html)
- **jq:** This command-line JSON processor can be downloaded and installed from [jq-download](https://stedolan.github.io/jq/download/)
- **Spring Boot CLI:** It can be downloaded from [spring-boot-cli](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-installing-the-cli)


## I have a lot of questions !!!

#### 1. What is the purpose of the @SpringBootApplication annotation?
* It enables component scanning, which looks for Spring components and configuration classes, in the package of the application class and all its sub-packages.
* The application class itself becomes a configuration class.
* It enables auto-configuration, where Spring Boot looks for JAR files in the classpath that it can configure automatically. If you have Tomcat in the classpath, Spring Boot will automatically configure Tomcat as an embedded web server.

#### 2. What are the main differences between the older Spring component for developing REST services, Spring Web MVC, and the new version, Spring WebFlux?
* Spring WebFlux supports the development of reactive/non-blocking, HTTP clients and services. It also supports non-Servlet-based web servers such as Netty. Spring Web MVC only supports Servlet-based web servers. Finally, Spring WebFlux comes with a new functional programming model complementing the annotation-driven model introduced by Spring Web MVC (which Spring WebFlux still supports).

#### 3. How does SpringFox help a developer to document REST APIs?
* SpringFox can create Swagger-based API documentation in runtime. It does so by examining the application at startup, for example, inspecting WebFlux-and Swagger-based annotations.

#### 4. What is the purpose of Docker Compose?
* Docker Compose is a tool in Docker used to manage (for example, start, scale, log, and stop) a group of related Docker containers with a single command.

#### 5. How can you separate protocol-specific error handling from the business logic in an API implementation class?
* You can use a common class annotated as @RestControllerAdvice and declare exception handler methods for the exceptions that the business logic might throw.

#### 6. What does : | mean in a YAML file?
* It is the start of a multiline value.

#### 7. What is MapStruct used for?
* MapStruct is a Java bean mapping tool that simplifies transforming model objects used in the API into entity objects used in the persistence layer.

#### 8. What does it mean when we specify that an operation is idempotent, and why is that useful?
* It means that the operation will return the same result if called several times with the same input parameters. The state of an underlying database, if any, will also remain the same if the operation is called one or several times with the same input parameters.

