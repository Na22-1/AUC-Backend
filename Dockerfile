FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
EXPOSE 443
COPY target/*.jar app.jar
COPY src/main/resources/springboot.p12 /src/main/resources/springboot.p12
ENTRYPOINT ["java","-jar","/app.jar", "p12"]
