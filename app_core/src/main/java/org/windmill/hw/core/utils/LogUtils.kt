package org.windmill.hw.core.utils

import android.util.Log

/**
 * File description.
 *
 * @author hw
 * @date 2024/11/6
 */
object LogUtils {

    private val tag = "android-sanjiang"

    private val isOn = true

    fun e(str: String, isThrowable: Boolean = false) {
        if (isOn) {
            if (!isThrowable) {
                Log.e(tag, str)
            } else {
                Log.e(tag, str, Throwable(""))
            }
        }
    }
}