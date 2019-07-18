package ru.startandroid.widgetsbase.metadata

import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.config.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
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