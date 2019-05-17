package ru.startandroid.organizer.home.widget

import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetSettings
import ru.startandroid.widgets.adapter.container.BaseWidgetContent
import ru.startandroid.widgets.adapter.container.WidgetContent
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import ru.startandroid.widgets.db.WidgetDbUpdater
import ru.startandroid.widgets.refresh.WidgetRefresher
import javax.inject.Inject
import javax.inject.Provider
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import ru.startandroid.widgets.db.WidgetInit

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
                id = TEST_WIDGET_2,
                title = "Test widget 21",
                refreshButtonIsVisible = true,
                settingsButtonIsVisible = true,
                closeButtonIsVisible = true)
    }

}

class TestWidget2Refresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater): WidgetRefresher {
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

class TestWidget2Init @Inject constructor(val gson: Gson) : WidgetInit {
    override fun initRecord(): WidgetDataEntityDb {
        val wdata = TestWidget2Data("test1", text2 = "test2")
        return WidgetDataEntityDb(TEST_WIDGET_2, gson.toJson(wdata))
    }
}

class TestWidget2RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2Refresher>,
        val widetInitProvider: Provider<TestWidget2Init>

): WidgetRegistratorImpl.RegisterData {
    override fun id(): Int  = TEST_WIDGET_2
    override fun widgetDataCls(): Class<out WidgetData> = TestWidget2Data::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = TestWidget2Settings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widetInitProvider
}