FROM openjdk:21

WORKDIR /app

COPY ./target/gateway-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/gateway.jar

ENTRYPOINT ["java", "-jar", "gateway.jar"]
