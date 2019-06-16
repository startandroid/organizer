package ru.startandroid.widgets.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgets.DB_TABLE_NAMES.WIDGET_DATA
import ru.startandroid.widgets.db.data.WidgetDataEntityDb

@Dao
interface WidgetDataDao {

    @Query("SELECT * FROM $WIDGET_DATA")
    fun getAll(): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM $WIDGET_DATA WHERE ID = :id")
    fun getById(id: Int): Single<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM $WIDGET_DATA WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetDataEntityDb?

    @Insert
    fun insert(vararg widgetDataEntityDb: WidgetDataEntityDb)

    @Insert
    fun insert(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetDataEntityDb: WidgetDataEntityDb): Long

    @Query("DELETE FROM $WIDGET_DATA")
    fun deleteAll()

}