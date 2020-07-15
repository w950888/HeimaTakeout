package com.bcd.wy.heimatakeout.ui.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bcd.wy.heimatakeout.R
import com.bcd.wy.heimatakeout.ui.activity.LoginActivity
import org.jetbrains.anko.find
import kotlin.math.acos

/**
 * @author Luke
 * @time 2020/6/15 下午 2:26
 * @version 1.0.0
 * @description
 */
class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userView = View.inflate(activity, R.layout.fragment_user, null)
        //(view as TextView).text = "用户"
        val ivLogin = userView.find<ImageView>(R.id.login)
        ivLogin.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
        }
        return userView
    }

}