# Sicredi Assembléia     <img src="https://github.com/balestrinc/sicredi-assembleia/workflows/CI%20Sicredi%20Assembl%C3%A9ia/badge.svg" />

## Stack
- Docker
- Docker-compose
- Java 11
- Kotlin
- Spring boot
- Postgres
- Kafka

## Architecture
Tryed to follow some concepts of hexagonal architecture where the core part (business logic) 
is loosely coupled with the "details part" like controllers and database.
The database isn't a business rule, the business rule is: "data needs to be persisted",
so the `core` does not depend directly on the repository and does not know the entity models, but on a service that stores the data instead.

If in future we need to add custom api returns for another consumer, like for a mobile app, we just need to create the appropriate convertes at `api` package and the `core` part won't need to change.

> What could be improved:
- build in separate project (gradle multi projects setup) to enforce dependency decoupling
- do not return `domain.model` directly on `api` but convert to specific response model
- isolate the service that is making requests to third-party service to validate CPF
- isolate the Messaging service from core

## Running

### Pre requisites
 - Docker
 - Docker-compose
 - Java JDK 11
 
### Running tests
1. Start the application dependencies
```shell script
docker-compose up
```

2. Run test
```shell script
./gradlew clean check
```

### Running App

1. Start the application dependencies
```shell script
docker-compose up
```

2. Run the application

```shell script
./gradlew :bootRun
```

The Application will be available at [localhost](http://localhost:8080)

#### Swagger docs
[Swagger docs](http://localhost:8080/swagger-ui.html)

## Connecting to the databases
```
docker-compose run --rm psql
```

### list databases
```
\l
```

### connect to app database
```
\c assembleia_db
```
