package ru.startandroid.widgetsbase.data.db.mapper

import com.google.gson.Gson
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import javax.inject.Inject

@ScopeApplication
class WidgetConfigEntityUiToDbMapper @Inject constructor(
        private val gson: Gson
) : Mapper<WidgetConfigEntity, WidgetConfigEntityDb> {

    override fun map(input: WidgetConfigEntity): WidgetConfigEntityDb {
        val config = configToJson(input.config)

        return WidgetConfigEntityDb(input.id, config, input.mainConfig.enabled, input.mainConfig.updateInterval)
    }

    private fun configToJson(config: WidgetConfig): String {
        return gson.toJson(config)
    }
}