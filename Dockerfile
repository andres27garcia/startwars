FROM openjdk:11.0.9.1-jre-buster

COPY target/siniestro-aws.jar app.jar

EXPOSE 9879

ENTRYPOINT ["java","-jar","/app.jar"]