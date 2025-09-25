package org.windmill.hw.core.base

import androidx.multidex.MultiDexApplication
import org.windmill.hw.core.utils.AppUtils

/**
 * File description.
 *
 * @author hw
 * @date 2024/10/25
 */
open class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        AppUtils.instance.setApplication(this)
    }
}