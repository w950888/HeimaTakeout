package com.bcd.wy.heimatakeout.ui.fragment

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcd.wy.heimatakeout.R
import com.bcd.wy.heimatakeout.dagger2.component.DaggerHomeFragmentComponent
import com.bcd.wy.heimatakeout.dagger2.module.HomeFragmentModule
import com.bcd.wy.heimatakeout.model.beans.Seller
import com.bcd.wy.heimatakeout.presenter.HomeFragmentPresenter
import com.bcd.wy.heimatakeout.ui.adapter.HomeRvAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * @author Luke
 * @time 2020/6/15 下午 2:26
 * @version 1.0.0
 * @description
 */
class HomeFragment : Fragment() {

    lateinit var homeRvAdapter: HomeRvAdapter
    lateinit var rvHome: RecyclerView
    //lateinit var homeFragmentPresenter: HomeFragmentPresenter

    private val TAG: String = HomeFragment::class.java.simpleName

    @Inject
    lateinit var homeFragmentPresenter: HomeFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(activity, R.layout.fragment_home, null)
        rvHome = view.find<RecyclerView>(R.id.rv_home)
        //从上到下的列表视图
        rvHome.layoutManager = LinearLayoutManager(activity)
        homeRvAdapter = HomeRvAdapter(activity)
        rvHome.adapter = homeRvAdapter
        //homeFragmentPresenter = HomeFragmentPresenter(this)
        // TODO: 2020/6/19  解耦view层和p层 （基于注解的依赖注入） 通过依赖注入生成HomeFragmentPresenter
        DaggerHomeFragmentComponent.builder()
            .homeFragmentModule(HomeFragmentModule(this))
            .build()
            .inject(this)
        distance = 120.dip2px()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    val datas: ArrayList<String> = ArrayList<String>()
    var sum: Int = 0
    var distance: Int = 0
    var alpha = 55
    private fun initData() {
/*        for (i in 0 until 100) {
            datas.add("我是商家:" + i)
        }*/
        homeFragmentPresenter.getHomeInfo()
        //homeRvAdapter.mDatas = datas


    }

    fun Int.dip2px(): Int {
        val scale = resources.displayMetrics.density
        return (toFloat() * scale + 0.5f).toInt()
    }

    val allList: ArrayList<Seller> = ArrayList<Seller>()
    fun onHomeSuccess(nearbySeller: List<Seller>, otherSeller: List<Seller>) {
        allList.clear()
        allList.addAll(nearbySeller)
        allList.addAll(otherSeller)
        homeRvAdapter.setData(allList)

        //有数据可以滚动才可以监听滚动事件
        rvHome.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //Log.d(TAG, "onScrolled:dx:${dx}--dy:${dy} ")
                sum += dy
                //Log.d(TAG, "onScrolled: sum:" + sum)
                if (sum > distance) {
                    alpha = 255
                } else {
                    alpha = (sum * 200 / distance)
                    alpha += 54
                }
                ll_title_container.setBackgroundColor(Color.argb(alpha, 0x31, 0x90, 0xe8))
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        toast("成功拿到首页数据")
    }

    fun onHomeFailed() {
        toast("获取首页数据失败")
    }
}