package ru.startandroid.widgetsbase.data.refresh

import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import ru.startandroid.domain.ScopeApplication
import ru.startandroid.widgetsbase.data.PARAM_KEY.WIDGET_ID
import javax.inject.Inject

@ScopeApplication
class WidgetsRefresher @Inject constructor() {

    fun refresh(id: Int) {
        WorkManager.getInstance()
                .enqueue(createRefreshWorker(id))
    }

    fun init(ids: IntArray) {
        for (id in ids) {
            WorkManager.getInstance()
                    .beginWith(createInitWorker(id))
                    .then(createCorrectWorker(id))
                    .then(createRefreshWorker(id))
                    .enqueue()
        }
    }

    private fun createInitWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<InitWorker>()
                .setInputData(data)
                .build()
    }

    private fun createCorrectWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<CorrectWorker>()
                .setInputData(data)
                .build()
    }

    private fun createRefreshWorker(id: Int): OneTimeWorkRequest {
        val data = Data.Builder().putInt(WIDGET_ID, id).build()
        return OneTimeWorkRequestBuilder<RefreshWorker>()
                .setInputData(data)
                .build()
    }

}