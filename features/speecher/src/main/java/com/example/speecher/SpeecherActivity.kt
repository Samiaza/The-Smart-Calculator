package com.example.speecher

import CustomDoubleField
import ModuleNameText
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.speecher.ui.theme.TheSmartCalculatorTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SpeecherActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var numberStr by remember { mutableStateOf("") }
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
                        ModuleNameText(name = "Speecher", Color.White)
                        CustomDoubleField(
                            label = "Number",
                            value = numberStr,
                            description = "x-coordinate of first circle",
                            onValueChange = { newValue -> numberStr = newValue }
                        )
                        Button(
                            onClick = {lifecycleScope.launch { result = speech(numberStr) }},
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        ) {
                            Text("Speech!")
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
                                textAlign = TextAlign.Center,
                                fontSize = 30.sp,
                                lineHeight = 45.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
