package ru.startandroid.widgetsbase.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.WidgetConfig
import ru.startandroid.widgetsbase.WidgetConfigEntity
import ru.startandroid.widgetsbase.WidgetData
import ru.startandroid.widgetsbase.WidgetDataEntity
import ru.startandroid.widgetsbase.db.data.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.db.data.WidgetDataEntityDb
import javax.inject.Inject
import kotlin.reflect.KClass

@ScopeApplication
class WidgetEntityMapper
@Inject
constructor(
        val widgetMetadataRepository: WidgetMappingMetadataRepository,
        private val gson: Gson
) {

    interface WidgetMappingMetadataRepository {
        fun getWidgetDataClass(id: Int): KClass<out WidgetData>?
        fun getWidgetConfigClass(id: Int): KClass<out WidgetConfig>?
    }

    fun map(widgetDataEntityDb: WidgetDataEntityDb?): WidgetDataEntity? {
        if (widgetDataEntityDb == null) return null

        val data = widgetMetadataRepository.getWidgetDataClass(widgetDataEntityDb.id)
                ?.let { gson.fromJson(widgetDataEntityDb.data, it.java) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun map(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

    fun map(widgetConfigEntityDb: WidgetConfigEntityDb): WidgetConfigEntity? {
        val config = widgetMetadataRepository.getWidgetConfigClass(widgetConfigEntityDb.id)
                ?.let { gson.fromJson(widgetConfigEntityDb.config, it.java) }

        if (config == null) return null

        return WidgetConfigEntity(widgetConfigEntityDb.id, config, widgetConfigEntityDb.enabled)
    }

    fun map(widgetConfigEntity: WidgetConfigEntity): WidgetConfigEntityDb {
        val config = gson.toJson(widgetConfigEntity.config)

        return WidgetConfigEntityDb(widgetConfigEntity.id, config, widgetConfigEntity.enabled)
    }
}