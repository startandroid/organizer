package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Flowable
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import javax.inject.Inject

class GetEnabledWidgetsUseCase @Inject constructor(
        private val widgetDataRepository: WidgetDataRepository
) {

    fun invoke(): Flowable<List<WidgetDataEntity>> =
            widgetDataRepository.getEnabledWidgets()
}