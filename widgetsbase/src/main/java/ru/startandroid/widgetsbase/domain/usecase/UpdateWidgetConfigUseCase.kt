package ru.startandroid.widgetsbase.domain.usecase

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadata
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataProvider
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import javax.inject.Inject

// TOTO rename to update widget config?
class UpdateWidgetUseCase @Inject constructor(
        private val widgetDataRepository: WidgetDataRepository,
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val widgetWorkManager: WidgetWorkManager
) {

    fun invoke(widgetConfigEntity: WidgetConfigEntity) {

        // get data from db
        // correct it with config
        // put new data to db
        // put new config to db

        var dataEntity = widgetDataRepository.getWidgetByIdSync(widgetConfigEntity.id)
        val refreshedData = widgetMetadataRepository.getWidgetMetadata(widgetConfigEntity.id).update.widgetRefresher().correctDataAccordingToConfig(dataEntity.data, widgetConfigEntity.config)
        widgetDataRepository.updateOrInsertSync(WidgetDataEntity(widgetConfigEntity.id, refreshedData))
        widgetConfigRepository.update(widgetConfigEntity)

        //widgetConfigRepository.update(widgetConfigEntity)
                //.observeOn(AndroidSchedulers.mainThread())
                //.doOnSuccess { widgetWorkManager.correctThenRefreshThenSchedule(widgetConfigEntity.id) }
                //.subscribe()
    }

}