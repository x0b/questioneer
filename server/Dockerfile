FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/server*.jar server.jar
CMD java ${JAVA_OPTS} -jar server.jar