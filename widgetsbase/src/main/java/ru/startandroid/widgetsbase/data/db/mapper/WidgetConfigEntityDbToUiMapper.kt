package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetMainConfig
import javax.inject.Inject

@ScopeApplication
class WidgetConfigEntityDbToUiMapper @Inject constructor(
        val widgetMetadataRepository: WidgetMetadataRepository,
        private val gson: Gson
) : Mapper<WidgetConfigEntityDb, WidgetConfigEntity> {

    override fun map(input: WidgetConfigEntityDb): WidgetConfigEntity {
        val config = configFromJson(input.id, input.config)
        return WidgetConfigEntity(input.id, config, WidgetMainConfig(input.enabled, input.updateInterval))
    }

    private fun configFromJson(id: Int, json: String): WidgetConfig {
        return widgetMetadataRepository.getWidgetMetadata(id).config.widgetConfigCls
                .let { gson.fromJson(json, it.java) }
    }
}