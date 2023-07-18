package plotting.client

interface Client {
    fun Builder(): ClientBuilder
    fun query(): Unit
    fun mutation(): Unit
    fun subscription(): Unit
}

interface ClientBuilder {
    fun serverUrl(url: String = "localhost", port: Int = 8080): ClientBuilder
    fun addSubscriptionModule(): ClientBuilder
    fun build(): Client
}
