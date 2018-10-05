# Id-card session service

## Build and run

1. Build executable JAR file.
```
./gradlew clean build
```

2. Run the webapp jar file
```
java -jar build/libs/id-card-session-service-0.0.1.jar
```

## Configuration

### application.properties

* Hazelcast configuration

| Parameter                                 | Required | Description                                           |   Example                        |
| ----------------------------------------- | -------- | ----------------------------------------------------- | -------------------------------- |
| session.server.multiCastEnabled           | Y        | Is hazelcast mutlicast enabled                        | false                            |
| session.server.tcpIp                      | N        | Hazelcast TCP IP. Required when multiCast is disabled | 127.0.0.1                        |
| session.server.sessionExpirationInSeconds | Y        | Duration of session expiration in seconds             | 240                              |

* Server ssl configuration

| Parameter                                 | Required | Description                                                              |   Example                             |
| ----------------------------------------- | -------- | ------------------------------------------------------------------------ | ------------------------------------- |
| server.ssl.key-store                      | Y        | Path to the key store that holds the SSL certificate                     | classpath:server-keystore.jks         |
| server.ssl.key-store-password             | Y        | Password used to access the key store                                    | password123                           |
| server.ssl.key-store-type                 | Y        | Type of the key store                                                    | PKCS12                                |
| server.ssl.key-alias                      | Y        | Alias that identifies the key in the key store                           | session-server                        |
| server.ssl.trust-store                    | Y        | Trust store that holds SSL certificates                                  | classpath:server-truststore.jks       |
| server.ssl.trust-store-password           | Y        | Password used to access the trust store                                  | password123                           |
| server.ssl.client-auth                    | Y        | Whether client authentication is wanted ("want") or needed ("need")      | need                                  |

