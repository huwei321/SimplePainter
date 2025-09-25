package org.windmill.hw.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.windmill.hw.app.data.bean.LoadingChangeBean

/**
 * File description.
 *
 * @author hw
 * @date 2024/11/8
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>() {

    protected var mLoadingDialog: BaseLoadingDialog? = null

    //该类绑定的ViewDataBinding
    lateinit var mDatabind: DB

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        mDatabind = DataBindingUtil.inflate<DB>(inflater, getLayout(), container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }
    override fun showLoading(bean: LoadingChangeBean) {
        if (mLoadingDialog == null) {
            mLoadingDialog = BaseLoadingDialog()
        }
        mLoadingDialog?.showByFlag(childFragmentManager, bean.flag)
    }

    override fun dismissLoading(bean: LoadingChangeBean) {
        mLoadingDialog?.dismissByFlag(bean.flag)
    }
}