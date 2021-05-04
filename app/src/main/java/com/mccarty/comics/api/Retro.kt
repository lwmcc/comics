package com.mccarty.comics.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Retro {
    fun getRetroFit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient().newBuilder().build())
            .build()
    }
}