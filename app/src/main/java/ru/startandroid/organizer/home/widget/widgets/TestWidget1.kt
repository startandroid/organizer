package ru.startandroid.organizer.home.widget.widgets

import android.view.View
import android.widget.TextView
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetSettings
import ru.startandroid.organizer.home.widget.common.adapter.BaseWidgetContent
import ru.startandroid.organizer.home.widget.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.common.registrator.WidgetRegistratorImpl
import javax.inject.Inject
import javax.inject.Provider


// TODO create a few big widgets to check if list scroll works well and widgets are being created correctly

data class TestWidget1Data(
        val text: String
) : WidgetData

data class TestWidget1Settings(
        val flag: Boolean
) : WidgetSettings

class TestWidget1Content @Inject constructor() : BaseWidgetContent<TestWidget1Data>() {

    lateinit var text: TextView

    override fun getLayoutId(): Int = R.layout.widget_test1

    override fun onViewInflated(widgetView: View) {
        text = widgetView.findViewById(R.id.text)
    }

    override fun onDataSet(widgetData: TestWidget1Data) {
        text.text = widgetData.text
        setHeader("Test widget 1", closeButtonIsVisible = true)
    }
}

class TestWidget1RegisterData @Inject constructor(val widgetContentProvider: Provider<TestWidget1Content>): WidgetRegistratorImpl.RegisterData {
    override fun id(): Int  = TEST_WIDGET_1
    override fun widgetDataCls(): Class<out WidgetData> = TestWidget1Data::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = TestWidget1Settings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
}