package ru.startandroid.widgetsbase.data.db.init.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.startandroid.widgetsbase.data.PARAM_KEY
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataConfig
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataContent
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository

class InitWorker(
        context: Context,
        private val workerParams: WorkerParameters,
        private val widgetDatabase: WidgetDatabase,
        private val widgetMetadaContent: WidgetMetadataContent,
        private val widgetMetadataConfig: WidgetMetadataConfig,
        private val widgetConfigRepository: WidgetConfigRepository,
        private val widgetDataRepository: WidgetDataRepository,
        private val widgetRefreshStatusRepository: WidgetRefreshStatusRepository
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val id = workerParams.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        Log.d("qweee", "InitWorker $id")
        if (id == 0) return Result.failure()

        val config = widgetMetadataConfig.initWidgetConfig
        val mainConfig = widgetMetadataConfig.initWidgetMainConfig
        val data = widgetMetadaContent.initWidgetData

        widgetDatabase.runInTransaction {
            widgetRefreshStatusRepository.initSync(id)
            widgetConfigRepository.updateOrInsertSync(WidgetConfigEntity(id, config, mainConfig))
            widgetDataRepository.updateOrInsertSync(id, data)
        }
        return Result.success()
    }
}


// do all init work in one worker for all ids

// CorrectWorker - should be deleted
// Correcter - should be created instead

// widgetDbDataHelper is used:
// in UpdateConfigUseCase


