package ru.startandroid.widgets.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.widgets.db.dao.WidgetConfigDao
import ru.startandroid.widgets.db.dao.WidgetDataDao
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb
import ru.startandroid.widgets.db.data.WidgetDataEntityDb

@Database(entities = [WidgetDataEntityDb::class, WidgetConfigEntityDb::class], version = 1)
abstract class WidgetDatabase : RoomDatabase() {
    abstract fun widgetDataDao(): WidgetDataDao
    abstract fun widgetConfigDao(): WidgetConfigDao
}