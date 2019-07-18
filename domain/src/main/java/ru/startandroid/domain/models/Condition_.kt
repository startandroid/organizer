package ru.startandroid.domain.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Condition_(@SerializedName("text")
                      @Expose
                      var text: String? = null,
                      @SerializedName("icon")
                      @Expose
                      var icon: String? = null,
                      @SerializedName("code")
                      @Expose
                      var code: Int? = null)