package com.assignment.paytm.module.activity

import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.assignment.paytm.R
import com.assignment.paytm.model.response.PopularItem
import com.assignment.paytm.module.fragments.DetailFragment

class DetailActivity : BaseActivity() {


    private var mDefaultFragment: DetailFragment = DetailFragment.newInstance()
    private lateinit var mPopularItem: PopularItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
        getDataFromPrevIntent()
        if (savedInstanceState == null && ::mPopularItem.isInitialized) {
            mDefaultFragment.setFetchWikiResponse(mPopularItem)
            setDefaultView(pFragment = mDefaultFragment)
        }
    }

    private fun setDefaultView(pFragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, pFragment)
            .commitNow()
    }

    private fun getDataFromPrevIntent() {
        when (intent.hasExtra(DetailActivity::class.java.simpleName)) {
            true -> {
                mPopularItem = intent.getSerializableExtra(DetailActivity::class.java.simpleName)!! as PopularItem
            }
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }
}
