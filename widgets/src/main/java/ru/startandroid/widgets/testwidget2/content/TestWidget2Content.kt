package ru.startandroid.organizer.home.widget

import kotlinx.android.synthetic.main.widget_test2.*
import ru.startandroid.widgets.R
import ru.startandroid.widgets.testwidget2.content.TestWidget2Data


import ru.startandroid.widgetsbase.ui.widgets.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget2Content @Inject constructor() : BaseWidgetContent<TestWidget2Data>() {

    override fun getLayoutId(): Int = R.layout.widget_test2

    override fun onDataSet(widgetData: TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
    }
}