package ru.startandroid.organizer.home.widget

//import ru.startandroid.widgets.db.WidgetInit
import android.util.Log
import kotlinx.android.synthetic.main.widget_test2.*
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.registrator.WidgetMetadatRepositoryImpl
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

class TestWidget2DbDataHelper @Inject constructor() : WidgetDbDataHelper {
    override fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData {
        return TestWidget2Data("test1", text2 = "test2")
    }

    override fun refreshData(config: WidgetConfig?): WidgetData? {
        Log.d("qweee", "widget2, refresh $config")

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
            Log.d("qweee", "refreshData widget 2 $strFileContents")
            return TestWidget2Data(text1 = "test1", text2 = strFileContents)
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun initConfig(): WidgetConfig? {
        return TestWidget2Config(true, false)
    }

}

class TestWidget2WidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2DbDataHelper>

) : WidgetMetadatRepositoryImpl.WidgetMetadata {
    override fun id(): Int = TEST_WIDGET_2
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget2Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget2Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
}