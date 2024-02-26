package com.example.hardmath

import com.example.logger.Logger
import kotlinx.coroutines.CancellationException
import java.math.BigInteger
import kotlin.math.cbrt
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.sqrt
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.job
import kotlin.coroutines.coroutineContext

suspend fun factorial(num: BigInteger): BigInteger {
    val job = coroutineContext.job
    var factorial = BigInteger.ONE
    var i = BigInteger.ONE
    while (i < num) {
        if (job.isActive.not()) throw CancellationException("Factorial is cancelled")
        i = i.add(BigInteger.ONE)
        factorial = factorial.multiply(i)
    }
    return factorial
}

suspend fun squareRoot(num: BigInteger): Double {
    return sqrt(num.toDouble())
}

suspend fun cubicRoot(num: BigInteger): Double {
    return cbrt(num.toDouble())
}

suspend fun naturalLogarithm(num: BigInteger): Double {
    return ln(num.toDouble())
}

suspend fun commonLogarithm(num: BigInteger): Double {
    return log10(num.toDouble())
}

suspend fun square(num: BigInteger): BigInteger {
    return num.pow(2)
}

suspend fun cube(num: BigInteger): BigInteger {
    return num.pow(3)
}

suspend fun isPrime(num: BigInteger): Boolean {
    val job = coroutineContext.job
    if (num <= BigInteger.ONE) return false
    if (num <= BigInteger.valueOf(3)) return true
    if (num.mod(BigInteger.valueOf(2)) == BigInteger.ZERO ||
        num.mod(BigInteger.valueOf(3)) == BigInteger.ZERO) return false

    var i = BigInteger.valueOf(5)
    while (i.multiply(i) <= num) {
        if (job.isActive.not()) throw CancellationException("Simplicity test is cancelled")
        if (num.mod(i) == BigInteger.ZERO ||
            num.mod(i.add(BigInteger.valueOf(2))) == BigInteger.ZERO) return false
        i = i.add(BigInteger.valueOf(6))
    }
    return true
}
