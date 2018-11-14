package ru.startandroid.organizer.home.widget.widgets

import android.view.View
import android.widget.TextView
import ru.startandroid.organizer.R
import javax.inject.Inject


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