package ru.startandroid.widgets.testwidget2.update

import android.util.Log
import ru.startandroid.widgets.testwidget2.content.TestWidget2Data
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.io.BufferedInputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class TestWidget2Refresh @Inject constructor() : WidgetRefresh {

    override fun refreshData(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData? {

        val url = URL("https://worldtimeapi.org/api/ip.txt")
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
            return TestWidget2Data(text1 = "test1", text2 = strFileContents)
        } catch(e: Exception) {
            return TestWidget2Data(text1 = "test1", text2 = e.localizedMessage)
        } finally {
            urlConnection.disconnect()
        }
    }

}