package ru.startandroid.widgetsbase.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.startandroid.widgetsbase.data.DB_TABLE_REFRESH_STATUS

@Entity(tableName = DB_TABLE_REFRESH_STATUS.TABLE_NAME)
data class WidgetRefreshStatusEntityDb(
        @PrimaryKey
        @ColumnInfo(name = DB_TABLE_REFRESH_STATUS.COLUMNS.ID)
        val id: Int,

        @ColumnInfo(name = DB_TABLE_REFRESH_STATUS.COLUMNS.STATUS)
        val status: Int
)