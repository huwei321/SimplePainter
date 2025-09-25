package org.windmill.hw.core.utils

import android.app.Activity
import android.content.Context
import android.os.Process
import java.util.Stack


/**
 * File description.
 *
 * @author hw
 * @date 2024/11/29
 */
class AppManager private constructor() {
    companion object {
        val instance: AppManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AppManager()
        }

        private var activityStack = Stack<Activity>()
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity?) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity?) {
        if (activityStack != null) {
            val it = activityStack.iterator()
            while (it.hasNext()) {
                val selectAct = it.next()
                if (selectAct == activity) {
                    it.remove()
                    break
                }
            }
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity {
        val activity = activityStack.lastElement()
        return activity
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size: Int = activityStack.size
        while (i < size) {
            if (null != activityStack[i]) {
                activityStack[i].finish()
            }
            i++
        }
        activityStack.clear()
    }

    /**
     * 结束其它的Activity
     */
    fun finishOtherActivity(activity: Activity?) {
        val it = activityStack.iterator()
        while (it.hasNext()) {
            val act = it.next()
            if (act !== activity) {
                act.finish()
                it.remove()
            }
        }
    }

    /**
     * 退出应用程序
     */
    fun exitApp(context: Context?) {
        try {
            finishAllActivity()
            System.exit(0)
            Process.killProcess(Process.myPid())
        } catch (e: Exception) {
        }
    }

}