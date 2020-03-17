package ru.startandroid.widgetsbase.data.db.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityDbToUiMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityUiToDbMapper
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository


class WidgetDataRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataEntityUiToDbMapper: WidgetDataEntityUiToDbMapper,
        private val widgetDataEntityDbToUiMapper: WidgetDataEntityDbToUiMapper
) : WidgetDataRepository {

    override fun getEnabledWidgets(): LiveData<List<WidgetDataEntity>> {
        return Transformations.map(widgetDatabase.widgetDataDao().getAllEnabled()) {
            it.mapNotNull {
                widgetDataEntityDbToUiMapper.map(it)
            }
        }
    }

    override fun getWidgetByIdSync(id: Int): WidgetDataEntity? {
        return widgetDataEntityDbToUiMapper.map(widgetDatabase.widgetDataDao().getByIdSync(id))
    }

    override fun updateOrInsertSync(widgetDataEntity: WidgetDataEntity): Long {
        return widgetDatabase.widgetDataDao()
                .updateOrInsertSync(widgetDataEntityUiToDbMapper.map(widgetDataEntity))
    }

}