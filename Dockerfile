FROM java:8

RUN set -ex \
  && ./mvnw clean install

CMD ["java", "-jar", "target/hackernews-0.0.1-SNAPSHOT.jar", "--server.port=80"]