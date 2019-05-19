package ru.startandroid.organizer.home.widget.widgets

import android.util.Log
import android.view.View
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.startandroid.data.WeatherAPI
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetDataEntity
import ru.startandroid.organizer.home.widget.common.WidgetSettings
import ru.startandroid.organizer.home.widget.common.adapter.BaseWidgetContent
import ru.startandroid.organizer.home.widget.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.common.registrator.WidgetRegistratorImpl
import ru.startandroid.organizer.home.widget.refresh.WidgetDbUpdater
import ru.startandroid.organizer.home.widget.refresh.WidgetRefresher
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
                id = WIDGETS_IDS.TEST_WIDGET_3,
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
                    widgetDbUpdater.getAndUpdate(WIDGETS_IDS.TEST_WIDGET_3, scheduler = AndroidSchedulers.mainThread()) { entity ->
                        Log.d("qweee", "widget1 func")
                        entity?.let {
                            var data = it.data as WeatherWidgetData
                            data = data.copy(time = "${(SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()))}", tempMain = result.current?.tempC?.toInt().toString()
                                    , temp1 = result.forecast?.forecastday?.get(0)?.day?.avgtempC?.toInt().toString()
                                    , temp2 = result.forecast?.forecastday?.get(1)?.day?.avgtempC?.toInt().toString()
                                    , temp3 = result.forecast?.forecastday?.get(2)?.day?.avgtempC?.toInt().toString()
                                    , day1 = result.forecast?.forecastday?.get(0)?.date.toString()
                                    , day2 = result.forecast?.forecastday?.get(1)?.date.toString()
                                    , day3 = result.forecast?.forecastday?.get(2)?.date.toString())
                            WidgetDataEntity(it.id, data)
                        }

                    }.subscribe()
                }, { error ->
                    error.printStackTrace()
                    Log.d("Result", "There are ${error.message.toString()} Java developers in Lagos")
                })
    }
}

class WeatherWidgetRegisterData @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetRefresher>

) : WidgetRegistratorImpl.RegisterData {
    override fun id(): Int = WIDGETS_IDS.TEST_WIDGET_3
    override fun widgetDataCls(): Class<out WidgetData> = WeatherWidgetData::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = WeatherWidgetSettings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
}