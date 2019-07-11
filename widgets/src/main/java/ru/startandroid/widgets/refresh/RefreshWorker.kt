package ru.startandroid.widgets.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgets.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.mapper.WidgetEntityMapper

class RefreshWorker(context: Context,
                    private val workerParams: WorkerParameters,
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
                    widgetEntityMapper.mapConfigDbToConfig(it)
                }?.config

        val data = widgetDbDataHelper?.refreshData(config)

        data?.let {
            val dataEntity = WidgetDataEntity(id, it)
            val dataEntityDb = widgetEntityMapper.mapDataToDataDb(dataEntity)
            widgetDatabase.widgetDataDao().updateOrInsert(dataEntityDb)
        }
        return Result.success()
    }
}