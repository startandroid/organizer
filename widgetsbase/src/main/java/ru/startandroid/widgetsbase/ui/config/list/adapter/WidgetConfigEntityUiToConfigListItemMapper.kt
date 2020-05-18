package ru.startandroid.widgetsbase.ui.config.list.adapter

import ru.startandroid.domain.mapping.Mapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import javax.inject.Inject

class WidgetConfigEntityUiToConfigListItemMapper @Inject constructor() : Mapper<WidgetConfigEntity, ConfigListItem> {
    override fun map(input: WidgetConfigEntity): ConfigListItem =
            ConfigListItem(input.id, "title ${input.id}", input.mainConfig.enabled)

}