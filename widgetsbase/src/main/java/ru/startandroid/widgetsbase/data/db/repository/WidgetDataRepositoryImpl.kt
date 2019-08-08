package ru.startandroid.widgetsbase.data.db.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.reactivex.Scheduler
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository


class WidgetDataRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataEntityMapper: WidgetDataEntityMapper
) : WidgetDataRepository {

    override fun getEnabledWidgets(): LiveData<List<WidgetDataEntity>> {
        return Transformations.map(widgetDatabase.widgetDataDao().getAllEnabled()) {
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
                .updateOrInsertSync(widgetDataEntityMapper.toDb(widgetDataEntity))
    }

}