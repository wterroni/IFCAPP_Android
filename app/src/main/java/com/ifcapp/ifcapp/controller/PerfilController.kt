package com.ifcapp.ifcapp.controller

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.ifcapp.ifcapp.api.APICepService
import com.ifcapp.ifcapp.models.Cep
import com.ifcapp.ifcapp.network.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilController {

    fun getCEP(context : Context) {
        val callCEPByCEP = RetrofitInitializer(APICepService.BASE_URL).cepService().getEnderecoByCEP("09280450")
        callCEPByCEP.enqueue(object: Callback<Cep> {
            override fun onResponse(call: Call<Cep>?,
                                    response: Response<Cep>) {
                response?.body()?.let {
                    val cep: Cep = it
                    Toast.makeText(context, cep.logradouro, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Cep>?,
                                   t: Throwable?) {
                Log.e("onFailure error", t?.message)
                Toast.makeText(context, "erro", Toast.LENGTH_SHORT).show()
            }
        })
    }
}