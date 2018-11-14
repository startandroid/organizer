package ru.startandroid.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface WidgetSettingsDao {

    @Query("SELECT * FROM widget_settings")
    fun getAll(): Flowable<List<WidgetSettingsEntityDb>>

    @Insert
    fun insertAll(widgetEntityDb: List<WidgetSettingsEntityDb>)

}