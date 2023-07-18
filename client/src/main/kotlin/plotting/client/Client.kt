package plotting.client

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Subscription

interface Client {
    fun builder(): ClientBuilder

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
interface ClientBuilder {
    /*
    * Sets the server URL, if nothing is specificd, default values will apply for local host.
    */
    fun serverUrl(url: String = "localhost", port: Int = 8080): ClientBuilder

    /**
     *
     * Adds WebSocket Network Transport for enabling subscriptions.
     *
     * Note: subscriptions-transport-ws was deprecated in favor of graphql-ws protocol, but
     * because of backward compatibility, Apollo by default uses the deprecated protocol. In
     * this implemention, we explicitly specify the correct version that is now being the HTTP
     * standard.
     */
    fun addSubscriptionModule(): ClientBuilder

    /*
    * Get the built client.
    */
    fun build(): Client
}
