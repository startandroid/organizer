package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMappingMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import javax.inject.Inject

@ScopeApplication
class WidgetDataEntityMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMappingMetadataRepository,
        private val gson: Gson
) {

    fun fromDb(widgetDataEntityDb: WidgetDataEntityDb?): WidgetDataEntity? {
        if (widgetDataEntityDb == null) return null

        val data = dataFromJson(widgetDataEntityDb.id, widgetDataEntityDb.data) ?: return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun toDb(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        val data = dataToJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

    fun dataToJson(data: WidgetData) = gson.toJson(data)

    fun dataFromJson(id: Int, json: String): WidgetData? {
        return widgetMetadataRepository.getWidgetDataClass(id)
                ?.let { gson.fromJson(json, it.java) }
    }

}