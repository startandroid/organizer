package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import javax.inject.Inject

@ScopeApplication
class WidgetConfigEntityMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMetadataRepository,
        private val gson: Gson
) {

    fun fromDb(widgetConfigEntityDb: WidgetConfigEntityDb?): WidgetConfigEntity? {
        if (widgetConfigEntityDb == null) return null

        val config = configFromJson(widgetConfigEntityDb.id, widgetConfigEntityDb.config)
                ?: return null

        return WidgetConfigEntity(widgetConfigEntityDb.id, config, widgetConfigEntityDb.enabled, widgetConfigEntityDb.updateInterval)
    }

    fun toDb(widgetConfigEntity: WidgetConfigEntity): WidgetConfigEntityDb {
        val config = configToJson(widgetConfigEntity.config)

        return WidgetConfigEntityDb(widgetConfigEntity.id, config, widgetConfigEntity.enabled, widgetConfigEntity.updateInterval)
    }

    fun configFromJson(id: Int, json: String): WidgetConfig? {
        return widgetMetadataRepository.getWidgetMetadata(id)?.config?.widgetConfigCls
                ?.let { gson.fromJson(json, it.java) }
    }

    fun configToJson(config: WidgetConfig): String {
        return gson.toJson(config)
    }

}