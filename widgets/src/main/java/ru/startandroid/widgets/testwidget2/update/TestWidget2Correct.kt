package ru.startandroid.widgets.testwidget2.update

import android.util.Log
import ru.startandroid.widgets.testwidget2.config.TestWidget2Config
import ru.startandroid.widgets.testwidget2.content.TestWidget2Data
import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class TestWidget2DbDataHelper @Inject constructor() : WidgetDbDataHelper {
    override fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData {
        return TestWidget2Data("test1", text2 = "test2")
    }

    override fun refreshData(config: WidgetConfigEntity?): WidgetData? {
        Log.d("qweee", "widget2, refresh $config")

        val url = URL("http://worldtimeapi.org/api/ip.txt")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val instream = BufferedInputStream(urlConnection.inputStream)

            val contents = ByteArray(1024)

            var bytesRead = 0
            var strFileContents: String = ""
            bytesRead = instream.read(contents)
            while ((bytesRead) != -1) {
                strFileContents += String(contents, 0, bytesRead)
                bytesRead = instream.read(contents)
            }
            //Log.d("qweee", "refreshData widget 2 $strFileContents")
            return TestWidget2Data(text1 = "test1", text2 = strFileContents)
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun getInitConfig(): WidgetConfigEntity? {
        return WidgetConfigEntity(
                id = ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_2,
                config = TestWidget2Config(true, false),
                updateInterval = 0,
                enabled = true)
    }

}