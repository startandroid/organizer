package ru.startandroid.widgets.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.startandroid.widgets.DB_TABLE_NAMES.WIDGET_CONFIG

@Entity(tableName = WIDGET_CONFIG)
data class WidgetConfigEntityDb(
        @PrimaryKey val id: Int,
        val config: String,
        val enabled: Boolean
)