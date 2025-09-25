package org.windmill.hw.core.base

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

abstract class BaseVmService<VM : BaseViewModel> : BaseService() {

    lateinit var mViewModel: VM
    override fun onCreate() {
        super.onCreate()
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        mViewModel.setContext(this)
        mViewModel.setScope(lifecycleScope)
        createObserver()
    }

    /**
     * 创建liveData观察者
     */
    open fun createObserver() {}
}