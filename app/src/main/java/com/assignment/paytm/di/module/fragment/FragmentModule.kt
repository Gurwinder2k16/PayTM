package com.assignment.paytm.di.module.fragment

import com.assignment.paytm.module.fragments.DetailFragment
import com.assignment.paytm.module.fragments.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}