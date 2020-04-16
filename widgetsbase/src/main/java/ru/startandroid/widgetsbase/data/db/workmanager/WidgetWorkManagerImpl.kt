package ru.startandroid.widgetsbase.data.db.workmanager

import android.util.Log
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
): WidgetWorkManager {

    private val workManager: WorkManager by lazy { workManagerProvider.get() }

    override fun init(ids: IntArray) {
        Log.d("qweee", "WidgetWorkManagerImpl init $ids")
        for (id in ids) {
            workManager
                    .beginWith(createInitWorker(id))
                    .then(listOf(createRefreshWorker(id), createScheduleRefreshWorker(id)))
                    .enqueue()
        }
    }

    override fun refresh(id: Int) {
        Log.d("qweee", "refresh $id")
        workManager.enqueue(createRefreshWorker(id))
    }

    override fun refreshAndScheduleRefresh(id: Int) {
        Log.d("qweee", "refreshThenSchedule $id")
        workManager
                .beginWith(listOf(createRefreshWorker(id), createScheduleRefreshWorker(id)))
                .enqueue()
    }

    override fun startPeriodicRefresh(id: Int, updateIntervalInMillis: Long) {
        Log.d("qweee", "WidgetWorkManagerImpl $id start $updateIntervalInMillis")
        workManager.enqueueUniquePeriodicWork("periodic_refresh_$id", ExistingPeriodicWorkPolicy.REPLACE, createPeriodicRefreshWorker(id, updateIntervalInMillis))
    }

    override fun stopPeriodicRefresh(id: Int) {
        Log.d("qweee", "WidgetWorkManagerImpl $id stop")
        workManager.cancelUniqueWork("periodic_refresh_$id")
    }


    private fun createInitWorker(id: Int): OneTimeWorkRequest {
        Log.d("qweee", "createInitWorker $id")
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<InitWorker>()
                .setInputData(data)
                .build()
    }

    private fun createRefreshWorker(id: Int): OneTimeWorkRequest {
        Log.d("qweee", "createRefreshWorker $id")
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<RefreshWorker>()
                .setInputData(data)
                .build()
    }

    private fun createScheduleRefreshWorker(id: Int): OneTimeWorkRequest {
        Log.d("qweee", "createScheduleRefreshWorker $id")
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<ScheduleRefreshWorker>()
                .setInputData(data)
                .build()
    }

    private fun createPeriodicRefreshWorker(id: Int, updateIntervalInMillis: Long): PeriodicWorkRequest {
        Log.d("qweee", "createPeriodicRefreshWorker $id")
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