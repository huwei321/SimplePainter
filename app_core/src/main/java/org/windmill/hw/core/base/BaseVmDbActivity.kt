package org.windmill.hw.core.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.windmill.hw.app.data.bean.LoadingChangeBean

abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {

    protected var mLoadingDialog: BaseLoadingDialog? = null

    lateinit var mDatabind: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        super.initDataBind()
        mDatabind = DataBindingUtil.setContentView(this, getLayout())
        mDatabind.lifecycleOwner = this
    }

    override fun showLoading(bean: LoadingChangeBean) {
        if (mLoadingDialog == null) {
            mLoadingDialog = BaseLoadingDialog()
        }
        mLoadingDialog?.showByFlag(supportFragmentManager, bean.flag)
    }

    override fun dismissLoading(bean: LoadingChangeBean) {
        mLoadingDialog?.dismissByFlag(bean.flag)
    }


}