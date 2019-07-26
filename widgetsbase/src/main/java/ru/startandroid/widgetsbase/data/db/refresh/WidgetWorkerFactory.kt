package ru.startandroid.widgetsbase.data.db.refresh

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.data.metadata.WidgetDbDataHelperRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import javax.inject.Inject

class WidgetWorkerFactory @Inject constructor(
        val widgetMetadataRepository: WidgetDbDataHelperRepository,
        private val widgetDataRepository: WidgetDataRepository,
        private val widgetConfigRepository: WidgetConfigRepository
) : WorkerFactory() {


    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        val id = workerParameters.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        val refresher = widgetMetadataRepository.getWidgetRefresherProvider(id)?.get()
        return when (workerClassName) {
            InitWorker::class.java.name -> InitWorker(appContext, workerParameters, refresher, widgetConfigRepository)
            RefreshWorker::class.java.name -> RefreshWorker(appContext, workerParameters, refresher, widgetDataRepository, widgetConfigRepository)
            CorrectWorker::class.java.name -> CorrectWorker(appContext, workerParameters, refresher, widgetDataRepository, widgetConfigRepository)
            else -> null
        }

    }
}