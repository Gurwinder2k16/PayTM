package com.example.daggerexample.di

import com.assignment.paytm.module.activity.DetailActivity
import com.assignment.paytm.module.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    abstract fun contributeDetailActivity(): DetailActivity
}
