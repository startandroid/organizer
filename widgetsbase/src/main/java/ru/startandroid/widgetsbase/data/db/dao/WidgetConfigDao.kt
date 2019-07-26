package ru.startandroid.widgetsbase.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import ru.startandroid.widgetsbase.data.DB_TABLE_CONFIG
import ru.startandroid.widgetsbase.data.db.model.WidgetConfigEntityDb

@Dao
interface WidgetConfigDao {

    @Query("SELECT * FROM ${DB_TABLE_CONFIG.TABLE_NAME} WHERE ID = :id")
    fun getById(id: Int): Single<WidgetConfigEntityDb>

    @Query("SELECT * FROM ${DB_TABLE_CONFIG.TABLE_NAME} WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetConfigEntityDb?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetConfigEntityDb: WidgetConfigEntityDb): Long


    @Query("""
        UPDATE ${DB_TABLE_CONFIG.TABLE_NAME} 
        SET ${DB_TABLE_CONFIG.COLUMNS.CONFIG} = :config, ${DB_TABLE_CONFIG.COLUMNS.ENABLED} = :enabled 
        WHERE ID = :id
        """)
    fun update(id: Int, config: String, enabled: Boolean): Single<Int>


}