package com.example.tpfinal

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class AdminArea(val ID: String, val LocalizedName: String)

interface AccuWeatherApi {
    @GET("locations/v1/adminareas/AR")
    suspend fun getAdminAreas(@Query("apikey") apiKey: String): List<AdminArea>
}

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://dataservice.accuweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: AccuWeatherApi by lazy {
        retrofit.create(AccuWeatherApi::class.java)
    }
}