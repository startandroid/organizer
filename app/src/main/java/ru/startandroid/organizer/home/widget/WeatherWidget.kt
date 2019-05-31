package ru.startandroid.organizer.home.widget

import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.widget_weather.*
import ru.startandroid.data.network.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.content.BaseWidgetContent
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.db.WidgetDbUpdater
import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
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

class WeatherWidgetRefresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater) : WidgetRefresher {

    var api: WeatherAPI = WeatherAPI.create()

    override fun refresh() {

        api.getCityWeather("Moscow", "3").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    widgetDbUpdater.getAndUpdate(WEATHER_WIDGET, scheduler = AndroidSchedulers.mainThread()) { entity ->
                        Log.d("qweee", "widget1 func")
                        entity?.let {
                            var data = it.data as WeatherWidgetData
                            var forecastList = result.forecast?.forecastday
                            if (forecastList != null) {
                                data = data.copy(time = "${(SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()))}", tempMain = result.current?.tempC?.toInt().toString()
                                        , temp1 = forecastList[0]?.day?.avgtempC?.toInt().toString()
                                        , temp2 = forecastList[1]?.day?.avgtempC?.toInt().toString()
                                        , temp3 = forecastList[2]?.day?.avgtempC?.toInt().toString()
                                        , day1 = forecastList[0]?.date.toString()
                                        , day2 = forecastList[1]?.date.toString()
                                        , day3 = forecastList[2]?.date.toString())
                            }
                            WidgetDataEntity(it.id, data)
                        }

                    }.subscribe()
                }, { error ->
                    error.printStackTrace()
                    Log.d("Result", "There are ${error.message.toString()} Java developers in Lagos")
                })
    }
}

class WeatherWidgetInit @Inject constructor(val gson: Gson) : WidgetInit {
    override fun initData(): WidgetDataEntityDb {
        val data = WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
        return WidgetDataEntityDb(WEATHER_WIDGET, gson.toJson(data))
    }

    override fun initConfig(): WidgetConfigEntityDb {
        val config = WeatherWidgetConfig(listOf(City(1, "City 1"), City(2, "City 2")))
        return WidgetConfigEntityDb(WEATHER_WIDGET, gson.toJson(config), true)
    }

}

class WeatherWidgetRegisterData @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetRefresher>,
        val widgetInitProvider: Provider<WeatherWidgetInit>

) : WidgetRegistratorImpl.RegisterData {
    override fun id(): Int = WEATHER_WIDGET
    override fun widgetDataCls(): KClass<out WidgetData> = WeatherWidgetData::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = WeatherWidgetConfig::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widgetInitProvider
}