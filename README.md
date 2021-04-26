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
[WIP]
