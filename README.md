# graphql-plot
A Kotlin GraphQL Client/Server architecture using Lets-Plot inside a Kotlin Multiplatform (JS) environment.

## Usage (temporary)

You can run the server through a docker container (so docker **must be installed** on the system) running:
```bash
./gradlew runDocker
```

Then you can test out the web application using the GrapQL client with:
```bash
./gradlew runJs
```

## Notes
The project structure should be temporary, we must define custom gradle tasks that build
only the client/web app, or the server.
Also, compile time checks fails in client's code, Queries/Mutations/Subscriptions aren't resolved
after building the project while coding, while runtime checks are working as expected.

Must investigate how to set `build/` folder and how to resolve types.
