package com.example.threadscoroutines.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper{
    companion object{
        val apiViacep = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}