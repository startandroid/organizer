package ru.startandroid.widgetsbase.data.db.refresh

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository

class RefreshWorker(context: Context,
                    private val workerParams: WorkerParameters,
                    private val widgetDbDataHelper: WidgetDbDataHelper?,
                    private val widgetDataRepository: WidgetDataRepository,
                    private val widgetConfigRepository: WidgetConfigRepository,
                    private val widgetRefreshStatusRepository: WidgetRefreshStatusRepository
) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        Log.d("qweee", "RefreshWorker $id start")
        if (id == 0) return Result.failure()

        val acquireResult = widgetRefreshStatusRepository.acquireRefreshSync(id)
        Log.d("qweee", "RefreshWorker $id acquireResult = $acquireResult")
        if (!acquireResult) return Result.success()

        val config = widgetConfigRepository.getByIdSync(id) ?: return Result.failure()
        if (!config.enabled) return Result.success()

        val data = widgetDbDataHelper?.refreshData(config)
        data?.let {
            widgetDataRepository.updateOrInsertSync(WidgetDataEntity(id, it))
        }

        val closeRefresh = widgetRefreshStatusRepository.closeRefreshSync(id)

        Log.d("qweee", "RefreshWorker $id done, closeRefresh = $closeRefresh")

        return Result.success()
    }
}