package ru.startandroid.widgetsbase.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
import ru.startandroid.widgetsbase.data.DB_MAPPING
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG
import ru.startandroid.widgetsbase.data.DB_TABLE_DATA
import ru.startandroid.widgetsbase.data.db.model.WidgetDataEntityDb

@Dao
interface WidgetDataDao {

    @Query("""
        SELECT data.* 
        FROM ${DB_TABLE_DATA.TABLE_NAME} data
        INNER JOIN ${DB_TABLE_CONFIG.TABLE_NAME} config ON data.${DB_TABLE_DATA.COLUMNS.ID} = config.${DB_TABLE_CONFIG.COLUMNS.ID}
        WHERE config.${DB_TABLE_CONFIG.COLUMNS.ENABLED} = ${DB_MAPPING.BOOLEAN.TRUE}
        """)
    fun getAllEnabled(): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM ${DB_TABLE_DATA.TABLE_NAME} WHERE ${DB_TABLE_DATA.COLUMNS.ID} = :id")
    fun getByIdSync(id: Int): WidgetDataEntityDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsertSync(widgetDataEntityDb: WidgetDataEntityDb): Long

}