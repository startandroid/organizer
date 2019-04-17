package ru.startandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Forecastday(@SerializedName("date")
                  @Expose
                  var date: String? = null,
                  @SerializedName("date_epoch")
                  @Expose
                  var dateEpoch: Int? = null,
                  @SerializedName("day")
                  @Expose
                  var day: Day? = null,
                  @SerializedName("astro")
                  @Expose
                  var astro: Astro? = null)