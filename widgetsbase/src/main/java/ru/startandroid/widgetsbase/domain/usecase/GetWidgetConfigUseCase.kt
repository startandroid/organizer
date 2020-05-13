package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Single
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import javax.inject.Inject

class GetWidgetConfigUseCase @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository
) {

    fun invoke(widgetId: Int): Single<WidgetConfigEntity> =
            widgetConfigRepository.getById(widgetId)

}