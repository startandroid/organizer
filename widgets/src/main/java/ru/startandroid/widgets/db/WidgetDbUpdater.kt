package ru.startandroid.widgets.db

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
//import ru.startandroid.data.AppDatabase
import ru.startandroid.widgets.WidgetData
import ru.startandroid.widgets.WidgetDataEntity
import ru.startandroid.widgets.WidgetEntityMapper
import java.util.concurrent.Executors
import javax.inject.Inject


class WidgetDbUpdater
@Inject
constructor(
        private val database: WidgetDatabase,
        private val widgetEntityMapper: WidgetEntityMapper) {

    // TODO use coroutines
    // TODO Error handling
    // TODO inject DB scheduler
    fun getAndUpdate(id: Int,
                     scheduler: Scheduler = Schedulers.io(),
                     func: (entity: WidgetDataEntity<out WidgetData>?) -> WidgetDataEntity<out WidgetData>?

    ): Completable {
        return getAndUpdate(id = id, scheduler = scheduler, transform = SingleTransformer { entityObservable ->
            entityObservable.map { entity -> func(entity) }
        })
    }


    private fun getAndUpdate(id: Int,
                             scheduler: Scheduler,
                             transform: SingleTransformer<WidgetDataEntity<out WidgetData>?, WidgetDataEntity<out WidgetData>>?
    ): Completable {
        val dbScheduler = Schedulers.from(Executors.newSingleThreadExecutor())

        return database.widgetDao().getWidgetSingle(id)
                .subscribeOn(dbScheduler)
                .map {
                    Log.d("qweee", "map from db")
                    it.firstOrNull()
                            ?.let { widgetEntityMapper.map(it) }
                }

                .observeOn(scheduler)
                .compose(transform)

                .observeOn(dbScheduler)
                .filter { it != null }
                .map {
                    Log.d("qweee", "map to db")
                    widgetEntityMapper.map(it)
                }
                .doOnSuccess {
                    database.widgetDao().updateOrInsert(it)
                }

                .ignoreElement()
    }

}