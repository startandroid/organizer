package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import javax.inject.Inject


class UpdateWidgetUseCase @Inject constructor(
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetWorkManager: WidgetWorkManager
) {

    fun invoke(widgetConfigEntity: WidgetConfigEntity) {
        widgetConfigRepository.update(widgetConfigEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { widgetWorkManager.correctThenRefreshThenSchedule(widgetConfigEntity.id) }
                .subscribe()
    }

}