package ru.startandroid.widgetsbase.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.startandroid.widgetsbase.data.DB_MAPPING
import ru.startandroid.widgetsbase.data.DB_TABLE_REFRESH_STATUS
import ru.startandroid.widgetsbase.data.DB_TABLE_REFRESH_STATUS.TABLE_NAME
import ru.startandroid.widgetsbase.data.db.model.WidgetRefreshStatusEntityDb

@Dao
interface WidgetRefreshStatusDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSync(widgetRefreshStatusEntityDb: WidgetRefreshStatusEntityDb): Long

    @Query("""
        UPDATE $TABLE_NAME 
        SET ${DB_TABLE_REFRESH_STATUS.COLUMNS.STATUS} = ${DB_MAPPING.REFRESH_STATUS.REFRESHING} 
        WHERE ID = :id AND ${DB_TABLE_REFRESH_STATUS.COLUMNS.STATUS} = ${DB_MAPPING.REFRESH_STATUS.DONE}
        """)
    fun acquireRefreshSync(id: Int): Int

    @Query(""" 
        UPDATE $TABLE_NAME
        SET ${DB_TABLE_REFRESH_STATUS.COLUMNS.STATUS} = ${DB_MAPPING.REFRESH_STATUS.DONE}
        WHERE ID = :id
    """)
    fun closeRefreshSync(id: Int): Int
}