package org.windmill.hw.core.http

import android.R.attr
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.lang.reflect.Type


/**
 * File description.
 *
 * @author hw
 * @date 2025/1/13
 */
class JsonOrXmlConverterFactory : Converter.Factory() {

    private val xmlFactory: Converter.Factory = SimpleXmlConverterFactory.create()
    private val jsonFactory: Converter.Factory = GsonConverterFactory.create()

    companion object{
        fun create(): JsonOrXmlConverterFactory {
            return JsonOrXmlConverterFactory()
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        for (annotation in annotations) {
            if (annotation !is ResponseFormat) {
                continue
            }
            val format: String = (annotation as ResponseFormat).format
            if ("xml".equals(format,true)) {
                return xmlFactory.responseBodyConverter(type, annotations, retrofit)
            } else {
                return jsonFactory.responseBodyConverter(type, annotations, retrofit)
            }

        }

        return null
    }
}