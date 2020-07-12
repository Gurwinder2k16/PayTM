package com.assignment.paytm.module.activity

import android.os.Bundle
import com.assignment.paytm.R
import com.assignment.paytm.module.fragments.MainFragment

class MainActivity : BaseActivity() {

    private var mDefaultFragment = MainFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            setDefaultFragment()
        }
    }

    private fun setDefaultFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, mDefaultFragment)
            .commitNow()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
