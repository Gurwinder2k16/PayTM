package com.assignment.paytm.module.fragments.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.paytm.application.ApplicationShared
import com.assignment.paytm.constants.Constants.Companion.DEFAULT_FILTER
import com.assignment.paytm.constants.Constants.Companion.DEFAULT_NORM
import com.assignment.paytm.model.response.CityResponse
import com.assignment.paytm.model.response.PopularItem
import com.assignment.paytm.network.networkapi.NetworkApi
import com.assignment.paytm.network.networkutil.apiConfig.ApiClientConfig
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var mListOfUsers = MutableLiveData<ArrayList<PopularItem>>()

    fun getUsersList(): MutableLiveData<ArrayList<PopularItem>> = mListOfUsers

    fun downloadUserList(
        pNorm: String = DEFAULT_NORM,
        pCity: String,
        pFilterBy: String = DEFAULT_FILTER,
        pShowProgress: Boolean = true
    ) {
        if (ApplicationShared.mCurrentInstance?.isConnected()!!) {
            val mObservable =
                ApiClientConfig().getRetrofit().create(NetworkApi::class.java).downloadData(
                    pNorm = pNorm,
                    pCity = pCity,
                    pFilterBy = pFilterBy
                )
            when (pShowProgress) {
                true -> ApplicationShared.mCurrentInstance?.showProgress()
            }
            mObservable.subscribeOn(Schedulers.io()).let {
                it.observeOn(AndroidSchedulers.mainThread())
                it.subscribe(this::handleResponse, this::handleError)
            }
        }
    }

    private fun handleResponse(pResponse: Response<JsonObject>) {
        ApplicationShared.mCurrentInstance?.hideProgressDialog()
        val myType = object : TypeToken<ArrayList<PopularItem>>() {}.type
        if(pResponse.body()!=null && pResponse.body()?.has("popular")!!) {
            val popularItemObject = pResponse.body()?.getAsJsonArray("popular").toString()
            val popularItem = Gson().fromJson<ArrayList<PopularItem>>(popularItemObject, myType)
            mListOfUsers.postValue(popularItem)
        }else{
            mListOfUsers.postValue(ArrayList<PopularItem>())
        }
    }

    private fun handleError(error: Throwable) {
        ApplicationShared.mCurrentInstance?.hideProgressDialog()
        Log.e(MainViewModel::class.java.simpleName, error.localizedMessage!!)
        mListOfUsers.postValue(ArrayList<PopularItem>())
    }
}
