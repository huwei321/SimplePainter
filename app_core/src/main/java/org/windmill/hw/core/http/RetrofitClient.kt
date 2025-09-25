package org.windmill.hw.core.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit

/**
 * File description.
 *
 * @author hw
 * @date 2025/1/2
 */
class RetrofitClient private constructor() {

    companion object {
        //单位秒
        private const val DEF_TIMEOUT_CONNECT = 30L
        private const val DEF_TIMEOUT_READ = 30L
        private const val DEF_TIMEOUT_WRITE = 30L
        val instance: RetrofitClient by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitClient()
        }
    }

    private var mRetrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {
        if (mRetrofit != null) {
            return mRetrofit!!
        }
        val builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true) //retry
            .connectTimeout(DEF_TIMEOUT_CONNECT * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(DEF_TIMEOUT_READ * 1000L, TimeUnit.MILLISECONDS)
            .writeTimeout(DEF_TIMEOUT_WRITE * 1000L, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpHeaderInterceptor())
            //.addInterceptor(ResponseInterceptor())
            .build()
        return Retrofit.Builder()
            .client(builder)
            .baseUrl("http://prod.vantruecam.com")
            //.addConverterFactory(GsonConverterFactory.create())
            //.addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(JsonOrXmlConverterFactory.create())
            .build()
    }
}