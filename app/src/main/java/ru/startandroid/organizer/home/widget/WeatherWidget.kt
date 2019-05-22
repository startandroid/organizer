package ru.startandroid.organizer.home.widget.widgets

import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.startandroid.data.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.WEATHER_WIDGET
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.WidgetSettings
import ru.startandroid.widgets.adapter.container.BaseWidgetContent
import ru.startandroid.widgets.adapter.container.WidgetContent
import ru.startandroid.widgets.db.WidgetDbUpdater
import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import ru.startandroid.widgets.refresh.WidgetRefresher
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider


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

data class WeatherWidgetSettings(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetSettings


class WeatherWidgetContent @Inject constructor() : BaseWidgetContent<WeatherWidgetData>() {

    lateinit var tvTime: TextView
    lateinit var tvTempMain: TextView
    lateinit var tvTemp1: TextView
    lateinit var tvTemp2: TextView
    lateinit var tvTemp3: TextView
    lateinit var tvDay1: TextView
    lateinit var tvDay2: TextView
    lateinit var tvDay3: TextView


    override fun getLayoutId(): Int = R.layout.widget_weather

    override fun onViewInflated(widgetView: View) {
        tvTime = widgetView.findViewById(R.id.time_val)
        tvTempMain = widgetView.findViewById(R.id.current_temp)
        tvTemp1 = widgetView.findViewById(R.id.forecast_day1_val)
        tvTemp2 = widgetView.findViewById(R.id.forecast_day2_val)
        tvTemp3 = widgetView.findViewById(R.id.forecast_day3_val)
        tvDay1 = widgetView.findViewById(R.id.forecast_day1)
        tvDay2 = widgetView.findViewById(R.id.forecast_day2)
        tvDay3 = widgetView.findViewById(R.id.forecast_day3)
    }

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
                settingsButtonIsVisible = true,
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
    override fun initRecord(): WidgetDataEntityDb {
        val wdata = WeatherWidgetData("11:23", "25", "21", "19", "17", "day1", "day2", "day3")
        return WidgetDataEntityDb(WEATHER_WIDGET, gson.toJson(wdata))
    }
}

class WeatherWidgetRegisterData @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetRefresher>,
        val widgetInitProvider: Provider<WeatherWidgetInit>

) : WidgetRegistratorImpl.RegisterData {
    override fun id(): Int = WEATHER_WIDGET
    override fun widgetDataCls(): Class<out WidgetData> = WeatherWidgetData::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = WeatherWidgetSettings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widgetInitProvider
}