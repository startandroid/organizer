package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMappingMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import javax.inject.Inject

@ScopeApplication
class WidgetConfigEntityMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMappingMetadataRepository,
        private val gson: Gson
) {

    fun fromDb(widgetConfigEntityDb: WidgetConfigEntityDb?): WidgetConfigEntity? {
        if (widgetConfigEntityDb == null) return null

        val config = configFromJson(widgetConfigEntityDb.id, widgetConfigEntityDb.config)
                ?: return null

        return WidgetConfigEntity(widgetConfigEntityDb.id, config, widgetConfigEntityDb.enabled)
    }

    fun toDb(widgetConfigEntity: WidgetConfigEntity): WidgetConfigEntityDb {
        val config = configToJson(widgetConfigEntity.config)

        return WidgetConfigEntityDb(widgetConfigEntity.id, config, widgetConfigEntity.enabled)
    }

    fun configFromJson(id: Int, json: String): WidgetConfig? {
        return widgetMetadataRepository.getWidgetConfigClass(id)
                ?.let { gson.fromJson(json, it.java) }
    }

    fun configToJson(config: WidgetConfig): String {
        return gson.toJson(config)
    }

}