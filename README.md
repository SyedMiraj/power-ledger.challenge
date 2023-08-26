# Installation Procedure on Docker
This application can be run using the docker-compose file. Copy the file named, plc-code-challenge-compose.yml file, and it will automatically pull the dependent images (redis, zookeeper and apache kafka) and run all containers.
The following command needs to run
```dockerfile
docker-compose -f plc-code-challenge-compose.yml up
```

Now from the browser, check the [Swagger-UI](http://localhost:8080/power-ledger/swagger-ui.html) for api docs. Also browse the [H2-db-console](http://localhost:8080/power-ledger/h2-console). User can register from the **/register** api and use his credential to generate JWT token from **/authenticate** api in AuthenticationController. For testing purpose an user will be automatically created with following credential.

**username:** *test*

**password:** *123* 

Database credential:

**username:** *root*

**password:** *root123*

## Application overview
This application is built using Spring Boot 2.7 and Maven is used as build tool. Following dependencies are added in this module
* spring-boot-starter-web
* swagger2
* spring-boot-starter-security
* jwt
* spring-boot-starter-cache
* spring-boot-starter-data-redis
* spring-kafka
* h2 database

### Key feature of the application
### 1: Asynchronous execution
Asynchronous execution is used to handle a large number of battery registrations concurrently.
```java
@Async
@Override
public void saveRequest(BatterySaveRequest model){
    ...
    }
```
and the asycronous configuration is maintained seperately in AsyncConfig class. For this demo application 2 core with maximum 2 pool size is used.

### 2: JWT token based authentication
This application is developed by implement JWT-based authentication and authorization for the API. This token will work on top of spring security and everytime used needs to authenticate and use the token as **Bearer** in subsequent api request.

### 3: Redis cache to improve query performance
Redis cache server is used to improve the query performance.

### 4: Kafka stream server
This application used apacke kafka for producing and consuming the message. Once the battery capacity will update, a message will be sent to kafka server and in this module a listener is added to listen the message.
Spring default configuration is used for kakfa streaming. 

```java
@KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
void listeningCapacityUpdate(String message){
    logger.info("Capacity update listening.");
    ...
    }
```
### 5: CI/CD pipeline using GitLab
This version control of this project is maintained gitlab and a CI/CD pipeline is used to test, build and deploy a image to gitlab registry. From this registry user can pull the image and run the containers. A docker-compose file is developed to run as standalone and added in this project folder.

### 6: Swagger for api documentation
Swagger2 is used to generate the REST API documents for RESTful web services. 

### 7: Unit testing
Multiple test case are added in this project.
