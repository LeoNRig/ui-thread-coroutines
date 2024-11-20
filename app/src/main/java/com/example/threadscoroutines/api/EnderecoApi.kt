package com.example.threadscoroutines.api

import com.example.threadscoroutines.model.Endereco
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EnderecoApi {

    @GET("ws/{cep}/json/")
    suspend fun recuperarEndereco(
        @Path("cep") cepDigitado: String

    ) : Response<Endereco>

}