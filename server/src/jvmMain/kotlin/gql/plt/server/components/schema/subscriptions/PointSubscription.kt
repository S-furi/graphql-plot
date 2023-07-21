package gql.plt.server.components.schema.subscriptions

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Subscription
import gql.plt.server.components.schema.models.Point
import kotlin.random.Random

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class PointSubscription : Subscription {
    @GraphQLDescription("Returns a random 2D point every 500 milliseconds")
    fun randomPoints(limit: Int? = null) = flow {
        (0..(limit ?: Int.MAX_VALUE)).forEach { _ ->
            emit(Point(Random.nextInt(100), Random.nextInt(100)))
            delay(500)
        }
    }
}