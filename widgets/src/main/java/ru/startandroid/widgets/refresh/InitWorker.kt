package ru.startandroid.widgets.refresh

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgets.PARAM_KEY
import ru.startandroid.widgets.WidgetConfigEntity
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.mapper.WidgetEntityMapper

class InitWorker(context: Context, val workerParams: WorkerParameters,
                 private val widgetRefresher: WidgetRefresher?,
                 private val widgetDatabase: WidgetDatabase,
                 private val widgetEntityMapper: WidgetEntityMapper
) : Worker(context, workerParams) {

    init {

    }

    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        if (id == 0) return Result.failure()

        val config = widgetRefresher?.initConfig()
        config?.let {
            val configEntity = WidgetConfigEntity(id, config)
            val configEntityDb = widgetEntityMapper.map(configEntity)
            widgetDatabase.widgetConfigDao().insert(configEntityDb)
        }
        return Result.success()

    }
}