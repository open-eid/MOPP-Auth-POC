# Id-card auth service

## Build and run

1. Build executable JAR file.
```
./gradlew clean build
```

2. Run the webapp jar file
```
java -jar webapp/build/libs/auth-webapp-0.0.1.jar
```

## Add Firebase support
To use the Firebase Admin SDKs, you'll need a Firebase project, a service account to communicate with the Firebase service, and a configuration file with your service account's credentials.

1. If you don't already have a Firebase project, add one in the [ Firebase console](https://console.firebase.google.com/). The Add project dialog also gives you the option to add Firebase to an existing Google Cloud Platform project.
2. Navigate to the Service Accounts tab in your project's settings page.
3. Click the Generate New Private Key button at the bottom of the Firebase Admin SDK section of the [Service Accounts](https://console.firebase.google.com/project/_/settings/serviceaccounts/adminsdk) tab.
After you click the button, a JSON file containing your service account's credentials will be downloaded. You'll need this to initialize the SDK in the next step.

Save this file to `/relying-party-auth-service/src/main/resources/` folder and rename it to `service-account.json`

## Generate database changes with liquibase
```
./gradlew updateSQL
```
This generates liquibase SQL script file: `/webapp/liquibase.sql`

## Configuration

### Settings.gradle

* Liquibase configuration

| Parameter                    | Required | Description                                  |   Example                        |
| ---------------------------- | -------- | -------------------------------------------- | -------------------------------- |
| gradle.ext.liquibaseUrl      | Y        | JDBC URL of the database for liquibase       | jdbc:postgresql://localhost:5432 |
| gradle.ext.liquibaseUser     | Y        | Login username of the database for liquibase | postgres                         |
| gradle.ext.liquibasePassword | Y        | Login password of the database for liquibase | password123                      |

### application.properties

* Datasource configuration

| Parameter                             | Required | Description                                                                        |   Example                        |
| ------------------------------------- | -------- | ---------------------------------------------------------------------------------- | -------------------------------- |
| spring.datasource.url                 | Y        | JDBC URL of the database                                                           | jdbc:postgresql://localhost:5432 |
| spring.datasource.username            | Y        | Login username of the database                                                     | postgres                         |
| spring.datasource.password            | Y        | Login password of the database                                                     | password123                      |
| spring.datasource.driver-class-name   | Y        | Fully qualified name of the JDBC driver. Auto-detected based on the URL by default | org.postgresql.Driver            |

* Server ssl configuration

| Parameter                     | Required | Description                                         |   Example              |
| ------------------------------| -------- | --------------------------------------------------- | -----------------------|
| server.ssl.key-store          | Y        | Path to the key store that holds the SSL certificate| classpath:keystore.p12 |
| server.ssl.key-store-password | Y        | Password used to access the key store               | password123            |
| server.ssl.key-store-type     | Y        | Type of the key store                               | PKCS12                 |
| server.ssl.key-alias          | Y        | Alias that identifies the key in the key store      | auth-server            |

* Messaging configuration

| Parameter                     | Required | Description            |   Example                              |
| ------------------------------| -------- | -----------------------| ---------------------------------------|
| auth.server.fireBaseEndpoint  | Y        | Firbase endpoint url   | /v1/projects/android-poc/messages:send |

* Session service configuration

| Parameter                                    | Required | Description                                                                             |   Example                              |
| ---------------------------------------------| -------- | ----------------------------------------------------------------------------------------| ---------------------------------------|
| auth.server.sessionEndpoint                  | Y        | Session service endpoint url                                                            | https://localhost:8445/                |
| auth.server.connectionTimeout                | N        | Client connection timeout in milliseconds. Default:10000                                | 10000                                  |
| auth.server.readTimeout                      | N        | Client read timeout in milliseconds. Default:10000                                      | 10000                                  |
| auth.server.sessionServiceKeyStore           | Y        | Path to the session key store that holds the SSL certificate. Must be in the classpath  | session-client-keystore.jks            |
| auth.server.sessionServiceKeyStorePassword   | Y        | Session service key store password                                                      | password123                            |
| auth.server.sessionServiceKeyStoreType       | N        | Session service key store type. Default: PKCS12                                         | PKCS12                                 |
| auth.server.sessionServiceTrustStore         | Y        | Path to the session trust store that holds the SSL certificate. Must be in the classpath| session-client-truststore.jks          |
| auth.server.sessionServiceTrustStorePassword | Y        | Session service trust store password                                                    | password123                            |
| auth.server.sessionServiceTrustStoreType     | N        | Session service trust store type. Default: PKCS12                                       | PKCS12                                 |
