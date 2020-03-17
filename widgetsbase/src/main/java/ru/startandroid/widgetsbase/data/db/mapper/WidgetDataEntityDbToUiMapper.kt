package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.domain.mapping.NullableMapper
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import javax.inject.Inject

@ScopeApplication
class WidgetDataEntityDbToUiMapper @Inject constructor(
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val gson: Gson
): NullableMapper<WidgetDataEntityDb, WidgetDataEntity> {

    override fun map(input: WidgetDataEntityDb?): WidgetDataEntity? {
        if (input == null) return null

        val data = dataFromJson(input.id, input.data) ?: return null

        return WidgetDataEntity(input.id, data)
    }

    private fun dataFromJson(id: Int, json: String): WidgetData? {
        return widgetMetadataRepository.getWidgetMetadata(id)?.content?.widgetDataCls
                ?.let { gson.fromJson(json, it.java) }
    }


}