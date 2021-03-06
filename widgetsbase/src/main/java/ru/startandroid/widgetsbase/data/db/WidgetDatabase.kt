package ru.startandroid.widgetsbase.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.widgetsbase.data.db.dao.WidgetConfigDao
import ru.startandroid.widgetsbase.data.db.dao.WidgetDataDao
import ru.startandroid.widgetsbase.data.db.dao.WidgetRefreshStatusDao
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.data.db.model.WidgetRefreshStatusEntityDb

@Database(entities = [WidgetDataEntityDb::class, WidgetConfigEntityDb::class, WidgetRefreshStatusEntityDb::class], version = 1)
abstract class WidgetDatabase : RoomDatabase() {
    abstract fun widgetDataDao(): WidgetDataDao
    abstract fun widgetConfigDao(): WidgetConfigDao
    abstract fun widgetRefreshStatusDao(): WidgetRefreshStatusDao
}