package org.windmill.hw.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson

abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {
    @LayoutRes
    abstract fun getLayout(): Int

    //val mViewModel: VM by lazy { ViewModelProvider(this)[getVmClazz(this)] }
    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    open fun getRootView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = createViewModel()
        mViewModel.setContext(requireActivity())
        mViewModel.setScope(viewLifecycleOwner.lifecycleScope)
        val rootView = getRootView(inflater, container)
        createObserver()
        registerUiChange()
        initView()
        initData()
        return rootView
    }

    open fun initView() {}

    open fun initData() {}

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    /**
     * 创建观察者
     */
    abstract fun createObserver()


    /**
     * 注册UI 事件
     */
    protected fun registerUiChange() {
        //显示弹窗
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        //关闭弹窗
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner) {
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
            viewModel.loadingChange.showDialog.observe(viewLifecycleOwner) {
                showLoading(it)
            }
            //关闭弹窗
            viewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner) {
                dismissLoading(it)
            }
        }
    }

//    inline fun <reified VM: Any> new(vararg params: Any): VM {
//        val clz = VM::class.java
//        val paramTypes = params.map { it::class.java }.toTypedArray()
//        val mCreate = clz.getDeclaredConstructor(*paramTypes)
//        mCreate. isAccessible = true
//        return mCreate. newInstance(* params)
//    }

}