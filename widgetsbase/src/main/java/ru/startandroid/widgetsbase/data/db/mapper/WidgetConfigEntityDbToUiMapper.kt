package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.NullableMapper
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import javax.inject.Inject

@ScopeApplication
class WidgetConfigEntityDbToUiMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMetadataRepository,
        private val gson: Gson
): NullableMapper<WidgetConfigEntityDb, WidgetConfigEntity> {

    override fun map(input: WidgetConfigEntityDb?): WidgetConfigEntity? {
        if (input == null) return null

        val config = configFromJson(input.id, input.config)
                ?: return null

        return WidgetConfigEntity(input.id, config, input.enabled, input.updateInterval)
    }

    private fun configFromJson(id: Int, json: String): WidgetConfig? {
        return widgetMetadataRepository.getWidgetMetadata(id)?.config?.widgetConfigCls
                ?.let { gson.fromJson(json, it.java) }
    }
}