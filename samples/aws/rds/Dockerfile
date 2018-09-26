FROM openjdk:8-jdk-alpine

COPY ./target/aws-rds-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=ec2", "-jar", "app.jar"]
