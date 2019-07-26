package ru.startandroid.widgetsbase.data.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository

class RefreshWorker(context: Context,
                    private val workerParams: WorkerParameters,
                    private val widgetDbDataHelper: WidgetDbDataHelper?,
                    private val widgetDataRepository: WidgetDataRepository,
                    private val widgetConfigRepository: WidgetConfigRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        val config = widgetConfigRepository.getByIdSync(id)

        val data = widgetDbDataHelper?.refreshData(config)
        data?.let {
            widgetDataRepository.updateOrInsertSync(WidgetDataEntity(id, it))
        }

        return Result.success()
    }
}