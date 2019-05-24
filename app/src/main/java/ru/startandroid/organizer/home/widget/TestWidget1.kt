package ru.startandroid.organizer.home.widget

import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.organizer.R
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.WidgetSettings
import ru.startandroid.widgets.adapter.container.BaseWidgetContent
import ru.startandroid.widgets.adapter.container.WidgetContent
import ru.startandroid.widgets.db.WidgetDbUpdater
import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import ru.startandroid.widgets.refresh.WidgetRefresher
import ru.startandroid.widgets.registrator.WidgetRegistratorImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass


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
        setContainerData(
                id = TEST_WIDGET_1,
                title = "Test widget 1",
                refreshButtonIsVisible = true,
                settingsButtonIsVisible = true,
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
    override fun initRecord(): WidgetDataEntityDb {
        val wdata = TestWidget1Data("test")
        return WidgetDataEntityDb(TEST_WIDGET_1, gson.toJson(wdata))
    }
}

class TestWidget1RegisterData @Inject constructor(
        val widgetContentProvider: Provider<TestWidget1Content>,
        val widgetRefresherProvider: Provider<TestWidget1Refresher>,
        val widetInitProvider: Provider<TestWidget1Init>
) : WidgetRegistratorImpl.RegisterData {

    override fun id(): Int = TEST_WIDGET_1
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget1Data::class
    override fun widgetSettingsCls(): KClass<out WidgetSettings> = TestWidget1Settings::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetRefresher> = widgetRefresherProvider
    override fun widgetInit(): Provider<out WidgetInit> = widetInitProvider

}
