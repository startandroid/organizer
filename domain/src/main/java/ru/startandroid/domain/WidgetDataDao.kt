package ru.startandroid.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface WidgetDataDao {

    @Query("SELECT * FROM widgets")
    fun getAll(): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM widgets WHERE ID = :id")
    fun getWidget(id: Int): Flowable<List<WidgetDataEntityDb>>

    @Query("SELECT * FROM widgets WHERE ID = :id")
    fun getWidgetSingle(id: Int): Single<List<WidgetDataEntityDb>> // TODO Single?


    @Insert
    fun insert(widgetDataEntityDb: WidgetDataEntityDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateOrInsert(widgetDataEntityDb: WidgetDataEntityDb): Long

    @Insert
    fun insertAll(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Query("DELETE FROM widgets")
    fun deleteAll()

}