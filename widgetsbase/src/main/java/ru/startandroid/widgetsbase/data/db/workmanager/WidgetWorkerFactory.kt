package ru.startandroid.widgetsbase.data.db.refresh.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
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
        val refresher = widgetMetadataRepository.getWidgetMetadata(id).update.widgetDbDataHelper.invoke()
        return when (workerClassName) {
            InitWorker::class.java.name -> InitWorker(appContext, workerParameters, widgetDatabase, refresher, widgetConfigRepository,widgetDataRepository, widgetRefreshStatusRepository)
            RefreshWorker::class.java.name -> RefreshWorker(appContext, workerParameters, refresher, widgetDataRepository, widgetConfigRepository, widgetRefreshStatusRepository)
            ScheduleRefreshWorker::class.java.name -> ScheduleRefreshWorker(appContext, workerParameters, widgetConfigRepository, widgetWorkManager)
            else -> null
        }

    }
}