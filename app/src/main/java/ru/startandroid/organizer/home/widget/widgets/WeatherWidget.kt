package ru.startandroid.organizer.home.widget.widgets

import android.util.Log
import android.view.View
import android.widget.TextView
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
import javax.inject.Inject
import javax.inject.Provider




data class WeatherWidgetData(
        val text1: String,
        val text2: String
) : WidgetData

data class WeatherWidgetSettings(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetSettings


class WeatherWidgetContent @Inject constructor() : BaseWidgetContent<WeatherWidgetData>() {

    lateinit var text1: TextView
    lateinit var text2: TextView

    override fun getLayoutId(): Int = R.layout.widget_test2

    override fun onViewInflated(widgetView: View) {
        text1 = widgetView.findViewById(R.id.text1)
        text2 = widgetView.findViewById(R.id.text2)
    }

    override fun onDataSet(widgetData: WeatherWidgetData) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(
                id = WIDGETS_IDS.TEST_WIDGET_3,
                title = "Weather widget",
                refreshButtonIsVisible = true,
                settingsButtonIsVisible = true,
                closeButtonIsVisible = true)
    }

}

class WeatherWidgetRefresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater): WidgetRefresher {
    override fun refresh() {
        Log.d("qweee", "refresh widget 2")
        widgetDbUpdater.getAndUpdate(WIDGETS_IDS.TEST_WIDGET_2) { entity ->


            entity?.let {

                val url = URL("http://worldtimeapi.org/api/ip.txt")
                val urlConnection = url.openConnection() as HttpURLConnection
                try {
                    val instream = BufferedInputStream(urlConnection.getInputStream())

                    val contents = ByteArray(1024)

                    var bytesRead = 0
                    var strFileContents: String = ""
                    bytesRead = instream.read(contents)
                    while ((bytesRead) != -1) {
                        strFileContents += String(contents, 0, bytesRead)
                        bytesRead = instream.read(contents)
                    }
                    Log.d("qweee", "refresh widget 2 $strFileContents")
                    var data = it.data as WeatherWidgetData
                    data = data.copy(text2 = strFileContents)
                    WidgetDataEntity(it.id, data)
                } finally {
                    urlConnection.disconnect()
                }
            }

        }.subscribe{ Log.d("qweee", "refresh widget 2 done")}


    }
}

class WeatherWidgetRegisterData @Inject constructor(
        val widgetContentProvider: Provider<WeatherWidgetContent>,
        val widgetRefresherProvider: Provider<WeatherWidgetRefresher>

): WidgetRegistratorImpl.RegisterData {
    override fun id(): Int  = WIDGETS_IDS.TEST_WIDGET_3
    override fun widgetDataCls(): Class<out WidgetData> = WeatherWidgetData::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = WeatherWidgetSettings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
}