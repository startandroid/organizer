package ru.startandroid.organizer.home.widget

//import ru.startandroid.widgets.db.WidgetInit
import ru.startandroid.organizer.home.widget.WIDGETS_IDS.TEST_WIDGET_2
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.registrator.WidgetMetadatRepositoryImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class TestWidget2Data(
        val text1: String,
        val text2: String
) : WidgetData

data class TestWidget2Config(
        val flag1: Boolean,
        val flag2: Boolean
) : WidgetConfig


class TestWidget2WidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<TestWidget2Content>,
        val widgetRefresherProvider: Provider<TestWidget2DbDataHelper>

) : WidgetMetadatRepositoryImpl.WidgetMetadata {
    override fun id(): Int = TEST_WIDGET_2
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget2Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget2Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
}