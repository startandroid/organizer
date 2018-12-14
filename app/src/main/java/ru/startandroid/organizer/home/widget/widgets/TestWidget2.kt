package ru.startandroid.organizer.home.widget.widgets

import android.net.Uri
import android.view.View
import android.widget.TextView
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.common.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.organizer.home.widget.common.WidgetData
import ru.startandroid.organizer.home.widget.common.WidgetSettings
import ru.startandroid.organizer.home.widget.common.adapter.BaseWidgetContent
import ru.startandroid.organizer.home.widget.common.adapter.WidgetContent
import ru.startandroid.organizer.home.widget.common.registrator.WidgetRegistratorImpl
import javax.inject.Inject
import javax.inject.Provider


data class TestWidget2Data(
        val text1: String,
        val text2: String
) : WidgetData

data class TestWidget2Settings(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetSettings


class TestWidget2Content @Inject constructor() : BaseWidgetContent<TestWidget2Data>() {

    lateinit var text1: TextView
    lateinit var text2: TextView

    override fun getLayoutId(): Int = R.layout.widget_test2

    override fun onViewInflated(widgetView: View) {
        text1 = widgetView.findViewById(R.id.text1)
        text2 = widgetView.findViewById(R.id.text2)
    }

    override fun onDataSet(widgetData: TestWidget2Data) {
        text1.text = widgetData.text1
        text2.text = widgetData.text2
        setContainerData(title = "Test widget 21",
                uri = Uri.parse("app://organizer/settings/testwidget2"),
                refreshButtonIsVisible = true,
                settingsButtonIsVisible = true,
                closeButtonIsVisible = true)
    }

    override fun onRefreshClick() {

    }
}

class TestWidget2RegisterData @Inject constructor(val widgetContentProvider: Provider<TestWidget2Content>): WidgetRegistratorImpl.RegisterData {
    override fun id(): Int  = TEST_WIDGET_2
    override fun widgetDataCls(): Class<out WidgetData> = TestWidget2Data::class.java
    override fun widgetSettingsCls(): Class<out WidgetSettings> = TestWidget2Settings::class.java
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
}