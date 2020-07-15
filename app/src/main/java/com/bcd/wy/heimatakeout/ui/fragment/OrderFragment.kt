package com.bcd.wy.heimatakeout.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bcd.wy.heimatakeout.R

/**
 * @author Luke
 * @time 2020/6/15 下午 2:26
 * @version 1.0.0
 * @description
 */
class OrderFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(activity, R.layout.fragment_, null)
        (view as TextView).text = "订单"
        return view
    }

}