package ru.startandroid.organizer.home.widget

import kotlinx.android.synthetic.main.widget_test1.*
import ru.startandroid.widgets.R
import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget1Content @Inject constructor() : BaseWidgetContent<ru.startandroid.widgets.testwidget1.TestWidget1Data>() {

    override fun getLayoutId(): Int = R.layout.widget_test1

    override fun onDataSet(widgetData: ru.startandroid.widgets.testwidget1.TestWidget1Data) {
        text.text = widgetData.text
        setContainerData(
                id = ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_1,
                title = "Test widget 1",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}