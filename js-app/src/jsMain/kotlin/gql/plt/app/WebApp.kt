package gql.plt.app

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope

import org.jetbrains.letsPlot.frontend.JsFrontendUtil
import org.jetbrains.letsPlot.letsPlot
import org.jetbrains.letsPlot.Figure
import org.jetbrains.letsPlot.geom.geomPoint

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.dom.clear

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import gql.plt.app.client.DefaultClient
import jsMain.client.PointsSubscription
import org.jetbrains.letsPlot.geom.geomSmooth

fun main() {
    window.onload = { createContext() }
}

fun createContext() {
    val data = mapOf(
        "x" to mutableListOf<Int>(),
        "y" to mutableListOf()
    )

    val job = MainScope().launch {
        getData()
            .onEach {
                it.data?.randomPoints?.let { point ->
                    data["x"]?.add(point.x) ; data["y"]?.add(point.y)
                }
                println(it.data?.randomPoints)
                addPlotToDiv(getPlot(data))
            }.onCompletion {
                println("Done Collecting")
                val finalPlot = getPlot(data) + geomSmooth(deg = 3)
                addPlotToDiv(finalPlot)
            }.collect()
    }

}

fun addPlotToDiv(p: Figure) {
    val contentDiv = document.getElementById("content")
    contentDiv?.clear()
    contentDiv?.appendChild(JsFrontendUtil.createPlotDiv(p))
}

fun getPlot(data: Map<String, Any>, xx: String = "x", yy: String = "y") =
    letsPlot(data) { x = xx ; y = yy } +
            geomPoint(color = "red", size = 3.0)

fun getData(): Flow<ApolloResponse<PointsSubscription.Data>> {
    val apolloClient = DefaultClient.Builder().serverUrl().addSubscriptionModule().build()
    return apolloClient.subscription(PointsSubscription(Optional.present(10))).toFlow()
}
