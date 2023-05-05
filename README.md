# Spring Session

This is a Spring Boot application to show using Spring Sessions with Redis as the Spring Data provider

It's largely based on this:

https://www.baeldung.com/spring-session

It requires you set the following environment variables (unless you're using localhost/6379, no password):

```
REDIS_PORT=???
REDIS_PASSWORD=???
REDIS_HOST=???
```

This project uses Gradle.

To build:

`./gradlew build`

To run:

`./gradlew bootRun`
