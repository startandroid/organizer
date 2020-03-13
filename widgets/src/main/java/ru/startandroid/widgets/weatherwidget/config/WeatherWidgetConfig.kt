package ru.startandroid.widgets.weatherwidget.config

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.startandroid.widgetsbase.domain.model.WidgetConfig

@Parcelize
data class WeatherWidgetConfig(
        val cities: List<City> = emptyList()
) : WidgetConfig

@Parcelize
data class City(val id: Int, val name: String) : Parcelable

