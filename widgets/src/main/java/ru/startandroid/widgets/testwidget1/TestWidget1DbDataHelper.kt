package ru.startandroid.widgets.testwidget1

import android.util.Log
import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestWidget1DbDataHelper @Inject constructor() : WidgetDbDataHelper {

    override fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData {
        Log.d("qweee", "widget1, correct $data $config")
        return ru.startandroid.widgets.testwidget1.TestWidget1Data("test")
    }

    override fun refreshData(config: WidgetConfigEntity?): WidgetData? {
        Log.d("qweee", "widget1, refresh $config")
        TimeUnit.SECONDS.sleep(3)
        return ru.startandroid.widgets.testwidget1.TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

    override fun getInitConfig(): WidgetConfigEntity? {
        Log.d("qweee", "widget1, init")
        return WidgetConfigEntity(
                id = ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_1,
                config = TestWidget1Config(true, "text1", listOf("one", "two", "three")),
                updateInterval = 0,
                enabled = true)
    }

}