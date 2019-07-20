package ru.startandroid.widgetsbase.metadata

import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.config.BaseWidgetConfigFragment
import ru.startandroid.widgetsbase.refresh.WidgetDbDataHelper
import javax.inject.Provider
import kotlin.reflect.KClass

interface WidgetMetadataRepository :
        WidgetRegistratorData,
        WidgetMappingMetadataRepository,
        WidgetContentMetadataRepository,
        WidgetDbDataHelperRepository,
        WidgetDbInitMetadataRepository,
        WidgetConfigScreenMetadataRepository

interface WidgetRegistratorData {
    fun registerData(widgetMetadataSet: Set<WidgetMetadata>)
}

interface WidgetMappingMetadataRepository {
    fun getWidgetDataClass(id: Int): KClass<out WidgetData>?
    fun getWidgetConfigClass(id: Int): KClass<out WidgetConfig>?
}

interface WidgetContentMetadataRepository {
    fun getWidgetContentProvider(id: Int): Provider<out WidgetContent>?
}

interface WidgetDbDataHelperRepository {
    fun getWidgetRefresherProvider(id: Int): Provider<out WidgetDbDataHelper>?
}

interface WidgetDbInitMetadataRepository {
    fun getWidgetIds(): IntArray
}

interface WidgetConfigScreenMetadataRepository {
    fun getConfigFragment(id: Int): BaseWidgetConfigFragment<*>?
    fun getWidgetTitleResId(id: Int): Int?
    fun getWidgetDescriptionResId(id: Int): Int?
}
