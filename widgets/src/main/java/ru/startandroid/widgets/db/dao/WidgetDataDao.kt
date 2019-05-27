package ru.startandroid.widgets.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgets.db.data.WidgetDataEntityDb

@Dao
interface WidgetDataDao {

    @Query("SELECT * FROM widgets")
    fun getAll(): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM widgets WHERE ID = :id")
    fun getWidget(id: Int): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM widgets WHERE ID = :id")
    fun getWidgetSingle(id: Int): Single<List<WidgetDataEntityDb>>


    @Insert
    fun insert(vararg widgetDataEntityDb: WidgetDataEntityDb)

    @Insert
    fun insert(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetDataEntityDb: WidgetDataEntityDb): Long

    @Insert
    fun insertAll(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Query("DELETE FROM widgets")
    fun deleteAll()

}