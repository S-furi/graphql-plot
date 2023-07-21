package gql.plt.app.client

import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Subscription

interface GraphQLClient {
    fun builder(): GraphQLClientBuilder

    /*
    * Wrapper around ApolloClient query command for execute queries.
    */
    fun <D : Query.Data> query(query: Query<D>): ApolloCall<D>

    /*
    * Wrapper around ApolloClient mutation command for execute mutations.
    */
    fun <D : Mutation.Data> mutation(mutation: Mutation<D>): ApolloCall<D>

    /*
    * Wrapper around ApolloClient subscription command for execute subscriptions.
    */
    fun <D : Subscription.Data> subscription(subscription: Subscription<D>): ApolloCall<D>
}

/**
 *
 * Builds the Apollo GraphQL client with the specified features.
 */
interface GraphQLClientBuilder {
    var isSubscriptionModuleEnabled: Boolean
    /*
    * Sets the server URL, if nothing is specified, default values will apply for local host.
    */
    fun serverUrl(host: String = "localhost", port: Int = 8080): GraphQLClientBuilder

    /**
     *
     * Adds WebSocket Network Transport for enabling subscriptions.
     *
     * Note: subscriptions-transport-ws was deprecated in favor of graphql-ws protocol, but
     * because of backward compatibility, Apollo by default uses the deprecated protocol. In
     * this implementation, we explicitly specify the correct version that is now being the HTTP
     * standard.
     */
    fun addSubscriptionModule(): GraphQLClientBuilder

    /*
    * Get the built client.
    */
    fun build(): GraphQLClient
}
