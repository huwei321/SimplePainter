package org.windmill.hw.simplepainter.ui

import org.windmill.hw.core.base.BaseVmDbActivity
import org.windmill.hw.simplepainter.R
import org.windmill.hw.simplepainter.databinding.DrawActBinding
import org.windmill.hw.simplepainter.vm.DrawViewModel

/**
 * File description.
 *
 * @author hw
 * @date 2025/9/25
 */
class DrawActivity : BaseVmDbActivity<DrawViewModel, DrawActBinding>() {
    override fun getLayout(): Int = R.layout.draw_act

    override fun createObserver() {
    }
}