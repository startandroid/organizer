package ru.startandroid.widgetsbase.data.db.refresh

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository

class InitWorker(context: Context, val workerParams: WorkerParameters,
                 private val widgetDbDataHelper: WidgetDbDataHelper?,
                 private val widgetConfigRepository: WidgetConfigRepository,
                 private val widgetRefreshStatusRepository: WidgetRefreshStatusRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        Log.d("qweee", "InitWorker $id")
        if (id == 0) return Result.failure()

        widgetRefreshStatusRepository.initSync(id)
        widgetDbDataHelper?.getInitConfig()?.let {
            widgetConfigRepository.updateOrInsertSync(it)
        }

        return Result.success()

    }
}