package com.ifcapp.ifcapp.api

import com.ifcapp.ifcapp.models.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APICepService {
    companion object {
        val BASE_URL = "https://viacep.com.br/ws/"
    }

    /* Retorna uma lista de objetos CEP */
    @GET("{estado}/{cidade}/{endereco}/json/")
    abstract fun getCEPByCidadeEstadoEndereco(@Path("estado") estado: String,
                                              @Path("cidade") cidade: String,
                                              @Path("endereco") endereco: String): Call<List<Cep>>

    /* Retorna apenas um objeto CEP */
    @GET("{CEP}/json/")
    abstract fun getEnderecoByCEP(@Path("CEP") CEP: String): Call<Cep>
}