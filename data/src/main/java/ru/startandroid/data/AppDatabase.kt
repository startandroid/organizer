package ru.startandroid.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.startandroid.domain.WidgetDao
import ru.startandroid.domain.WidgetEntity


@Database(entities = [WidgetEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun widgetDao(): WidgetDao
}