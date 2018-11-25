package ru.startandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Forecast {

    @SerializedName("forecastday")
    @Expose
    var forecastday: List<Forecastday>? = null

}
