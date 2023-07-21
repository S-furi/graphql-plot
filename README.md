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

Then you can test out the web application using the GrapQL client with:
```bash
./gradlew :js-app:jsBrowserRun
```
