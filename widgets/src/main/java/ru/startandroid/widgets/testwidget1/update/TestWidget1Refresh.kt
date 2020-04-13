package ru.startandroid.widgets.testwidget1.update

import android.util.Log
import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestWidget1Refresh @Inject constructor() : WidgetRefresh {

    override fun refreshData(config: WidgetConfigEntity): WidgetData? {
        Log.d("qweee", "widget1, refresh $config")
        TimeUnit.SECONDS.sleep(3)
        return TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

}