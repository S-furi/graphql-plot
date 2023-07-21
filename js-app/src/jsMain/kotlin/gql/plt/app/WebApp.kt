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

fun main() {
    window.onload = { createContext() }
}

fun createContext() {
    val data = mapOf(
        "x" to mutableListOf<Int>(),
        "y" to mutableListOf()
    )

    var t = 0

    MainScope().launch {
        getData()
            .onEach {
                data["x"]?.add(t)
                it.data?.randomPoints?.let { it1 -> data["y"]?.add(it1.x) }

                println(it.data)

                addPlotToDiv(getPlot(data))
                t += 1
            }.collect()
    }
}

fun addPlotToDiv(p: Figure) {
    val contentDiv = document.getElementById("content")
    contentDiv?.clear()
    contentDiv?.appendChild(JsFrontendUtil.createPlotDiv(p))
}

fun getPlot(data: Map<String, Any>, xx: String = "x", yy: String = "y") =
    letsPlot(data) + geomDensity(stat = Stat.identity) { x = xx ; y = yy}

fun getData(): Flow<ApolloResponse<PointsSubscription.Data>> {
    val apolloClient = DefaultClient.Builder().serverUrl().addSubscriptionModule().build()
    return apolloClient.subscription(PointsSubscription(Optional.present(10))).toFlow()
}
