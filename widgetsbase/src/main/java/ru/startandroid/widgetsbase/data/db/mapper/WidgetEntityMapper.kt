package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.data.metadata.WidgetMappingMetadataRepository
import javax.inject.Inject

// TODO remove it

@ScopeApplication
class WidgetEntityMapper
@Inject
constructor(
        val widgetMetadataRepository: WidgetMappingMetadataRepository,
        private val gson: Gson
) {



    fun mapDataDbToData(widgetDataEntityDb: WidgetDataEntityDb?): WidgetDataEntity? {
        if (widgetDataEntityDb == null) return null

        val data = widgetMetadataRepository.getWidgetDataClass(widgetDataEntityDb.id)
                ?.let { gson.fromJson(widgetDataEntityDb.data, it.java) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun mapDataToDataDb(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

    fun mapConfigDbToConfig(widgetConfigEntityDb: WidgetConfigEntityDb?): WidgetConfigEntity? {
        if (widgetConfigEntityDb == null) return null

        val config = configFromJson(widgetConfigEntityDb.id, widgetConfigEntityDb.config)

        //val config = widgetMetadataRepository.getWidgetConfigClass(widgetConfigEntityDb.id)
//                ?.let { gson.fromJson(widgetConfigEntityDb.config, it.java) }

        if (config == null) return null

        return WidgetConfigEntity(widgetConfigEntityDb.id, config, widgetConfigEntityDb.enabled)
    }

    fun mapConfigToConfigDb(widgetConfigEntity: WidgetConfigEntity): WidgetConfigEntityDb {
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