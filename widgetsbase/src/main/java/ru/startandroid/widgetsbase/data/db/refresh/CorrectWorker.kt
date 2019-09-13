package ru.startandroid.widgetsbase.data.db.refresh

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository

class CorrectWorker(context: Context, val workerParams: WorkerParameters,
                    private val widgetDbDataHelper: WidgetDbDataHelper?,
                    private val widgetDataRepository: WidgetDataRepository,
                    private val widgetConfigRepository: WidgetConfigRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()
        Log.d("qweee", "CorrectWorker $id")
        var dataEntity = widgetDataRepository.getWidgetByIdSync(id)
        val configEntity = widgetConfigRepository.getByIdSync(id)

        val refreshedData = widgetDbDataHelper?.correctDataAccordingToConfig(dataEntity, configEntity)
        refreshedData?.let {
            widgetDataRepository.updateOrInsertSync(WidgetDataEntity(id, it))
        }

        return Result.success()
    }
}