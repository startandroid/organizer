package ru.startandroid.widgets.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.startandroid.widgets.DB_TABLE_NAMES.WIDGET_DATA

@Entity(tableName = WIDGET_DATA)
data class WidgetDataEntityDb(
        @PrimaryKey val id: Int,
        val data: String
)