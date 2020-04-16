package ru.startandroid.widgetsbase.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import ru.startandroid.widgetsbase.data.DB_MAPPING
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG
import ru.startandroid.widgetsbase.data.DB_TABLE_DATA
import ru.startandroid.widgetsbase.data.DB_TABLE_REFRESH_STATUS
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb
import ru.startandroid.widgetsbase.data.db.model.WidgetDataExtendedEntityDb

@Dao
interface WidgetDataDao {

    @Query("""
        SELECT data.*, refresh.status as refreshStatus
        FROM ${DB_TABLE_DATA.TABLE_NAME} data
        INNER JOIN ${DB_TABLE_CONFIG.TABLE_NAME} config ON data.${DB_TABLE_DATA.COLUMNS.ID} = config.${DB_TABLE_CONFIG.COLUMNS.ID}
        INNER JOIN ${DB_TABLE_REFRESH_STATUS.TABLE_NAME} refresh ON data.${DB_TABLE_DATA.COLUMNS.ID} = refresh.${DB_TABLE_REFRESH_STATUS.COLUMNS.ID}
        WHERE config.${DB_TABLE_CONFIG.COLUMNS.ENABLED} = ${DB_MAPPING.BOOLEAN.TRUE}
        """)
    fun getAllEnabled(): Flowable<List<WidgetDataExtendedEntityDb>>

    @Query("SELECT * FROM ${DB_TABLE_DATA.TABLE_NAME} WHERE ${DB_TABLE_DATA.COLUMNS.ID} = :id")
    fun getByIdSync(id: Int): WidgetDataEntityDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsertSync(widgetDataEntityDb: WidgetDataEntityDb): Long

}