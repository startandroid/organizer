package ru.startandroid.widgetsbase.data.db.refresh.worker

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
        val config = widgetConfigRepository.getByIdSync(id)
        config.let {
            if (it.mainConfig.updateInterval.durationInMillis > 0 && it.mainConfig.enabled) {
                widgetWorkManager.startPeriodicRefresh(id, it.mainConfig.updateInterval.durationInMillis)
            } else {
                widgetWorkManager.stopPeriodicRefresh(id)
            }
        }

        return Result.success()
    }
}