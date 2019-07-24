package ru.startandroid.widgetsbase.data.metadata

import ru.startandroid.widgetsbase.data.refresh.WidgetDbDataHelper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.ui.widgets.adapter.content.WidgetContent
import ru.startandroid.widgetsbase.ui.config.BaseWidgetConfigFragment
import javax.inject.Provider
import kotlin.reflect.KClass

interface WidgetMetadataRepository :
        WidgetRegistratorMetadataRepository,
        WidgetMappingMetadataRepository,
        WidgetContentMetadataRepository,
        WidgetDbDataHelperRepository,
        WidgetDbInitMetadataRepository,
        WidgetConfigScreenMetadataRepository

interface WidgetRegistratorMetadataRepository {
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
