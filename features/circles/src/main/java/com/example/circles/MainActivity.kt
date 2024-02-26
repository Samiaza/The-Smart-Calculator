package com.example.circles

import CustomDoubleField
import ModuleNameText
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.circles.ui.theme.TheSmartCalculatorTheme
import com.example.logger.Logger
import kotlinx.coroutines.launch

class CirclesActivity : ComponentActivity() {
    private val tag = this::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i(tag, "Activity created")
        setContent {
            var x1str by remember { mutableStateOf("") }
            var y1str by remember { mutableStateOf("") }
            var r1str by remember { mutableStateOf("") }
            var x2str by remember { mutableStateOf("") }
            var y2str by remember { mutableStateOf("") }
            var r2str by remember { mutableStateOf("") }
            var result: Pair<String, Array<Pair<Double, Double>>?> 
            by remember { mutableStateOf(Pair("Waiting", arrayOf())) }

            TheSmartCalculatorTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ModuleNameText(name = "Circles")
                        CustomDoubleField(
                            label = "X1",
                            value = x1str,
                            description = "x-coordinate of first circle",
                            onValueChange = { newValue -> x1str = newValue }
                        )
                        CustomDoubleField(
                            label = "Y2",
                            value = y1str,
                            description = "y-coordinate of first circle",
                            onValueChange = { newValue -> y1str = newValue }
                        )
                        CustomDoubleField(
                            label = "R1",
                            value = r1str,
                            description = "radius of first circle",
                            onValueChange = { newValue -> r1str = newValue }
                        )
                        CustomDoubleField(
                            label = "X1",
                            value = x2str,
                            description = "x-coordinate of first circle",
                            onValueChange = { newValue -> x2str = newValue }
                        )
                        CustomDoubleField(
                            label = "Y2",
                            value = y2str,
                            description = "y-coordinate of first circle",
                            onValueChange = { newValue -> y2str = newValue }
                        )
                        CustomDoubleField(
                            label = "R2",
                            value = r2str,
                            description = "radius of first circle",
                            onValueChange = { newValue -> r2str = newValue }
                        )
                        Button(
                            onClick = {
                                Logger.i("${this::class.simpleName}", "Calculating launched")
                                lifecycleScope.launch {
                                    result = solve(x1str, y1str, r1str, x2str, y2str, r2str)
                                } },
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text("Calculate")
                        }
                        Text(text = result.first,
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            textAlign = TextAlign.Center)
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            result.second?.forEach {
                                Text(text = "[${it.first}, ${it.second}]")
                            } ?: Text(text = "Waiting")
                        }
                    }
                }
            }
        }
    }
}
