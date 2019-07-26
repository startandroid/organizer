package ru.startandroid.widgetsbase.domain.repository

import io.reactivex.Flowable
import ru.startandroid.widgetsbase.domain.model.WidgetDataEntity

interface WidgetDataRepository {

    fun getEnabledWidgets(): Flowable<List<WidgetDataEntity>>

    fun getWidgetByIdSync(id: Int): WidgetDataEntity?

    fun updateOrInsertSync(widgetDataEntity: WidgetDataEntity): Long
}