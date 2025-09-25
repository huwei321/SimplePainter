package org.windmill.hw.core.base

import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * File description.
 *
 * @author hw
 * @date 2024/11/15
 */
abstract class AppFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    private val mainScope by lazy { CoroutineScope(Dispatchers.IO) }

    /**
     * 创建liveData观察者
     */
    override fun createObserver() {}


}