package org.windmill.hw.core.base

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.windmill.hw.app.data.bean.LoadingChangeBean
import org.windmill.hw.app.data.bean.ResponseBean

open class BaseViewModel : ViewModel(), DefaultLifecycleObserver {
    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }
    //val showDialog = MutableLiveData<LoadingChangeBean>()

    //val dismissDialog = MutableLiveData<LoadingChangeBean>()

    //错误信息
    val mErrorResponse = MutableLiveData<ResponseBean<Any>>()

    protected var mContext: Context? = null

    protected var lifecycleScope: LifecycleCoroutineScope? = null

    fun setContext(mContext: Context?) {
        this.mContext = mContext
    }

    fun setScope(scope: LifecycleCoroutineScope?) {
        this.lifecycleScope = scope
    }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套
     */
    open inner class UiLoadingChange {
        //显示加载框
        //val showDialog by lazy { EventLiveData<LoadingChangeBean>() }
        val showDialog = MutableLiveData<LoadingChangeBean>()

        //隐藏
        //val dismissDialog by lazy { EventLiveData<LoadingChangeBean>() }
        val dismissDialog = MutableLiveData<LoadingChangeBean>()
    }
}