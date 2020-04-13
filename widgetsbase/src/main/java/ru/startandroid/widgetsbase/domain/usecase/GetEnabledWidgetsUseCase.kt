package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Flowable
import ru.startandroid.domain.mapping.CollectionMapperImpl
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.ui.config.list.adapter.ConfigListItem
import ru.startandroid.widgetsbase.ui.config.list.adapter.WidgetConfigEntityUiToConfigListItemMapper
import javax.inject.Inject

class GetEnabledWidgetsUseCase @Inject constructor(
        private val widgetDataRepository: WidgetDataRepository
) {

    fun invoke(): Flowable<List<WidgetDataEntity>> =
            widgetDataRepository.getEnabledWidgets()
}