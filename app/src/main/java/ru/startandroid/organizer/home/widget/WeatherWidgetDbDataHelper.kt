package ru.startandroid.organizer.home.widget

import ru.startandroid.data.network.WeatherAPI
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherWidgetDbDataHelper @Inject constructor() : WidgetDbDataHelper {

    override fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData {
        return WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
    }

    override fun refreshData(config: WidgetConfig?): WidgetData? {
        val api: WeatherAPI = WeatherAPI.create()

        val response = api.getCityWeather("Moscow", "3").execute()
        val weatherData = response.body()

        return weatherData?.forecast?.forecastday?.let {
            WeatherWidgetData(time = "${(SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()))}",
                    tempMain = weatherData.current?.tempC?.toInt().toString()
                    , temp1 = it[0]?.day?.avgtempC?.toInt().toString()
                    , temp2 = it[1]?.day?.avgtempC?.toInt().toString()
                    , temp3 = it[2]?.day?.avgtempC?.toInt().toString()
                    , day1 = it[0]?.date.toString()
                    , day2 = it[1]?.date.toString()
                    , day3 = it[2]?.date.toString())
        }

    }

    override fun initConfig(): WidgetConfig? {
        return WeatherWidgetConfig(listOf(City(1, "City 1"), City(2, "City 2")))
    }
}