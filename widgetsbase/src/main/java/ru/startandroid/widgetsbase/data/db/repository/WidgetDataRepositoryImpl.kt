package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository


// TODO pass scheduler into constructor

class WidgetDataRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataEntityMapper: WidgetDataEntityMapper
): WidgetDataRepository {

    override fun getEnabledWidgets(): Flowable<List<WidgetDataEntity>> {
        // TODO get only enabled
        return widgetDatabase.widgetDataDao().getAll()
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
        return widgetDatabase.widgetDataDao().updateOrInsert(widgetDataEntityMapper.toDb(widgetDataEntity))
    }

}