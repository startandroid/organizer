package ru.startandroid.organizer.home.widget

import android.util.Log
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.widget_test1.*
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.widgets.WidgetConfig
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.adapter.content.BaseWidgetContent
import ru.startandroid.widgets.adapter.content.WidgetContent
import ru.startandroid.widgets.db.WidgetDbUpdater
import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
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

class TestWidget1Refresher @Inject constructor(val widgetDbUpdater: WidgetDbUpdater) : WidgetRefresher {
    override fun refresh() {
        Log.d("qweee", "refresh widget 1")

        widgetDbUpdater.getAndUpdate(TEST_WIDGET_1, scheduler = AndroidSchedulers.mainThread()) { entity ->
            Log.d("qweee", "widget1 func")
            entity?.let {
                var data = it.data as TestWidget1Data
                data = data.copy(text = "Time is ${System.currentTimeMillis()}")
                WidgetDataEntity(it.id, data)
            }

        }.subscribe()
    }
}

class TestWidget1Init @Inject constructor(val gson: Gson) : WidgetInit {
    override fun initData(): WidgetDataEntityDb {
        val data = TestWidget1Data("test")
        return WidgetDataEntityDb(TEST_WIDGET_1, gson.toJson(data))
    }

    override fun initConfig(): WidgetConfigEntityDb {
        val config = TestWidget1Config(true)
        return WidgetConfigEntityDb(TEST_WIDGET_1, gson.toJson(config), true)
    }

}

class TestWidget1RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget1Content>,
        val widgetRefresherProvider: Provider<TestWidget1Refresher>,
        val widetInitProvider: Provider<TestWidget1Init>
) : WidgetRegistratorImpl.RegisterData {

    override fun id(): Int = TEST_WIDGET_1
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget1Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget1Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widetInitProvider

}
