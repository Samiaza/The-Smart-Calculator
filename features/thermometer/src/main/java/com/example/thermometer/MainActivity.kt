package com.example.thermometer

import CustomDoubleField
import ModuleNameText
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
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
import com.example.thermometer.ui.theme.TheSmartCalculatorTheme
import kotlinx.coroutines.launch

class ThermometerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var temperatureStr by remember { mutableStateOf("") }
            var scale by remember { mutableStateOf(Scale.Celsius) }
            var season by remember { mutableStateOf(Season.S) }
            var seasonSwitchState by remember { mutableStateOf(false) }
            var result by remember { mutableStateOf("waiting") }
            TheSmartCalculatorTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ModuleNameText(name = "Thermometer", Color.Green)
                        CustomDoubleField(
                            label = "Temperature",
                            value = temperatureStr,
                            description = "x-coordinate of first circle",
                            onValueChange = { newValue -> temperatureStr = newValue }
                        )
                        Text(text = "Season: " + season.value,
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            textAlign = TextAlign.Center)
                        Switch(
                            checked = seasonSwitchState,
                            onCheckedChange = {
                                seasonSwitchState = it
                                season = if(it) Season.S else Season.W
                                lifecycleScope.launch { result = solve(temperatureStr, scale, season) }
                            }
                        )
                        Text(text = "Scale: " + scale.name,
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            textAlign = TextAlign.Center
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Scale.entries.forEach {
                                val selected = scale == it
                                Text(
                                    "${it.mark}",
                                    Modifier
                                        .padding(10.dp)
                                        .border(
                                            width = if (selected) {2.dp} else {0.dp},
                                            color = Color.Black
                                        )
                                        .weight(1f)
                                        .background(if (selected) Color.LightGray else Color.DarkGray)
                                        .padding(10.dp)
                                        .selectable(
                                            selected = selected,
                                            onClick = {
                                                scale = it
                                                lifecycleScope.launch { result = solve(temperatureStr, scale, season)}
                                            }
                                        ),
                                    textAlign = TextAlign.Center

                                )
                            }
                        }
                        Button(
                            onClick = { lifecycleScope.launch { result = solve(temperatureStr, scale, season) }},
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text("Check microclimate")
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = result,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
