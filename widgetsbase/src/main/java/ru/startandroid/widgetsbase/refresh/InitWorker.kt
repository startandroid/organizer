package ru.startandroid.widgetsbase.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.PARAM_KEY
import ru.startandroid.widgetsbase.WidgetConfigEntity
import ru.startandroid.widgetsbase.db.WidgetDatabase
import ru.startandroid.widgetsbase.mapper.WidgetEntityMapper

class InitWorker(context: Context, val workerParams: WorkerParameters,
                 private val widgetDbDataHelper: WidgetDbDataHelper?,
                 private val widgetDatabase: WidgetDatabase,
                 private val widgetEntityMapper: WidgetEntityMapper
) : Worker(context, workerParams) {

    init {

    }

    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        val config = widgetDbDataHelper?.initConfig()
        config?.let {
            val configEntity = WidgetConfigEntity(id, config)
            val configEntityDb = widgetEntityMapper.map(configEntity)
            widgetDatabase.widgetConfigDao().insert(configEntityDb)
        }
        return Result.success()

    }
}