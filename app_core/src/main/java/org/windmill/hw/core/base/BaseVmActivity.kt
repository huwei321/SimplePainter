package org.windmill.hw.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {
    @LayoutRes
    abstract fun getLayout(): Int

    /**
     * 是否需要使用DataBinding 供子类BaseVmDbActivity修改，用户请慎动
     */
    var isUseDb: Boolean = false

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this)[getVmClazz(this)]
        mViewModel.setContext(this)
        mViewModel.setScope(lifecycleScope)
        super.onCreate(savedInstanceState)
        if (!isUseDb) {
            setContentView(getLayout())
        } else {
            initDataBind()
        }
        createObserver()
        registerUiChange()
        initView(savedInstanceState)
        initData(savedInstanceState)
    }

    open fun initView(savedInstanceState: Bundle?) {}

    fun userDataBinding(isUserDb: Boolean) {
        this.isUseDb = isUserDb
    }

    open fun initData(savedInstanceState: Bundle?) {}

    /**
     * 供子类BaseVmDbActivity 初始化Databinding操作
     */
    open fun initDataBind() {}

    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()

    /**
     * 注册UI 事件
     */
    protected fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(this) {
            showLoading(it)
        }
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(this) {
            dismissLoading(it)
        }
    }

    /**
     * 将非该Activity绑定的ViewModel添加 loading回调 防止出现请求时不显示 loading 弹窗bug
     * @param viewModels Array<out BaseViewModel>
     */
    protected fun addLoadingObserve(vararg viewModels: BaseViewModel) {
        viewModels.forEach { viewModel ->
            //显示弹窗
            viewModel.loadingChange.showDialog.observe(this) {
                showLoading(it)
            }
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(this) {
                dismissLoading(it)
            }
        }
    }
}