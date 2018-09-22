package ru.startandroid.domain

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WidgetDao {

    @Query("SELECT * FROM widgets")
    fun getAll(): LiveData<List<WidgetEntity>>

}