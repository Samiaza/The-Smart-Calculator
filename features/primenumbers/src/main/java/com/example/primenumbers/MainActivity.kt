package com.example.primenumbers

import CustomIntField
import ModuleNameText
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.logger.Logger
import com.example.primenumbers.ui.theme.TheSmartCalculatorTheme
import kotlinx.coroutines.launch

class PrimeNumbersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.i("${this::class.simpleName}", "Activity created")
        setContent {
            var intNumStr by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var mode by remember { mutableStateOf(CreateSetMode.LOWER) }
            var result: Pair<String, Set<Pair<Int, Boolean>>?>
                    by remember { mutableStateOf(Pair("Waiting", mutableSetOf())) }
            TheSmartCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ModuleNameText(name = "Primes", Color.Yellow)
                        CustomIntField(
                            label = "Number",
                            value = intNumStr,
                            description = "x-coordinate of first circle",
                            onValueChange = { newValue -> intNumStr = newValue }
                        )
                        Text("Mode: ${mode.name}")
                        Box(
                            Modifier.padding(10.dp)
                        ) {
                            IconButton(onClick = { expanded = true }) {
                                Icon(Icons.TwoTone.ArrowDropDown, contentDescription = "Select mode")
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                Modifier.fillMaxWidth().padding(10.dp)
                            ) {
                                Text("Lower",
                                    Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .padding(bottom = 10.dp)
                                        .clickable {
                                            mode = CreateSetMode.LOWER
                                            Logger.i("${this::class.simpleName}", "Lower mode selected")
                                            lifecycleScope.launch {
                                                result =  createSet(intNumStr, mode)
                                            }})
                                Divider(thickness = 2.dp)
                                Text("Higher",
                                    Modifier
                                        .align(alignment = Alignment.CenterHorizontally)
                                        .padding(top = 10.dp)
                                        .clickable {
                                            mode = CreateSetMode.HIGHER
                                            Logger.i("${this::class.simpleName}", "Higher mode selected")
                                            lifecycleScope.launch {
                                                result =  createSet(intNumStr, mode)
                                            }})
                            }
                        }

                        Button(
                            onClick = {
                                Logger.i("${this::class.simpleName}", "Create Set launched")
                                lifecycleScope.launch {
                                    result =  createSet(intNumStr, mode) }},
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text("Create Set")
                        }
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .border(2.dp, Color.Black)
                                .padding(10.dp),
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            result.second?.forEach {
                                Text(text = "${it.first}" + if (it.second) " - prime" else "")
                            } ?: Text(text = result.first)
                        }
                    }
                }
            }
        }
    }
}
