package ru.startandroid.widgetsbase.data.metadata

import ru.startandroid.widgetsbase.data.db.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.ui.config.widget.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import javax.inject.Provider
import kotlin.reflect.KClass

interface WidgetMetadata {
    fun id(): Int
    fun titleResId(): Int
    fun descriptionResId(): Int
    fun widgetDataCls(): KClass<out WidgetData>
    fun widgetConfigCls(): KClass<out WidgetConfig>
    fun widgetContentProvider(): Provider<out WidgetContent>
    fun widgetRefresher(): Provider<out WidgetDbDataHelper>
    fun widgetConfigFragment(): BaseWidgetConfigFragment<*>
}