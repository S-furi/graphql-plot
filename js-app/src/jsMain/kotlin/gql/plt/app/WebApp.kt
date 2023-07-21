package gql.plt.app

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.frontend.JsFrontendUtil

import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot

import org.jetbrains.letsPlot.Figure

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.dom.clear

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import gql.plt.app.client.DefaultClient
import jsMain.client.PointsSubscription
import org.jetbrains.letsPlot.geom.geomPoint

fun main() {
    window.onload = { createContext() }
}

fun createContext() {
    val data = mapOf(
        "x" to mutableListOf<Int>(),
        "y" to mutableListOf()
    )

    MainScope().launch {
        getData()
            .onEach {
                it.data?.randomPoints?.let { point ->
                    data["x"]?.add(point.x) ; data["y"]?.add(point.y)
                }

                addPlotToDiv(getPlot(data))
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
