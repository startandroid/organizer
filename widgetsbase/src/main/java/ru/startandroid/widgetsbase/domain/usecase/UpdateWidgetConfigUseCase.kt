package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import javax.inject.Inject

class UpdateWidgetConfigUseCase @Inject constructor(
        private val widgetDataRepository: WidgetDataRepository,
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val widgetDatabase: WidgetDatabase,
        private val widgetWorkManager: WidgetWorkManager
) {

    // TODOL check if data has to be refreshed.
    // Maybe config changes were minor, to hide or show smth on widget, without data changes
    fun invoke(widgetConfigEntity: WidgetConfigEntity): Completable {

        return Flowable.fromCallable {
            widgetDatabase.runInTransaction {
                val currentData = widgetDataRepository.getWidgetByIdSync(widgetConfigEntity.id)

                widgetMetadataRepository
                        .getWidgetMetadata(widgetConfigEntity.id)
                        .refresh
                        .widgetCorrect?.let {
                            val correctedData = it.correctDataAccordingToConfig(currentData.data, widgetConfigEntity.config)
                            widgetDataRepository.updateOrInsertSync(widgetConfigEntity.id, correctedData)
                        }
                widgetConfigRepository.updateSync(widgetConfigEntity)
            }
        }
                .subscribeOn(Schedulers.io())
                .doOnNext {
                    widgetWorkManager.refreshAndScheduleRefresh(widgetConfigEntity.id)
                }.ignoreElements()
    }

}