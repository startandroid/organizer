package ru.startandroid.widgetsbase.ui.config.list.adapter

import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity

class WidgetConfigEntityUiToConfigListItem: Mapper<WidgetConfigEntity, ConfigListItem> {
    override fun map(input: WidgetConfigEntity): ConfigListItem =
        ConfigListItem(input.id, "title ${input.id}", input.mainConfig.enabled)

}