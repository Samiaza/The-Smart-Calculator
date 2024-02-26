package com.example.primenumbers

import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

enum class CreateSetMode {
    LOWER, HIGHER
}

suspend fun createSet(sourceNumStr: String, mode: CreateSetMode): Pair<String, Set<Pair<Int, Boolean>>?> {
    return try {
        val sourceNum = sourceNumStr.toIntOrNull() ?: throw Exception("Wrong Int entered")
        val resSet: MutableSet<Pair<Int, Boolean>> = mutableSetOf()
        val baseInt = if (mode == CreateSetMode.LOWER) sourceNum else reverseInt(sourceNum)
        var tmpInt = baseInt
        var multiplier = 10
        do {
            tmpInt /= 10
            val numToSet = reverseInt(baseInt - tmpInt * multiplier)
            resSet.add(Pair(numToSet, isPrime(numToSet)))
            multiplier *= 10
        } while (tmpInt != 0)
        Pair("Success", resSet)
    } catch (ex : Exception) {
        Pair("Error: " + ex.message.toString(), null)
    }

}

fun reverseInt(num: Int) : Int {
    val numSign = num.sign
    val numReversedStr = abs(num).toString().reversed()
    return numReversedStr.toInt() * numSign
}

fun isPrime(num: Int) : Boolean {
    if (num < 2) return false
    for (i in 2..sqrt(num.toDouble()).toInt()) {
        if (num % i == 0) return false
    }
    return true
}
