package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Flowable
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import javax.inject.Inject

class GetWidgetsConfigsUseCase @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository
) {

    fun invoke(): Flowable<List<WidgetConfigEntity>> =
        widgetConfigRepository.getAll()

}