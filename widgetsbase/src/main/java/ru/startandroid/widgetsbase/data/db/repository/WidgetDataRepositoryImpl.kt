package ru.startandroid.widgetsbase.data.db.repository

import io.reactivex.Flowable
import io.reactivex.Scheduler
import ru.startandroid.domain.mapping.CollectionMapperImpl
import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityDbToUiMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataEntityUiToDbMapper
import ru.startandroid.widgetsbase.data.db.mapper.WidgetDataExtendedEntityDbToUiMapper
import ru.startandroid.widgetsbase.domain.model.WidgetData
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity
import ru.startandroid.widgetsbase.domain.repository.WidgetDataRepository


class WidgetDataRepositoryImpl(
        private val widgetDatabase: WidgetDatabase,
        private val widgetDataEntityUiToDbMapper: WidgetDataEntityUiToDbMapper,
        private val widgetDataEntityDbToUiMapper: WidgetDataEntityDbToUiMapper,
        private val widgetDataExtendedEntityDbToUiMapper: WidgetDataExtendedEntityDbToUiMapper,
        private val dbScheduler: Scheduler
) : WidgetDataRepository {

    override fun getEnabledWidgets(): Flowable<List<WidgetDataEntity>> {
        return widgetDatabase.widgetDataDao().getAllEnabled()
                .subscribeOn(dbScheduler)
                .map {
                    CollectionMapperImpl(widgetDataExtendedEntityDbToUiMapper).map(it)
                }
    }

    override fun getWidgetByIdSync(id: Int): WidgetDataEntity {
        return widgetDataEntityDbToUiMapper.map(widgetDatabase.widgetDataDao().getByIdSync(id))
    }

    override fun updateOrInsertSync(id: Int, widgetData: WidgetData): Long {
        return widgetDatabase.widgetDataDao()
                .updateOrInsertSync(widgetDataEntityUiToDbMapper.map(WidgetDataEntity(id, widgetData)))
    }

}