package com.haomai.promotor.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import com.haomai.promotor.common.utils.Logger

/**
 * A custom logging interceptor for OkHttp.
 * It logs request and response details.
 * Note: For production, consider using OkHttp's official HttpLoggingInterceptor.
 */
class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val startTime = System.nanoTime()
        Logger.d("OkHttp-Request", "Sending request ${request.url} on ${chain.connection()}\n${request.headers}")

        val response = chain.proceed(request)

        val endTime = System.nanoTime()
        Logger.d("OkHttp-Response", "Received response for ${response.request.url} in ${(endTime - startTime) / 1e6}ms\n${response.headers}")

        return response
    }
}