package ru.startandroid.widgets.refresh

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.startandroid.widgets.PARAM_KEY
import ru.startandroid.widgets.db.WidgetDatabase
import ru.startandroid.widgets.mapper.WidgetEntityMapper
import javax.inject.Inject
import javax.inject.Provider

class WidgetWorkerFactory @Inject constructor(val widgetRegistrator: ToWorkerFactoryRegistrator, val widgetDatabase: WidgetDatabase, val widgetEntityMapper: WidgetEntityMapper) : WorkerFactory() {

    interface ToWorkerFactoryRegistrator {
        fun registerWidgetToWorkerFactory(registerFunc: (id: Int, widgetRefresher: Provider<out WidgetRefresher>) -> Unit)
    }

    private val widgets = mutableMapOf<Int, Provider<out WidgetRefresher>>()

    init {
        reg()
    }

    fun reg() {
        // temporary workaround, will be fixed
        widgetRegistrator.registerWidgetToWorkerFactory { id, widgetRefresher -> widgets.put(id, widgetRefresher) }
    }

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        val id = workerParameters.inputData.getInt(PARAM_KEY.WIDGET_ID, 0)
        val refresher = widgets.get(id)?.get()

        return when (workerClassName) {
            InitWorker::class.java.name -> InitWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            RefreshWorker::class.java.name -> RefreshWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            CorrectWorker::class.java.name -> CorrectWorker(appContext, workerParameters, refresher, widgetDatabase, widgetEntityMapper)
            else -> null
        }

    }
}