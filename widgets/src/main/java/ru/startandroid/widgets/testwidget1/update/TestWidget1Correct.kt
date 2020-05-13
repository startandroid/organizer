package ru.startandroid.widgets.testwidget1.update

import android.util.Log
import ru.startandroid.widgets.testwidget1.config.TestWidget1Config
import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgetsbase.data.db.correct.WidgetCorrect
import ru.startandroid.widgetsbase.data.db.refresh.WidgetRefresh
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TestWidget1Correct @Inject constructor() : WidgetCorrect {

    override fun correctDataAccordingToConfig(data: WidgetData, config: WidgetConfig): WidgetData {
        return TestWidget1Data("test")
    }

}