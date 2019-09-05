package ru.startandroid.widgetsbase.data.db.refresh

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager

class ScheduleRefreshWorker(context: Context,
                            private val workerParams: WorkerParameters,
                            private val widgetConfigRepository: WidgetConfigRepository,
                            private val widgetWorkManager: WidgetWorkManager
) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()
        Log.d("qweee", "ScheduleRefreshWorker $id")
        val config = widgetConfigRepository.getByIdSync(id)
        config?.let {
            if (it.updateInterval > 0 && it.enabled) {
                widgetWorkManager.startPeriodicRefresh(id, it.updateInterval)
            } else {
                widgetWorkManager.stopPeriodicRefresh(id)
            }
        }

        return Result.success()
    }
}