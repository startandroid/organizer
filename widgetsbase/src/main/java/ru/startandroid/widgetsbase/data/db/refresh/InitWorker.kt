package ru.startandroid.widgetsbase.data.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class InitWorker(context: Context, val workerParams: WorkerParameters,
                 private val widgetDbDataHelper: WidgetDbDataHelper?,
                 private val widgetConfigRepository: WidgetConfigRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        if (id == 0) return Result.failure()
        val config = widgetDbDataHelper?.initConfig()
        config?.let {
            val configEntity = WidgetConfigEntity(id, config)
            widgetConfigRepository.updateOrInsertSync(configEntity)
        }
        return Result.success()

    }
}