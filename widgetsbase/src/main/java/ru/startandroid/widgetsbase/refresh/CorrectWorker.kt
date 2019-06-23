package ru.startandroid.widgetsbase.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.WidgetDataEntity
import ru.startandroid.widgetsbase.db.WidgetDatabase
import ru.startandroid.widgetsbase.mapper.WidgetEntityMapper

class CorrectWorker(context: Context, val workerParams: WorkerParameters,
                    private val widgetDbDataHelper: WidgetDbDataHelper?,
                    private val widgetDatabase: WidgetDatabase,
                    private val widgetEntityMapper: WidgetEntityMapper
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        val id = workerParams.inputData.getInt(WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        val config = widgetDatabase.widgetConfigDao()
                .getByIdSync(id)
                ?.let {
                    widgetEntityMapper.map(it)
                }
                ?.config

        val data = widgetDatabase.widgetDataDao()
                .getByIdSync(id)
                ?.let {
                    widgetEntityMapper.map(it)
                }
                ?.data


        val refreshedData = widgetDbDataHelper?.correctDataAccordingToConfig(data, config)

        refreshedData?.let {
            val dataEntity = WidgetDataEntity(id, it)
            val dataEntityDb = widgetEntityMapper.map(dataEntity)
            widgetDatabase.widgetDataDao().updateOrInsert(dataEntityDb)
        }

        return Result.success()
    }
}