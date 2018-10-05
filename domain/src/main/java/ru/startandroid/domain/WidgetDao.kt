package ru.startandroid.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable


@Dao
interface WidgetDao {

    @Query("SELECT * FROM widgets")
    fun getAll(): Flowable<List<WidgetEntity>>

    @Query("DELETE FROM widgets")
    fun deleteAll()

    @Insert
    fun insert(widgetEntity: WidgetEntity)

    @Insert
    fun insert(list: List<WidgetEntity>)

}