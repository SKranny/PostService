FROM openjdk:11.0-jdk-slim
WORKDIR /build
ADD ./target/demo*.jar ./post-service.jar
EXPOSE 8085
CMD java -jar post-service.jar

