package me.stefanofuri.application.server.schema.subscriptions

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Subscription
import me.stefanofuri.application.server.schema.models.Point
import kotlin.random.Random

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.delay

class PointSubscription : Subscription {
    @GraphQLDescription("Returns a random 2D point every 500 milliseconds")
    fun randomPoints(limit: Int? = null) = flow {
        (0..(limit ?: Int.MAX_VALUE)).forEach { _ ->
            emit(Point(Random.nextInt(), Random.nextInt()))
            delay(500)
        }
    }
}