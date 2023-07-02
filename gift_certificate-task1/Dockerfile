FROM openjdk:8-jdk-alpine
COPY build/libs/*.jar ./certificate.jar
ENTRYPOINT ["java", "-jar", "./certificate.jar"]