package com.example.speecher

import com.example.speechmodule.SpeechModule

suspend fun speech(numberStr: String): String {
    return try {
        val number = numberStr.toDoubleOrNull() ?: throw Exception("Wrong number format\nTry again")
        SpeechModule.numberToWords(number)
    } catch(e: Exception) {
        "Error: " + e.message.toString()
    }
}