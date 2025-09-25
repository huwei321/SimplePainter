package org.windmill.hw.core.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import org.windmill.hw.app.data.bean.LoadingChangeBean
import org.windmill.hw.app.res.R
import org.windmill.hw.core.utils.AppManager

/**
 * File description.
 *
 * @author hw
 * @date 2024/10/25
 */
open class BaseActivity : AppCompatActivity() {

    protected val mLogTag = this.javaClass.simpleName

    private var isFullScreen = false

    protected var isAutoBars = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.instance.addActivity(this)
        if (isAutoBars) {
            changeStatusBar()
        }
        //changeScreenToPortrait()
    }

    fun changeStatusBar() {
        ImmersionBar.with(this)
            .reset()
            //.hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
            .fullScreen(true)
            .transparentStatusBar()  //透明状态栏，不写默认透明色
            //.transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
            //.transparentBar()             //透明状态栏和导航栏，不写默认状态栏为透明色，导航栏为黑色（设置此方法，fullScreen()方法自动为true）
            //.statusBarColor(R.color.colorPrimary)     //状态栏颜色，不写默认透明色.init()
            //
            .statusBarDarkFont(true)
            .init()
    }

    fun setFullScreen(flag: Boolean) {
        isFullScreen = flag
    }

    fun changeScreenToLandscape(view: View? = null) {
        view?.visibility = View.GONE
        ImmersionBar.with(this)
            .reset()
            .fitsSystemWindows(false)
            .barColor(R.color.color_00000000)
            .statusBarDarkFont(true)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .navigationBarDarkIcon(true)
            .fullScreen(true)
            .removeSupportAllView()
            .init()
    }

    fun changeScreenToPortrait(view: View? = null) {
        view?.visibility = View.INVISIBLE
        ImmersionBar.with(this)
            .reset()
            .transparentStatusBar()  //透明状态栏，不写默认透明色
            .statusBarDarkFont(true)
            .fullScreen(false)
            .apply {
                if (view != null) {
                    this.statusBarView(view)
                } else {
                    this.statusBarColor(R.color.color_3e2942)
                    this.fitsSystemWindows(true)
                }
            }
            .transparentStatusBar()
            .init()
    }

    open fun showLoading(bean: LoadingChangeBean) {

    }

    open fun dismissLoading(bean: LoadingChangeBean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.instance.removeActivity(this)
    }
}