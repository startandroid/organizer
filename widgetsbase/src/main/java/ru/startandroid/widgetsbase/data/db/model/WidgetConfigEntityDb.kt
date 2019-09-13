package ru.startandroid.widgetsbase.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG

@Entity(tableName = DB_TABLE_CONFIG.TABLE_NAME)
data class WidgetConfigEntityDb(
        @PrimaryKey
        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.ID)
        val id: Int,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.CONFIG)
        val config: String,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.ENABLED)
        val enabled: Boolean,

        @ColumnInfo(name = DB_TABLE_CONFIG.COLUMNS.UPDATE_INTERVAL)
        val updateInterval: Long

)