package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Single
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetConfigEntityMapper
import ru.startandroid.widgetsbase.domain.model.WidgetConfig
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetConfigRepository

class WidgetConfigRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetConfigEntityMapper: WidgetConfigEntityMapper
) : WidgetConfigRepository {

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


}