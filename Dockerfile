FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw

COPY src/ src/

RUN ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=build /app/target/thindesk-1.0-alpha.war app.war

EXPOSE 10000

ENTRYPOINT ["sh", "-c", "exec java -Dserver.address=0.0.0.0 -Dserver.port=${PORT:-10000} -jar /app/app.war"]