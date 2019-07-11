package ru.startandroid.widgets.db.dao

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgets.DB_TABLE_NAMES.WIDGET_CONFIG
import ru.startandroid.widgets.db.data.WidgetConfigEntityDb

@Dao
interface WidgetConfigDao {

    @Query("SELECT * FROM $WIDGET_CONFIG WHERE ID = :id")
    fun getById(id: Int): Single<List<WidgetConfigEntityDb>>

    @Query("SELECT * FROM $WIDGET_CONFIG WHERE ID = :id")
    fun getByIdSync(id: Int): WidgetConfigEntityDb?

    @Query("SELECT * FROM $WIDGET_CONFIG")
    fun getAll(): Flowable<List<WidgetConfigEntityDb>>

    @Insert
    fun insert(vararg widgetEntityDb: WidgetConfigEntityDb)

    @Insert
    fun insert(widgetEntityDb: List<WidgetConfigEntityDb>)

    @Update
    fun update(widgetEntityDb: WidgetConfigEntityDb): Single<Int>

}