package com.notekar.utils

import android.util.Log
import com.notekar.BuildConfig


/**
 * Created by Kumar Himanshu(himanshubit@gmail.com) on 14-01-2020.
 * Copyright (c) 2020 All rights reserved.
 */
object CustomLog {

    fun d(tag: String, message: String) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.d(tag, message)
        }
    }

    fun d(tag: String, message: String, exception: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.d(tag, message, exception)
        }
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.e(tag, message)
        }
    }

    fun e(tag: String, message: String, exeption: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.e(tag, message, exeption)
        }
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.w(tag, message)
        }
    }

    fun w(tag: String, message: String, throwable: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.w(tag, message, throwable)
        }
    }

    fun w(tag: String, throwable: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.w(tag, throwable)
        }
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.i(tag, message)
        }
    }

    fun i(tag: String, message: String, throwable: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.i(tag, message, throwable)
            Log.getStackTraceString(throwable)
        }
    }

    fun i(tag: String, throwable: Throwable) {
        if (BuildConfig.IS_LOG_ENABLE) {
            Log.i(tag, Log.getStackTraceString(throwable))
        }
    }

}