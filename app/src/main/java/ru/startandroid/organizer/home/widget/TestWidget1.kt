package ru.startandroid.organizer.home.widget

import android.util.Log
import kotlinx.android.synthetic.main.widget_test1.*
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.adapter.content.BaseWidgetContent
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.refresh.WidgetRefresher
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class TestWidget1Data(
        val text: String
) : WidgetData

data class TestWidget1Config(
        val flag: Boolean
) : WidgetConfig

class TestWidget1Content @Inject constructor() : BaseWidgetContent<TestWidget1Data>() {

    override fun getLayoutId(): Int = R.layout.widget_test1

    override fun onDataSet(widgetData: TestWidget1Data) {
        text.text = widgetData.text
        setContainerData(
                id = TEST_WIDGET_1,
                title = "Test widget 1",
                refreshButtonIsVisible = true,
                configButtonIsVisible = true,
                closeButtonIsVisible = true)
    }
}

class TestWidget1Refresher @Inject constructor() : WidgetRefresher {

    override fun correctDataAccordingToConfig(data: WidgetData?, config: WidgetConfig?): WidgetData {
        Log.d("qweee", "widget1, correct $data $config")
        return TestWidget1Data("test")
    }

    override fun refreshData(config: WidgetConfig?): WidgetData? {
        Log.d("qweee", "widget1, refresh $config")
        return TestWidget1Data("Time is ${System.currentTimeMillis()}")
    }

    override fun initConfig(): WidgetConfig? {
        Log.d("qweee", "widget1, init")
        return TestWidget1Config(true)
    }

}

class TestWidget1RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget1Content>,
        val widgetRefresherProvider: Provider<TestWidget1Refresher>
) : WidgetRegistratorImpl.RegisterData {

    override fun id(): Int = TEST_WIDGET_1
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget1Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget1Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider

}
