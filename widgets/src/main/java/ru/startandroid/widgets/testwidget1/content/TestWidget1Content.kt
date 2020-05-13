package ru.startandroid.organizer.home.widget

import kotlinx.android.synthetic.main.widget_test1.*
import ru.startandroid.widgets.R
import ru.startandroid.widgets.testwidget1.content.TestWidget1Data
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget1Content @Inject constructor() : BaseWidgetContent<TestWidget1Data>() {

    override fun getLayoutId(): Int = R.layout.widget_test1

    override fun onDataSet(widgetData: TestWidget1Data) {
        text.text = widgetData.text
    }
}