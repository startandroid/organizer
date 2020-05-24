package ru.startandroid.widgetsbase.data.db.workmanager

import androidx.work.*
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import ru.startandroid.widgetsbase.data.db.init.worker.InitWorker
import ru.startandroid.widgetsbase.data.db.refresh.worker.RefreshWorker
import ru.startandroid.widgetsbase.data.db.refresh.worker.ScheduleRefreshWorker
import ru.startandroid.widgetsbase.data.metadata.WidgetMetadataRepository
import ru.startandroid.widgetsbase.domain.repository.WidgetWorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Creates and schedules workers for init and update widgets data
 */
@ScopeApplication
class WidgetWorkManagerImpl @Inject constructor(
        private val workManagerProvider: dagger.Lazy<WorkManager>,
        private val widgetMetadataRepository: WidgetMetadataRepository
) : WidgetWorkManager {

    private val workManager: WorkManager by lazy { workManagerProvider.get() }

    override fun init(ids: IntArray) {
        for (id in ids) {
            workManager
                    .beginWith(createInitWorker(id))
                    .then(listOf(createRefreshWorker(id), createScheduleRefreshWorker(id)))
                    .enqueue()
        }
    }

    override fun refresh(id: Int) {
        workManager.enqueue(createRefreshWorker(id))
    }

    override fun refreshAndScheduleRefresh(id: Int) {
        workManager
                .beginWith(listOf(createRefreshWorker(id), createScheduleRefreshWorker(id)))
                .enqueue()
    }

    override fun startPeriodicRefresh(id: Int, updateIntervalInMillis: Long) {
        workManager.enqueueUniquePeriodicWork("periodic_refresh_$id", ExistingPeriodicWorkPolicy.REPLACE, createPeriodicRefreshWorker(id, updateIntervalInMillis))
    }

    override fun stopPeriodicRefresh(id: Int) {
        workManager.cancelUniqueWork("periodic_refresh_$id")
    }


    private fun createInitWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<InitWorker>()
                .setInputData(data)
                .build()
    }

    private fun createRefreshWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<RefreshWorker>()
                .setInputData(data)
                .build()
    }

    private fun createScheduleRefreshWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<ScheduleRefreshWorker>()
                .setInputData(data)
                .build()
    }

    private fun createPeriodicRefreshWorker(id: Int, updateIntervalInMillis: Long): PeriodicWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()


        val constraints = Constraints.Builder().apply {
            if (widgetMetadataRepository.getWidgetMetadata(id).refresh.needsInternet)
                setRequiredNetworkType(NetworkType.CONNECTED)
        }.build()
        return PeriodicWorkRequestBuilder<RefreshWorker>(updateIntervalInMillis, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .setInputData(data)
                .build()
    }

}