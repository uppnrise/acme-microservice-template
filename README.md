[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![CircleCI](https://img.shields.io/circleci/build/github/uppnrise/acme-microservice-template?token=4335d02d7ba72d490a8a10b2d9c455cab6316b61)](https://app.circleci.com/pipelines/github/uppnrise/acme-microservice-template)
[![codecov](https://codecov.io/gh/uppnrise/acme-microservice-template/branch/master/graph/badge.svg?token=TBR1L2U0FK)](https://codecov.io/gh/uppnrise/acme-microservice-template)
# acme-microservices-template

---
This project is a template for microservices architecture pattern using Spring, Docker and Kubernetes. Inspired and referenced from Magnus Larsson's [Hands On Spring Boot And Spring Cloud](https://subscription.packtpub.com/book/web_development/9781789613476?uuid=bcce201d-2958-4a34-9d8f-c1f9b0af4f06).

## Services

~~### eureka-server~~ (replaced with kubernetes discovery service. But you can still find it in *with_netflix_eureka_discovery branch.*)
- A discovery service which tracks existing microservices and their instances.

### gateway
- An edge server(api gateway) which hides private services from external usage and protect public services when they're used by external clients. 

### authorization server
- An oauth 2.0 based legacy auth server which provides development aimed resource security.

### product-composite-service
- It calls three core microservices to create an aggregated information.

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
Library modules which have been used by different microservices.

### api
- It holds business models and service interfaces of microservices.

### util
- It holds exception models/handlers and service utility methods.

## Setup

### Tool Installation

- **Git:** Can be downloaded and installed from [git-scm](https://git-scm.com/downloads).
- **Java11:** Can be downloaded and installed from [Zulu OpenJDK](https://www.azul.com/downloads/zulu-community/?version=java-11-lts&package=jdk).
- **curl:** This command-line tool for testing HTTP-based APIs can be downloaded and installed from [curl-download](https://curl.se/download.html).
- **jq:** This command-line JSON processor can be downloaded and installed from [jq-download](https://stedolan.github.io/jq/download/).
- **Spring Boot CLI:** It can be downloaded from [spring-boot-cli](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started-installing-the-cli).
- **Minikube**: Local kubernetes cluster creator. - [minikube-github-repo](https://github.com/kubernetes/minikube).
- **kubectl**: Kubernetes command-line tool. - [kubectl-download](https://kubernetes.io/docs/tasks/tools/).
- **VirtualBox**: x86 and AMD64/Intel64 virtualization product. - [virtualbox-download](https://www.virtualbox.org/wiki/Downloads).
- **siege** - HTTP based load/traffic testing and benchmarking tool. - [siege-download](https://github.com/JoeDog/siege)

### Create Local Kubernetes Cluster

#### Development Environment
- Unset KUBECONFIG environment variable to ensure that kubectl context is created in the default config file, ~.kube/config.
```bash
unset KUBECONFIG
```

- Create your local cluster with "acme-microservice-template" name.
```bash
minikube start -p acme-microservice-template --memory=5000 --cpus=2 --disk-size=20g --kubernetes-version=v1.21.1 --vm-driver=virtualbox
```

- Set current profile to "acme-microservice-template".
```bash
minikube profile acme-microservice-template
```

- Direct the local Docker client to communicate with Docker Engine in Minikube.
```bash
eval $(minikube docker-env)
```

- Build and create microservices' images which are specified in docker-compose.yml.
```bash
./gradlew build && docker-compose build
```

- Create a namespace and make it default namespace for kubectl.
```bash
kubectl create namespace hands-on
kubectl config set-context $(kubectl config current-context) --namespace=hands-on
```

- Create the config-map for the configuration repository based on the files in config-repo.
```bash
kubectl create configmap config-repo --from-file=config-repo/ --save-config
```

- Create the secret for the configuration server.
```bash
kubectl create secret generic config-server-secrets --from-literal=ENCRYPT_KEY=my-very-secure-encrypt-key --from-literal=SPRING_SECURITY_USER_NAME=dev-usr --from-literal=SPRING_SECURITY_USER_PASSWORD=dev-pwd --save-config
```

- Create the secret for the clients of the configuration server.
```bash
kubectl create secret generic config-client-credentials --from-literal=CONFIG_SERVER_USR=dev-usr --from-literal=CONFIG_SERVER_PWD=dev-pwd --save-config
```

- Deploy the microservices for development environment.
```bash
kubectl apply -k kubernetes/services/overlays/dev
```

- Wait for the deployments and their pods to be up and running.
```bash
kubectl wait --timeout=600s --for=condition=ready pod --all
```

- List Docker images that are used for development.
```bash
kubectl get pods -o pods | jq .items[].spec.containers[].image
```

#### Staging/Production Environment
- To simulate production-grade resource managers, MySQL, MongoDB and RabbitMQ will run outside of Kubernetes cluster.
```bash
eval $(minikube docker-env)
docker-compose up -d mongodb mysql rabbitmq
```

- Tag existing Docker images with v1.
```bash
docker tag hands-on/auth-server hands-on/auth-server:v1
docker tag hands-on/config-server hands-on/config-server:v1
docker tag hands-on/gateway hands-on/gateway:v1
docker tag hands-on/product-composite-service hands-on/product-composite-service:v1
docker tag hands-on/product-service hands-on/product-service:v1
docker tag hands-on/recommendation-service hands-on/recommendation-service:v1
docker tag hands-on/review-service hands-on/review-service:v1
```

- Create a namespace and make it default namespace for kubectl.
```bash
kubectl create namespace hands-on
kubectl config set-context $(kubectl config current-context) --namespace=hands-on
```

- Create the config-map for the configuration repository based on the files in config-repo.
```bash
kubectl create configmap config-repo --from-file=config-repo/ --save-config
```

- Create the secret for the configuration server with production credentials.
```bash
kubectl create secret generic config-server-secrets --from-literal=ENCRYPT_KEY=my-very-secure-encrypt-key --from-literal=SPRING_SECURITY_USER_NAME=prod-usr --from-literal=SPRING_SECURITY_USER_PASSWORD=prod-pwd --save-config
```

- Create the secret for the clients of the configuration server production credentials.
```bash
kubectl create secret generic config-client-credentials --from-literal=CONFIG_SERVER_USR=prod-usr --from-literal=CONFIG_SERVER_PWD=prod-pwd --save-config
```

- Deploy the microservices for production environment.
```bash
kubectl apply -k kubernetes/services/overlays/prod
```

- Wait for the deployments and their pods to be up and running.
```bash
kubectl wait --timeout=600s --for=condition=ready pod --all
```

- List Docker images that are used for production.
```bash
kubectl get pods -o pods | jq .items[].spec.containers[].image
```

### Testing

- Start the test-em-all.bash file which includes all features of the project.
```bash
HOST=$(minikube)
PORT=31443
./test-em-all.bash
```

- Start a simple load testing with siege to simulate one user submits one request per second on average.
```bash
siege https://$(minikube ip):31443/actuator/health -c1 -d1
```

### Cleaning up your environment

- Delete the namespace.
```bash
kubectl delete namespace hands-on
```

- Shut down resource managers that run outside of Kubernetes.
```bash
eval $(minikube docker-env)
docker-compose down
```

- Delete profile on virtualbox.
```bash
minikube delete --profile acme-microservice-template
```



## If you have a lot of questions, check below !!!

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

#### 9. What is the purpose of Netflix Eureka?
* As a discovery service.

#### 10. What are the main features of Spring Cloud Gateway?
* Acting as an edge server, it can hide private APIs and secure public APIs from external usage. It also provides a single entry point to the public APIs in a system landscape of microservices.

#### 11. What backends are supported by Spring Cloud Config?
* Spring Cloud Config comes with built-in support for the following:
  * Git repositories 
  * Local filesystem 
  * HashiCorp Vault 
  * JDBC databases
    
#### 12. What are the capabilities that Resilience4j provides?
* The main capabilities are as follows:
  * Circuit breaker 
  * Rate limiter 
  * Bulkhead 
  * Retries Timeout
    
#### 13. What are the trace tree and span concepts used for in distributed tracing, and what is the paper called that defined them?
* The paper is named Dapper, a Large-Scale Distributed Systems Tracing Infrastructure; see https:/​/​ai.​google/​research/​pubs/​pub36356. In Dapper, the tracing information from a complete workflow is called a trace tree, and subparts of the tree, such as the basic units of work, are called spans. Spans can, in turn, consist of sub-spans, which form the trace tree. A correlation ID is called TraceId, and a span is identified by its own unique SpanId, along with the TraceId of the trace tree it belongs to.

#### 14. What is the purpose of OAuth 2.0 authorization codes?
* Authorization codes are used in an authorization code grant flow as a one-time password to increase security when compared to an implicit grant flow. When using an authorization code grant flow, it is only in respect of code that is visible in the web browser; as soon as it is used by the backend code in exchange for an access token, it is invalidated. So even if it is stolen in the web browser, it is of little use to an attacker. The attacker also needs access to the client secret to be able to use it.

#### 15. What is the purpose of OAuth 2.0 scopes?
* They can be used as time-constrained access rights.

#### 16. What does it mean when a token is a JWT token?
* JWT tokens, a.k.a a JSON Web Tokens, are an open standard (https://tools.ietf.org/html/rfc7519) for sending information in a token.
  
#### 17. What does OpenID Connect add to OAuth 2.0?
* OpenID Connect enables client applications to verify the identity of users.

