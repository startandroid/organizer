package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.data.db.model.WidgetDataExtendedEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import javax.inject.Inject

@ScopeApplication
class WidgetDataExtendedEntityDbToUiMapper @Inject constructor(
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val gson: Gson
) : Mapper<WidgetDataExtendedEntityDb, WidgetDataEntity> {

    override fun map(input: WidgetDataExtendedEntityDb): WidgetDataEntity {
        val data = dataFromJson(input.widgetDataEntityDb.id, input.widgetDataEntityDb.data)

        return WidgetDataEntity(input.widgetDataEntityDb.id, data, input.refreshStatus)
    }

    private fun dataFromJson(id: Int, json: String): WidgetData {
        return widgetMetadataRepository.getWidgetMetadata(id).content.widgetDataCls
                .let { gson.fromJson(json, it.java) }
    }


}