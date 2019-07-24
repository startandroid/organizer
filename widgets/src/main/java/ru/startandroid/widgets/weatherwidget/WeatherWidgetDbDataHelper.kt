package ru.startandroid.widgets.weatherwidget


import ru.startandroid.data.network.WeatherAPI
import ru.startandroid.widgetsbase.data.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherWidgetDbDataHelper @Inject constructor() : WidgetDbDataHelper {

    override fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData {
        return WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
    }

    override fun refreshData(config: WidgetConfigEntity?): WidgetData? {
        val api: WeatherAPI = WeatherAPI.create()

        val response = api.getCityWeather("Moscow", "3").execute()
        val weatherData = response.body()

        return weatherData?.forecast?.forecastday?.let {
            ru.startandroid.widgets.weatherwidget.WeatherWidgetData(time = "${(SimpleDateFormat("HH:mm").format(Calendar.getInstance().time))}",
                    tempMain = weatherData.current?.tempC?.toInt().toString()
                    , temp1 = it[0].day?.avgtempC?.toInt().toString()
                    , temp2 = it[1].day?.avgtempC?.toInt().toString()
                    , temp3 = it[2].day?.avgtempC?.toInt().toString()
                    , day1 = it[0].date.toString()
                    , day2 = it[1].date.toString()
                    , day3 = it[2].date.toString())
        }

    }

    override fun initConfig(): WidgetConfig? {
        return ru.startandroid.widgets.weatherwidget.WeatherWidgetConfig(listOf(ru.startandroid.widgets.weatherwidget.City(1, "City 1"), ru.startandroid.widgets.weatherwidget.City(2, "City 2")))
    }
}