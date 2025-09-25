package org.windmill.hw.core.http

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

/**
 * File description.
 *
 * @author hw
 * @date 2023/8/25
 */
class StringTypeAdapter : TypeAdapter<String>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter?, value: String?) {
        if (value == null) {
            out?.nullValue()
        } else {
            out?.value(value)
        }
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): String {
        return when (`in`.peek()) {
            JsonToken.NULL -> {
                `in`.nextNull()
                ""
            }

            else -> `in`.nextString()
        }
    }
}