package ru.startandroid.widgets.testwidget1

import kotlinx.android.parcel.Parcelize
import ru.startandroid.organizer.home.widget.TestWidget1Content
import ru.startandroid.widgets.R
import ru.startandroid.widgets.WIDGETS_IDS.TEST_WIDGET_1
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

data class TestWidget1Data(
        val text: String
) : WidgetData()

@Parcelize
data class TestWidget1Config(
        val flag: Boolean,
        val text: String,
        val list: List<String>
) : WidgetConfig

class TestWidget1WidgetMetadata @Inject constructor(
        val widgetContentProvider: Provider<TestWidget1Content>,
        val widgetRefresherProvider: Provider<TestWidget1DbDataHelper>
) : WidgetMetadata {
    override fun id(): Int = TEST_WIDGET_1
    override fun titleResId(): Int = R.string.test_widget_1_title
    override fun descriptionResId(): Int = R.string.test_widget_1_description
    override fun widgetDataCls(): KClass<out WidgetData> = TestWidget1Data::class
    override fun widgetConfigCls(): KClass<out WidgetConfig> = TestWidget1Config::class
    override fun widgetContentProvider(): Provider<out WidgetContent> = widgetContentProvider
    override fun widgetRefresher(): Provider<out WidgetDbDataHelper> = widgetRefresherProvider
    override fun widgetConfigFragment(): BaseWidgetConfigFragment<*> = TestWidget1ConfigFragment()
}
