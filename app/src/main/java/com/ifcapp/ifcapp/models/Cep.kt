package com.ifcapp.ifcapp.models

import java.io.Serializable

class Cep : Serializable {

    lateinit var cep : String
    lateinit var logradouro : String
    lateinit var complemento : String
    lateinit var bairro : String
    lateinit var localidade : String
    lateinit var uf : String
    lateinit var unidade : String
    lateinit var ibge : String
    lateinit var gia : String
    var erro : String = ""
}