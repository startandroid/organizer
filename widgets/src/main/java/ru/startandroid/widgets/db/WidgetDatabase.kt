package ru.startandroid.widgets.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.widgets.db.dao.WidgetDataDao
import ru.startandroid.widgets.db.dao.WidgetSettingsDao
import ru.startandroid.widgets.db.data.WidgetDataEntityDb
import ru.startandroid.widgets.db.data.WidgetSettingsEntityDb

@Database(entities = [WidgetDataEntityDb::class, WidgetSettingsEntityDb::class], version = 1)
abstract class WidgetDatabase : RoomDatabase() {
    abstract fun widgetDao(): WidgetDataDao
    abstract fun widgetSettingsDao(): WidgetSettingsDao
}