package org.windmill.hw.core.base

import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope


/**
 * File description.
 * LifecycleService
 * @author hw
 * @date 2024/11/13
 */
abstract class BaseService : LifecycleService(), ViewModelStoreOwner,
    HasDefaultViewModelProviderFactory {

    protected val mViewModelStore = ViewModelStore()

    protected var mFactory: ViewModelProvider.Factory? = null

    override fun onCreate() {
        super.onCreate()
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    mViewModelStore.clear()
                    source.lifecycle.removeObserver(this)
                }
            }
        })
    }

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = if (mFactory != null) mFactory!! else (ViewModelProvider.AndroidViewModelFactory(
            application
        ).also {
            mFactory = it
        })

    override val viewModelStore: ViewModelStore
        get() = mViewModelStore

}