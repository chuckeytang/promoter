 package com.haomai.promotor.common.utils

object Logger { // 可能是 object 而不是 class，或者有一个 companion object

    // Debug 级别日志
    fun d(tag: String, message: String) {
        // 实际的日志实现，例如使用 Android's Log.d()
        android.util.Log.d(tag, message)
    }

    // 你可能还有其他级别的日志方法，例如：
    fun i(tag: String, message: String) { /* ... */ }
    fun w(tag: String, message: String) { /* ... */ }
    fun e(tag: String, message: String, throwable: Throwable? = null) { /* ... */ }
}