package ru.startandroid.widgets.testwidget1

import android.util.Log
import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import javax.inject.Inject

class TestWidget1DbDataHelper @Inject constructor() : WidgetDbDataHelper {

    override fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData {
        Log.d("qweee", "widget1, correct $data $config")
        return ru.startandroid.widgets.testwidget1.TestWidget1Data("test")
    }

    override fun refreshData(config: WidgetConfigEntity?): WidgetData? {
        Log.d("qweee", "widget1, refresh $config")
        return ru.startandroid.widgets.testwidget1.TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

    override fun initConfig(): WidgetConfig? {
        Log.d("qweee", "widget1, init")
        return TestWidget1Config(true, "text1", listOf("one", "two", "three"))
    }

}