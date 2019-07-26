package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import io.reactivex.Scheduler
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository


class WidgetDataRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataEntityMapper: WidgetDataEntityMapper,
        private val dbScheduler: Scheduler
) : WidgetDataRepository {

    override fun getEnabledWidgets(): Flowable<List<WidgetDataEntity>> {
        return widgetDatabase.widgetDataDao().getAllEnabled()
                .subscribeOn(dbScheduler)
                .map {
                    it.mapNotNull {
                        widgetDataEntityMapper.fromDb(it)
                    }
                }
    }

    override fun getWidgetByIdSync(id: Int): WidgetDataEntity? {
        return widgetDataEntityMapper.fromDb(widgetDatabase.widgetDataDao().getByIdSync(id))
    }

    override fun updateOrInsertSync(widgetDataEntity: WidgetDataEntity): Long {
        return widgetDatabase.widgetDataDao()
                .updateOrInsert(widgetDataEntityMapper.toDb(widgetDataEntity))
    }

}