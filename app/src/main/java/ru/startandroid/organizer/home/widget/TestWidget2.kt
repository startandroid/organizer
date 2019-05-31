package ru.startandroid.organizer.home.widget

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.widget_test2.*
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_2
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
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class TestWidget2Data(
        val text1: String,
        val text2: String
) : WidgetData

data class TestWidget2Config(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetConfig


class TestWidget2Content @Inject constructor() : BaseWidgetContent<TestWidget2Data>() {


    override fun getLayoutId(): Int = R.layout.widget_test2


    override fun onDataSet(widgetData: TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(
                id = TEST_WIDGET_2,
                title = "Test widget 21",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}

class TestWidget2Refresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater) : WidgetRefresher {
    @SuppressLint("CheckResult")
    override fun refresh() {
        Log.d("qweee", "refresh widget 2")
        widgetDbUpdater.getAndUpdate(TEST_WIDGET_2) { entity ->

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

        }.subscribe { Log.d("qweee", "refresh widget 2 done") }


    }
}

class TestWidget2Init @Inject constructor(val gson: Gson) : WidgetInit {
    override fun initData(): WidgetDataEntityDb {
        val data = TestWidget2Data("test1", text2 = "test2")
        return WidgetDataEntityDb(TEST_WIDGET_2, gson.toJson(data))
    }

    override fun initConfig(): WidgetConfigEntityDb {
        val config = TestWidget2Config(true, false)
        return WidgetConfigEntityDb(TEST_WIDGET_2, gson.toJson(config), true)
    }

}

class TestWidget2RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2Refresher>,
        val widetInitProvider: Provider<TestWidget2Init>

) : WidgetRegistratorImpl.RegisterData {
    override fun id(): Int = TEST_WIDGET_2
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget2Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget2Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widetInitProvider
}