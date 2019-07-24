package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMappingMetadataRepository
import javax.inject.Inject

class WidgetDataEntityMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMappingMetadataRepository,
        private val gson: Gson
) {

    fun fromDb(widgetDataEntityDb: WidgetDataEntityDb?): WidgetDataEntity? {
        if (widgetDataEntityDb == null) return null

        val data = widgetMetadataRepository.getWidgetDataClass(widgetDataEntityDb.id)
                ?.let { gson.fromJson(widgetDataEntityDb.data, it.java) }

        if (data == null) return null

        return WidgetDataEntity(widgetDataEntityDb.id, data)
    }

    fun toDb(widgetDataEntity: WidgetDataEntity): WidgetDataEntityDb {
        val data = gson.toJson(widgetDataEntity.data)

        return WidgetDataEntityDb(widgetDataEntity.id, data)
    }

}