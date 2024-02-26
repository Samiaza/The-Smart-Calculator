package com.example.logger

import android.util.Log

object Logger {

    const val ASSERT: Int = Log.ASSERT
    const val DEBUG: Int = Log.DEBUG
    const val ERROR: Int = Log.ERROR
    const val INFO: Int = Log.INFO
    const val VERBOSE: Int = Log.VERBOSE
    const val WARN: Int = Log.WARN

    fun d(tag: String?, msg: String): Int {
        return Log.d(tag, msg)
    }

    fun d(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.d(tag, msg, tr)
    }

    fun e(tag: String?, msg: String): Int {
        return Log.e(tag, msg)
    }

    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.e(tag, msg, tr)
    }

    fun getStackTraceString(tr: Throwable?): String {
        return Log.getStackTraceString(tr)
    }

    fun i(tag: String?, msg: String): Int {
        return Log.i(tag, msg)
    }

    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.i(tag, msg, tr)
    }

    fun isLoggable(tag: String?, level: Int): Boolean {
        return Log.isLoggable(tag, level)
    }

    fun w(tag: String?, msg: String): Int {
        return Log.w(tag, msg)
    }

    fun w(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.w(tag, msg, tr)
    }

    fun w(tag: String?, tr: Throwable?): Int {
        return Log.w(tag, tr)
    }

    fun wtf(tag: String?, tr: Throwable): Int {
        return Log.wtf(tag, tr)
    }

    fun wtf(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.wtf(tag, msg, tr)
    }
}