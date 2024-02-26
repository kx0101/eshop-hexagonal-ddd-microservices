FROM openjdk:21

WORKDIR /app

COPY ./customers-app/target/customers-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/customers-app.jar

ENTRYPOINT ["java", "-jar", "customers-app.jar"]
