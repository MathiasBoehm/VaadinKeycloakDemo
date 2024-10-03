# Getting Started

This is a simple integration demo for Vaadin, Spring Boot and Keycloak



## Create Docker Container
```shell
$ docker run -p 8080:8080
-e KEYCLOAK_ADMIN=admin
-e KEYCLOAK_ADMIN_PASSWORD=admin
--name keycloak
quay.io/keycloak/keycloak:21.0.0 start-dev
```


## Start Docker Container
```shell
$ docker start keycloak
```