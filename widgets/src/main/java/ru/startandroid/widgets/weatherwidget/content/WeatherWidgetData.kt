package ru.startandroid.widgets.weatherwidget.content

import ru.startandroid.widgetsbase.domain.model.WidgetData

data class WeatherWidgetData(
        val time: String,
        val tempMain: String,
        val temp1: String,
        val temp2: String,
        val temp3: String,
        val day1: String,
        val day2: String,
        val day3: String
) : WidgetData()