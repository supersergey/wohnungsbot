FROM --platform=linux/amd64 amazoncorretto:17-alpine
WORKDIR /app
COPY build/libs/*.jar wohnungsbot.jar
ENTRYPOINT java -jar wohnungsbot.jar $WOHNUNGSBOT_PROFILE
