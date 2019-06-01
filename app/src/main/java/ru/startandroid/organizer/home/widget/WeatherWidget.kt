package ru.startandroid.organizer.home.widget

//import ru.startandroid.widgets.db.WidgetInit
import kotlinx.android.synthetic.main.widget_weather.*
import ru.startandroid.data.network.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.adapter.content.BaseWidgetContent
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.refresh.WidgetRefresher
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class WeatherWidgetData(
        val time: String,
        val tempMain: String,
        val temp1: String,
        val temp2: String,
        val temp3: String,
        val day1: String,
        val day2: String,
        val day3: String
) : WidgetData

data class WeatherWidgetConfig(
        val cities: List<City> = emptyList()
) : WidgetConfig

data class City(val id: Int, val name: String)


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
                id = WEATHER_WIDGET,
                title = "Weather widget",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}

class WeatherWidgetRefresher @Inject constructor() : WidgetRefresher {

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

class WeatherWidgetRegisterData @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetRefresher>

) : WidgetRegistratorImpl.RegisterData {
    override fun id(): Int = WEATHER_WIDGET
    override fun widgetDataCls(): KClass<out WidgetData> = WeatherWidgetData::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = WeatherWidgetConfig::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
}