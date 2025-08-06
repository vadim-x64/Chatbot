FROM openjdk:23

WORKDIR /app

COPY out/artifacts/chatbot_jar/chatbot.jar /app/chatbot.jar

CMD ["java", "-jar", "/app/chatbot.jar"]
