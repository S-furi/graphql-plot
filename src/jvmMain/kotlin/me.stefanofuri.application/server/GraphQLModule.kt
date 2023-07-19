package me.stefanofuri.application.server

import com.expediagroup.graphql.dataloader.KotlinDataLoaderRegistryFactory
import com.expediagroup.graphql.generator.hooks.FlowSubscriptionSchemaGeneratorHooks
import com.expediagroup.graphql.server.ktor.DefaultKtorGraphQLContextFactory
import com.expediagroup.graphql.server.ktor.GraphQL
import com.expediagroup.graphql.server.ktor.graphQLGetRoute
import com.expediagroup.graphql.server.ktor.graphQLPostRoute
import com.expediagroup.graphql.server.ktor.graphQLSDLRoute
import com.expediagroup.graphql.server.ktor.graphQLSubscriptionsRoute
import com.expediagroup.graphql.server.ktor.graphiQLRoute
import io.ktor.serialization.jackson.JacksonWebsocketContentConverter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.Routing
import io.ktor.server.websocket.WebSockets
import me.stefanofuri.application.server.schema.queries.TestQuery
import me.stefanofuri.application.server.schema.subscriptions.PointSubscription

fun Application.graphQLModule() {
    install(WebSockets) {
        pingPeriodMillis = 1000
        contentConverter = JacksonWebsocketContentConverter()
    }

    install(CORS) {
        anyHost() // to remove in production.
    }

    install(GraphQL) {
        schema {
            packages = listOf("me.stefanofuri.application.server")
            queries = listOf(TestQuery())
            mutations = emptyList()
            subscriptions = listOf(PointSubscription())
            hooks = FlowSubscriptionSchemaGeneratorHooks()
        }
        engine {
            dataLoaderRegistryFactory =
                KotlinDataLoaderRegistryFactory(
                    // Insert here custom data loaders
                )
        }
        server { contextFactory = DefaultKtorGraphQLContextFactory() }
    }

    install(Routing) {
        graphQLGetRoute()
        graphQLPostRoute()
        graphQLSubscriptionsRoute()
        graphiQLRoute()
        graphQLSDLRoute()
    }
}
