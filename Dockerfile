FROM openjdk:11.0.9.1-jre-buster

COPY target/siniestro-aws.jar app.jar
COPY target/libs /libs

EXPOSE 9884

ENTRYPOINT ["java", "-cp", "\"libs/*\"", "-jar", "/app.jar"]