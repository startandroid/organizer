package ru.startandroid.widgetsbase.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG.COLUMNS
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG.TABLE_NAME
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb

@Dao
interface WidgetConfigDao {

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAll(): Flowable<List<WidgetConfigEntityDb>>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ID = :id")
    fun getById(id: Int): Single<WidgetConfigEntityDb>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetConfigEntityDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetConfigEntityDb: WidgetConfigEntityDb): Long


    @Query("""
        UPDATE ${TABLE_NAME} 
        SET ${COLUMNS.CONFIG} = :config, ${COLUMNS.ENABLED} = :enabled 
        WHERE ID = :id
        """)
    fun update(id: Int, config: String, enabled: Boolean): Single<Int>

    @Query("""
        UPDATE ${TABLE_NAME} 
        SET ${COLUMNS.ENABLED} = :enabled 
        WHERE ID = :id
        """)
    fun setEnabled(id: Int, enabled: Boolean): Single<Int>

}