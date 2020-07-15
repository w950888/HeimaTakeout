package com.bcd.wy.heimatakeout.presenter

import android.util.Log
import com.bcd.wy.heimatakeout.model.beans.Seller
import com.bcd.wy.heimatakeout.model.net.ResponseInfo
import com.bcd.wy.heimatakeout.model.net.TakeoutService
import com.bcd.wy.heimatakeout.ui.fragment.HomeFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Luke
 * @time 2020/6/16 下午 4:58
 * @version 1.0.0
 * @description
 */
class HomeFragmentPresenter(val homeFragment: HomeFragment) {
    private val TAG: String = HomeFragmentPresenter::class.java.simpleName

    val takeoutService: TakeoutService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.107:8080/TakeoutService/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        takeoutService = retrofit.create<TakeoutService>(TakeoutService::class.java!!)
    }

    fun getHomeInfo() {

        // TODO: 2020/6/17 异步访问
        val homeCall = takeoutService.getHomeInfo()
        homeCall.enqueue(object : Callback<ResponseInfo> {
            override fun onFailure(call: Call<ResponseInfo>?, t: Throwable?) {
                //没有连上服务器
                Log.e(TAG, "onResponse: 没有连接上服务器")
            }

            override fun onResponse(call: Call<ResponseInfo>?, response: Response<ResponseInfo>?) {
                if (response == null) {
                    Log.e(TAG, "onResponse: 服务器没有返回数据")
                } else {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body.code == "0") {
                            val json = body.data
                            parserJson(json)
                        } else if (body.code == "-1") {
                            //根据接口文档定义,比如定义-1为空数据
                        }
                    } else {
                        Log.e(TAG, "onResponse: 服务器代码错误")
                    }
                }
/*                response?.let {
                    if (response.isSuccessful) {

                    }
                }*/
            }
        })
/*        //有数据,成功页面
        homeFragment.onHomeSuccess(nearbySeller, otherSeller)
        //无数据,异常页面
        homeFragment.onHomeFailed()*/
    }

    private fun parserJson(json: String) {
        //解析数据
        val gson = Gson()
        val jsonObject = JSONObject(json)
        val nearby = jsonObject.getString("nearbySellerList")

        val nearbySeller: List<Seller> =
            gson.fromJson(nearby, object : TypeToken<List<Seller>>() {}.type)

        val other = jsonObject.getString("otherSellerList")
        val otherSeller: List<Seller> =
            gson.fromJson(nearby, object : TypeToken<List<Seller>>() {}.type)

        Log.e(TAG, "parserJson: " + nearby)
        // TODO: 2020/6/19 刷新UI
        if (nearbySeller.isNotEmpty() || otherSeller.isNotEmpty()) {
            //有数据,成功页面
            homeFragment.onHomeSuccess(nearbySeller, otherSeller)
        } else {
            //无数据,异常页面
            homeFragment.onHomeFailed()
        }
    }
}