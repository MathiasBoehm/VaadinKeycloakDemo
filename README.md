# Getting Started

## Create Docker Container
$ docker run -p 8080:8080
-e KEYCLOAK_ADMIN=admin
-e KEYCLOAK_ADMIN_PASSWORD=admin
--name keycloak
quay.io/keycloak/keycloak:21.0.0 start-dev


## Start Docker Container
$ docker start keycloak


### infos see also
https://medium.com/@kspoyraz7/spring-boot-keycloak-role-based-authorization-with-jwt-3bd29bdd9016

https://stackoverflow.com/questions/74873222/keycloak-with-spring-boot-based-on-roles-does-not-work-which-were-assigned-to-t/74873680#74873680