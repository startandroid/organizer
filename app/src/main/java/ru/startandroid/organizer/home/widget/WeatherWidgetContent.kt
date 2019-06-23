package ru.startandroid.organizer.home.widget

import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import javax.inject.Inject

class WeatherWidgetContent @Inject constructor() : BaseWidgetContent<WeatherWidgetData>() {


    override fun getLayoutId(): Int = R.layout.widget_weather


    override fun onDataSet(widgetData: WeatherWidgetData) {
        tvTime.text = widgetData.time
        tvTempMain.text = widgetData.tempMain + "\u2103"
        tvTemp1.text = widgetData.temp1 + "\u2103"
        tvTemp2.text = widgetData.temp2 + "\u2103"
        tvTemp3.text = widgetData.temp3 + "\u2103"
        tvDay1.text = widgetData.day1
        tvDay2.text = widgetData.day2
        tvDay3.text = widgetData.day3

        setContainerData(
                id = WIDGETS_IDS.WEATHER_WIDGET,
                title = "Weather widget",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}