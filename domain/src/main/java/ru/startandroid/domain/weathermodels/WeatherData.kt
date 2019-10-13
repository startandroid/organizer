package ru.startandroid.domain.models.weathermodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherData(@SerializedName("location")
                       @Expose
                       var location: Location? = null,
                       @SerializedName("current")
                       @Expose
                       var current: Current? = null,
                       @SerializedName("forecast")
                       @Expose
                       var forecast: Forecast? = null)

