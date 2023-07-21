# graphql-plot
A Kotlin GraphQL Client/Server architecture using Lets-Plot inside a Kotlin Multiplatform (JS) environment.

## Usage
First of all, run the server with:
```bash
./gradlew :server:run
```

You can also run the server through a docker container (so docker **must be installed** on the system) running:
```bash
./gradlew :server:runDocker
```
Once the server is started, download the schema with the following Gradle task:
```bash
./gradlew :js-app:downloadApolloSchema --endpoint='http://localhost:8080/graphql' \
--schema=./js-app/src/jsMain/resources/graphql/schema.graphqls
```

Then you can test out the web application using the GrapQL client with:
```bash
./gradlew :js-app:jsBrowserRun
```
