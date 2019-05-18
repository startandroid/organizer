package ru.startandroid.organizer.home.widget.widgets

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.TextView
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetSettings
import ru.startandroid.organizer.home.widget.common.adapter.BaseWidgetContent
import ru.startandroid.organizer.home.widget.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.common.registrator.WidgetRegistratorImpl
import ru.startandroid.organizer.home.widget.refresh.WidgetDbUpdater
import ru.startandroid.organizer.home.widget.refresh.WidgetRefresher
import javax.inject.Inject
import javax.inject.Provider
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import ru.startandroid.organizer.home.widget.common.WidgetDataEntity


data class TestWidget2Data(
        val text1: String,
        val text2: String
) : WidgetData

data class TestWidget2Settings(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetSettings


class TestWidget2Content @Inject constructor() : BaseWidgetContent<TestWidget2Data>() {

    lateinit var text1: TextView
    lateinit var text2: TextView

    override fun getLayoutId(): Int = R.layout.widget_test2

    override fun onViewInflated(widgetView: View) {
        text1 = widgetView.findViewById(R.id.text1)
        text2 = widgetView.findViewById(R.id.text2)
    }

    override fun onDataSet(widgetData: TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(
                id = WIDGETS_IDS.TEST_WIDGET_2,
                title = "Test widget 21",
                refreshButtonIsVisible = true,
                settingsButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}

class TestWidget2Refresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater): WidgetRefresher {
    @SuppressLint("CheckResult")
    override fun refresh() {
        Log.d("qweee", "refresh widget 2")
        widgetDbUpdater.getAndUpdate(TEST_WIDGET_2) {entity ->

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
                    var data = it.data as TestWidget2Data
                    data = data.copy(text2 = strFileContents)
                    WidgetDataEntity(it.id, data)
                } finally {
                    urlConnection.disconnect()
                }
            }

        }.subscribe{Log.d("qweee", "refresh widget 2 done")}


    }
}

class TestWidget2RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2Refresher>

): WidgetRegistratorImpl.RegisterData {
    override fun id(): Int  = TEST_WIDGET_2
    override fun widgetDataCls(): Class<out WidgetData> = TestWidget2Data::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = TestWidget2Settings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
}