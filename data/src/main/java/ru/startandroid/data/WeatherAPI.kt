package ru.startandroid.data


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.startandroid.domain.models.WeatherData


interface WeatherAPI {

    @GET("q={city}&days=7")
    fun getCityWeather(@Query("city") city: String): Call<WeatherData>

}