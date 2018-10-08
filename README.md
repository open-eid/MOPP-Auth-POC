# Demo Android POC webapp

## Build and run

1. Build executable JAR file.
```
./gradlew clean build
```

2. Run the webapp jar file
```
java -jar build/libs/demo-webapp.jar
```

## Configuration

### application.properties

* wro4j configuration

| Parameter                                 | Required | Description                                           |   Example                        |
| ----------------------------------------- | -------- | ----------------------------------------------------- | -------------------------------- |
| wro4j.filterUrl                           | Y        | Url to which the wro filter is mapped                 | /owr                             |

 * Auth service connection configuration

| Parameter                                 | Required | Description                                           |   Example                              |
| ----------------------------------------- | -------- | ----------------------------------------------------- | -------------------------------------- |
| auth.demo.authEndpoint                    | Y        | Auth service endpoint                                 | https://localhost:8443/authentication/ |
| auth.demo.trustStore                      | Y        | Trust store that holds SSL certificates               | clienttruststore.p12                   |
| auth.demo.trustStorePassword              | Y        | Password used to access the trust store               | password123                            |
