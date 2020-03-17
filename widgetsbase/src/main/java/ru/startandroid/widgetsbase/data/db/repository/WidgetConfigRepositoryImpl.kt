package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import ru.startandroid.device.analytics.Analytics
import ru.startandroid.device.analytics.WidgetEnableEvent
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityDbToUiMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityUiToDbMapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetConfigEntityUiToDbMapper: WidgetConfigEntityUiToDbMapper,
        private val widgetConfigEntityDbToUiMapper: WidgetConfigEntityDbToUiMapper,
        private val dbScheduler: Scheduler,
        private val analytics: Analytics
) : WidgetConfigRepository {

    override fun getAll(): Flowable<List<WidgetConfigEntity>> =
            widgetDatabase.widgetConfigDao().getAll().map {
                it.mapNotNull {
                    widgetConfigEntityDbToUiMapper.map(it)
                }
            }

    override fun getByIdSync(id: Int): WidgetConfigEntity? =
            widgetConfigEntityDbToUiMapper.map(widgetDatabase.widgetConfigDao().getByIdSync(id))

    override fun updateOrInsertSync(widgetConfigEntity: WidgetConfigEntity): Long =
            widgetDatabase.widgetConfigDao().updateOrInsertSync(widgetConfigEntityUiToDbMapper.map(widgetConfigEntity))

    override fun getById(id: Int): Single<WidgetConfigEntity> =
            widgetDatabase.widgetConfigDao().getById(id).map {
                widgetConfigEntityDbToUiMapper.map(it)
            }

    override fun update(widgetConfigEntity: WidgetConfigEntity): Single<Int> =
            widgetDatabase.widgetConfigDao().update(widgetConfigEntityUiToDbMapper.map(widgetConfigEntity))
                    .subscribeOn(dbScheduler)

    override fun setEnabled(id: Int, enabled: Boolean): Single<Int> {
        analytics.logEvent(WidgetEnableEvent(id, enabled))
        return widgetDatabase.widgetConfigDao().setEnabled(id, enabled)
                .subscribeOn(dbScheduler)
    }

}