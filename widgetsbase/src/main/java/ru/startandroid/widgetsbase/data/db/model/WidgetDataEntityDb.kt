package ru.startandroid.widgetsbase.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.startandroid.widgetsbase.data.DB_TABLE_DATA

@Entity(tableName = DB_TABLE_DATA.TABLE_NAME)
data class WidgetDataEntityDb(
        @PrimaryKey
        @ColumnInfo(name = DB_TABLE_DATA.COLUMNS.ID)
        val id: Int,

        @ColumnInfo(name = DB_TABLE_DATA.COLUMNS.DATA)
        val data: String
)