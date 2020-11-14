[![Logo](https://insights.nisumlatam.com//wp-content/uploads/2016/09/GELFImage.jpg "Logo")](https://insights.nisumlatam.com "Logo")

# **Technical Test Java Developer**

> Developed by:
14/11/2020
J[uan Carlos Cardona Padilla - Colombia](www.linkedin.com/in/juan-carlos-cardona-padilla "uan Carlos Cardona Padilla - Colombia")

# Test
Desarrolle una aplicación que exponga una API RESTful de creación de usuarios.
Todos los endpoints deben aceptar y retornar solamente JSON, inclusive al para los mensajes de error. Todos los mensajes deben seguir el formato:

    { "mensaje" : "mensaje de error" }

#### Registro
Ese endpoint deberá recibir un usuario con los campos:
"nombre", "correo", "contraseña",
más un listado de objetos "teléfono", respetando el siguiente formato:

    {
      "name" : "Juan Rodriguez" ,
       "email" : " juan@rodriguez.org " ,
       "password" : "hunter2" ,
       "phones" : [
                             {
    							"number" : "1234567" ,
    							"citycode" : "1" ,
    							"contrycode" : "57"
                             }
                        ]
    }

● Responder el código de status HTTP adecuado.
● En caso de éxito, retorne el usuario y los siguientes campos:
-  **id** : id del usuario (puede ser lo que se genera por el banco de datos, pero sería
-         más deseable un UUID)
-  **created** : fecha de creación del usuario
-  **modified** : fecha de la última actualización de usuario
-  **last_login** : del último ingreso (en caso de nuevo usuario, va a coincidir con la
-             fecha de creación)
-  **token** : token de acceso de la API (puede ser UUID o JWT)
- **isactive**: Indica si el usuario sigue habilitado dentro del sistema.
● Si caso el correo conste en la base de datos, deberá retornar un error "El correo ya
registrado".
● El correo debe seguir una expresión regular para validar que formato sea el correcto.
(aaaaaaa@dominio.cl)
● La clave debe seguir una expresión regular para validar que formato sea el correcto. (Una
Mayuscula, letras minúsculas, y dos numeros)
● El token deberá ser persistido junto con el usuario
Requisitos
● Plazo: 2 días
● Banco de datos en memoria, como HSQLDB o H2.
● Proceso de build via Gradle.
● Persistencia con Hibernate.
● Framework Spring.
● Servidor Tomcat o Jetty Embedded
● Java 8+
● Entrega en un repositorio público (github o bitbucket) con el código fuente y script de
creación de BD.
● Entrega diagrama de la solución.
Requisitos deseables
● JWT cómo token
● Pruebas de unidad
------------
# Solution

This application was generated using JHipster 6.7.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.7.1](https://www.jhipster.tech/documentation-archive/v6.7.1).

## Developend with

* 	[Gradle](https://gradle.org/) - Dependency Management
* 	[JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - Java™ Platform, Standard Edition Development Kit 
* 	[Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new Spring Applications
* 	[H2](https://www.h2database.com) - Open-Source Relational Database Management System
* 	[git](https://git-scm.com/) - Free and Open-Source distributed version control system 


## Archivos y Directorios

A continuación una explicación general de los componentes

```
.
├── Spring Elements
├── src
│   └── main
│       └── java
│           ├── com.nisum.aop.logging
│           ├── com.nisum.config
│           ├── com.nisum.domain
│           ├── com.nisum.repository
│           ├── com.nisum.security
│           ├── com.nisum.service
│           ├── com.nisum.web.rest
├── src
│   └── main
│       └── resources
│           ├── application-dev.yml
│           ├── application-prod.yml
│           ├── application.yml
│           
├── src
│   └── test
│       └── java
```

## Packages

- `domain` — to hold our entities;
- `repository` — to communicate with the database;
- `services` — to hold our business logic;
- `security` — security configuration;
- `web.rest` — to listen to the client;

- `resources/` - Contains all the static resources, templates and property files.
- `resources/app` - contains static resources such as css, js and images.
- `resources/templates` - contains server-side templates which are rendered by Spring.
- `resources/application.yml` - It contains application-wide properties. Spring reads the properties defined in this file to configure your application. You can define server’s default port, server’s context path, database URLs etc, in this file.
- `test/` - contains unit and integration tests
- `build.gradle` - contains all the project dependencies


## Development

To start your application in the dev profile, run:

    ./gradlew

##### Main Class Spring Boot
com.nisum NisumApp

------------

## Check Database

[http://localhost:8080/h2-console/login.do](http://localhost:8080/h2-console/login.do "http://localhost:8080/h2-console/login.do")

## Check Api

[http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs "http://localhost:8080/v2/api-docs")

## Usage guide

> 1. Auntenticate in : http://localhost:8080/api/authenticate basic autentication **user**:admin **psw**: admin

> 2. Endpoint createUser http://localhost:8080/api/createuser  use Bearer token autentication.

## How was it resolved?

##### Endpoint in:
> com.nisum.web.rest.UserResource.createUserCustom

##### Script Database in:
Control version database with [Liquibase](https://www.liquibase.org/ "Liquibase")
> config.liquibase.changelog

##### Validator payload
	@NotBlank(message = "name is mandatory")
    @Size(max = 50)
    @Size(min = 1, max = 50)
    private String name;

	@NotBlank(message = "email is mandatory")
    @Email
    @Size(min = 5, max = 254)
    private String email;
    
    @NotBlank(message = "password is mandatory")
    @Pattern(regexp = Constants.PASSWORD_REGEX,message = " El password debe tener Una Mayúscula, letras minúsculas, y dos números")
    @Size(min = 1, max = 50)
    private String password;

    @NotNull(message = "phones is mandatory")
    @Valid
    private List<PhoneUserDTO> phones;

   /**La clave debe seguir una expresión regular para validar que formato sea el correcto. (Una Mayuscula, letras minúsculas, y dos numeros)**/
    public static final String PASSWORD_REGEX = "**^(?=(?:.*\\d){2})(?=(?:.*[A-Z]){1})(?=(?:.*[a-z]))\\S{8,16}$**";
	
El correo debe seguir una expresión regular para validar que formato sea el correcto.
(aaaaaaa@dominio.cl)
	@NotBlank(message = "email is mandatory")
    @Email
    @Size(min = 5, max = 254)
    private String email;



##### [JWT JSON Web Tokens](https://jwt.io/ "JWT JSON Web Tokens")

> Check package com.nisum.security.jwt;


------------

## Building for production

### Packaging as jar

To build the final jar and optimize the nisum application for production, run:

    ./gradlew -Pprod clean bootJar

To ensure everything worked, run:

    java -jar build/libs/*.jar

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

    ./gradlew -Pprod -Pwar clean bootWar

## Testing

To launch your application's tests, run:

    ./gradlew test integrationTest jacocoTestReport

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the gradle plugin.

Then, run a Sonar analysis:

```
./gradlew -Pprod clean check jacocoTestReport sonarqube
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mssql database in a docker container, run:

    docker-compose -f src/main/docker/mssql.yml up -d

To stop it and remove the container, run:

    docker-compose -f src/main/docker/mssql.yml down

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./gradlew bootJar -Pprod jibDockerBuild

Then run:

    docker-compose -f src/main/docker/app.yml up -d

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.




