package ru.startandroid.organizer.home.widget

import android.util.Log
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import javax.inject.Inject

class TestWidget1DbDataHelper @Inject constructor() : WidgetDbDataHelper {

    override fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData {
        Log.d("qweee", "widget1, correct $data $config")
        return TestWidget1Data("test")
    }

    override fun refreshData(config: WidgetConfig?): WidgetData? {
        Log.d("qweee", "widget1, refresh $config")
        return TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

    override fun initConfig(): WidgetConfig? {
        Log.d("qweee", "widget1, init")
        return TestWidget1Config(true)
    }

}