package ru.startandroid.organizer.home.widget.widgets

import android.view.View
import android.widget.TextView
import ru.startandroid.organizer.R
import javax.inject.Inject


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
        setHeader("Test widget 21", true, true, true)
    }

    override fun onRefreshClick() {

    }


}

