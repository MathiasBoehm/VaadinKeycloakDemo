# Getting Started

## Create Docker Container
$ docker run -p 8080:8080
-e KEYCLOAK_ADMIN=admin
-e KEYCLOAK_ADMIN_PASSWORD=admin
--name keycloak
quay.io/keycloak/keycloak:21.0.0 start-dev


## Start Docker Container
$ docker start keycloak