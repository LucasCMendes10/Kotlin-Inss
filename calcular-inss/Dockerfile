FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/calcular-inss-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=default

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar \
    --spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]