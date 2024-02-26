package com.example.thermometer

import java.lang.StringBuilder

enum class Scale(val mark: Char) {
    Celsius('\u2103'),
    Fahrenheit('F'),
    Kelvin('K')
}

enum class Season(val value: String) {
    S("Summer"),
    W("Winter")
}

fun fromCelsiusToFahrenheit(arg : Float) : Float {
    return 9f / 5f * arg + 32f
}

fun fromCelsiusToKelvin(arg : Float) : Float {
    return arg + 273.15f;
}

suspend fun solve(temperatureStr: String, scale: Scale, season: Season): String {
    return try {
        var temperature =
            temperatureStr.toFloatOrNull() ?: throw Exception("Wrong temperature entered")
        if (temperature < -273.15) throw Exception("Wrong temperature entered")

        var temperatureMin = if (season == Season.S) 22f else 20f
        var temperatureMax = if (season == Season.S) 25f else 22f

        if (scale == Scale.Fahrenheit) {
            temperature = fromCelsiusToFahrenheit(temperature)
            temperatureMin = fromCelsiusToFahrenheit(temperatureMin)
            temperatureMax = fromCelsiusToFahrenheit(temperatureMax)
        } else if (scale == Scale.Kelvin) {
            temperature = fromCelsiusToKelvin(temperature)
            temperatureMin = fromCelsiusToKelvin(temperatureMin)
            temperatureMax = fromCelsiusToKelvin(temperatureMax)
        }

        val resStr = StringBuilder("The temperature is $temperature ${scale.mark}.")
        resStr.append("\nThe comfortable temperature is from ")
        resStr.append("$temperatureMin to $temperatureMax ${scale.mark}.")

        if (temperature < temperatureMin) {
            resStr.append("\nPlease, make it warmer by ${temperatureMin - temperature} degrees.")
        } else if (temperature > temperatureMax) {
            resStr.append("\nPlease, make it colder by ${temperature - temperatureMax} degrees.")
        } else {
            resStr.append("\nThe temperature is comfortable.")
        }
        resStr.toString()
    } catch(e: Exception) {
        "Error: " + e.message.toString()
    }
}

