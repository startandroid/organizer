package ru.startandroid.widgets.testwidget1.update

import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestWidget1Refresh @Inject constructor() : WidgetRefresh {

    override fun refreshData(currentWidgetData: WidgetData, widgetConfig: WidgetConfig): WidgetData? {
        TimeUnit.SECONDS.sleep(1)
        return TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

}