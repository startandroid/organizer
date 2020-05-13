package ru.startandroid.organizer.exchange.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ExchangeRateApi {
    @GET("getrate/{date}/{from}/{to}")
    fun getRate(@Path("date") date: String,
                @Path("from") currencyFrom: String,
                @Path("to") currencyTo: String
    ): Call<String>
}