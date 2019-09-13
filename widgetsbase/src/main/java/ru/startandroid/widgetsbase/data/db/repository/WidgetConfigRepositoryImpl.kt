package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.device.analytics.WidgetEnableEvent
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetConfigEntityMapper: WidgetConfigEntityMapper,
        private val dbScheduler: Scheduler,
        private val analytics: Analytics
) : WidgetConfigRepository {

    override fun getAll(): Flowable<List<WidgetConfigEntity>> =
            widgetDatabase.widgetConfigDao().getAll().map {
                it.mapNotNull {
                    widgetConfigEntityMapper.fromDb(it)
                }
            }

    override fun getByIdSync(id: Int): WidgetConfigEntity? =
            widgetConfigEntityMapper.fromDb(widgetDatabase.widgetConfigDao().getByIdSync(id))

    override fun updateOrInsertSync(widgetConfigEntity: WidgetConfigEntity): Long =
            widgetDatabase.widgetConfigDao().updateOrInsertSync(widgetConfigEntityMapper.toDb(widgetConfigEntity))

    override fun getById(id: Int): Single<WidgetConfigEntity> =
            widgetDatabase.widgetConfigDao().getById(id).map {
                widgetConfigEntityMapper.fromDb(it)
            }

    override fun update(widgetConfigEntity: WidgetConfigEntity): Single<Int> =
            widgetDatabase.widgetConfigDao().update(widgetConfigEntityMapper.toDb(widgetConfigEntity))
                    .subscribeOn(dbScheduler)

    override fun setEnabled(id: Int, enabled: Boolean): Single<Int> {
        analytics.logEvent(WidgetEnableEvent(id, enabled))
        return widgetDatabase.widgetConfigDao().setEnabled(id, enabled)
                .subscribeOn(dbScheduler)
    }

}