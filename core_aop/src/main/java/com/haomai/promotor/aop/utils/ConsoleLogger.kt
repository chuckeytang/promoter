package com.haomai.promotor.aop.utils

import com.haomai.promotor.common_jvm.utils.Logger // 导入 core_common_jvm 中的接口

class ConsoleLogger : Logger {
    override fun debug(tag: String, message: String) {
        println("DEBUG/$tag: $message")
    }

    override fun info(tag: String, message: String) {
        println("INFO/$tag: $message")
    }

    override fun warn(tag: String, message: String) {
        System.err.println("WARN/$tag: $message") // 警告和错误通常打印到标准错误流
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        System.err.println("ERROR/$tag: $message")
        throwable?.printStackTrace(System.err)
    }
}