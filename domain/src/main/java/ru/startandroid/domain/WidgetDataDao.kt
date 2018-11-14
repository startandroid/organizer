package ru.startandroid.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface WidgetDataDao {

    @Query("SELECT * FROM widgets")
    fun getAll(): Flowable<List<WidgetDataEntityDb>>

    @Insert
    fun insert(widgetDataEntityDb: WidgetDataEntityDb)

    @Insert
    fun insertAll(widgetDataEntityDb: List<WidgetDataEntityDb>)

    @Query("DELETE FROM widgets")
    fun deleteAll()

}