package com.assignment.paytm.module.fragments;

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.paytm.R
import com.assignment.paytm.model.response.PopularItem
import com.assignment.paytm.module.fragments.viewmodels.DetailViewModel
import com.assignment.paytm.utiles.ImageUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_user_header.*
import kotlinx.android.synthetic.main.layout_actionbar.*
import kotlinx.android.synthetic.main.layout_sub_content.*
import javax.inject.Inject

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    @Inject
    lateinit var mViewModel: DetailViewModel

    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mFetchWikiResponse: PopularItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(DetailViewModel::class.java)
        iv_back.setOnClickListener { activity?.onBackPressed() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToView()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    fun setFetchWikiResponse(pFetchWikiResponse: PopularItem) {
        mFetchWikiResponse = pFetchWikiResponse
    }

    private fun setDataToView() {
        when (::mFetchWikiResponse.isInitialized) {
            true -> {
                tv_title.text = mFetchWikiResponse.name
                tv_desc.text = mFetchWikiResponse.city!!
                ImageUtils.newInstance().downloadImage(
                    pUrl = mFetchWikiResponse.horizontalCoverImage!!,
                    pView = iv_users
                )

                tv_url.text = getString(R.string.repo_url_s, mFetchWikiResponse.venueName)
                tv_full_desc.text = getString(R.string.tag,mFetchWikiResponse.model)
            }
        }
    }
}
