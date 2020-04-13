package ru.startandroid.widgets.testwidget2.update

import ru.startandroid.widgets.testwidget2.content.TestWidget2Data
import ru.startandroid.widgetsbase.data.db.correct.WidgetCorrect
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import javax.inject.Inject

class TestWidget2Correct @Inject constructor() : WidgetCorrect {

    override fun correctDataAccordingToConfig(data: WidgetData, config: WidgetConfig): WidgetData {
        return TestWidget2Data("test1", text2 = "test2")
    }

}