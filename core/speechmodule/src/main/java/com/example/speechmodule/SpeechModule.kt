package com.example.speechmodule

import com.ibm.icu.text.RuleBasedNumberFormat
import com.ibm.icu.util.ULocale

object SpeechModule {
    var locale: ULocale? = null

    fun numberToWords(number: Number): String {
        return RuleBasedNumberFormat(locale ?: ULocale("en", "US"),
            RuleBasedNumberFormat.SPELLOUT).format(number)
    }
}