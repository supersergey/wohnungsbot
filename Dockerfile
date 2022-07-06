FROM openjdk:17
WORKDIR /app
COPY build/libs/*.jar wohnungsbot.jar
ENTRYPOINT ["java","-jar","wohnungsbot.jar"]
