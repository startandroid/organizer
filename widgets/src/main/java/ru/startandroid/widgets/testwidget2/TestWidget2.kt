package ru.startandroid.widgets.testwidget2

import kotlinx.android.parcel.Parcelize
import ru.startandroid.organizer.home.widget.TestWidget2Content
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.config.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class TestWidget2Data(
        val text1: String,
        val text2: String
) : WidgetData

@Parcelize
data class TestWidget2Config(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetConfig


class TestWidget2WidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2DbDataHelper>

) : WidgetMetadata {
    override fun id(): Int = TEST_WIDGET_2
    override fun titleResId(): Int = R.string.test_widget_2_title
    override fun descriptionResId(): Int = R.string.test_widget_2_description
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget2Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget2Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
    override fun widgetConfigFragment(): BaseWidgetConfigFragment<*> = TestWidget2ConfigFragment()
}