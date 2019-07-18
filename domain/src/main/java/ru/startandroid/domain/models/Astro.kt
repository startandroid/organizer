package ru.startandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Astro(@SerializedName("sunrise")
                 @Expose
                 var sunrise: String? = null, @SerializedName("sunset")
                 @Expose
                 var sunset: String? = null, @SerializedName("moonrise")
                 @Expose
                 var moonrise: String? = null, @SerializedName("moonset")
                 @Expose
                 var moonset: String? = null)