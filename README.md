# Sicredi Assembléia

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
