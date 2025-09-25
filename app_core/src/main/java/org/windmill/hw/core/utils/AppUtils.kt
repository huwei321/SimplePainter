package org.windmill.hw.core.utils

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.util.Locale


/**
 * File description.
 *
 * @author hw
 * @date 2024/10/28
 */
class AppUtils private constructor() {
    private var myApp: Application? = null
        get

    //companion object : SingletonHolder<AppUtils, Application>(::AppUtils)
    companion object {
        val instance: AppUtils by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AppUtils()
        }
    }

    fun setApplication(myApp: Application) {
        this.myApp = myApp
    }

    fun getApplication(): Application? {
        return this.myApp
    }

    /**
     * 获取当前apk的版本号
     *
     * @param mContext
     * @return
     */
    fun getVersionCode(): Long {
        var versionCode = 0L
        if (this.myApp == null) {
            return versionCode
        }
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                versionCode = this.myApp!!.packageManager.getPackageInfo(
                    this.myApp!!.packageName, 0
                ).longVersionCode
            } else {
                versionCode = this.myApp!!.packageManager.getPackageInfo(
                    this.myApp!!.packageName, 0
                ).versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    /**
     * 获取当前apk的版本名
     *
     * @param context 上下文
     * @return
     */
    fun getVersionName(): String {
        var versionName: String? = ""
        if (this.myApp == null) {
            return versionName ?: ""
        }
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionName
            versionName = this.myApp!!.packageManager.getPackageInfo(
                this.myApp!!.packageName, 0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName ?: ""
    }

    fun getAppPackageName(): String {
        var packageName = ""
        if (this.myApp == null) {
            return packageName
        }
        try {
            packageName = this.myApp!!.packageName
        } catch (ex: Exception) {

        }
        return packageName
    }

    fun getCountry(): String {
        if (myApp == null) {
            return ""
        }
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myApp!!.resources.configuration.locales.get(0)
        } else {
            myApp!!.resources.configuration.locale
        }
        //或者仅仅使用 locale = Locale.getDefault(); 不需要考虑接口 deprecated(弃用)问题
        return locale.country
    }

    fun getLanguageOnly(): String {
        if (myApp == null) {
            return ""
        }
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            myApp!!.resources.configuration.locales.get(0)
        } else {
            myApp!!.resources.configuration.locale
        }
        //或者仅仅使用 locale = Locale.getDefault(); 不需要考虑接口 deprecated(弃用)问题
        return locale.language
    }

    /** 获取厂商信息  */
    fun getManufacturer(): String {
        return Build.MANUFACTURER?.trim() ?: ""
    }

    fun isSamsung(): Boolean {
        return getManufacturer().lowercase(Locale.getDefault()).indexOf("samsung") != -1
    }

}