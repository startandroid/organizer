package ru.startandroid.widgetsbase.data.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.db.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.data.db.mapper.WidgetEntityMapper
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository

class CorrectWorker(context: Context, val workerParams: WorkerParameters,
                    private val widgetDbDataHelper: WidgetDbDataHelper?,
                    private val widgetDataRepository: WidgetDataRepository,
                    private val widgetConfigRepository: WidgetConfigRepository,
                    //private val widgetDatabase: WidgetDatabase,
                    private val widgetEntityMapper: WidgetEntityMapper
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        var dataEntity = widgetDataRepository.getWidgetByIdSync(id)
        val configEntity = widgetConfigRepository.getWidgetByIdSync(id)

        val refreshedData = widgetDbDataHelper?.correctDataAccordingToConfig(dataEntity, configEntity)

        refreshedData?.let {
            dataEntity?.copy(data = it)?.let {
                widgetDataRepository.updateOrInsertSync(it)
            }
        }

        return Result.success()
    }
}