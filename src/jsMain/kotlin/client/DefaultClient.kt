package client

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Subscription
import com.apollographql.apollo3.network.ws.GraphQLWsProtocol
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport

class DefaultClient(private val client: ApolloClient) : GraphQLClient {
    private var isBuilt: Boolean = false
    private var isSubscriptionModuleEnabled = false

    private constructor(builder: Builder) : this(builder.apolloClientBuilder.build()) {
        isBuilt = true
        isSubscriptionModuleEnabled = builder.isSubscriptionModuleEnabled
    }

    override fun builder(): GraphQLClientBuilder = Builder()

    override fun <D : Query.Data> query(query: Query<D>): ApolloCall<D> {
        checkBuilt()
        return client.query(query)
    }

    override fun <D : Mutation.Data> mutation(mutation: Mutation<D>): ApolloCall<D> {
        checkBuilt()
        return client.mutation(mutation)
    }

    override fun <D : Subscription.Data> subscription(subscription: Subscription<D>): ApolloCall<D> {
        checkBuilt()
        if (!isSubscriptionModuleEnabled) throw IllegalStateException("Subscription module were not enabled. Add it to the client calling `addSubscriptionModule()` in client builder.")
        return client.subscription(subscription)
    }

    private fun checkBuilt() = if (!isBuilt) throw ClientNotBuiltException() else true

    class Builder : GraphQLClientBuilder {
        private var url: String? = null

        private var isBuilt: Boolean = false
        override var isSubscriptionModuleEnabled: Boolean = false
        val apolloClientBuilder = ApolloClient.Builder()

        override fun serverUrl(host: String, port: Int): Builder = apply {
            url = "$host:$port"
            apolloClientBuilder.serverUrl("http://$url/graphql")
        }

        override fun addSubscriptionModule(): Builder = apply {
            if (url == null) {
                throw IllegalStateException("You must provide a url before setting up subscription module")
            }
            apolloClientBuilder.subscriptionNetworkTransport(
                WebSocketNetworkTransport.Builder()
                    .serverUrl("ws://$url/subscriptions")
                    // specifying "graphql-ws" protocol
                    .protocol(GraphQLWsProtocol.Factory())
                    .build()
            )

            isSubscriptionModuleEnabled = true
        }

        override fun build(): DefaultClient {
            isBuilt = true
            return DefaultClient(this)
        }

    }
}

class ClientNotBuiltException :
    Exception("The client was not built properly! Make sure to call `build()` before running any operation.")
