package ru.startandroid.organizer.home.widget

import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget1Content @Inject constructor() : BaseWidgetContent<TestWidget1Data>() {

    override fun getLayoutId(): Int = R.layout.widget_test1

    override fun onDataSet(widgetData: TestWidget1Data) {
        text.text = widgetData.text
        setContainerData(
                id = WIDGETS_IDS.TEST_WIDGET_1,
                title = "Test widget 1",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}