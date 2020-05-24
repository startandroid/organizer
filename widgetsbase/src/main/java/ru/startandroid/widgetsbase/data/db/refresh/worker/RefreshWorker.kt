package ru.startandroid.widgetsbase.data.db.refresh.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRefresh
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository

class RefreshWorker(context: Context,
                    private val workerParams: WorkerParameters,
                    private val widgetMetadataRefresh: WidgetMetadataRefresh,
                    private val widgetDataRepository: WidgetDataRepository,
                    private val widgetConfigRepository: WidgetConfigRepository,
                    private val widgetRefreshStatusRepository: WidgetRefreshStatusRepository
) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        val configEntity = widgetConfigRepository.getByIdSync(id)
        if (!configEntity.mainConfig.enabled) return Result.success()

        val acquireResult = widgetRefreshStatusRepository.acquireRefreshSync(id)
        if (!acquireResult) return Result.success()
        try {
            val currentData = widgetDataRepository.getWidgetByIdSync(id).data
            val data = widgetMetadataRefresh.widgetRefresh?.refreshData(currentData, configEntity.config)
            data?.let {
                widgetDataRepository.updateOrInsertSync(id, it)
            }
        } catch (e: Exception) {
        } finally {
            widgetRefreshStatusRepository.closeRefreshSync(id)
        }
        return Result.success()
    }
}