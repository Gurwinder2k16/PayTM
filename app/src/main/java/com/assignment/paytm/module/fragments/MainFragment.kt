package com.assignment.paytm.module.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.paytm.R
import com.assignment.paytm.constants.Constants
import com.assignment.paytm.model.response.PopularItem
import com.assignment.paytm.module.adapters.RecycleViewAdadpter
import com.assignment.paytm.module.fragments.viewmodels.MainViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.layout_actionbar.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    internal lateinit var mViewModelFactory: ViewModelProvider.Factory

    private lateinit var mRecycleViewAdapter: RecycleViewAdadpter
    private var mUserList = ArrayList<PopularItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewModel()
        initAdapter()
        initView()
        setUpSearchView()
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun initView() {
        rv_swipe_refresh_users.setOnRefreshListener {
            rv_swipe_refresh_users.isRefreshing = false
            downloadData(true,city_searchView.query.toString())
        }
        iv_back.setOnClickListener { activity?.onBackPressed() }
        city_searchView.setQuery(Constants.DEFAULT_CITY,true)
        downloadData(true,city_searchView.query.toString())
    }

    private fun initAdapter() {
        when (::mRecycleViewAdapter.isInitialized) {
            false -> {
                mRecycleViewAdapter = RecycleViewAdadpter(pPopularEventList = mUserList)
                rv_users.layoutManager = LinearLayoutManager(context)
                rv_users.adapter = mRecycleViewAdapter
                mRecycleViewAdapter.notifyDataSetChanged()
            }
            true -> mRecycleViewAdapter.notifyDataSetChanged()
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this, mViewModelFactory).get(MainViewModel::class.java)
        viewModel.getUsersList().observe(activity!!, Observer {
            if(it!=null) {
                mUserList.clear()
                mUserList.addAll(it)
                setResetView()
                initAdapter()
            }
        })
    }

    private fun setResetView() {
        when (mUserList.isNotEmpty()) {
            true -> {
                rv_users.visibility = View.VISIBLE
                tv_no_item.visibility = View.GONE
            }
            false -> {
                rv_users.visibility = View.GONE
                tv_no_item.visibility = View.VISIBLE
            }
        }
    }

    private fun downloadData(pShowProgress: Boolean = false,pCity:String) {
        viewModel.downloadUserList(
            pCity = pCity,
            pShowProgress=pShowProgress
        )
    }

    private fun setUpSearchView(){
        city_searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                when (city_searchView.query.toString().isEmpty()) {
                    true -> {
                        setResetView()
                    }
                    false -> {
                        downloadData(false,city_searchView.query.toString())
                    }
                }
                return true
            }
        })
        city_searchView.setOnCloseListener {
            mUserList.clear()
            mRecycleViewAdapter.notifyDataSetChanged()
            false
        }
    }
}
