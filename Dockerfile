FROM openjdk:11.0-jdk-slim
WORKDIR /build
ADD ./target/demo*.jar ./friend-service.jar
EXPOSE 8085
CMD java -jar friend-service.jar

