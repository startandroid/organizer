package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Flowable
import ru.startandroid.domain.mapping.CollectionMapperImpl
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.ui.config.list.adapter.ConfigListItem
import ru.startandroid.widgetsbase.ui.config.list.adapter.WidgetConfigEntityUiToConfigListItemMapper
import javax.inject.Inject

class GetConfigListUseCase @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetConfigEntityUiToConfigListItemMapper: WidgetConfigEntityUiToConfigListItemMapper
) {

    fun invoke(): Flowable<List<ConfigListItem>> =
            widgetConfigRepository.getAll().map {
                CollectionMapperImpl(widgetConfigEntityUiToConfigListItemMapper).map(it)
            }

}