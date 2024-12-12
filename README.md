# backin_backend
second backend proof for novatec Solutions


El desarrollo consiste en una Api desarrollada coon Spring Boot 3.4.0, Java 21 y el driver de postresql tanto en un ambiente local como en una base de datos remota

## Modelo:
- El modelo consiste en dos entidades fundamentales con una relación uno a muchos con dos entidades, Card que representa los datos de las tarjeas y Transaction que represetna las transacciones realizadas con cada tarjeta.

## Ejecución
- Existen dos maneras de despliegue de la aplicación en entorno local y cloud
- Para el caso del entorno local se debe ejecutar el compilado de la aplicación y este expondrá el microservicio en el puerto 8080
- Además de ello se puede acceder al archivo Dockerfile y crear la imagen de la aplicación y poder ejecutar el contenedor en el mismo puerto.
- Para el caso cloud, está desplegado en una instancia oracle cloud donde se puede realizar el llamado de los servicios solicitados en la prueba.
