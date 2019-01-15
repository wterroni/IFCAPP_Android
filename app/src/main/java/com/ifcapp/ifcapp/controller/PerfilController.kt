package com.ifcapp.ifcapp.controller

import android.util.Log
import com.ifcapp.ifcapp.api.APICepService
import com.ifcapp.ifcapp.models.Cep
import com.ifcapp.ifcapp.network.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PerfilController(private val listener: CepListener) {


    fun getCEP(cep: String) {
        val callCEPByCEP = RetrofitInitializer(APICepService.BASE_URL).cepService()
                .getEnderecoByCEP(cep)

        callCEPByCEP.enqueue(object: Callback<Cep> {
            override fun onResponse(call: Call<Cep>?,
                                    response: Response<Cep>) {
                listener.onCepAvailable(response.body() as Cep)
            }

            override fun onFailure(call: Call<Cep>?,
                                   t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }
}

interface CepListener {

    fun onCepAvailable(cep: Cep)

}