# Sicredi Assembléia


## setup inicial:
`docker-compose up`
`SYSTEM_ENV="dev" PGPASSWORD="password" ./scripts/setup-pg.sh 'localhost' 5433 'username'`


## list databases:
`PGPASSWORD="password" psql -h localhost -p 5433 -U username  -c "\l"`


## connect to database
`docker-compose run --rm psql`

## Swagger docs
[Local Swagger docs](http://localhost:8080/swagger-ui.html)
