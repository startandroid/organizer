package ru.startandroid.widgetsbase.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.widgetsbase.db.dao.WidgetConfigDao
import ru.startandroid.widgetsbase.db.dao.WidgetDataDao
import ru.startandroid.widgetsbase.db.data.WidgetConfigEntityDb
import ru.startandroid.widgetsbase.db.data.WidgetDataEntityDb

@Database(entities = [WidgetDataEntityDb::class, WidgetConfigEntityDb::class], version = 1)
abstract class WidgetDatabase : RoomDatabase() {
    abstract fun widgetDataDao(): WidgetDataDao
    abstract fun widgetConfigDao(): WidgetConfigDao
}