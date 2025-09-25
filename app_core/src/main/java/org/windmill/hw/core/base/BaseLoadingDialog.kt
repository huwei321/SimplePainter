package org.windmill.hw.core.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.windmill.hw.app.res.R
import org.windmill.hw.app_core.databinding.LoadingLayoutBinding
import org.windmill.hw.core.utils.LogUtils

/**
 * File description.
 *
 * @author hw
 * @date 2024/11/25
 */
class BaseLoadingDialog : BaseDialogFragment(), LifecycleEventObserver {

    private val flagList = arrayListOf<String>()

    private lateinit var mViewBinding: LoadingLayoutBinding

    private lateinit var mAnim: Animation

    private var time: Long = 0L

    private var mDelayJob: Deferred<Unit>? = null
    private var mDelayJob2: Deferred<Unit>? = null

    private var isColse = true

    //private var mDelayListener: DelayDismissListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
        this.isColse = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = LoadingLayoutBinding.inflate(inflater, container, false)
        // 必须设置这两个,才能设置宽度
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        mAnim =
            AnimationUtils.loadAnimation(requireContext(), R.anim.loading_anim)
        return mViewBinding.root
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event.targetState) {

            Lifecycle.State.RESUMED -> {
                mViewBinding.ivLoadingImg.startAnimation(mAnim)
            }

            Lifecycle.State.DESTROYED -> {
                mViewBinding.ivLoadingImg.clearAnimation()
            }

            else -> {

            }
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        super.show(manager, tag)
        time = System.currentTimeMillis()
        isColse = false
    }

    override fun dismiss() {
        isColse = true
        super.dismiss()
        mDelayJob2?.cancel()
        mDelayJob?.cancel()
    }

    override fun dismissAllowingStateLoss() {
        isColse = true
        super.dismissAllowingStateLoss()
        mDelayJob2?.cancel()
        mDelayJob?.cancel()
    }

    /**
     * 防止多个请求
     * dialog.isShowing
     */
    fun showByFlag(manager: FragmentManager, flag: String) {
        flagList.add(flag)
        LogUtils.e("showByFlag=============================")
        LogUtils.e("isVisible = ${isVisible}   isAdded=${isAdded}")
        if (isColse) {
            isColse = false
            if (!this.isVisible && !this.isAdded) {
                //if(manager.findFragmentByTag(this.javaClass.simpleName) == null){
                show(manager, this.javaClass.simpleName)
                //}
            }
        }
    }

    /**
     * 多请求时候最后的一个才结束
     */
    fun dismissByFlag(flag: String) {
        LogUtils.e("dismissByFlag=============================")
        flagList.remove(flag)
        if (flagList.isEmpty()) {
            dismissAllowingStateLoss()
        }
    }


    /**
     * 防止过快的关闭造成闪
     */
    /*fun dismissDelay() {
        var chazhi = System.currentTimeMillis() - time
        lifecycleScope.launch {
            if (chazhi < 2 * 1000) {
                mDelayJob = async(context = Dispatchers.IO) {
                    delay(3000)
                }
                mDelayJob?.await()
            }
            mDelayJob2 = async(context = Dispatchers.Main) {
                if (isActive && !isColse) {
                    dismissAllowingStateLoss()
                    //mDelayListener?.onFinishDismissDelay()
                }
            }
            mDelayJob2?.await()
        }
    }*/

    //fun setDelayListener(listener: DelayDismissListener?) {
    //    this.mDelayListener = listener
    //}
}