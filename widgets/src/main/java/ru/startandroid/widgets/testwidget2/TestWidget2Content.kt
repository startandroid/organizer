package ru.startandroid.organizer.home.widget

import kotlinx.android.synthetic.main.widget_test2.*
import ru.startandroid.widgets.R


import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget2Content @Inject constructor() : BaseWidgetContent<ru.startandroid.widgets.testwidget2.TestWidget2Data>() {


    override fun getLayoutId(): Int = R.layout.widget_test2


    override fun onDataSet(widgetData: ru.startandroid.widgets.testwidget2.TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(
                id = ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_2,
                title = "Test widget 21",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}