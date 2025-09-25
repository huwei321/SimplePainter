package org.windmill.hw.core.base

import androidx.fragment.app.Fragment
import org.windmill.hw.app.data.bean.LoadingChangeBean

/**
 * File description.
 *
 * @author hw
 * @date 2024/10/25
 */
open class BaseFragment : Fragment() {

    protected val mLogTag = this.javaClass.simpleName

    open fun showLoading(bean: LoadingChangeBean) {

    }

    open fun dismissLoading(bean: LoadingChangeBean) {

    }
}