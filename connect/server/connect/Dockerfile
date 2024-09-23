FROM openjdk:17
LABEL authors="gnatykdm"

WORKDIR /app
COPY target/connectserver-0.0.1-SNAPSHOT.jar /app/connectserver-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "connectserver-0.0.1-SNAPSHOT.jar"]
