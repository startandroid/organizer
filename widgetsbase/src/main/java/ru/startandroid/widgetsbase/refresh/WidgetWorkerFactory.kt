package ru.startandroid.widgetsbase.refresh

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.PARAM_KEY
import ru.startandroid.widgetsbase.db.WidgetDatabase
import ru.startandroid.widgetsbase.mapper.WidgetEntityMapper
import ru.startandroid.widgetsbase.metadata.WidgetDbDataHelperRepository
import javax.inject.Inject

class WidgetWorkerFactory @Inject constructor(
        val widgetMetadataRepository: WidgetDbDataHelperRepository,
        val widgetDatabase: WidgetDatabase,
        val widgetEntityMapper: WidgetEntityMapper) : WorkerFactory() {


    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        val id = workerParameters.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        val refresher = widgetMetadataRepository.getWidgetRefresherProvider(id)?.get()

        return when (workerClassName) {
            InitWorker::class.java.name -> InitWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            RefreshWorker::class.java.name -> RefreshWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            CorrectWorker::class.java.name -> CorrectWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            else -> null
        }

    }
}