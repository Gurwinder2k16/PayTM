package com.assignment.paytm.network.networkapi

import com.assignment.paytm.model.response.CityResponse
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("home")
    fun downloadData(
        @Query("norm") pNorm: String? = "",
        @Query("filterBy") pFilterBy: String? = "",
        @Query("city") pCity: String? = ""
    ): Observable<Response<JsonObject>>
}