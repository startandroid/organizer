package ru.startandroid.widgetsbase.data.db.repository

import ru.startandroid.widgetsbase.data.db.WidgetDatabase
import ru.startandroid.widgetsbase.data.db.model.WidgetRefreshStatusEntityDb
import ru.startandroid.widgetsbase.domain.repository.WidgetRefreshStatusRepository
import javax.inject.Inject

class WidgetRefreshStatusRepositoryImpl @Inject constructor(
        private val widgetDatabase: WidgetDatabase
) : WidgetRefreshStatusRepository {

    override fun initSync(widgetId: Int): Long =
            widgetDatabase.widgetRefreshStatusDao().insertSync(WidgetRefreshStatusEntityDb(widgetId, 0))


    override fun acquireRefreshSync(widgetId: Int): Boolean =
            widgetDatabase.widgetRefreshStatusDao().acquireRefreshSync(widgetId) == 1


    override fun closeRefreshSync(widgetId: Int): Boolean =
            widgetDatabase.widgetRefreshStatusDao().closeRefreshSync(widgetId) == 1


}