package plotting.app

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.frontend.JsFrontendUtil

import kotlin.random.Random
import kotlin.math.sqrt
import kotlin.math.PI
import kotlin.math.ln
import kotlin.math.cos

import org.jetbrains.letsPlot.geom.geomDensity
import org.jetbrains.letsPlot.letsPlot

import org.jetbrains.letsPlot.Figure

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.dom.clear

fun main() {
    window.onload = { createContext() }
}

fun createContext() {
    val data = mapOf(
        "x" to mutableListOf<Double>(),
        "y" to mutableListOf()
    )

    var t = 0.0

    MainScope().launch {
        getData()
            .onEach {
                data["x"]?.add(t)
                data["y"]?.add(it)
                addPlotToDiv(getPlot(data))
                t += 1.0
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

fun getData(): Flow<Double> = flow {
    while(true) {
        emit(nextGaussian())
        delay(200)
    }
}

fun nextGaussian(): Double {
    var u = 0.0
    var v = 0.0
    while (u < 1.0e-7) u = Random.nextDouble()
    while (v < 1.0e-7) v = Random.nextDouble()
    return sqrt(-2.0 * ln(u)) * cos(2.0 * PI * v)
}
