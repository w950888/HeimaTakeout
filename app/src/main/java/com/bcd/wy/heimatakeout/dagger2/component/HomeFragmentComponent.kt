package com.bcd.wy.heimatakeout.dagger2.component

import com.bcd.wy.heimatakeout.dagger2.module.HomeFragmentModule
import com.bcd.wy.heimatakeout.ui.fragment.HomeFragment
import dagger.Component

@Component(modules = arrayOf(HomeFragmentModule::class))
interface HomeFragmentComponent {
    fun inject(homeFragment: HomeFragment)
}