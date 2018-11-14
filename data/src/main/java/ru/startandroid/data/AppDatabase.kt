package ru.startandroid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.domain.WidgetDataDao
import ru.startandroid.domain.WidgetDataEntityDb
import ru.startandroid.domain.WidgetSettingsDao
import ru.startandroid.domain.WidgetSettingsEntityDb


@Database(entities = [WidgetDataEntityDb::class, WidgetSettingsEntityDb::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun widgetDao(): WidgetDataDao
    abstract fun widgetSettingsDao(): WidgetSettingsDao
}