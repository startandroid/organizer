package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetConfigEntityMapper: WidgetConfigEntityMapper,
        private val dbScheduler: Scheduler
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
            widgetDatabase.widgetConfigDao().updateOrInsert(widgetConfigEntityMapper.toDb(widgetConfigEntity))

    override fun getById(id: Int): Single<WidgetConfigEntity> =
            widgetDatabase.widgetConfigDao().getById(id).map {
                widgetConfigEntityMapper.fromDb(it)
            }

    override fun update(id: Int, config: WidgetConfig, enabled: Boolean): Single<Int> =
            widgetDatabase.widgetConfigDao().update(id, widgetConfigEntityMapper.configToJson(config), enabled)
                    .subscribeOn(dbScheduler)

    override fun setEnabled(id: Int, enabled: Boolean): Single<Int> =
            widgetDatabase.widgetConfigDao().setEnabled(id, enabled)
                    .subscribeOn(dbScheduler)

}