package ru.startandroid.widgets.weatherwidget.update


import ru.startandroid.data.network.WeatherAPI
import ru.startandroid.widgets.weatherwidget.content.WeatherWidgetData
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherWidgetRefresh @Inject constructor(private val api: WeatherAPI) : WidgetRefresh {


    override fun refreshData(config: WidgetConfigEntity): WidgetData? {

        val response = api.getCityWeather("Moscow", "3").execute()
        val weatherData = response.body()

        return weatherData?.forecast?.forecastday?.let {
            WeatherWidgetData(time = "${(SimpleDateFormat("HH:mm").format(Calendar.getInstance().time))}",
                    tempMain = weatherData.current?.tempC?.toInt().toString()
                    , temp1 = it[0].day?.avgtempC?.toInt().toString()
                    , temp2 = it[1].day?.avgtempC?.toInt().toString()
                    , temp3 = it[2].day?.avgtempC?.toInt().toString()
                    , day1 = it[0].date.toString()
                    , day2 = it[1].date.toString()
                    , day3 = it[2].date.toString())
        }

    }

}