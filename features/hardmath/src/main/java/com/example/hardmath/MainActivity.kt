package com.example.hardmath

import CustomBigIntegerField
import CustomLabeledResult
import ModuleNameText
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.hardmath.ui.theme.TheSmartCalculatorTheme
import com.example.logger.Logger
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
//import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.cancellation.CancellationException

class HardMathActivity : ComponentActivity() {
    private val tag = this::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(tag, "Activity created")
        setContent {
            var numberStr by remember { mutableStateOf("") }

            var factorialResult by remember { mutableStateOf("waiting") }
            var simplicityTestResult by remember { mutableStateOf("waiting") }

            var factorialIsRunning by remember { mutableStateOf(false) }
            var simplicityTestIsRunning by remember { mutableStateOf(false) }

            var somethingIsRunning by remember { mutableStateOf(false) }

            var factorialJob: Deferred<String>? = null
            var simplicityTestJob: Deferred<String>? = null

            TheSmartCalculatorTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val outerScrollState = rememberScrollState()
                    Column(
                        Modifier
                            .verticalScroll(outerScrollState),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ModuleNameText(name = "Hard Math", Color.White)
                        CustomBigIntegerField(
                            label = "Number",
                            value = numberStr,
                            description = "number for testing",
                            onValueChange = { newValue -> numberStr = newValue }
                        )
                        Button(
                            onClick = {
                                somethingIsRunning = factorialIsRunning || simplicityTestIsRunning
                                if (somethingIsRunning.not()) {
                                    lifecycleScope.launch {
                                        val num = numberStr.toBigIntegerOrNull()
                                        if (num == null) factorialResult = "error"
                                        num?.let {
                                            factorialJob = async(Dispatchers.IO, CoroutineStart.LAZY) {
                                                Logger.i(tag, "factorial coroutine started")
                                                factorial(it).toString()
                                            }
                                            try {
                                                factorialIsRunning = true
                                                somethingIsRunning = true
                                                withTimeout(1000) {
                                                    factorialResult = factorialJob?.await().toString()
                                                    Logger.i(tag, "factorial coroutine finished")
                                                }
                                            } catch (e: TimeoutCancellationException) {
                                                Logger.w(tag, e.message+ "\nfactorial coroutine received time-out")
                                                factorialResult =
                                                    "a time-limit error has occurred. please try again."
                                            } catch (e: CancellationException) {
                                                Logger.w(tag, e.message+ "\nfactorial coroutine received cancellation")
                                                factorialResult = "cancelled"
                                            } finally {
                                                somethingIsRunning = simplicityTestIsRunning
                                                factorialIsRunning = false
                                            }
                                        }
                                    }
                                    lifecycleScope.launch {
                                        val num = numberStr.toBigIntegerOrNull()
                                        if (num == null) simplicityTestResult = "error"
                                        num?.let {
                                            simplicityTestJob = async(Dispatchers.Unconfined) {
                                                Logger.i(tag, "simplicity coroutine started")
                                                isPrime(it).toString()
                                            }
                                            try {
                                                simplicityTestIsRunning = true
                                                somethingIsRunning = true
                                                withTimeout(1000) {
                                                    delay(999)
                                                    simplicityTestResult = simplicityTestJob?.await().toString()
                                                    Logger.i(tag, "simplicity coroutine finished")
                                                }
                                            } catch (e: TimeoutCancellationException) {
                                                Logger.w(tag, e.message+ "\nsimplicity coroutine received time-out")
                                                simplicityTestResult =
                                                    "a time-limit error has occurred. please try again."
                                            } catch (e: CancellationException) {
                                                Logger.w(tag, e.message+ "\nsimplicity coroutine received cancellation")
                                                simplicityTestResult = "cancelled"
                                            } finally {
                                                simplicityTestIsRunning = false
                                                somethingIsRunning = factorialIsRunning
                                            }
                                        }
                                    }
                                } else {
                                    factorialJob?.let {
                                        if (it.isActive) it.cancel()
                                    }
                                    simplicityTestJob?.let {
                                        if (it.isActive) it.cancel()
                                    }
                                }
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text(if(somethingIsRunning.not()) "Run all tasks" else "Cancel all tasks")
                        }

                        CustomLabeledResult(label = "factorial", result = factorialResult)
                        CustomLabeledResult(label = "simplicity", result = simplicityTestResult)

                    }
                }
            }
        }
    }
}
