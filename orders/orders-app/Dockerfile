FROM openjdk:21

WORKDIR /app

COPY ./orders-app/target/orders-app-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/orders-app.jar

ENTRYPOINT ["java", "-jar", "orders-app.jar"]
