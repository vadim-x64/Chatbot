FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

RUN mvn clean package -DskipTests

RUN ls -la target/

RUN find target -name "*.jar" -not -name "*original*" -exec cp {} app.jar \;

CMD ["java", "-jar", "app.jar"]