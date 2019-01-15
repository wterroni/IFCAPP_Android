package com.ifcapp.ifcapp.network

import com.ifcapp.ifcapp.api.APICepService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer(url : String) {
    private val retrofit = Retrofit.Builder()
            .baseUrl(APICepService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun cepService() = retrofit.create(APICepService::class.java)
}
