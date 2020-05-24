package ru.startandroid.widgetsbase.data.db.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.init.worker.InitWorker
import ru.startandroid.widgetsbase.data.db.refresh.worker.RefreshWorker
import ru.startandroid.widgetsbase.data.db.refresh.worker.ScheduleRefreshWorker
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import javax.inject.Inject

class WidgetWorkerFactory @Inject constructor(
        private val widgetMetadataRepository: WidgetMetadataRepository,
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataRepository: WidgetDataRepository,
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetRefreshStatusRepository: WidgetRefreshStatusRepository,
        private val widgetWorkManager: WidgetWorkManager
) : WorkerFactory() {


    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        val id = workerParameters.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        val widgetMetadata = widgetMetadataRepository.getWidgetMetadata(id)

        return when (workerClassName) {
            InitWorker::class.java.name -> InitWorker(appContext, workerParameters, widgetDatabase, widgetMetadata.content, widgetMetadata.config, widgetConfigRepository, widgetDataRepository, widgetRefreshStatusRepository)
            RefreshWorker::class.java.name -> RefreshWorker(appContext, workerParameters, widgetMetadata.refresh, widgetDataRepository, widgetConfigRepository, widgetRefreshStatusRepository)
            ScheduleRefreshWorker::class.java.name -> ScheduleRefreshWorker(appContext, workerParameters, widgetConfigRepository, widgetWorkManager)
            else -> null
        }

    }
}