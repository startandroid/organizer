package ru.startandroid.widgets.testwidget1.update

import android.util.Log
import ru.startandroid.widgets.testwidget1.config.TestWidget1Config
import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestWidget1Refresh @Inject constructor() : WidgetRefresh {



//    override fun correctDataAccordingToConfig(data: WidgetDataEntity?, config: WidgetConfigEntity?): WidgetData {
//        Log.d("qweee", "widget1, correct $data $config")
//        return TestWidget1Data("test")
//    }
//
//    override fun getInitConfig(): WidgetConfigEntity? {
//        Log.d("qweee", "widget1, init")
//        return WidgetConfigEntity(
//                id = ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_1,
//                config = TestWidget1Config(true, "text1", listOf("one", "two", "three")),
//                updateInterval = 0,
//                enabled = true)
//    }

    override fun refreshData(config: WidgetConfigEntity): WidgetData {
        Log.d("qweee", "widget1, refresh $config")
        TimeUnit.SECONDS.sleep(3)
        return TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

}