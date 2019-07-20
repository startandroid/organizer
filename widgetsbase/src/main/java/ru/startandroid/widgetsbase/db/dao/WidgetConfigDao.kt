package ru.startandroid.widgetsbase.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgetsbase.DB_TABLE_CONFIG
import ru.startandroid.widgetsbase.db.data.WidgetConfigEntityDb

@Dao
interface WidgetConfigDao {

    @Query("SELECT * FROM ${DB_TABLE_CONFIG.TABLE_NAME} WHERE ID = :id")
    fun getById(id: Int): Single<WidgetConfigEntityDb>

    @Query("SELECT * FROM ${DB_TABLE_CONFIG.TABLE_NAME} WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetConfigEntityDb?

    @Query("SELECT * FROM ${DB_TABLE_CONFIG.TABLE_NAME}")
    fun getAll(): Flowable<List<WidgetConfigEntityDb>>

    @Insert
    fun insert(vararg widgetEntityDb: WidgetConfigEntityDb)

    @Insert
    fun insert(widgetEntityDb: List<WidgetConfigEntityDb>)

    @Update
    fun update(widgetEntityDb: WidgetConfigEntityDb): Single<Int>

    @Query("""
        UPDATE ${DB_TABLE_CONFIG.TABLE_NAME} 
        SET ${DB_TABLE_CONFIG.COLUMNS.CONFIG} = :config, ${DB_TABLE_CONFIG.COLUMNS.ENABLED} = :enabled 
        WHERE ID = :id
        """)
    fun update(id: Int, config: String, enabled: Boolean): Single<Int>


}