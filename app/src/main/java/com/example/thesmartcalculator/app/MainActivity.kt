package com.example.thesmartcalculator.app

import ModuleLaunchButton
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.circles.CirclesActivity
import com.example.hardmath.HardMathActivity
import com.example.logger.Logger
import com.example.primenumbers.PrimeNumbersActivity
import com.example.speecher.SpeecherActivity
import com.example.thermometer.ThermometerActivity
import com.example.thesmartcalculator.app.ui.theme.TheSmartCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("${this::class.simpleName}", "Create performed")
        setContent {
            TheSmartCalculatorTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(40.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ModuleLaunchButton(name = "Module: Circles",
                            activityClass = CirclesActivity::class.java)
                        ModuleLaunchButton(name = "Module: Primes",
                            activityClass = PrimeNumbersActivity::class.java)
                        ModuleLaunchButton(name = "Module: Thermometer",
                            activityClass = ThermometerActivity::class.java)
                        ModuleLaunchButton(name = "Module: Speecher",
                            activityClass = SpeecherActivity::class.java)
                        ModuleLaunchButton(name = "Module: Hard Math",
                            activityClass = HardMathActivity::class.java)
                    }
                }
            }
        }
//        Logger.i("result", SpeechModule.apply { locale = ULocale.JAPAN }.numberToWords(232423.453456))
    }
}