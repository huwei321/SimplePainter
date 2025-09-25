package org.windmill.hw.core.base

import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * File description.
 *
 * @author hw
 * @date 2024/10/31
 */
abstract class AppActivity <VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>(){
    private val mainScope by lazy { CoroutineScope(Dispatchers.IO) }

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}

}