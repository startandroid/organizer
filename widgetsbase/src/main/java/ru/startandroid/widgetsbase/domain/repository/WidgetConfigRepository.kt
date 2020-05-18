package ru.startandroid.widgetsbase.domain.repository

import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.widgetsbase.domain.model.WidgetConfigEntity

interface WidgetConfigRepository {

    fun getAll(): Flowable<List<WidgetConfigEntity>>

    fun updateOrInsertSync(widgetConfigEntity: WidgetConfigEntity): Long

    fun getByIdSync(id: Int): WidgetConfigEntity

    fun getById(id: Int): Single<WidgetConfigEntity>

    fun updateSync(widgetConfigEntity: WidgetConfigEntity): Int

    fun setEnabled(id: Int, enabled: Boolean): Single<Int>

}