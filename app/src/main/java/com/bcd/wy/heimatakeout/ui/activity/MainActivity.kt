package com.bcd.wy.heimatakeout.ui.activity

import android.app.Fragment
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.bcd.wy.heimatakeout.R
import com.bcd.wy.heimatakeout.ui.fragment.HomeFragment
import com.bcd.wy.heimatakeout.ui.fragment.MoreFragment
import com.bcd.wy.heimatakeout.ui.fragment.OrderFragment
import com.bcd.wy.heimatakeout.ui.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //val fragments: ArrayList<Fragment> = ArrayList()
    val fragments: List<Fragment> =
        listOf<Fragment>(HomeFragment(), OrderFragment(), UserFragment(), MoreFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val main_bottom_bar = findViewById<LinearLayout>(R.id.main_bottom_bar)
        //initFragments()
        //判断设备是否有虚拟按键 如果有增加paddingBottom=50dp
        if (checkDeviceHasNavigationBar(this)) {
            //ll_main_activity.setPadding(0, 0, 0, dp2px(this,50.toFloat()))
            ll_main_activity.setPadding(0, 0, 0, 50.dp2px2())
        }
        initBottomBar()
        changeIndex(0)
    }

    private fun initFragments() {

    }

    private fun initBottomBar() {
        for (i in 0 until main_bottom_bar.childCount) {
            main_bottom_bar.getChildAt(i).setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    changeIndex(i)
                }
            })
        }
    }

    private fun changeIndex(index: Int) {
        for (i in 0 until main_bottom_bar.childCount) {
            val child = main_bottom_bar.getChildAt(i)
            if (i == index) {
                //选中 禁用效果
                //child.isEnabled = false
                setEnable(child, false)
            } else {
                //没有选中 启用效果 enable=true
                //child.isEnabled = true
                setEnable(child, true)
            }
        }
        fragmentManager.beginTransaction().replace(R.id.main_content, fragments[index]).commit()
    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if (child is ViewGroup) {
            for (i in 0 until child.childCount) {
                child.getChildAt(i).isEnabled = isEnable
            }
        }
    }


    //获取是否存在NavigationBar
    fun checkDeviceHasNavigationBar(context: Context): Boolean {
        var hasNavigationBar = false
        val rs = context.getResources()
        val id = rs.getIdentifier("config_showNavigationBar", "bool", "android")
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id)
        }
        try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val m = systemPropertiesClass.getMethod("get", String::class.java)
            val navBarOverride = m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
            if ("1" == navBarOverride) {
                hasNavigationBar = false
            } else if ("0" == navBarOverride) {
                hasNavigationBar = true
            }
        } catch (e: Exception) {

        }
        return hasNavigationBar
    }

    /**
     * dip或dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    fun Int.dip2px(): Int {
        val scale = resources.displayMetrics.density
        return (toFloat() * scale + 0.5f).toInt()
    }


    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue
            .applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                context.resources.displayMetrics
            ).toInt()
    }

    /**
     * 吧转换功能添加到Int类的扩展函数
     */
     public fun Int.dp2px2(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(),
            resources.displayMetrics
        ).toInt()
    }

}