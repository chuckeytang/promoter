package com.haomai.promotor.network.api

import android.content.Context
import com.haomai.promotor.network.interceptors.CacheInterceptor
import com.haomai.promotor.network.interceptors.LoggingInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitClient {

    private const val BASE_URL = "https://api.yourserver.com/"

    fun create(context: Context): ApiService {
        // Create a cache object
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
        val cache = Cache(File(context.cacheDir, "http-cache"), cacheSize)

        // Build OkHttpClient with interceptors and cache
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .addInterceptor(CacheInterceptor(context)) // Add cache interceptor
            .cache(cache) // Set cache
            .build()

        // Build Retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}