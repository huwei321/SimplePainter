package org.windmill.hw.simplepainter.ui

import org.windmill.hw.core.base.BaseViewModel
import org.windmill.hw.core.base.BaseVmDbActivity
import org.windmill.hw.simplepainter.R
import org.windmill.hw.simplepainter.databinding.MainActBinding

/**
 * File description.
 *
 * @author hw
 * @date 2025/9/25
 */
class MainActivity : BaseVmDbActivity<BaseViewModel, MainActBinding>() {
    override fun getLayout() = R.layout.main_act

    override fun createObserver() {

    }
}