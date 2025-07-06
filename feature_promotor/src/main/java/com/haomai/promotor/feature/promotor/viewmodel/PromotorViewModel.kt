package com.haomai.promotor.feature.promotor.viewmodel

import com.haomai.promotor.common.viewmodel.BaseViewModel

// 继承 BaseViewModel，并指定状态中数据的类型，比如是 String
class PromotorViewModel : BaseViewModel<String>() {

    fun fetchData() {
        // 2. 使用 execute 函数来加载数据
        execute {
            // This is a suspend block. You can perform network requests here.
            kotlinx.coroutines.delay(2000) // Simulate a network delay
            "This is the data loaded successfully!" // Return the result
        }
    }

    fun causeError() {
        execute {
            kotlinx.coroutines.delay(1000)
            throw IllegalStateException("Something went wrong!")
        }
    }
}