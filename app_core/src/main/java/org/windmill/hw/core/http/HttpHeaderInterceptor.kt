package org.windmill.hw.core.http

import android.util.Log
import org.windmill.hw.core.utils.LogUtils
//import mu.KotlinLogging
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer

/**
 * File description.
 *
 * @author hw
 * @date 2023/8/25
 */
class HttpHeaderInterceptor : Interceptor {
    //private val logger = KotlinLogging.logger {}
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        builder.header("Content-Type", "application/json; charset=UTF-8")
//        //builder.header("Content-Type", "text/html; charset=UTF-8")
//        builder.removeHeader("User-Agent")//移除旧的
//        builder.addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36")//添加真正的头部
        logRequest(chain.request())
        val request = builder.build()
        val response = chain.proceed(request)
        logResponse(response)
        return response
    }


    private fun logRequest(request: Request) {
        val body = request.body
        val hasRequestBody = body != null
        //logger.error { "--> ${request.method()} ${request.url()} HTTP/1.1" }
        request.headers.toMultimap().forEach { (t, u) ->
            LogUtils.e("--> headers $t: $u")
        }

        if (hasRequestBody) {
            val buffer = Buffer()
            body!!.writeTo(buffer)
            LogUtils.e("--> ${buffer.readUtf8()} ")
        }
    }

    private fun logResponse(response: Response) {
        val body = response.body
        val contentLength = body?.contentLength()
        val headers = response.headers
        val headersLength = headers.toMultimap().toString().length
        LogUtils.e(
            "<-- ${response.code} ${response.message} ${
                response.request.url
            } (${if (contentLength != null && contentLength != -1L) "$contentLength-byte" else "unknown-length"} body, $headersLength-byte headers)"
        )

        headers.toMultimap().forEach { (t, u) ->
            LogUtils.e("<-- headers $t: $u")
        }

        if (hasBody(response)) {
            val source = body!!.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer
            LogUtils.e(
                "<-- ${response.request.url.encodedPath} ↓ \n" +
                        "<-- ${buffer.clone().readUtf8()}"
            )
        }
    }

    private fun hasBody(response: Response): Boolean {
        return response.body != null
    }

}