package ru.startandroid.domain.models.weathermodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Forecast(@SerializedName("forecastday")
                    @Expose
                    var forecastday: List<Forecastday>? = null)
