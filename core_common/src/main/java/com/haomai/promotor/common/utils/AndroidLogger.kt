package com.haomai.promotor.common.utils

import android.util.Log
import com.haomai.promotor.common_jvm.utils.Logger

class AndroidLogger : Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }
    override fun info(tag: String, message: String) {
        Log.i(tag, message)
    }
    override fun warn(tag: String, message: String) {
        Log.w(tag, message)
    }
    override fun error(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}