package com.example.circles

import java.lang.Exception
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

suspend fun solve(x1str: String, y1str: String, r1str: String, x2str: String, y2str: String, r2str: String):
        Pair<String, Array<Pair<Double, Double>>?> {
    return try {
        val x1 = x1str.toDoubleOrNull()!!
        val y1 = y1str.toDoubleOrNull()!!
        val r1 = r1str.toDoubleOrNull()!!
        val x2 = x2str.toDoubleOrNull()!!
        val y2 = y2str.toDoubleOrNull()!!
        val r2 = r2str.toDoubleOrNull()!!
        val distanceBetweenCenters = sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2));
        if ( distanceBetweenCenters < abs(r1 - r2)) {
            Pair("One circle is inside another", null)
        } else if ( distanceBetweenCenters <= r1 + r2) {
            val resMess = "The circles intersect"
            val a = (r1.pow(2) - r2.pow(2)
                    + distanceBetweenCenters.pow(2)) / (2 * distanceBetweenCenters)
            val h = sqrt(r1.pow(2) - a.pow(2))
            val xInter = x1 + a * (x2 - x1) / distanceBetweenCenters
            val yInter = y1 + a * (y2 - y1) / distanceBetweenCenters
            val xIntersect1 = xInter + h * (y2 - y1) / distanceBetweenCenters
            val xIntersect2 = xInter - h * (y2 - y1) / distanceBetweenCenters
            val yIntersect1 = yInter - h * (x2 - x1) / distanceBetweenCenters
            val yIntersect2 = yInter + h * (x2 - x1) / distanceBetweenCenters
            if (xIntersect1 == xIntersect2 && yIntersect1 == yIntersect2) {
                Pair(resMess, arrayOf(Pair(xIntersect1, yIntersect1)))
            } else {
                Pair(resMess, arrayOf(Pair(xIntersect1, yIntersect1),
                    Pair(xIntersect2, yIntersect2)))
            }
        } else {
            Pair("The circles is not intersect", null)
        }
    } catch(e: Exception) {
        Pair("Something went wrong: " + e.message.toString() + "\nCheck input fields", null)
    }
}
