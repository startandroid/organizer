package ru.startandroid.organizer.home.widget

import ru.startandroid.organizer.R
import ru.startandroid.widgetsbase.adapter.content.BaseWidgetContent
import javax.inject.Inject

class TestWidget2Content @Inject constructor() : BaseWidgetContent<TestWidget2Data>() {


    override fun getLayoutId(): Int = R.layout.widget_test2


    override fun onDataSet(widgetData: TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(
                id = WIDGETS_IDS.TEST_WIDGET_2,
                title = "Test widget 21",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}