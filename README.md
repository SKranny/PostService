# Java Spring project "Social networking site"
### Post-service

## Description
Post-service provides a variety of options for managing posts and tags, as well as news feeds and user posts on their pages. Post-service can provide such functionality as: 
- create posts
- delete posts
- delayed post creation
- Tag management and more.
## Service technologies
- Java version 11
- Spring Framework
- Flyway
- Lombok
- Mapstruct
- Spring Data JPA
- PostgreSQL
- Spring Security
- Spring Cloud OpenFeign
- Spring Cloud Netflix Eureka
- JWT(JsonWebToken)
- Nexus repository
- Kafka
- Swagger OpenApi
- JUnit
## Technical Description
### How to run the application on your device:
1. (Pre-configuring the PostgreSQL database) Specify in the application.yaml file, or in the environment variables in your IDE, the required application configuration parameters to run:
    - SERVER_PORT (The port of your application. Specify it manually if you are not going to use the default port: 8085)
    - KAFKA_HOST (The address of the Kafka broker. The default host is localhost:9092. Replace it if you are not going to use the default)
    - DB_PASSWORD (Password for the database)
    - USER_NAME (Username for the database)
    - POST_SERVICE_DATABASE_URL (The address of the database your application connects to. You should specify it manually if you are not going to use default postgresql url: jdbc:postgresql://localhost:5432/post_service)
    - SECRET_KEY (Your application's secret key. This is needed to protect your service which uses JWT technology)
    - EUREKA_URI (Address of your Eureka server. Specify it if you are not going to use the default address: http://localhost:8081/eureka)
2. Run the file PostApplication.java.
