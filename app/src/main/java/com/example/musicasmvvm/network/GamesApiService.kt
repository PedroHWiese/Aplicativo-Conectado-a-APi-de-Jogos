package com.example.musicasmvvm.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://www.freetogame.com/api/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface GamesApiService {
    @GET("games")
    suspend fun getGames(): String
}

//inicialização do objeto feito em singleton de modo lento
object GamesApi {
    val retrofitService : GamesApiService by lazy {
        retrofit.create(GamesApiService::class.java)
    }
}