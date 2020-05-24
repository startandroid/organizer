package ru.startandroid.widgetsbase.data.db.dao

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG.COLUMNS
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG.TABLE_NAME
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb

@Dao
interface WidgetConfigDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAll(): Flowable<List<WidgetConfigEntityDb>>

    @Query("SELECT * FROM $TABLE_NAME WHERE ID = :id")
    fun getById(id: Int): Single<WidgetConfigEntityDb>

    @Query("SELECT * FROM $TABLE_NAME WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetConfigEntityDb

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsertSync(widgetConfigEntityDb: WidgetConfigEntityDb): Long

    @Update
    fun updateSync(widgetConfigEntityDb: WidgetConfigEntityDb): Int

    @Query("""
        UPDATE $TABLE_NAME 
        SET ${COLUMNS.ENABLED} = :enabled 
        WHERE ID = :id
        """)
    fun setEnabled(id: Int, enabled: Boolean): Single<Int>

}