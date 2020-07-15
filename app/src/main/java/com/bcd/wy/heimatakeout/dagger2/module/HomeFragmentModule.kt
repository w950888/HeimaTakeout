package com.bcd.wy.heimatakeout.dagger2.module

import com.bcd.wy.heimatakeout.presenter.HomeFragmentPresenter
import com.bcd.wy.heimatakeout.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule(val homeFragment: HomeFragment) {

    @Provides
    fun provideHomeFragmentPresenter(): HomeFragmentPresenter {
        return HomeFragmentPresenter(homeFragment)
    }
}