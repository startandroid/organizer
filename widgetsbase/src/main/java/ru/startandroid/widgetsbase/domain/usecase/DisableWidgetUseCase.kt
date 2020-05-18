package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Completable
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import javax.inject.Inject


class DisableWidgetUseCase @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetWorkManager: WidgetWorkManager
) {

    fun invoke(widgetId: Int): Completable {
        widgetWorkManager.stopPeriodicRefresh(widgetId)
        return widgetConfigRepository.setEnabled(widgetId, false).ignoreElement()
    }

}