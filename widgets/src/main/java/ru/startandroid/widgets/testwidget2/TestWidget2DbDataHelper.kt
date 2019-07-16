package ru.startandroid.widgets.testwidget2

import android.util.Log
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class TestWidget2DbDataHelper @Inject constructor() : WidgetDbDataHelper {
    override fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData {
        return ru.startandroid.widgets.testwidget2.TestWidget2Data("test1", text2 = "test2")
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
            return ru.startandroid.widgets.testwidget2.TestWidget2Data(text1 = "test1", text2 = strFileContents)
        } finally {
            urlConnection.disconnect()
        }
    }

    override fun initConfig(): WidgetConfig? {
        return ru.startandroid.widgets.testwidget2.TestWidget2Config(true, false)
    }

}