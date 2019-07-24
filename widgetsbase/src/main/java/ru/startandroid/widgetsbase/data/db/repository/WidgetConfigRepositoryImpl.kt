package ru.startandroid.widgetsbase.data.db.repository

import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetConfigEntityMapper: WidgetConfigEntityMapper
): WidgetConfigRepository {
    override fun getWidgetByIdSync(id: Int): WidgetConfigEntity? =
        widgetConfigEntityMapper.fromDb(widgetDatabase.widgetConfigDao().getByIdSync(id))

}