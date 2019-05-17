package ru.startandroid.widgets.db.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "widget_settings")
data class WidgetSettingsEntityDb(
        @PrimaryKey val id: Int,
        val settings: String,
        val order: Int,
        val enabled: Boolean
)