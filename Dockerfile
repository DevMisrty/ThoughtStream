FROM eclipse-temurin:21-jre

COPY /target/thoughtstream.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]