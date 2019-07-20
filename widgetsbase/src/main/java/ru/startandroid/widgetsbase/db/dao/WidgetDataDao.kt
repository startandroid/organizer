package ru.startandroid.widgetsbase.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgetsbase.DB_TABLE_DATA
import ru.startandroid.widgetsbase.db.data.WidgetDataEntityDb

@Dao
interface WidgetDataDao {

    @Query("SELECT * FROM ${DB_TABLE_DATA.TABLE_NAME}")
    fun getAll(): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM ${DB_TABLE_DATA.TABLE_NAME} WHERE ID = :id")
    fun getById(id: Int): Single<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM ${DB_TABLE_DATA.TABLE_NAME} WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetDataEntityDb?

    @Insert
    fun insert(vararg widgetDataEntityDb: WidgetDataEntityDb)

    @Insert
    fun insert(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetDataEntityDb: WidgetDataEntityDb): Long

    @Query("DELETE FROM ${DB_TABLE_DATA.TABLE_NAME}")
    fun deleteAll()

}