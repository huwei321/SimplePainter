package org.windmill.hw.core.base

import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.lang.reflect.Field

/**
 * File description.
 * 解决短时间多次点击显示
 * @author hw
 * @date 2024/11/12
 */
open class BaseDialogFragment : DialogFragment() {
    private var Tag = this::class.java.simpleName

    override fun show(manager: FragmentManager, tag: String?) {
        val fragment = manager.findFragmentByTag(tag)
        if (fragment?.isAdded != true && fragment?.isVisible != true) {
            try {
                val dismissed: Field = DialogFragment::class.java.getDeclaredField("mDismissed")
                dismissed.isAccessible = true
                dismissed.set(this, false)
                val showByMe: Field = DialogFragment::class.java.getDeclaredField("mShownByMe")
                showByMe.isAccessible = true
                showByMe.set(this, true)
                val ft: FragmentTransaction = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            } catch (ex: Exception) {
                Log.e(Tag, "AppAlertDialog show: ${ex?.message}")
                super.show(manager, tag)
            }
        }
    }
}